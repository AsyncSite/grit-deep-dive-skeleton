# Redis 캐시 일관성 스켈레톤

## 시나리오

상품 가격이 DB에 쓰인 뒤 Redis cache invalidation이 늦거나 누락되어 사용자가 오래된 가격을 읽습니다.

요구사항:

- cache miss 시 primary store에서 읽고 Redis에 채웁니다.
- primary store update 후 Redis cache를 명시적으로 무효화합니다.
- 업데이트 이후 같은 상품을 다시 읽으면 새 version과 price가 보여야 합니다.
- stale read가 허용되는 업무와 허용되지 않는 업무를 PR에서 구분합니다.

## 구현 대상

`RedisProductCache`를 구현합니다.

기본 방향:

- read: Redis value를 `version:price` 형태로 읽어 `ProductSnapshot` 복원
- write: TTL이 있는 cache entry 저장
- evict: write 후 해당 상품 cache 삭제

## 실행

```bash
docker compose up -d redis
./gradlew test
```

처음에는 `UnsupportedOperationException`으로 실패합니다.

## 책 좌표

- 4장: redis.conf와 cache 정책
- 10장: 복제 지연과 consistency
- 14장: MSA에서 Redis를 쓰는 다섯 자리
