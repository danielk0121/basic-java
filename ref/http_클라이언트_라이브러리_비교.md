# HTTP 클라이언트 라이브러리 비교

> 작성일: 2026-04-27
> 대상: Spring Boot 3.x / Java 17 환경에서의 HTTP 클라이언트 라이브러리 선택 가이드

---

## 비교 대상

- RestTemplate (Spring)
- RestClient (Spring 6.1+, Spring Boot 3.2+)
- WebClient (Spring WebFlux)
- JDK HttpClient (Java 11+)
- OkHttp (Square)

---

## 요약

- RestTemplate — 유지보수 모드. 신규 코드에 사용 금지
- RestClient — Spring Boot 3.2 이상에서 동기 방식의 **권장 대안**
- WebClient — 리액티브(비동기) 아키텍처가 필요한 경우 선택
- JDK HttpClient — 외부 의존성 없이 Java 11+ 표준 API로 사용 가능
- OkHttp — Spring 생태계 외부에서 강력한 프로덕션 기능이 필요할 때

---

## 라이브러리별 상세

### 1. RestTemplate

- Spring 3.0부터 제공된 동기/블로킹 HTTP 클라이언트
- **유지보수 모드**: Spring Framework 7.0에서 공식 deprecated 예정 (제거는 8.0)
- 현재 OSS 지원은 2029년까지 유지되나, 신규 기능 추가 없음
- 기존 레거시 코드 유지 목적 외에는 사용하지 않는 것이 권장됨

**장점**
- 사용법이 단순하고 레퍼런스가 많음
- 오래된 프로젝트와의 호환성

**단점**
- deprecated 예정
- 비동기/논블로킹 미지원
- 스레드당 1MB 스택 메모리 소비 (고부하 시 문제)

---

### 2. RestClient ✅ 신규 프로젝트 권장

- Spring 6.1 / Spring Boot 3.2에서 도입
- RestTemplate의 후계자: 동기 방식이지만 WebClient 스타일의 fluent API 제공
- Java 21 가상 스레드(Virtual Thread)와 궁합이 좋음 — 블로킹 방식이지만 높은 동시성 확보 가능
- 기존 RestTemplate 인스턴스를 래핑하여 점진적 마이그레이션 가능

**장점**
- Spring Boot 3.2+에서 가장 권장되는 선택
- 직관적인 fluent API
- 리액티브 스택 없이 사용 가능 (spring-webflux 의존성 불필요)
- Virtual Thread 친화적

**단점**
- Spring 6.1 미만 환경에서는 사용 불가
- 스트리밍/SSE 등 리액티브 기능은 WebClient에 비해 제한적

---

### 3. WebClient

- Spring 5.0에서 도입된 비동기/논블로킹 HTTP 클라이언트
- Spring WebFlux(Reactor) 기반으로 동작
- 동기 방식으로도 `.block()` 호출로 사용 가능하나 권장하지 않음

**장점**
- 완전한 리액티브 스트림 지원 (Mono, Flux)
- 스트리밍, SSE(Server-Sent Events) 처리에 적합
- 고부하 환경에서 메모리 효율 높음 (스레드 스택 불필요)

**단점**
- `spring-boot-starter-webflux` 의존성 추가 필요
- Reactor 학습 비용 있음
- 단순 REST 호출에는 오버스펙

**선택 기준**: 리액티브 아키텍처 전반을 채택할 때, 또는 스트리밍/SSE가 필요할 때

---

### 4. JDK HttpClient (java.net.http)

- Java 11에서 표준 API로 도입
- Java 21 가상 스레드 환경에서 블로킹 방식으로도 높은 성능 달성 가능
- 외부 라이브러리 의존성 없음

**장점**
- 표준 JDK API — 추가 의존성 없음
- HTTP/2 지원
- 동기/비동기 모두 지원 (`send()` / `sendAsync()`)
- Java 21 Virtual Thread와 결합 시 고성능 가능

**단점**
- Spring 생태계와의 통합이 RestClient/WebClient보다 번거로움
- 인터셉터, 재시도 등 고급 기능은 직접 구현 필요
- Spring Boot auto-configuration 없음

**선택 기준**: Spring 미사용 순수 Java 환경, 또는 외부 의존성 최소화가 목표일 때

---

### 5. OkHttp (Square)

- Android/Java용 오픈소스 HTTP 클라이언트 (Square 제공)
- 프로덕션 환경 최적화에 강점 — OkHttpClient 단일 인스턴스 재사용 권장

