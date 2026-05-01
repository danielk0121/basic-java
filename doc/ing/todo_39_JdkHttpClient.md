# todo 39: JDK HttpClient로 REST 서버 호출

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)
> 사전 작업: [todo 36 HTTP 서버 컨트롤러](todo_36_HTTP서버컨트롤러.md)

## 목적

- Java 11+ 표준 API `java.net.http.HttpClient`로 todo 36 서버를 호출한다.
- 외부 라이브러리 없이 표준 JDK만으로 REST 호출이 가능함을 학습한다.

## 학습 포인트

- `HttpClient.newHttpClient()` / `HttpClient.newBuilder()...build()`
- 빌더 옵션: `connectTimeout`, `version(HTTP_2)`, `followRedirects`
- 요청: `HttpRequest.newBuilder().uri(...).GET()/POST()/PUT()/DELETE().build()`
- 본문 publisher: `BodyPublishers.ofString(json)`
- 응답 처리:
  - 동기: `client.send(request, BodyHandlers.ofString())`
  - 비동기: `client.sendAsync(...)` → `CompletableFuture<HttpResponse>`
- 응답 객체: `response.statusCode()`, `response.headers()`, `response.body()`
- JSON 직렬화: Gson 또는 Jackson (수동 처리 — 자동 변환 없음)

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/JdkHttpClient39Test.java`
