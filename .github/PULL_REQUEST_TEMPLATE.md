## 1. 가설 — 어느 분기에 섰는가

<!-- 예: 전체 랭킹 ZSET은 유지하지 않고 top-window만 보관한다. 이유는... -->

## 2. 풀이 — 스켈레톤 위에 무엇을 추가/변경했는가

<!-- 핵심 diff와 설계 판단을 1~2단락으로 적는다. 책 본문 요약 금지. -->

## 3. 검증 — 어떻게 동작했는가

<!-- 데이터 수, ZSET size, top N 결과, 메모리/latency 관찰값을 적는다. -->

### 체크리스트

- [ ] `./gradlew test` 통과
- [ ] score update 후 descending ranking이 맞음
- [ ] top-window size가 상한을 넘지 않음
- [ ] 정확한 전체 순위와 bounded top-N 사이의 trade-off를 적음
- [ ] sharding 또는 time-bucket이 필요한 경계를 설명함
