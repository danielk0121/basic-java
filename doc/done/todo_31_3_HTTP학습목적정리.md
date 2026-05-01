# todo 31_3: HTTP 학습 목적 정리 및 테스트 코드 정합성 점검

## 학습 목적

1. **HTTP 클라이언트 라이브러리 1개(OkHttp)를 적당히 잘 사용하도록 연습한다.**
   - 라이브러리 초기화, 설정(커넥션 풀/타임아웃/인터셉터/EventListener), 요청/응답 처리, JSON 직렬화/역직렬화
   - 더미 서버는 `MockWebServer` 사용 (라이브러리 학습이 주 목적)

2. **REST API 서버 코드에 맞춰서 클라이언트 테스트 코드를 작성 및 테스트할 수 있다.**
   - `src/main/.../http/UserController.java`가 실제 서버
   - `@SpringBootTest(webEnvironment = RANDOM_PORT)`로 실제 서버를 띄워 클라이언트 호출 검증
   - 응답 형식(상태 코드, 헤더, JSON 필드명)을 서버 코드와 정확히 일치시킨다

## 재검토 결과

### 목적 1 (라이브러리 학습) — MockWebServer 사용 OK
- `OkHttp31InitTest` — OkHttp 인스턴스 초기화 학습
- `OkHttp32ConfigTest` — 커넥션 풀, 인터셉터, Dispatcher 학습
- `OkHttp33GetPostTest` — GET/POST 기본기 학습
- `exercise/Ex01_OkHttpBasicTest` — 기초 연습 문제

### 목적 2 (서버 연동) — UserController와 응답 형식 일치 필요
- `OkHttp34CrudTest` — 자체 static class User 사용 → main의 record User 재사용으로 통일
- `OkHttp35JsonTest` — `@SerializedName("user_email")` 사용. UserController는 `email`로 반환 → 자체 User 제거하고 main User 활용 + 학습 포인트(중첩객체/페이지)는 별도 더미 도메인 유지
- `exercise/Ex02_JsonExchangeTest` — 자체 record User → main User로 통일
- `OkHttp37ServerIntegrationTest` — 이미 main User 사용 (수정 불필요)
- `exercise/Ex03_ServerIntegrationTest` — 이미 main User 사용 (수정 불필요)

## 작업 항목

- [x] OkHttp34CrudTest — 자체 도메인 제거, main `User`/`ApiError` 사용
- [x] OkHttp35JsonTest — UserController 응답 형식과 어긋나는 `user_email` 제거. main `User` 재사용. `@SerializedName`/중첩 학습은 별도 Account/Order 더미 도메인으로 분리하고 컨트롤러 응답과 무관함을 주석으로 명시
- [x] exercise/Ex02_JsonExchangeTest — 자체 record User 제거, main User 사용 (Test/Answer 양쪽)
- [x] 전체 테스트 통과 확인

## 원칙

- 목적 1 단계의 테스트는 라이브러리 학습이 우선이므로 MockWebServer 응답을 자유롭게 스크립팅해도 됨
- 단, 도메인을 등장시킬 때는 **main의 User/ApiError를 재사용**하여 "서버 응답 형식"과 일관되게 유지
- 학습 보조용 도메인(중첩 Order, Page wrapper 등)이 필요하면 컨트롤러 응답이 아님을 주석으로 명시
