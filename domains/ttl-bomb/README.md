# Redis TTL 폭탄 스켈레톤

## 시나리오

프로모션 상품 cache 10,000개가 정확히 12:00에 동시에 만료됩니다.

만료 순간 cache miss가 한꺼번에 발생하고 DB QPS가 치솟습니다.

## 구현 대상

`JitteredTtlPolicy`를 구현합니다.

기본 방향:

- base TTL을 그대로 쓰지 않습니다.
- key별로 만료 시각이 퍼지도록 jitter를 적용합니다.
- 같은 key는 같은 배포 사이클에서 예측 가능한 TTL을 받도록 deterministic jitter를 우선 검토합니다.

## 실행

```bash
docker compose up -d redis
./gradlew test
```

처음에는 `UnsupportedOperationException`으로 실패합니다.

## 책 좌표

- 8장: active expiration
- 14장: MSA cache 적용 자리
- 16장: Redis를 쓰지 말아야 하는 경계
