## 1. 가설 — 어느 분기에 섰는가

<!-- 예: base TTL 60초에 deterministic jitter를 넣어 만료 시각을 분산한다. 이유는... -->

## 2. 풀이 — 스켈레톤 위에 무엇을 추가/변경했는가

<!-- 핵심 diff와 설계 판단을 1~2단락으로 적는다. 책 본문 요약 금지. -->

## 3. 검증 — 어떻게 동작했는가

<!-- TTL 분포, distinct TTL 수, Redis TTL 관찰값, 재현 결과를 적는다. -->

### 체크리스트

- [ ] `./gradlew test` 통과
- [ ] 동일 base TTL이 여러 만료 시각으로 분산됨
- [ ] TTL 하한/상한을 벗어나지 않음
- [ ] random jitter와 deterministic jitter 중 채택 이유를 적음
- [ ] refresh-ahead 또는 single-flight가 필요한 경계를 설명함
