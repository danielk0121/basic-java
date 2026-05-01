# UserController API 응답 예시

> `dev.danielk.basicjava.http.UserController` 기준
> 응답 형식: root + 필드 + 배열 + 중첩 객체의 다단계 JSON 구조

## 도메인 / DTO 매핑

- 도메인 (`http.domain`): `User`, `WishProduct`, `Product`
- 응답 DTO (`http.dto`): `UserResponse`, `WishProductResponse`, `ProductResponse`

```
UserResponse
├─ id, name, email, joinedAt
└─ wishProducts: List<WishProductResponse>
   └─ product: ProductResponse
      └─ id, name, price
```

## 정책 요약

- 신규 가입(POST) 시 서버가 `joinedAt = now()` 와 하드코딩된 샘플 `wishProducts` (키보드, 모니터) 를 부여
- CUD(POST/PUT)는 단일 정보(name/email)만 변경 — wishProducts는 클라이언트가 변경 불가, 서버가 유지
- DELETE는 204 No Content
- 4xx 응답 본문은 `ApiError { status, message }`

---

## GET /users/{id}

```json
{
  "id": 1,
  "name": "daniel",
  "email": "daniel@example.com",
  "joinedAt": "2026-05-02T10:15:30",
  "wishProducts": [
    {
      "product": {
        "id": 1,
        "name": "키보드",
        "price": 30000
      }
    },
    {
      "product": {
        "id": 3,
        "name": "모니터",
        "price": 300000
      }
    }
  ]
}
```

설명
- 단건 조회 응답
- root 객체의 `wishProducts`는 배열, 각 요소는 `product` 중첩 객체를 가짐 (다단계 구조)
- `joinedAt`은 ISO_LOCAL_DATE_TIME 형식 문자열 — 클라이언트는 `LocalDateTime`으로 역직렬화 (Gson에서는 `GsonFactory`의 TypeAdapter 사용)

---

## GET /users — 제공하지 않음

전체 목록 조회는 데이터 양이 무한정 늘어날 수 있어 현업에서 사실상 사용되지 않는 형태이므로 컨트롤러가 제공하지 않는다.
대신 페이지네이션 엔드포인트(`GET /users/page`)를 사용한다.

---

## GET /users/page?page=0&size=2 — 페이지 응답

```json
{
  "data": [
    {
      "id": 1,
      "name": "alice",
      "email": "a@x.com",
      "joinedAt": "2026-05-02T10:00:00",
      "wishProducts": [
        {"product": {"id": 1, "name": "키보드", "price": 30000}}
      ]
    },
    {
      "id": 2,
      "name": "bob",
      "email": "b@x.com",
      "joinedAt": "2026-05-02T10:05:00",
      "wishProducts": []
    }
  ],
  "page": 0,
  "total": 5
}
```

설명
- 페이지 래퍼 객체: `{ data: [...], page, total }`
- 클라이언트는 `TypeToken<Page<UserResponse>>` 같은 제네릭 래퍼로 역직렬화

---

## POST /users — 생성

요청 본문 (`UserRequest`)
```json
{
  "name": "daniel",
  "email": "daniel@example.com"
}
```

응답
- 상태: `201 Created`
- 헤더: `Location: /users/1`
- 본문 (`UserResponse`):
```json
{
  "id": 1,
  "name": "daniel",
  "email": "daniel@example.com",
  "joinedAt": "2026-05-02T10:15:30",
  "wishProducts": [
    {"product": {"id": 1, "name": "키보드", "price": 30000}},
    {"product": {"id": 3, "name": "모니터", "price": 300000}}
  ]
}
```

설명
- 클라이언트는 단순한 `name`, `email`만 보내지만 응답은 다단계 구조
- `id`, `joinedAt`, `wishProducts`는 모두 서버가 채움
- 신규 ID는 `Location` 헤더의 마지막 path segment에서 추출 가능

---

## PUT /users/{id} — 수정

요청 본문 (`UserRequest`)
```json
{
  "name": "updated",
  "email": "u@example.com"
}
```

응답 (200 OK, `UserResponse`)
```json
{
  "id": 1,
  "name": "updated",
  "email": "u@example.com",
  "joinedAt": "2026-05-02T10:15:30",
  "wishProducts": [
    {"product": {"id": 1, "name": "키보드", "price": 30000}},
    {"product": {"id": 3, "name": "모니터", "price": 300000}}
  ]
}
```

설명
- name/email만 변경되고 `joinedAt` 과 `wishProducts`는 그대로 유지됨

---

## DELETE /users/{id}

- 상태: `204 No Content`
- 본문: 없음

---

## 에러 응답 (`ApiError`)

존재하지 않는 ID 조회 시 (404)
```json
{
  "status": 404,
  "message": "user not found: 999"
}
```

설명
- `UserController.handleNotFound`가 `UserNotFoundException`을 잡아서 반환
- 클라이언트는 응답 코드로 분기한 뒤 본문을 `ApiError`로 역직렬화

---

## 다단계 구조 점검 포인트

- root 객체에 일반 필드(id/name/email/joinedAt)와 배열 필드(wishProducts)가 함께 존재
- 배열 요소는 단순 값이 아닌 객체 (`WishProductResponse`)
- 그 객체는 또 다른 객체(`ProductResponse`)를 중첩
- → 클라이언트 측에서는 `Gson.fromJson(... UserResponse.class)` 한 번 호출로 모든 계층이 자동 역직렬화됨
