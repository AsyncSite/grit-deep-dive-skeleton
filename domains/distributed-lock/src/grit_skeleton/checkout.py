from __future__ import annotations

from grit_skeleton.inventory import InventoryStore
from grit_skeleton.redis_lock import RedisLock


class CheckoutService:
    def __init__(self, lock: RedisLock, inventory: InventoryStore) -> None:
        self.lock = lock
        self.inventory = inventory

    def reserve_one(self, sku: str, ttl_ms: int = 500, work_delay_ms: int = 0) -> bool:
        lease = self.lock.acquire(f"inventory:{sku}", ttl_ms=ttl_ms)
        if lease is None:
            return False

        try:
            return self.inventory.reserve_one_unsafe(sku, work_delay_ms=work_delay_ms)
        finally:
            self.lock.release(lease)
