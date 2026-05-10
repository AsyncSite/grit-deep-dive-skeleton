# 수락 기준

## L1

- `RedisLock.acquire()`가 lock 획득 성공/실패를 구분한다.
- 같은 key에 대해 동시에 두 lease가 발급되지 않는다.
- `RedisLock.release()`가 본인 token일 때만 lock을 지운다.
- `./gradlew test`가 통과한다.

## L2

- lock TTL이 작업 시간보다 짧아질 때 생기는 split-brain성 위험을 설명한다.
- wrong-token release 테스트를 직접 추가하거나 기존 테스트를 확장한다.
- 동시 요청 수를 100 이상으로 올려도 성공 주문이 1건임을 보인다.

## L3

- fencing token 또는 monotonic sequence를 설계하고 적용 위치를 설명한다.
- `WAIT`를 붙였을 때 얻는 것과 얻지 못하는 것을 구분한다.
- Redlock을 채택하지 않는 경우와 채택할 수 있는 경우를 분리해서 쓴다.
