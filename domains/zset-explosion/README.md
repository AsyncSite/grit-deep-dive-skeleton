# Redis ZSET 폭주 스켈레톤

## 시나리오

랭킹 서비스가 사용자 1M명의 점수를 하나의 ZSET에 계속 쌓습니다.

처음에는 단순합니다. `ZADD`, `ZREVRANGE`로 충분해 보입니다. 시간이 지나면 메모리 사용량과 p99 latency가 같이 자랍니다.

## 구현 대상

`RedisRankingBoard`를 구현합니다.

기본 방향:

- `recordScore`: 점수 갱신 후 top-window 상한을 유지
- `top`: 높은 score부터 N개 조회
- `size`: 현재 ZSET cardinality 조회
- top-window 상한은 `RankingService` 생성자에서 받음

## 실행

```bash
docker compose up -d redis
./gradlew test
```

처음에는 `UnsupportedOperationException`으로 실패합니다.

## 책 좌표

- 5장: Skiplist
- 7장: Listpack과 encoding 전환
- 16장: Redis를 안 쓰는 자리
