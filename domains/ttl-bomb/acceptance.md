# 수락 기준

## L1

- 같은 base TTL이 key별로 여러 TTL 값으로 분산된다.
- jitter 결과가 하한/상한을 벗어나지 않는다.
- `./gradlew test`가 통과한다.

## L2

- Redis에 쓴 key들의 TTL 분포를 확인한다.
- random jitter와 deterministic jitter의 차이를 설명한다.
- base TTL의 80~120% 같은 정책 범위를 PR에 명시한다.

## L3

- refresh-ahead 또는 single-flight가 필요한 경계를 설명한다.
- active expiration과 lazy expiration의 차이를 연결한다.
- TTL 폭탄을 메트릭으로 탐지하는 방법을 제안한다.
