# 문제 정의

## 배경

TTL은 cache freshness를 관리하는 간단한 도구입니다. 하지만 대량 key에 같은 TTL을 넣으면 같은 시각에 만료됩니다.

동일 시각 만료는 cache miss를 한꺼번에 만들고, 그 부하는 DB로 떨어집니다.

## 목표

TTL jitter를 적용해 cache 만료 시각을 분산합니다.

## 제약

- Redis 단일 인스턴스를 사용합니다.
- stale-while-revalidate 구현은 선택입니다.
- 회사 코드, 회사 데이터, 실제 트래픽 로그를 사용하지 않습니다.
- 구현 언어는 Java 25, 프레임워크는 Spring Boot로 고정합니다.

## 금지

- 모든 key에 같은 TTL을 넣는 구현
- TTL 하한/상한 없이 무작위로 흩뿌리는 구현
- `Thread.sleep()`으로 테스트 타이밍만 맞추는 구현
- 테스트만 통과시키는 hard-code

## 제출물

- 구현 코드
- 테스트 결과
- PR 본문 3섹션
- jitter 폭을 어떻게 정했는지에 대한 설명
