# todo 38: WebClient로 REST 서버 호출

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)
> 사전 작업: [todo 36 HTTP 서버 컨트롤러](todo_36_HTTP서버컨트롤러.md)

## 목적

- Reactor 기반 비동기/논블로킹 클라이언트 `WebClient`로 todo 36 서버를 호출한다.
- `spring-boot-starter-webflux` 의존성 추가 필요 (test scope 권장).

## 학습 포인트

- `WebClient.create(baseUrl)` / `WebClient.builder()...`
- 비동기 결과: `Mono<T>`, `Flux<T>`
- 동기 변환: `.block()` (테스트 검증 시)
- CRUD 호출 — GET/POST/PUT/DELETE
- 응답 처리:
  - `.bodyToMono(User.class)`, `.bodyToFlux(User.class)`
  - `.toEntity(User.class)` — 상태 코드/헤더 포함
- 에러 처리: `.onStatus(...)`, `.onErrorResume(...)`
- retry 연산자: `.retryWhen(Retry.backoff(...))`

## 의존성

- `org.springframework.boot:spring-boot-starter-webflux` (testImplementation)

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/WebClient38Test.java`
