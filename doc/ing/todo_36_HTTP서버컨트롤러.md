# todo 36: HTTP 서버 — REST 컨트롤러

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)

## 목적

- todo 37~39의 클라이언트 학습 테스트가 호출할 실제 REST 서버를 구성한다.
- REST API 학습이 주 목적이므로 **레이어드 구조(Service/Repository)를 사용하지 않고**, Controller에서 인메모리 저장소로 직접 처리한다.

## 구조

- `src/main/java/dev/danielk/basicjava/http/User.java` — 도메인 (id, name, email)
- `src/main/java/dev/danielk/basicjava/http/UserController.java` — 엔드포인트 + 인메모리 `ConcurrentHashMap<Long, User>` 저장
- `src/main/java/dev/danielk/basicjava/http/ApiError.java` — 에러 응답 DTO

## 엔드포인트

- `GET    /users`         — 전체 목록 조회 (200, JSON array)
- `GET    /users/{id}`    — 단건 조회 (200 / 404)
- `POST   /users`         — 생성 (201 + Location 헤더)
- `PUT    /users/{id}`    — 수정 (200 / 404)
- `DELETE /users/{id}`    — 삭제 (204 / 404)
- `GET    /users/page?page=0&size=10` — 페이지 응답 학습용 (`{ data, page, total }`)

## 학습 포인트

- `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- `@PathVariable`, `@RequestParam`, `@RequestBody`
- `ResponseEntity` — 상태 코드, 헤더(Location), 본문 제어
- 예외 → 4xx 응답 매핑 (`@ExceptionHandler`)
- `@SpringBootTest(webEnvironment = RANDOM_PORT)` 통합 테스트 기반

## 테스트

- `src/test/java/.../http/UserController36Test.java` — `TestRestTemplate` 또는 RestClient로 직접 서버 검증
- 각 엔드포인트의 정상/실패 케이스 검증

## 코드 위치

- main: `src/main/java/dev/danielk/basicjava/http/`
- test: `src/test/java/dev/danielk/basicjava/http/UserController36Test.java`
