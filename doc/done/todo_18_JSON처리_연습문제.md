# todo_18: JSON 처리 - 연습문제

## 목표
Gson과 Jackson ObjectMapper에 대한 연습문제 및 답안 작성

## 연습문제 목록

### 문제 1 — Gson 기본 직렬화
`User` 클래스(필드: id(int), username(String), email(String))를 작성하고,
인스턴스를 Gson으로 JSON 문자열로 변환하라.
예상 출력: `{"id":1,"username":"alice","email":"alice@example.com"}`

### 문제 2 — Gson 역직렬화 (Class 지정)
아래 JSON 문자열을 Gson으로 `User` 객체로 변환하고, 각 필드 값을 검증하라.
```json
{"id":2,"username":"bob","email":"bob@example.com"}
```

### 문제 3 — Gson 리스트 역직렬화 (TypeToken)
아래 JSON 배열을 `List<User>`로 변환하고 사이즈와 첫 번째 원소의 username을 검증하라.
```json
[{"id":1,"username":"alice","email":"alice@example.com"},{"id":2,"username":"bob","email":"bob@example.com"}]
```

### 문제 4 — Jackson 기본 직렬화
`User` 객체를 Jackson ObjectMapper로 JSON 문자열로 변환하라.
(getter 필요)

### 문제 5 — Jackson 역직렬화 (TypeReference)
아래 JSON 배열을 Jackson ObjectMapper + `TypeReference<List<User>>`로 변환하고
사이즈와 두 번째 원소의 email을 검증하라.
```json
[{"id":1,"username":"alice","email":"alice@example.com"},{"id":2,"username":"bob","email":"bob@example.com"}]
```

## 파일 구조
- `json/JsonTest.java` — 보일러플레이트 (직렬화/역직렬화 패턴 참고용)
- `json/exercise/Ex01_GsonBasicTest.java` — 문제 (Gson 3개, @Disabled + TODO)
- `json/exercise/Ex01_GsonBasicAnswer.java` — 답안 (Gson 3개)
- `json/exercise/Ex02_JacksonBasicTest.java` — 문제 (Jackson 2개, @Disabled + TODO)
- `json/exercise/Ex02_JacksonBasicAnswer.java` — 답안 (Jackson 2개)

## 작업 내용
- [x] `json/exercise/` 폴더 생성
- [x] `Ex01_GsonBasicTest.java` / `Ex01_GsonBasicAnswer.java` 작성 (Gson 문제 1~3)
- [x] `Ex02_JacksonBasicTest.java` / `Ex02_JacksonBasicAnswer.java` 작성 (Jackson 문제 1~2)
- [x] 문제 파일: `@Disabled` + `throw new UnsupportedOperationException("구현 필요")`
- [x] 답안 파일: `[문제]` / `[풀이]` 주석 분리
- [x] Answer 테스트 전체 통과 확인

## 완료 기준
- Answer 파일 테스트 5개 모두 통과
- datetime/exercise 폴더 구조와 동일한 패턴 적용

## 완료일
2026-04-29
