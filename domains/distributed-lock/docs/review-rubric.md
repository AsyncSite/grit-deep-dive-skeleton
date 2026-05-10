# 리뷰 루브릭

## Correctness

- lock acquire가 원자적으로 동작하는가
- release가 token을 검증하는가
- 재고 차감이 lock 보호 구간 안에 있는가
- 테스트가 race를 충분히 흔드는가

## Redis 이해

- `SET NX PX`를 한 명령으로 사용하는가
- Lua release의 필요성을 설명하는가
- TTL을 안전장치로만 보고 정합성 보장으로 오해하지 않는가
- 단일 Redis lock과 Redlock의 경계를 설명하는가

## 운영 판단

- 이 시나리오에서 Redis lock이 적합한 이유를 말하는가
- DB unique constraint, queue, pessimistic lock 같은 대안을 비교했는가
- 장애 시나리오를 하나 이상 적었는가

## PR 품질

- 가설, 풀이, 검증 3섹션이 모두 채워졌는가
- 책 본문 요약이 아니라 본인 구현 판단을 적었는가
- 측정값이나 테스트 결과가 붙어 있는가
