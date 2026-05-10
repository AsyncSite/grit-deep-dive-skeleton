from __future__ import annotations

from concurrent.futures import ThreadPoolExecutor
import os

import pytest
from redis import Redis
from redis.exceptions import RedisError

from grit_skeleton.checkout import CheckoutService
from grit_skeleton.inventory import InventoryStore
from grit_skeleton.redis_lock import LockLease, RedisLock


@pytest.fixture()
def redis_client() -> Redis:
    client = Redis.from_url(
        os.getenv("REDIS_URL", "redis://localhost:6379/0"),
        decode_responses=True,
    )
    try:
        client.ping()
    except RedisError as exc:
        pytest.skip(f"Redis is not available: {exc}")

    client.flushdb()
    return client


def test_lock_acquire_blocks_second_owner(redis_client: Redis) -> None:
    lock = RedisLock(redis_client)

    lease = lock.acquire("inventory:limited", ttl_ms=1000)

    assert lease is not None
    assert lock.acquire("inventory:limited", ttl_ms=1000) is None


def test_release_must_check_token(redis_client: Redis) -> None:
    lock = RedisLock(redis_client)
    lease = lock.acquire("inventory:limited", ttl_ms=1000)
    assert lease is not None

    wrong_lease = LockLease(
        key=lease.key,
        token="wrong-token",
        ttl_ms=lease.ttl_ms,
        acquired_at_ms=lease.acquired_at_ms,
    )

    assert lock.release(wrong_lease) is False
    assert redis_client.exists(lease.key) == 1
    assert lock.release(lease) is True
    assert redis_client.exists(lease.key) == 0


def test_checkout_allows_exactly_one_success(redis_client: Redis) -> None:
    sku = "limited"
    inventory = InventoryStore(redis_client)
    inventory.reset(sku, 1)

    service = CheckoutService(RedisLock(redis_client), inventory)

    with ThreadPoolExecutor(max_workers=50) as executor:
        results = list(
            executor.map(
                lambda _: service.reserve_one(sku, ttl_ms=500, work_delay_ms=10),
                range(100),
            )
        )

    assert sum(1 for result in results if result) == 1
    assert inventory.remaining(sku) == 0
