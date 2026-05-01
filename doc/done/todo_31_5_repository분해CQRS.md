# todo 31_5: Repository 분해 (CQRS-style)

> 목적
> - CUD는 단일 도메인 단위 repository에서 처리한다.
> - READ는 복합 도메인을 결합하는 query 객체에서 처리한다.
> - 컨트롤러는 단순해야 한다 (HTTP 클라이언트 학습 시 server mocking이 쉬워야 함).

## 결정 사항

- POST 응답의 wishProducts는 저장 후 `WishProductRepository.findByUserId`로 다시 읽어 사용 (저장과 읽기 분리, 일관성 확보).

## 신규 클래스 (`http.sampledata`)

- `UserRepository` — user 테이블 단위
  - `nextId()`, `save(User)`, `update(User)`, `delete(id)`, `existsById(id)`, `findRawById(id) → Optional<User>`
  - id 시퀀스 시작값은 생성자 주입
- `WishProductRepository` — wish_product 테이블 단위
  - `saveAll(userId, List<WishProduct>)`, `findByUserId(userId) → List<WishProduct>`, `deleteByUserId(userId)`
- `UserQuery` — 조회 조합 (CQRS의 read 측)
  - `findById(id) → Optional<UserWithWishProducts>`
  - `findPage(page, size) → List<UserWithWishProducts>`
  - `count() → int`

## 변경

- `SampleDataConfiguration`
  - `UserRepository`(시드 활성 여부에 따른 시작값), `WishProductRepository`, `UserQuery` 빈 등록
  - `SampleDataCreator`는 `@ConditionalOnProperty(app.sample-data.enabled=true)`로 등록
- `SampleDataCreator`
  - `UserRepository.save(user)` + `WishProductRepository.saveAll(userId, wishProducts)` 두 단계 시드
- `UserController`
  - CUD: `UserRepository` + `WishProductRepository` 직접 사용
    - POST: 두 repository 저장 → 응답은 `UserQuery.findById` 또는 두 repository 재조회로 구성
    - PUT: `UserRepository.update`만 (wishProducts 유지)
    - DELETE: 두 repository 모두 정리
  - READ: `UserQuery` 사용

## 제거

- `SampleDataRepository` 삭제

## 패키지 구조 (변경 후)

```
http
├─ UserController, ApiError, UserNotFoundException
├─ domain/    User, Product, WishProduct
├─ dto/       UserRequest, UserResponse, WishProductResponse, ProductResponse
└─ sampledata/
   ├─ SampleDataFactory
   ├─ SampleDataCreator
   ├─ SampleDataConfiguration
   ├─ UserRepository
   ├─ WishProductRepository
   ├─ UserQuery
   └─ UserWithWishProducts
```

## 작업 항목

- [x] UserRepository 작성
- [x] WishProductRepository 작성
- [x] UserQuery 작성
- [x] SampleDataConfiguration 갱신 (3개 빈 등록)
- [x] SampleDataCreator 갱신 (두 단계 저장)
- [x] UserController 갱신 (CUD/READ 분리)
- [x] SampleDataRepository 삭제
- [x] 전체 테스트 통과 후 커밋
