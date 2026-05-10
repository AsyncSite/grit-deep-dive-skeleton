from __future__ import annotations

import time

from redis import Redis


class InventoryStore:
    def __init__(self, client: Redis, prefix: str = "inventory") -> None:
        self.client = client
        self.prefix = prefix

    def reset(self, sku: str, quantity: int) -> None:
        self.client.set(self._key(sku), quantity)

    def remaining(self, sku: str) -> int:
        raw = self.client.get(self._key(sku))
        return int(raw or 0)

    def reserve_one_unsafe(self, sku: str, work_delay_ms: int = 0) -> bool:
        """Intentionally non-atomic inventory update.

        The assignment is to protect this section with RedisLock. Do not replace
        this method with DECR in the first solution, or the lock problem
        disappears.
        """
        key = self._key(sku)
        current = int(self.client.get(key) or 0)
        if current <= 0:
            return False

        if work_delay_ms > 0:
            time.sleep(work_delay_ms / 1000)

        self.client.set(key, current - 1)
        return True

    def _key(self, sku: str) -> str:
        return f"{self.prefix}:{sku}"
