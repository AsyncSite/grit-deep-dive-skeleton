# Domain Branch Index

## Active

### `domain/distributed-lock`

동시 결제 상황에서 Redis 기반 분산락으로 재고 1개를 안전하게 차감하는 과제입니다.

핵심 학습 좌표:

- Redis `SET key value NX PX ttl`
- Lua 기반 안전한 release
- lock TTL과 작업 시간의 충돌
- Redlock 논쟁과 fencing token의 필요성
- Book+Class 8장 Lua atomicity, 12장 CAP/WAIT, 16장 의사결정 플레이북

## Planned

### `domain/cache-consistency`

DB write와 Redis cache invalidation 사이의 stale read를 다룹니다.

### `domain/zset-explosion`

랭킹 서비스에서 ZSET 메모리와 p99 latency가 폭증하는 상황을 다룹니다.

### `domain/ttl-bomb`

동일 시각 만료되는 대량 key로 DB QPS가 치솟는 상황을 다룹니다.

### `domain/pubsub-to-streams`

Pub/Sub disconnect로 유실되는 메시지를 Streams 기반 처리로 바꾸는 상황을 다룹니다.
