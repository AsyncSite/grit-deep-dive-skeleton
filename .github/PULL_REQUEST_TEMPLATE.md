## 1. 가설 — 어느 분기에 섰는가

<!-- 예: write 후 cache evict를 먼저 적용했다. write-through는 이번 시나리오에서 제외했다. 이유는... -->

## 2. 풀이 — 스켈레톤 위에 무엇을 추가/변경했는가

<!-- 핵심 diff와 설계 판단을 1~2단락으로 적는다. 책 본문 요약 금지. -->

## 3. 검증 — 어떻게 동작했는가

<!-- 테스트, stale read 재현, 지연 시뮬레이션, 측정값을 적는다. -->

### 체크리스트

- [ ] `./gradlew test` 통과
- [ ] write 후 stale cache가 남지 않음
- [ ] cache miss 시 primary store에서 읽고 Redis에 다시 채움
- [ ] cache-aside, write-through, explicit invalidation 중 채택 이유를 적음
- [ ] invalidation lag가 허용되는 업무와 허용되지 않는 업무를 구분함
