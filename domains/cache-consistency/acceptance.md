# 수락 기준

## L1

- cache miss 시 primary store를 읽고 Redis cache를 채운다.
- primary update 이후 해당 상품 cache를 evict한다.
- `./gradlew test`가 통과한다.

## L2

- 동일 상품을 여러 번 읽을 때 두 번째 읽기는 cache hit가 된다.
- update 직후 읽기에서 새 version이 보인다.
- TTL과 explicit invalidation의 역할 차이를 PR에 적는다.

## L3

- invalidation 실패 또는 지연 시 보상 전략을 설명한다.
- Redis Pub/Sub, Streams, outbox 중 어떤 방식이 필요한지 분기한다.
- stale read가 허용되는 화면과 허용되지 않는 트랜잭션을 구분한다.
