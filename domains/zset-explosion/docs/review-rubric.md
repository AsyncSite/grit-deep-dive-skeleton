# 리뷰 루브릭

## Correctness

- 높은 score가 먼저 반환되는가
- member update가 idempotent한가
- ZSET cardinality 상한이 유지되는가

## Redis 이해

- ZSET이 dict + skiplist 조합임을 이해하는가
- top-N 조회와 전체 순위 조회의 비용 차이를 설명하는가
- trimming 명령의 rank 방향을 틀리지 않았는가

## 운영 판단

- 전체 랭킹 정확도 포기를 명시했는가
- 서비스 화면 요구사항과 자료구조 결정을 연결했는가
- 메모리 폭주 관측 지표를 제시했는가

## PR 품질

- 가설, 풀이, 검증 3섹션이 모두 채워졌는가
- 책 본문 요약이 아니라 본인 구현 판단을 적었는가
- 테스트 결과가 붙어 있는가
