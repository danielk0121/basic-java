okhttp client 사용하도록 예제 구성
단, okhttp 단일 인스턴스 권장 사용 방식이 아니라, 학습 예제 이므로, 매번 인스턴스를 구성하도록 사용
그리고 별도 예제로, http client side 설정 내용을 추가
- 소켓 재사용 옵션
- keep alive 옵션
- 커넥션 풀 사용
- 기타 http client side 보편적인 라이브러리 설정

src/test 에 학습 테스트 코드로 작성
todo_31 http 처리 문서 하위 문서로 문서를 먼저 작성
- todo 31 http client 초기화
- todo 32 http client 라이브러리 설정 (커넥션 풀, 소켓 재사용, keep-alive 등)
- todo 33 http get post 요청 (더비 서버 코드 작성)
- todo 34 http 기본 domain curd rest json api 서버에 대한 client side 응답 처리
- todo 35 http json array/object get api 에 대한 client side 응답 처리 (json 직렬화/역직렬화 집중)