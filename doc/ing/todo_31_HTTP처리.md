# todo 31: HTTP 처리

> HTTP 클라이언트/서버 코드 구현 — 라이브러리 비교 문서 작성 후 코드 구현 진행

## 사전 작업
- [x] HTTP 클라이언트 라이브러리 비교 문서 작성 → [ref/http_클라이언트_라이브러리_비교.md](../../ref/http_클라이언트_라이브러리_비교.md)
  - 결론: RestClient (동기) + WebClient (비동기) 우선 구현. RestTemplate 제외(deprecated 예정)
  - 학습 우선순위로 OkHttp 예제 먼저 진행 (커넥션 풀, EventListener 등 학습성 우수)

## 하위 작업 (OkHttp 학습 예제)

- [ ] [todo 31-1: OkHttp 클라이언트 초기화](todo_31_1_OkHttp클라이언트초기화.md)
- [ ] [todo 32: OkHttp 라이브러리 설정](todo_32_OkHttp라이브러리설정.md) — 커넥션풀, keep-alive, 소켓 재사용 등
- [ ] [todo 33: OkHttp GET/POST 요청](todo_33_OkHttpGetPost.md) — MockWebServer 더미 서버
- [ ] [todo 34: OkHttp REST CRUD 응답 처리](todo_34_OkHttpRestCrud.md)
- [ ] [todo 35: OkHttp JSON array/object 응답 처리](todo_35_OkHttpJsonArrayObject.md)

## 후속 작업 (별도 진행)

- [ ] RestClient GET/POST 요청
- [ ] WebClient GET/POST 요청
- [ ] JDK HttpClient (Java 11+) GET/POST 요청
- [ ] HTTP 서버: REST 컨트롤러 GET/POST/PUT/DELETE
