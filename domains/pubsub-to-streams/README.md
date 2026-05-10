# Redis Pub/Sub to Streams 스켈레톤

## 시나리오

Pub/Sub subscriber가 재시작하는 순간 발행된 메시지가 유실됩니다.

운영 요구사항이 at-least-once 전달과 재처리라면 Pub/Sub는 맞지 않습니다. Redis Streams consumer group으로 바꿔야 합니다.

## 구현 대상

`RedisReliableEventStream`을 구현합니다.

기본 방향:

- `append`: `XADD`
- `createGroupIfAbsent`: `XGROUP CREATE`
- `readNew`: consumer group으로 새 메시지 읽기
- `ack`: 처리 완료 후 `XACK`
- `pendingCount`: ack 전 메시지 수 확인

## 실행

```bash
docker compose up -d redis
./gradlew test
```

처음에는 `UnsupportedOperationException`으로 실패합니다.

## 책 좌표

- 12장: CAP과 Streams
- 14장: MSA queue/cache 자리
- 16장: Redis Streams와 Kafka의 경계
