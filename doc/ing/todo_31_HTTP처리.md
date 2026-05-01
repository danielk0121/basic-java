# todo 31: HTTP 처리

> HTTP 클라이언트/서버 코드 구현 — 라이브러리 비교 문서 작성 후 코드 구현 진행

## 사전 작업
- [x] HTTP 클라이언트 라이브러리 비교 문서 작성 → [ref/http_클라이언트_라이브러리_비교.md](../../ref/http_클라이언트_라이브러리_비교.md)
  - 결론: RestClient (동기) + WebClient (비동기) 우선 구현. RestTemplate 제외(deprecated 예정)
  - 학습 우선순위로 OkHttp 예제 먼저 진행 (커넥션 풀, EventListener 등 학습성 우수)

## 1단계: OkHttp 학습 예제 (완료)

- [x] [todo 31-1: OkHttp 클라이언트 초기화](todo_31_1_OkHttp클라이언트초기화.md)
- [x] [todo 32: OkHttp 라이브러리 설정](todo_32_OkHttp라이브러리설정.md) — 커넥션풀, keep-alive, 소켓 재사용 등
- [x] [todo 33: OkHttp GET/POST 요청](todo_33_OkHttpGetPost.md) — MockWebServer 더미 서버
- [x] [todo 34: OkHttp REST CRUD 응답 처리](todo_34_OkHttpRestCrud.md)
- [x] [todo 35: OkHttp JSON array/object 응답 처리](todo_35_OkHttpJsonArrayObject.md)

## 2단계: HTTP 서버 + Spring 클라이언트 (진행 예정)

- 구조 방침: REST API 학습이 주 목적이므로 Service/Repository 레이어 없이 Controller에서 인메모리 저장소로 직접 처리한다.
- 한 번 구성한 todo 36 서버를 todo 37/38/39 클라이언트가 모두 호출하여 클라이언트별 API 차이를 비교한다.

- [ ] [todo 36: HTTP 서버 — REST 컨트롤러](todo_36_HTTP서버컨트롤러.md) (`src/main`에 컨트롤러 단일 레이어)
- [ ] [todo 37: RestClient로 REST 서버 호출](todo_37_RestClient.md)
- [ ] [todo 38: WebClient로 REST 서버 호출](todo_38_WebClient.md)
- [ ] [todo 39: JDK HttpClient로 REST 서버 호출](todo_39_JdkHttpClient.md)
