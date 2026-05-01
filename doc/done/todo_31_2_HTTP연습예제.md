# todo 31_2: HTTP(OkHttp) 처리 연습 예제

> 보일러플레이트 — todo 31~37에서 다룬 OkHttp 클라이언트 사용법을 실전 문제로 연습

## 작업 목록
- [x] Ex01 OkHttp 기초 요청 — 문제/답안 작성
- [x] Ex02 JSON 직렬화/역직렬화 (MockWebServer) — 문제/답안 작성
- [x] Ex03 Spring 서버 통합 호출 — 문제/답안 작성

## 예제별 계획

- Ex01 OkHttp 기초 요청
  - 시나리오: GET / POST 요청을 만들고 상태 코드, 본문, 헤더를 추출
  - 커버 메서드: `OkHttpClient`, `Request.Builder`, `RequestBody.create`, `FormBody`, `Response.code/body/header`
  - 더미 서버: `MockWebServer`

- Ex02 JSON 직렬화/역직렬화
  - 시나리오: User 도메인을 JSON으로 보내고, JSON 응답을 객체/리스트로 받아서 처리
  - 커버 메서드: `Gson.toJson/fromJson`, `TypeToken<List<T>>`, `MediaType("application/json")`
  - 더미 서버: `MockWebServer`로 정해진 JSON 응답 enqueue

- Ex03 Spring 서버 통합 호출
  - 시나리오: `@SpringBootTest(RANDOM_PORT)`로 실제 todo 36 서버 띄우고, OkHttp로 CRUD 시나리오 수행
  - 커버 메서드: 위 두 예제의 종합 + `@LocalServerPort`, Location 헤더 → ID 추출, 4xx 에러 본문 파싱

## 구현 파일
- `src/test/java/dev/danielk/basicjava/http/exercise/`

## 파일 링크
- [Ex01_OkHttpBasicTest.java](../../src/test/java/dev/danielk/basicjava/http/exercise/Ex01_OkHttpBasicTest.java) / [Ex01_OkHttpBasicAnswer.java](../../src/test/java/dev/danielk/basicjava/http/exercise/Ex01_OkHttpBasicAnswer.java)
- [Ex02_JsonExchangeTest.java](../../src/test/java/dev/danielk/basicjava/http/exercise/Ex02_JsonExchangeTest.java) / [Ex02_JsonExchangeAnswer.java](../../src/test/java/dev/danielk/basicjava/http/exercise/Ex02_JsonExchangeAnswer.java)
- [Ex03_ServerIntegrationTest.java](../../src/test/java/dev/danielk/basicjava/http/exercise/Ex03_ServerIntegrationTest.java) / [Ex03_ServerIntegrationAnswer.java](../../src/test/java/dev/danielk/basicjava/http/exercise/Ex03_ServerIntegrationAnswer.java)

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`, 테스트는 `@Disabled("구현 필요")`
- 답안 파일: 풀이 코드 + 설명 주석 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋
