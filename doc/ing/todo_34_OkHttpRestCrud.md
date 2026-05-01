# todo 34: OkHttp REST CRUD 응답 처리

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)

## 목적

- 표준 REST 도메인 API에 대한 client side 응답 처리를 학습한다.
- 도메인 예시: `User { id, name, email }`
- 엔드포인트:
  - `GET /users/{id}` — 단건 조회
  - `POST /users` — 생성 (201 Created)
  - `PUT /users/{id}` — 수정
  - `DELETE /users/{id}` — 삭제 (204 No Content)

## 학습 포인트

- 상태 코드별 분기: 200 / 201 / 204 / 4xx / 5xx
- `Location` 헤더로 신규 리소스 위치 확인 (POST 응답)
- 빈 응답 본문 처리 (204)
- 에러 응답 본문 파싱 (4xx 시 메시지 추출)
- JSON 직렬화/역직렬화 (Gson)
- 요청 본문 직렬화: 도메인 → JSON 문자열

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/OkHttpCrudTest.java`
