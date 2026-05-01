# todo 31: HTTP 처리

> 학습 목표: **OkHttp 단일 라이브러리**로 HTTP 클라이언트/서버 예제를 학습한다.
> 다른 HTTP 클라이언트(RestClient, WebClient, JDK HttpClient)는 다루지 않는다.

## 사전 작업
- [x] HTTP 클라이언트 라이브러리 비교 문서 작성 → [ref/http_클라이언트_라이브러리_비교.md](../../ref/http_클라이언트_라이브러리_비교.md)
  - 본 학습에서는 **OkHttp만** 사용 (커넥션 풀, EventListener, Interceptor 등 학습성 우수)
  - 서버는 Spring Boot `@RestController`로 구성 (학습 주제는 클라이언트/서버 통신)

## 1단계: OkHttp 클라이언트 학습 (완료)

- [x] [todo 31-1: OkHttp 클라이언트 초기화](todo_31_1_OkHttp클라이언트초기화.md)
- [x] [todo 32: OkHttp 라이브러리 설정](todo_32_OkHttp라이브러리설정.md) — 커넥션풀, keep-alive, 소켓 재사용 등
- [x] [todo 33: OkHttp GET/POST 요청](todo_33_OkHttpGetPost.md) — MockWebServer 더미 서버
- [x] [todo 34: OkHttp REST CRUD 응답 처리](todo_34_OkHttpRestCrud.md)
- [x] [todo 35: OkHttp JSON array/object 응답 처리](todo_35_OkHttpJsonArrayObject.md)

## 2단계: HTTP 서버 + OkHttp로 호출 (진행 예정)

- 구조 방침: REST API 학습이 주 목적이므로 Service/Repository 레이어 없이 Controller에서 인메모리 저장소로 직접 처리한다.
- 1단계의 OkHttp 학습 내용을 종합해 실제 서버를 호출한다 (MockWebServer가 아닌 실제 Spring 서버).

- [ ] [todo 36: HTTP 서버 — REST 컨트롤러](todo_36_HTTP서버컨트롤러.md) (`src/main`에 컨트롤러 단일 레이어)
- [ ] [todo 37: OkHttp 클라이언트로 todo 36 서버 호출](todo_37_OkHttp서버통합.md)
