# todo 31-1: OkHttp 클라이언트 초기화

> 상위: [todo_31_HTTP처리.md](todo_31_HTTP처리.md)
> 라이브러리: `com.squareup.okhttp3:okhttp`

## 목적

- OkHttp `OkHttpClient` 인스턴스를 생성하고 기본 요청 흐름을 익힌다.
- 학습 예제이므로 **매 테스트마다 새 인스턴스를 생성**한다 (실 운영에서는 단일 인스턴스 권장).

## 학습 포인트

- 기본 생성: `new OkHttpClient()`
- 빌더 생성: `new OkHttpClient.Builder().build()`
- 타임아웃 설정: `connectTimeout`, `readTimeout`, `writeTimeout`, `callTimeout`
- 요청 객체: `Request.Builder().url(...).build()`
- 호출 실행: `client.newCall(request).execute()` (동기), `enqueue(...)` (비동기)
- 응답 처리: try-with-resources로 `Response` 닫기 (소켓 누수 방지)

## 주의사항

- 인스턴스를 매번 새로 만들면 커넥션 풀, 디스패처 스레드 풀이 매번 새로 생성된다.
- 운영 코드에서는 단일 인스턴스를 공유하거나 `newBuilder()`로 파생시켜야 한다.

## 코드 위치

- `src/test/java/dev/danielk/basicjava/http/OkHttpInitTest.java`
