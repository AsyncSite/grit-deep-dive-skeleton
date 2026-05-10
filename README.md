# Grit Deep Dive Skeleton · Cache Consistency

현재 브랜치: `domain/cache-consistency`

DB write와 Redis cache invalidation 사이의 stale read를 다루는 Spring Boot 과제입니다.

## 빠른 시작

```bash
docker compose up -d redis
./gradlew test
```

처음 테스트는 실패합니다. 실패가 과제의 출발점입니다.

구현 대상은 `src/main/java/com/teamgrit/deepdive/skeleton/adapter/redis/RedisProductCache.java`입니다.

## 과제 문서

- [문제 정의](domains/cache-consistency/problem.md)
- [수락 기준](domains/cache-consistency/acceptance.md)
- [리뷰 루브릭](domains/cache-consistency/docs/review-rubric.md)

## PR 원칙

PR은 책 내용을 다시 요약하는 자리가 아닙니다.

각 PR은 다음 3섹션만 채웁니다.

1. 가설: 어느 분기에 섰는가
2. 풀이: 스켈레톤 위에 무엇을 추가하거나 바꿨는가
3. 검증: 어떤 부하, 장애, 벤치마크로 확인했는가

## 라이선스

이 레포의 과제 스켈레톤과 운영 문서는 Grit Deep Dive 교육 상품 자산입니다.
별도 라이선스를 명시하기 전까지 무단 재배포를 허용하지 않습니다.
