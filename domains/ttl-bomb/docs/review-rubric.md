# 리뷰 루브릭

## Correctness

- TTL이 실제로 분산되는가
- TTL 하한/상한이 유지되는가
- 같은 key에 대한 정책이 예측 가능한가

## Redis 이해

- expire가 정확한 시각의 삭제 보장이 아님을 이해하는가
- active expiration과 lazy expiration을 구분하는가
- TTL 분산이 DB QPS를 어떻게 낮추는지 설명하는가

## 운영 판단

- jitter 폭 선택 근거가 있는가
- TTL만으로 부족한 경우를 설명하는가
- refresh-ahead, request coalescing, lock의 경계를 구분하는가

## PR 품질

- 가설, 풀이, 검증 3섹션이 모두 채워졌는가
- 책 본문 요약이 아니라 본인 구현 판단을 적었는가
- 테스트 결과가 붙어 있는가
