# todo 18_1: JSON 처리 연습 예제

> 보일러플레이트 — todo 18 JSON 처리에서 다룬 Gson / Jackson 메서드를 실전 문제로 연습

## 작업 목록
- [x] Ex01 Gson 기본 — 문제/답안 작성
- [x] Ex02 Jackson 기본 — 문제/답안 작성

## 예제별 계획

- Ex01 Gson 기본
  - 시나리오: User 객체 직렬화, JSON → User 역직렬화, JSON 배열 → List<User> 역직렬화
  - 커버 메서드: `new Gson()`, `gson.toJson`, `gson.fromJson(Class)`, `gson.fromJson(TypeToken)`

- Ex02 Jackson 기본
  - 시나리오: User 객체 직렬화, JSON 배열 → List<User> 역직렬화
  - 커버 메서드: `new ObjectMapper()`, `om.writeValueAsString`, `om.readValue(TypeReference)`

## 구현 파일
- `src/test/java/dev/danielk/basicjava/json/exercise/`

## 파일 링크
- [Ex01_GsonBasicTest.java](../../src/test/java/dev/danielk/basicjava/json/exercise/Ex01_GsonBasicTest.java) / [Ex01_GsonBasicAnswer.java](../../src/test/java/dev/danielk/basicjava/json/exercise/Ex01_GsonBasicAnswer.java)
- [Ex02_JacksonBasicTest.java](../../src/test/java/dev/danielk/basicjava/json/exercise/Ex02_JacksonBasicTest.java) / [Ex02_JacksonBasicAnswer.java](../../src/test/java/dev/danielk/basicjava/json/exercise/Ex02_JacksonBasicAnswer.java)

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: `[문제]` / `[풀이]` 주석 분리 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋

## 완료일
2026-04-29
