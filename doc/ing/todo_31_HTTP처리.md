# todo 31: HTTP 처리

> HTTP 클라이언트/서버 코드 구현 — 라이브러리 비교 문서 작성 후 코드 구현 진행

## 사전 작업
- [x] HTTP 클라이언트 라이브러리 비교 문서 작성 → [ref/http_클라이언트_라이브러리_비교.md](../../ref/http_클라이언트_라이브러리_비교.md)
  - 결론: RestClient (동기) + WebClient (비동기) 우선 구현. RestTemplate 제외(deprecated 예정)

## HTTP 클라이언트
- [ ] RestTemplate GET/POST 요청
- [ ] HttpClient GET/POST 요청 (Java 11+)
- [ ] JSON 직렬화/역직렬화: ObjectMapper
- [ ] 쿼리 파라미터 및 헤더 설정

## HTTP 서버
- [ ] REST 컨트롤러 기본 구조: GET/POST/PUT/DELETE