**장점**
- HTTP/2, TLS 1.3 기본 지원
- 커넥션 풀, 재시도, 인터셉터 내장
- 성능과 안정성 검증 (Android 생태계에서 광범위하게 사용)
- Retrofit과 조합 시 강력한 REST 클라이언트 구성 가능

**단점**
- 외부 라이브러리 의존성 추가 필요 (`com.squareup.okhttp3`)
- Spring Boot auto-configuration 없음 (별도 starter 필요)
- Spring 생태계 밖에서 주로 사용되는 경향

**선택 기준**: Spring 외부 환경, Retrofit 사용 시, 또는 고급 인터셉터/재시도 기능이 필요할 때

---

## 비교 요약

- 방식
  - RestTemplate — 동기/블로킹
  - RestClient — 동기/블로킹
  - WebClient — 비동기/논블로킹
  - JDK HttpClient — 동기 + 비동기 모두 지원
  - OkHttp — 동기 + 비동기 모두 지원
- Spring 통합
  - RestTemplate — Spring 내장
  - RestClient — Spring 내장 (6.1+)
  - WebClient — Spring 내장 (WebFlux)
  - JDK HttpClient — 수동 설정 필요
  - OkHttp — 별도 starter 필요
- 상태
  - RestTemplate — 유지보수 모드 (deprecated 예정)
  - RestClient — **현재 권장**
  - WebClient — 리액티브 권장
  - JDK HttpClient — 안정
  - OkHttp — 안정
- Java 17 + Spring Boot 3.x 적합성
  - RestTemplate — 낮음
  - RestClient — 높음
  - WebClient — 높음 (리액티브 필요 시)
  - JDK HttpClient — 중간
  - OkHttp — 중간

---

## 이 프로젝트에서의 선택

> Spring Boot 3.5.0 / Java 17 / 학습/레퍼런스 목적

- **RestClient** — Spring Boot 3.2+ 표준, 동기 방식, 구현 우선 대상
- **WebClient** — 비교 학습 목적으로 함께 구현
- RestTemplate — deprecated 예정이므로 구현 제외
- JDK HttpClient / OkHttp — 필요 시 추가 검토

---

## 운영 관점 분석: 커넥션풀 / Retry / 소켓 재사용

### 1. 커넥션풀 지원

- **RestTemplate**
  - 자체 풀 없음 — 기본 `SimpleClientHttpRequestFactory`는 `HttpURLConnection` 사용 (풀링 미흡)
  - 프로덕션에서는 `HttpComponentsClientHttpRequestFactory` + Apache HttpClient `PoolingHttpClientConnectionManager` 조합 필수
  - 풀 설정을 직접 구성해야 함 (maxTotal, maxPerRoute 등)

- **RestClient**
  - 내부적으로 RestTemplate과 동일한 `ClientHttpRequestFactory` 추상화 사용
  - Apache HttpClient 5 / JDK HttpClient / Jetty / Reactor Netty 중 선택 가능
  - 선택한 팩토리의 풀링 정책을 따름 — Apache HttpClient 5 사용 시 풀링 강력

- **WebClient**
  - Reactor Netty 기본 — `ConnectionProvider`로 풀 관리 (기본 활성화)
  - `ConnectionProvider.builder().maxConnections().pendingAcquireTimeout()...` 세밀 조정
  - HTTP/2 멀티플렉싱 지원 시 단일 커넥션 재사용 효율 매우 높음

- **JDK HttpClient**
  - 내장 커넥션 풀 보유 — 단일 `HttpClient` 인스턴스 재사용 시 자동 풀링
  - **풀 크기 직접 설정 API 없음** — JVM 시스템 프로퍼티(`jdk.httpclient.connectionPoolSize` 등)로만 제한적 조정
  - HTTP/2 기본 지원으로 멀티플렉싱 자동 활용

- **OkHttp**
  - `ConnectionPool` 클래스 명시적 제공 — 기본 5 idle / 5분 keepAlive
  - `OkHttpClient` 단일 인스턴스 공유가 권장 패턴
  - HTTP/2 멀티플렉싱 + 커넥션 코얼레싱(같은 IP면 다른 호스트도 재사용) 지원

### 2. Retry 지원

- **RestTemplate**
  - 내장 retry 없음 — Spring Retry(`@Retryable`) 또는 Resilience4j 별도 적용
  - 인터셉터(`ClientHttpRequestInterceptor`)로 직접 구현 가능

- **RestClient**
  - 내장 retry 없음 — Resilience4j / Spring Retry와 조합
  - `RestClient.Builder.requestInterceptor()`에 retry 로직 삽입 가능

