## 1. 가설 — 어느 분기에 섰는가

<!-- 예: Pub/Sub 대신 Streams consumer group을 쓴다. at-least-once를 받고 중복 처리는 consumer idempotency로 넘긴다. -->

## 2. 풀이 — 스켈레톤 위에 무엇을 추가/변경했는가

<!-- 핵심 diff와 설계 판단을 1~2단락으로 적는다. 책 본문 요약 금지. -->

## 3. 검증 — 어떻게 동작했는가

<!-- append/read/ack/pending 결과, consumer 재시작 시나리오를 적는다. -->

### 체크리스트

- [ ] `./gradlew test` 통과
- [ ] consumer group으로 메시지를 읽음
- [ ] ack 전 메시지가 pending에 남음
- [ ] ack 후 pending count가 줄어듦
- [ ] Pub/Sub, Streams, Kafka의 경계를 설명함
