# Grit Deep Dive Skeleton · Distributed Lock

현재 브랜치: `domain/distributed-lock`

Spring Boot와 Redis로 분산락을 구현해 재고 1개를 안전하게 차감하는 과제입니다.

## 빠른 시작

```bash
docker compose up -d redis
./gradlew test
```

처음 테스트는 실패합니다. 실패가 과제의 출발점입니다.

구현 대상은 `src/main/java/com/teamgrit/deepdive/skeleton/adapter/redis/RedisDistributedLock.java`입니다.

## 과제 문서

- [문제 정의](domains/distributed-lock/problem.md)
- [수락 기준](domains/distributed-lock/acceptance.md)
- [리뷰 루브릭](domains/distributed-lock/docs/review-rubric.md)

## PR 제출

PR은 `.github/PULL_REQUEST_TEMPLATE.md`의 3섹션만 채웁니다.

책 내용을 다시 작문하지 않습니다. 가설, 풀이, 검증만 남깁니다.

## 라이선스

이 레포의 과제 스켈레톤과 운영 문서는 Grit Deep Dive 교육 상품 자산입니다.
별도 라이선스를 명시하기 전까지 무단 재배포를 허용하지 않습니다.