- **WebClient**
  - Reactor 연산자 `retry()`, `retryWhen(Retry.backoff(...))` 내장
  - 지수 백오프, 지터, 조건부 재시도 등 풍부한 옵션 — 5개 중 가장 강력
  - 비동기 특성상 retry가 스레드 점유 없이 동작

- **JDK HttpClient**
  - 멱등(idempotent) 메서드(GET 등)에 한해 자동 재시도 (커넥션 단계 한정)
  - 응답 기반 재시도(5xx 등)는 지원 안 함 — 사용자 구현 필요
  - `jdk.httpclient.redirects.retrylimit` 시스템 프로퍼티로 제어

- **OkHttp**
  - `retryOnConnectionFailure(true)` 기본 활성화 — 커넥션 실패 자동 재시도
  - Interceptor 체인으로 응답 기반 retry 구현 용이
  - Route 실패 시 다른 IP로 자동 fallback (DNS 다중 응답 시)

### 3. 소켓 재사용 / 모니터링

- **RestTemplate** (Apache HttpClient 사용 가정)
  - keep-alive 기반 재사용 — `PoolingHttpClientConnectionManager.getTotalStats()` / `getStats(route)`
  - 활성/유휴/대기 커넥션 수 조회 가능
  - JMX 노출 가능 (수동 등록)

- **RestClient**
  - 동일 — 사용 중인 `ClientHttpRequestFactory` 백엔드의 모니터링 API 그대로 활용
  - Apache HttpClient 5 백엔드 시 `PoolingHttpClientConnectionManager` 통계 사용

- **WebClient (Reactor Netty)**
  - Micrometer 자동 통합 — `reactor.netty.connection.provider.*` 메트릭 노출
  - `total.connections`, `active.connections`, `idle.connections`, `pending.connections` 등
  - Spring Boot Actuator + Prometheus와 즉시 연동 — 모니터링 측면 최강

- **JDK HttpClient**
  - 공식 모니터링 API 부재 — 풀 상태 조회 불가
  - 디버그 로깅(`jdk.httpclient.HttpClient.log=requests,headers`)으로 간접 확인만 가능
  - 프로덕션 관측 관점에서 가장 약점

- **OkHttp**
  - `ConnectionPool.connectionCount()`, `idleConnectionCount()` 직접 제공
  - `EventListener` 인터페이스 — 커넥션 시작/종료/재사용/획득 등 세밀한 이벤트 콜백
  - Micrometer `okhttp3` 바인더로 메트릭 연동 가능

### 종합 평가

- 풀 제어 세밀도: OkHttp ≈ Apache HttpClient(RestTemplate/RestClient 백엔드) > WebClient > JDK HttpClient
- Retry 풍부도: WebClient > OkHttp > 기타(외부 라이브러리 의존)
- 모니터링/관측성: WebClient(Reactor Netty + Micrometer) > OkHttp(EventListener) > Apache HttpClient > JDK HttpClient
- 소켓 재사용 효율: WebClient(HTTP/2 멀티플렉싱) ≈ JDK HttpClient ≈ OkHttp > Apache HttpClient(HTTP/1.1 keep-alive)

### 학습 프로젝트 권장 조합

- RestClient + Apache HttpClient 5 — 풀/모니터링 학습용 (PoolingHttpClientConnectionManager 통계 확인)
- WebClient + Reactor Netty + Actuator — 메트릭/리액티브 retry 학습용
- OkHttp + EventListener — 커넥션 라이프사이클 이벤트 학습용

---

## 참고 자료

- [Which Java HTTP client should I use in 2024?](https://www.wiremock.io/post/java-http-client-comparison)
- [Comparison of Java HTTP Clients — reflectoring.io](https://reflectoring.io/comparison-of-java-http-clients/)
- [The state of HTTP clients in Spring — spring.io](https://spring.io/blog/2025/09/30/the-state-of-http-clients-in-spring/)
- [RestClient vs. WebClient vs. RestTemplate — digma.ai](https://digma.ai/restclient-vs-webclient-vs-resttemplate/)
- [Spring WebClient vs. RestTemplate — Baeldung](https://www.baeldung.com/spring-webclient-resttemplate)
- [The End of RestTemplate: Spring RestClient vs. WebClient vs. Retrofit in 2026 — Medium](https://medium.com/@mesfandiari77/the-end-of-resttemplate-spring-restclient-vs-webclient-vs-retrofit-in-2026-0ca96c463544)
- [JDK HttpClient with Virtual Threads and WebClient — Java Code Geeks](https://www.javacodegeeks.com/2025/11/jdk-httpclient-with-virtual-threads-and-webclient-the-future-of-asynchronous-http-in-java.html)
