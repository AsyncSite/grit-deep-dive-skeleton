# 리뷰 루브릭

## Correctness

- write 후 cache evict가 보장되는가
- cache miss와 cache hit 경로가 분리되어 있는가
- version이 바뀐 상품을 오래된 cache로 덮어쓰지 않는가

## Redis 이해

- Redis를 source of truth로 오해하지 않는가
- TTL과 invalidation의 역할을 구분하는가
- cache key naming이 명확한가

## 운영 판단

- stale read 허용 범위를 업무 기준으로 설명하는가
- invalidation 실패 시 탐지/복구 방법을 제시하는가
- write-through, write-behind, cache-aside의 경계를 설명하는가

## PR 품질

- 가설, 풀이, 검증 3섹션이 모두 채워졌는가
- 책 본문 요약이 아니라 본인 구현 판단을 적었는가
- 테스트 결과가 붙어 있는가
