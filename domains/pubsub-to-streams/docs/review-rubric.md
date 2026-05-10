# 리뷰 루브릭

## Correctness

- XADD/read/ack 흐름이 이어지는가
- ack 전 pending이 관찰되는가
- ack 후 pending이 줄어드는가

## Redis 이해

- Pub/Sub과 Streams의 전달 보장 차이를 설명하는가
- consumer group과 consumer의 역할을 구분하는가
- pending entries list를 이해하는가

## 운영 판단

- at-least-once로 인한 중복 처리 전략을 제시하는가
- Redis Streams와 Kafka의 경계를 설명하는가
- 재처리, DLQ, trimming을 운영 관점에서 다루는가

## PR 품질

- 가설, 풀이, 검증 3섹션이 모두 채워졌는가
- 책 본문 요약이 아니라 본인 구현 판단을 적었는가
- 테스트 결과가 붙어 있는가
