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

### `domain/cache-consistency`

DB write와 Redis cache invalidation 사이의 stale read를 다룹니다.

핵심 학습 좌표:

- Cache-aside read path
- Write 후 explicit invalidation
- TTL과 invalidation의 역할 분리
- 복제 지연과 stale read 허용 범위
- Book+Class 4장 redis.conf, 10장 복제, 14장 MSA cache 자리

### `domain/zset-explosion`

랭킹 서비스에서 ZSET 메모리와 p99 latency가 폭증하는 상황을 다룹니다.

핵심 학습 좌표:

- Redis ZSET score update
- Top-window trimming
- 전체 정확 순위와 bounded memory trade-off
- Skiplist, listpack, encoding 전환
- Book+Class 5장 Skiplist, 7장 Listpack, 16장 의사결정 플레이북

### `domain/ttl-bomb`

동일 시각 만료되는 대량 key로 DB QPS가 치솟는 상황을 다룹니다.

핵심 학습 좌표:

- TTL jitter
- Active expiration과 lazy expiration
- Cache miss burst
- Refresh-ahead, single-flight 확장 경계
- Book+Class 8장 active expiration, 14장 MSA cache 자리

### `domain/pubsub-to-streams`

Pub/Sub disconnect로 유실되는 메시지를 Streams 기반 처리로 바꾸는 상황을 다룹니다.

핵심 학습 좌표:

- Pub/Sub의 연결 중 전달 특성
- Redis Streams `XADD`, consumer group, `XACK`
- Pending entries list
- At-least-once와 idempotent consumer
- Book+Class 12장 Streams, 16장 Redis와 Kafka의 경계
