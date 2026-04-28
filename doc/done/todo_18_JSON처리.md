# todo_18: JSON 처리

## 목표
Gson과 Jackson ObjectMapper를 이용한 JSON 직렬화/역직렬화 보일러플레이트 구현

## 참고
- study-hellojava 프로젝트의 JSON 처리 코드 참고

## 구현 내용

### 의존성 추가 (build.gradle)
- `com.google.code.gson:gson:2.12.1` 추가 (Jackson은 spring-boot-starter-web에 포함)

### 테스트 파일
- `src/test/java/dev/danielk/basicjava/json/`

### 파일 링크
- [JsonTest.java](../../src/test/java/dev/danielk/basicjava/json/JsonTest.java)

### 샘플 POJO
- `Product` (inner class): id, name, price 필드, 기본 생성자, 전체 생성자, getter/setter, toString

### Gson 테스트 (5개)
- `gson_toJson_object` — 객체 → JSON 문자열
- `gson_fromJson_object_byClass` — JSON 문자열 → 객체 (Class 지정)
- `gson_fromJson_object_byTypeToken` — JSON 문자열 → 객체 (TypeToken 지정, Gson 2.10+)
- `gson_toJson_list` — 리스트 → JSON 배열 문자열
- `gson_fromJson_list_byTypeToken` — JSON 배열 문자열 → 리스트 (TypeToken<List<T>> 지정)

### Jackson ObjectMapper 테스트 (5개)
- `objectMapper_writeValueAsString_object` — 객체 → JSON 문자열
- `objectMapper_readValue_object_byClass` — JSON 문자열 → 객체 (Class 지정)
- `objectMapper_readValue_object_byTypeReference` — JSON 문자열 → 객체 (TypeReference 지정)
- `objectMapper_writeValueAsString_list` — 리스트 → JSON 배열 문자열
- `objectMapper_readValue_list_byTypeReference` — JSON 배열 문자열 → 리스트 (TypeReference<List<T>> 지정)

## 주의사항
- ObjectMapper 직렬화 시 getter 필요, 역직렬화 시 기본 생성자 + setter 필요
- TypeToken (Gson) vs TypeReference (Jackson): 런타임 제네릭 타입 보존용

## 완료일
2026-04-28
