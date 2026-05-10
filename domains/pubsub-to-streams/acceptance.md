# 수락 기준

## L1

- 메시지를 stream에 append할 수 있다.
- consumer group으로 메시지를 읽을 수 있다.
- ack 전 메시지가 pending에 남는다.
- ack 후 pending count가 줄어든다.
- `./gradlew test`가 통과한다.

## L2

- consumer 이름을 바꿔도 group pending 상태를 설명할 수 있다.
- at-least-once와 exactly-once의 차이를 PR에 적는다.
- idempotent consumer가 왜 필요한지 설명한다.

## L3

- `XAUTOCLAIM` 또는 dead-letter stream이 필요한 경계를 제시한다.
- Redis Streams와 Kafka의 보존, 재처리, backpressure 차이를 분리한다.
- stream length trimming 전략을 제안한다.
