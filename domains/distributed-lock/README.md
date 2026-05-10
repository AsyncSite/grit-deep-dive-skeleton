# Redis 분산락 스켈레톤

## 시나리오

동시 결제 1000 TPS 상황에서 재고 1개 상품에 100명이 동시에 접근합니다.

요구사항은 단순합니다.

- 성공 주문은 정확히 1건이어야 합니다.
- 실패 주문은 재고 부족으로 종료되어야 합니다.
- lock을 잡은 요청만 재고를 차감해야 합니다.
- lock release는 반드시 token을 검증해야 합니다.
- lock TTL이 작업 시간보다 짧아지는 위험을 설명해야 합니다.

## 구현 대상

`src/main/java/com/teamgrit/deepdive/skeleton/adapter/redis/RedisDistributedLock.java`의 `acquire`와 `release`를 구현합니다.

기본 방향:

- acquire: `SET key token NX PX ttl_ms`
- release: Lua script로 token 비교 후 `DEL`
- token: 요청마다 고유한 값
- 실패 시 busy wait 금지. 테스트에서는 즉시 실패해도 됩니다.

## 실행

```bash
docker compose up -d redis
./gradlew test
```

처음에는 `UnsupportedOperationException`으로 실패합니다.

## 책 좌표

- 8장: Lua atomicity
- 12장: CAP, WAIT, Redlock 논쟁
- 16장: Redis를 쓰는 자리와 쓰지 않는 자리
