from __future__ import annotations

from concurrent.futures import ThreadPoolExecutor
import os

from redis import Redis

from grit_skeleton.checkout import CheckoutService
from grit_skeleton.inventory import InventoryStore
from grit_skeleton.redis_lock import RedisLock


def main() -> None:
    redis_url = os.getenv("REDIS_URL", "redis://localhost:6379/0")
    client = Redis.from_url(redis_url, decode_responses=True)
    client.flushdb()

    sku = "limited"
    inventory = InventoryStore(client)
    inventory.reset(sku, 1)

    service = CheckoutService(RedisLock(client), inventory)

    with ThreadPoolExecutor(max_workers=50) as executor:
        results = list(
            executor.map(
                lambda _: service.reserve_one(sku, ttl_ms=500, work_delay_ms=10),
                range(100),
            )
        )

    print(
        {
            "success_count": sum(1 for result in results if result),
            "remaining": inventory.remaining(sku),
        }
    )


if __name__ == "__main__":
    main()
