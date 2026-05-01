# todo 35: OkHttp JSON array/object 응답 처리

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)

## 목적

- JSON array / object 응답에 대한 직렬화/역직렬화에 집중한다.
- Gson `TypeToken` 활용, 중첩 객체, null/누락 필드, 다양한 타입 변환 학습.

## 학습 포인트

### Object 응답
- 단일 객체: `gson.fromJson(json, User.class)`
- 누락 필드는 default(null/0)로 채워짐
- 추가 필드는 무시됨 (기본 동작)

### Array 응답
- 배열 → `List<T>`: `gson.fromJson(json, new TypeToken<List<User>>(){}.getType())`
- 배열 → `T[]`: `gson.fromJson(json, User[].class)`

### 중첩 객체
- `Order { id, user: User, items: List<Item> }` 같은 구조
- 한 번에 역직렬화

### 직렬화
- `gson.toJson(obj)` → 문자열
- `@SerializedName("user_id")`로 snake_case ↔ camelCase 매핑
- null 필드 포함 옵션: `new GsonBuilder().serializeNulls()`

### 페이지/래퍼 응답
- `{ "data": [...], "page": 0, "total": 100 }` 형태
- `Page<T>` 제네릭 클래스로 역직렬화

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/OkHttpJsonTest.java`
