# todo 37: OkHttp 클라이언트로 todo 36 서버 호출

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)
> 사전 작업: [todo 36 HTTP 서버 컨트롤러](todo_36_HTTP서버컨트롤러.md)

## 목적

- 1단계(31~35)에서 학습한 OkHttp 사용법을 종합해 **실제 Spring Boot 서버**를 호출한다.
- MockWebServer가 아닌 실서버 통신을 통해 종단간 흐름을 검증한다.

## 구성

- `@SpringBootTest(webEnvironment = RANDOM_PORT)`로 todo 36 서버를 띄운다.
- 테스트 안에서 OkHttp 클라이언트를 직접 생성해 `http://localhost:{port}/users`로 호출한다.
- JSON 직렬화/역직렬화는 Gson 사용 (todo 35에서 학습한 패턴).

## 학습 포인트

- 실서버에 대한 OkHttp 호출
- `@LocalServerPort`로 동적 포트 주입받기
- CRUD 시나리오 종합:
  - POST 생성 → Location 헤더로 신규 ID 추출
  - GET 단건 → 도메인 역직렬화
  - GET 전체 목록 → `List<User>` 역직렬화 (TypeToken)
  - PUT 수정 → 변경 결과 검증
  - DELETE → 204 확인 후 GET 시 404 검증
- 4xx 에러 응답: 존재하지 않는 ID 조회 시 404 + ApiError 본문 파싱

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/OkHttp37ServerIntegrationTest.java`
