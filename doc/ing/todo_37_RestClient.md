# todo 37: RestClient로 REST 서버 호출

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)
> 사전 작업: [todo 36 HTTP 서버 컨트롤러](todo_36_HTTP서버컨트롤러.md)

## 목적

- Spring Boot 3.2+ 표준 동기 HTTP 클라이언트 `RestClient`로 todo 36 서버를 호출한다.
- `@SpringBootTest(RANDOM_PORT)`로 실제 서버를 띄워 통합 테스트 형태로 작성한다.

## 학습 포인트

- `RestClient.create(baseUrl)` / `RestClient.builder().baseUrl(...).build()`
- fluent API — `.get().uri(...).retrieve().body(...)`
- 요청/응답 처리:
  - GET 단건/리스트 (`ParameterizedTypeReference`로 `List<User>`)
  - POST + `.toBodilessEntity()`로 헤더/상태 코드 확인 (Location 검증)
  - PUT, DELETE
- 에러 처리: `.onStatus(HttpStatusCode::is4xxClientError, ...)`
- 헤더 추가, 쿼리 파라미터
- ObjectMapper(Jackson) 자동 직렬화 (Spring Boot 기본)

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/RestClient37Test.java`
