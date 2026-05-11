# 문제 정의

## 배경

실시간 랭킹은 Redis ZSET의 대표 사용처입니다. 하지만 전체 사용자 랭킹을 단일 ZSET에 무제한으로 쌓으면 메모리와 조회 비용이 계속 자랍니다.

## 목표

상위 N명만 필요한 화면이라는 제약을 활용해 bounded top-window 랭킹을 구현합니다.

## 제약

- Redis 단일 인스턴스를 사용합니다.
- 전체 정확 순위가 아니라 top-window 정확도를 우선합니다.
- 회사 코드, 회사 데이터, 실제 사용자 ID를 사용하지 않습니다.
- 구현 언어는 Java 25, 프레임워크는 Spring Boot로 고정합니다.

## 금지

- 모든 사용자를 무제한으로 ZSET에 남기는 구현
- Java memory에서 정렬한 뒤 Redis를 단순 저장소로만 쓰는 구현
- score update 시 기존 member 중복을 방치하는 구현
- 테스트만 통과시키는 hard-code

## 제출물

- 구현 코드
- 테스트 결과
- PR 본문 3섹션
- 전체 랭킹 정확도와 top-window bounded memory 사이의 trade-off 설명
