# todo 31_4: 다단계 JSON 응답 + 도메인/DTO 분리 리팩터링

> 목적
> 1. UserController가 다루는 req/res JSON이 root + 필드 + 배열을 가진 일반적 다단계 구조가 되도록 한다.
> 2. 컨트롤러 VO를 도메인 / Request DTO / Response DTO로 명확히 분리한다.

## 데이터 모델 (단순화)

도메인 (`http` 패키지) — 서버 내부 상태, record
- `User { Long id, String name, String email, LocalDateTime joinedAt, List<Wishlist> wishlist }`
- `Wishlist { Product product }`
- `Product { Long id, String name, long price }`

요청 DTO (`http.dto` 패키지) — 클라이언트 → 서버
- `UserRequest { String name, String email }`
  - id / joinedAt / wishlist는 서버가 채우므로 클라이언트는 보내지 않음
  - 인스턴스 메서드 `toDomain()` (CUD CUD 정책상 wishlist 변경 불가, 컨트롤러에서 별도 처리)

응답 DTO (`http.dto` 패키지) — 서버 → 클라이언트
- `UserResponse { Long id, String name, String email, LocalDateTime joinedAt, List<WishlistResponse> wishlist }`
  - 정적 팩토리 `from(User)`
- `WishlistResponse { ProductResponse product }`
  - 정적 팩토리 `from(Wishlist)`
- `ProductResponse { Long id, String name, long price }`
  - 정적 팩토리 `from(Product)`

## 컨트롤러 정책

기존 엔드포인트 그대로 유지. **CUD는 단일 정보(name/email)만 변경**한다.
- 신규 사용자에게는 하드코딩된 샘플 wishlist (키보드, 모니터)를 부여
- joinedAt은 서버가 LocalDateTime.now()로 채움

엔드포인트
- `GET    /users`            → `List<UserResponse>`
- `GET    /users/{id}`       → `UserResponse`
- `POST   /users`            `UserRequest` → `ResponseEntity<UserResponse>` (201 + Location)
- `PUT    /users/{id}`       `UserRequest` → `UserResponse` (name/email만 변경)
- `DELETE /users/{id}`       → 204
- `GET    /users/page`       → `Page<UserResponse>`

## 응답 JSON 예시 (`GET /users/{id}`)

```json
{
  "id": 1,
  "name": "daniel",
  "email": "d@example.com",
  "joinedAt": "2026-05-02T10:00:00",
  "wishlist": [
    {"product": {"id": 1, "name": "키보드", "price": 30000}},
    {"product": {"id": 3, "name": "모니터", "price": 300000}}
  ]
}
```

## 영향받는 테스트 코드

- `OkHttp34CrudTest` — 다단계 응답 본문 + UserRequest는 name/email만
- `OkHttp35JsonTest` — root + 필드 + 배열 시나리오
- `OkHttp37ServerIntegrationTest` — 실제 서버 + 샘플 wishlist 검증
- `exercise/Ex02_JsonExchangeTest`, `Ex02_..Answer`
- `exercise/Ex03_ServerIntegrationTest`, `Ex03_..Answer`
- `GsonFactory` — LocalDateTime TypeAdapter 추가 (Gson 기본 직렬화 미지원 대응)

## 작업 항목

- [x] 도메인 record: `User { id, name, email, joinedAt, wishlist }`, `Wishlist`, `Product`
- [x] DTO record 4종 작성 (`UserRequest`, `UserResponse`, `WishlistResponse`, `ProductResponse`), `from(domain)` / `toDomain()` 메서드
- [x] UserController CUD 정책: name/email만 변경, 신규 가입 시 샘플 wishlist 부여
- [x] GsonFactory 도입 (LocalDateTime TypeAdapter)
- [x] 테스트 코드 일괄 갱신 (34/35/37 + exercise Ex02/Ex03)
- [x] 전체 테스트 통과 후 커밋

## 원칙

- 매핑 책임은 DTO 안에 둔다 (record `from`/`toDomain` 메서드).
- 컨트롤러는 매핑을 직접 작성하지 않고 DTO 메서드를 호출만 한다.
- 도메인 record는 직렬화에 영향받지 않도록 컨트롤러 외부에 노출하지 않는다 (응답은 항상 *Response).
- 서버가 관리하는 필드(id, joinedAt, wishlist)는 클라이언트가 보내지 않는다.
