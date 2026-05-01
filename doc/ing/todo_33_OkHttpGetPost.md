# todo 33: OkHttp GET / POST 요청

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)

## 목적

- OkHttp로 GET / POST 요청을 보내고 응답을 처리하는 기본 패턴을 익힌다.
- 더미 서버는 OkHttp의 `MockWebServer`를 사용한다 (별도 서버 기동 불필요).

## 학습 포인트

### GET 요청
- `Request.Builder().url(url).get().build()`
- 쿼리 파라미터: `HttpUrl.Builder().addQueryParameter(...)`
- 헤더 추가: `.addHeader("Authorization", "Bearer ...")`

### POST 요청
- form 전송: `FormBody.Builder().add(...).build()`
- JSON 전송: `RequestBody.create(json, MediaType.parse("application/json"))`
- multipart 전송: `MultipartBody.Builder().setType(MultipartBody.FORM)...`

### 응답 처리
- `response.code()`, `response.isSuccessful()`
- `response.body().string()` (한 번만 호출 가능)
- 헤더 조회: `response.header("Content-Type")`
- try-with-resources 필수

### MockWebServer 더미 서버
- `MockWebServer server = new MockWebServer(); server.start();`
- `server.enqueue(new MockResponse().setBody(...).setResponseCode(200))`
- `server.url("/path")` — 요청 URL 획득
- `server.takeRequest()` — 서버가 받은 요청 검증

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/OkHttpGetPostTest.java`
