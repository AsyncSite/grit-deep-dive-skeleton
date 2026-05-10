# Grit Deep Dive Skeleton

Grit Deep Dive의 도메인 과제 스켈레톤을 모아두는 레포입니다.

## 이름

정식 레포명은 `grit-deep-dive-skeleton`입니다.

`skeletone`은 오타입니다.

## 브랜치 모델

`main`은 인덱스와 운영 규칙만 보관합니다.

도메인별 과제 스켈레톤은 브랜치로 분리합니다.

| 브랜치 | 도메인 | 상태 |
|--------|--------|------|
| `domain/distributed-lock` | Redis 분산락 | 초안 |
| `domain/cache-consistency` | 캐시 일관성 | 예정 |
| `domain/zset-explosion` | ZSET 폭주 | 예정 |
| `domain/ttl-bomb` | TTL 폭탄 | 예정 |
| `domain/pubsub-to-streams` | Pub/Sub to Streams | 예정 |

## 참여자 사용 흐름

```bash
git clone https://github.com/AsyncSite/grit-deep-dive-skeleton.git
cd grit-deep-dive-skeleton
git switch domain/distributed-lock
```

참여자는 도메인 브랜치를 fork한 뒤 본인 풀이 브랜치에서 PR을 올립니다.

## PR 원칙

PR은 책 내용을 다시 요약하는 자리가 아닙니다.

각 PR은 다음 3섹션만 채웁니다.

1. 가설: 어느 분기에 섰는가
2. 풀이: 스켈레톤 위에 무엇을 추가하거나 바꿨는가
3. 검증: 어떤 부하, 장애, 벤치마크로 확인했는가

## 라이선스

이 레포의 과제 스켈레톤과 운영 문서는 Grit Deep Dive 교육 상품 자산입니다.
별도 라이선스를 명시하기 전까지 무단 재배포를 허용하지 않습니다.
