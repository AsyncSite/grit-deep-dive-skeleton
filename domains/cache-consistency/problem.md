# 문제 정의

## 배경

상품 가격 원본은 primary store에 있습니다. Redis는 조회 latency를 줄이기 위한 cache입니다.

문제는 write 이후입니다. 원본 가격은 바뀌었는데 Redis에 오래된 값이 남아 있으면 사용자는 stale price를 봅니다. 결제·재고·쿠폰 같은 영역에서는 이 stale read가 곧 장애가 됩니다.

## 목표

cache-aside 구조에서 update 이후 stale cache가 남지 않게 만듭니다.

## 제약

- primary store는 과제 단순화를 위해 in-memory store입니다.
- Redis는 cache 계층으로만 사용합니다.
- 회사 코드, 회사 데이터, 실제 결제 API를 사용하지 않습니다.
- 구현 언어는 Java 21, 프레임워크는 Spring Boot로 고정합니다.

## 금지

- update 후 cache를 그대로 두는 구현
- TTL만 믿고 stale read를 방치하는 구현
- 테스트만 통과시키는 hard-code
- application service가 `StringRedisTemplate`을 직접 다루는 구조

## 제출물

- 구현 코드
- 테스트 결과
- PR 본문 3섹션
- cache-aside와 write-through 중 무엇을 채택했는지에 대한 설명
