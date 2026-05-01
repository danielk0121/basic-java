# todo 32: OkHttp 라이브러리 설정

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)

## 목적

- HTTP 클라이언트의 보편적 설정을 OkHttp로 학습한다.
- 커넥션 풀, keep-alive, 소켓 재사용, 타임아웃, 인터셉터 등.

## 학습 포인트

### 1. 커넥션 풀 (ConnectionPool)
- `new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit)`
- 기본값: 5 idle / 5분 keepAlive
- `OkHttpClient.Builder().connectionPool(pool)`로 주입
- 통계 조회: `pool.connectionCount()`, `pool.idleConnectionCount()`

### 2. Keep-Alive 옵션
- HTTP/1.1 기본 keep-alive 활성화 (별도 설정 불필요)
- `Connection: close` 헤더로 비활성화 가능
- `keepAliveDuration`: 유휴 커넥션을 풀에 보관할 시간

### 3. 소켓 재사용
- 같은 호스트에 대해 풀의 idle 커넥션을 자동 재사용
- `EventListener`로 `connectionAcquired` / `connectionReleased` 이벤트 관찰
- HTTP/2 멀티플렉싱: 단일 커넥션으로 동시 다중 요청

### 4. 타임아웃
- `connectTimeout`: TCP 연결 수립 제한
- `readTimeout`: 응답 바이트 수신 간격 제한
- `writeTimeout`: 요청 바이트 송신 간격 제한
- `callTimeout`: 전체 호출(연결~응답 완료) 제한

### 5. 인터셉터
- Application Interceptor: 재시도/리다이렉트 이전, 한 번 실행
- Network Interceptor: 실제 네트워크 요청마다 실행 (재시도 포함)
- 로깅, 인증 헤더 추가, 메트릭 수집 등

### 6. 재시도 / 실패 복구
- `retryOnConnectionFailure(true)` (기본 활성)
- DNS 다중 응답 시 자동 fallback

### 7. 디스패처 (Dispatcher)
- 비동기 요청의 동시성 제어
- `maxRequests`, `maxRequestsPerHost` 설정

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/OkHttpConfigTest.java`
