# 수락 기준

## L1

- score update 후 `top(N)`이 score 내림차순으로 반환된다.
- top-window size가 상한을 넘지 않는다.
- `./gradlew test`가 통과한다.

## L2

- 같은 member score가 갱신될 때 중복 없이 최신 score만 반영된다.
- window 밖 사용자는 잘려 나간다.
- `ZREMRANGEBYRANK` 또는 동등한 trimming 전략을 PR에 설명한다.

## L3

- 전체 정확 순위가 필요한 경우의 대안을 설명한다.
- daily/monthly time-bucket 또는 sharding이 필요한 경계를 적는다.
- `OBJECT ENCODING`, `MEMORY USAGE`를 관찰하는 확장 테스트를 제안한다.
