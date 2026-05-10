from __future__ import annotations

from dataclasses import dataclass
import secrets
import time

from redis import Redis


@dataclass(frozen=True)
class LockLease:
    key: str
    token: str
    ttl_ms: int
    acquired_at_ms: int


class RedisLock:
    def __init__(self, client: Redis, prefix: str = "lock") -> None:
        self.client = client
        self.prefix = prefix

    def acquire(self, name: str, ttl_ms: int) -> LockLease | None:
        """Acquire a lock lease.

        TODO:
        - Generate a unique token per attempt.
        - Use one Redis command: SET key token NX PX ttl_ms.
        - Return LockLease on success.
        - Return None when another owner already holds the lock.
        """
        token = secrets.token_urlsafe(24)
        key = self._key(name)
        acquired_at_ms = int(time.time() * 1000)

        raise NotImplementedError(
            f"implement SET {key} {token} NX PX {ttl_ms}; acquired_at={acquired_at_ms}"
        )

    def release(self, lease: LockLease) -> bool:
        """Release a lock lease only when the token matches.

        TODO:
        - Use Lua so GET + DEL is atomic.
        - Return True only when the lock was deleted.
        - Return False when the token does not match or the key already expired.
        """
        raise NotImplementedError("implement token-checked Lua release")

    def _key(self, name: str) -> str:
        return f"{self.prefix}:{name}"
