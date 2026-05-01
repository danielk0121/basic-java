# 구현 목록

## 프로젝트 목표
- Java SDK를 활용한 보일러플레이트 코드 구현
- 단순한 알고리즘 구현

## 범위 정의
- 포함: HTTP IO 처리
- 제외: 파일 IO, JDBC IO 등 그 외 IO 처리

---

## 보일러플레이트

### 11. 문자열 처리
- 문자열 자르기: substring, split
- 문자열 검색: indexOf, contains, startsWith, endsWith
- 문자열 변환: 대소문자, trim, replace, replaceAll
- 문자열 뒤집기
- 문자열 반복 및 합치기: repeat, join, concat
- 문자열 숫자 변환: parseInt, valueOf, toString
- 문자열 포맷: String.format, printf
- 정규식 매칭 및 추출
- 문자열 비교: equals, equalsIgnoreCase, compareTo
- 문자 접근: charAt, chars, toCharArray
- 문자열 길이 및 공백 여부: length, isEmpty, isBlank
- 문자열 앞뒤 공백 제거: strip, stripLeading, stripTrailing
- 문자열 치환 및 삭제: replace, replaceFirst, replaceAll
- 문자 시퀀스 조작: CharSequence subSequence
- StringBuilder를 이용한 가변 문자열 처리

**파일 링크**
- [StringBasicTest.java](../src/test/java/dev/danielk/basicjava/string/StringBasicTest.java): 자르기, 검색, 비교, 문자 접근, 길이/공백, 공백 제거
- [StringTransformTest.java](../src/test/java/dev/danielk/basicjava/string/StringTransformTest.java): 대소문자, 치환, 뒤집기, 반복/합치기, 숫자 변환, 포맷, 정규식
- [StringBuilderTest.java](../src/test/java/dev/danielk/basicjava/string/StringBuilderTest.java): StringBuilder 조작, CharSequence subSequence
- [Ex01_LogParserTest.java](../src/test/java/dev/danielk/basicjava/string/exercise/Ex01_LogParserTest.java) / [Ex01_LogParserAnswer.java](../src/test/java/dev/danielk/basicjava/string/exercise/Ex01_LogParserAnswer.java): 연습 01 로그 파서
- [Ex02_WordStatTest.java](../src/test/java/dev/danielk/basicjava/string/exercise/Ex02_WordStatTest.java) / [Ex02_WordStatAnswer.java](../src/test/java/dev/danielk/basicjava/string/exercise/Ex02_WordStatAnswer.java): 연습 02 단어 통계 분석기
- [Ex03_NameFormatterTest.java](../src/test/java/dev/danielk/basicjava/string/exercise/Ex03_NameFormatterTest.java) / [Ex03_NameFormatterAnswer.java](../src/test/java/dev/danielk/basicjava/string/exercise/Ex03_NameFormatterAnswer.java): 연습 03 이름 포매터
- [Ex04_PasswordValidatorTest.java](../src/test/java/dev/danielk/basicjava/string/exercise/Ex04_PasswordValidatorTest.java) / [Ex04_PasswordValidatorAnswer.java](../src/test/java/dev/danielk/basicjava/string/exercise/Ex04_PasswordValidatorAnswer.java): 연습 04 비밀번호 검증기

### 12. 배열 처리
- 배열 정렬: 오름차순, 내림차순, 커스텀 비교자
- 배열 검색: 선형 탐색, 이진 탐색 (Arrays.binarySearch)
- 배열 복사: 얕은 복사, 깊은 복사 (Arrays.copyOf, Arrays.copyOfRange)
- 배열 변환: 배열 → 리스트, 리스트 → 배열
- 배열 필터링 및 매핑: stream filter, map
- 다차원 배열 순회
- 배열 최대/최소/합계
- 배열 채우기: Arrays.fill
- 배열 비교: Arrays.equals, Arrays.deepEquals
- 배열 문자열 변환: Arrays.toString, Arrays.deepToString

**파일 링크**
- [ArrayTest.java](../src/test/java/dev/danielk/basicjava/array/ArrayTest.java): 배열 처리 보일러플레이트
- [Ex01_GradeProcessorTest.java](../src/test/java/dev/danielk/basicjava/array/exercise/Ex01_GradeProcessorTest.java) / [Ex01_GradeProcessorAnswer.java](../src/test/java/dev/danielk/basicjava/array/exercise/Ex01_GradeProcessorAnswer.java): 연습 01 성적 처리기
- [Ex02_InventoryManagerTest.java](../src/test/java/dev/danielk/basicjava/array/exercise/Ex02_InventoryManagerTest.java) / [Ex02_InventoryManagerAnswer.java](../src/test/java/dev/danielk/basicjava/array/exercise/Ex02_InventoryManagerAnswer.java): 연습 02 재고 관리 시스템
- [Ex03_SurveyAnalyzerTest.java](../src/test/java/dev/danielk/basicjava/array/exercise/Ex03_SurveyAnalyzerTest.java) / [Ex03_SurveyAnalyzerAnswer.java](../src/test/java/dev/danielk/basicjava/array/exercise/Ex03_SurveyAnalyzerAnswer.java): 연습 03 설문 결과 분석기

### 13. 리스트 처리
- 리스트 집합 연산: 합집합, 교집합, 차집합
- 리스트 정렬: 오름차순, 내림차순, 커스텀 비교자
- 리스트 중복 제거
- 리스트 슬라이싱: subList
- 리스트 뒤집기: Collections.reverse
- 리스트 최대/최소: Collections.max, Collections.min
- 리스트 빈도수 집계: Collections.frequency

**파일 링크**
- [ListTest.java](../src/test/java/dev/danielk/basicjava/collection/ListTest.java): 리스트 처리 보일러플레이트
- [Ex01_CartSystemTest.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex01_CartSystemTest.java) / [Ex01_CartSystemAnswer.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex01_CartSystemAnswer.java): 연습 01 장바구니 시스템
- [Ex02_StudentGradeTest.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex02_StudentGradeTest.java) / [Ex02_StudentGradeAnswer.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex02_StudentGradeAnswer.java): 연습 02 학생 성적 관리
- [Ex03_TeamRosterTest.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex03_TeamRosterTest.java) / [Ex03_TeamRosterAnswer.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex03_TeamRosterAnswer.java): 연습 03 팀 로스터 관리

### 14. 맵 처리
- 맵 순회: keySet, entrySet, values
- 맵 빈도수 집계: 문자/단어 등장 횟수
- 맵 정렬: 키 기준, 값 기준
- 맵 기본값 처리: getOrDefault, putIfAbsent
- 맵 병합: merge, compute

**파일 링크**
- [MapTest.java](../src/test/java/dev/danielk/basicjava/collection/MapTest.java): 맵 처리 보일러플레이트
- [Ex04_WordFrequencyTest.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex04_WordFrequencyTest.java) / [Ex04_WordFrequencyAnswer.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex04_WordFrequencyAnswer.java): 연습 04 단어 빈도 분석기
- [Ex05_ScoreAggregatorTest.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex05_ScoreAggregatorTest.java) / [Ex05_ScoreAggregatorAnswer.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex05_ScoreAggregatorAnswer.java): 연습 05 학생 점수 집계기
- [Ex06_GroupClassifierTest.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex06_GroupClassifierTest.java) / [Ex06_GroupClassifierAnswer.java](../src/test/java/dev/danielk/basicjava/collection/exercise/Ex06_GroupClassifierAnswer.java): 연습 06 그룹별 데이터 분류기

### 15. 숫자/수학 처리
- 최대공약수(GCD), 최소공배수(LCM)
- 소수 판별 및 소수 목록 생성
- 진법 변환: 10진수 ↔ 2/8/16진수
- 숫자 자릿수 추출 및 합계
- 순열, 조합 생성

**파일 링크**
- [MathTest.java](../src/test/java/dev/danielk/basicjava/math/MathTest.java): 숫자/수학 처리 보일러플레이트
- [Ex01_CryptoHelperTest.java](../src/test/java/dev/danielk/basicjava/math/exercise/Ex01_CryptoHelperTest.java) / [Ex01_CryptoHelperAnswer.java](../src/test/java/dev/danielk/basicjava/math/exercise/Ex01_CryptoHelperAnswer.java): 연습 01 암호화 도우미
- [Ex02_NumberPuzzleTest.java](../src/test/java/dev/danielk/basicjava/math/exercise/Ex02_NumberPuzzleTest.java) / [Ex02_NumberPuzzleAnswer.java](../src/test/java/dev/danielk/basicjava/math/exercise/Ex02_NumberPuzzleAnswer.java): 연습 02 숫자 퍼즐 검증기

### 16. 날짜/시간 처리
- 현재 날짜/시간 가져오기
- 날짜 포맷 변환: LocalDate, LocalDateTime, DateTimeFormatter
- 날짜 연산: 더하기, 빼기, 차이 계산
- 타임존 처리

**파일 링크**
- [DateTimeTest.java](../src/test/java/dev/danielk/basicjava/datetime/DateTimeTest.java): 날짜/시간 처리 보일러플레이트
- [Ex01_EventSchedulerTest.java](../src/test/java/dev/danielk/basicjava/datetime/exercise/Ex01_EventSchedulerTest.java) / [Ex01_EventSchedulerAnswer.java](../src/test/java/dev/danielk/basicjava/datetime/exercise/Ex01_EventSchedulerAnswer.java): 연습 01 이벤트 일정 관리기
- [Ex02_WorkHoursCalculatorTest.java](../src/test/java/dev/danielk/basicjava/datetime/exercise/Ex02_WorkHoursCalculatorTest.java) / [Ex02_WorkHoursCalculatorAnswer.java](../src/test/java/dev/danielk/basicjava/datetime/exercise/Ex02_WorkHoursCalculatorAnswer.java): 연습 02 근무 시간 계산기
- [Ex03_MeetingTimeConverterTest.java](../src/test/java/dev/danielk/basicjava/datetime/exercise/Ex03_MeetingTimeConverterTest.java) / [Ex03_MeetingTimeConverterAnswer.java](../src/test/java/dev/danielk/basicjava/datetime/exercise/Ex03_MeetingTimeConverterAnswer.java): 연습 03 글로벌 회의 시간 변환기

### 17. 랜덤 처리
- 정수 난수 생성: Random, ThreadLocalRandom
- 범위 지정 난수 생성
- 실수 난수 생성
- 리스트/배열 셔플
- 랜덤 요소 추출

**파일 링크**
- [RandomTest.java](../src/test/java/dev/danielk/basicjava/random/RandomTest.java): 랜덤 처리 보일러플레이트
- [Ex01_CardDeckTest.java](../src/test/java/dev/danielk/basicjava/random/exercise/Ex01_CardDeckTest.java) / [Ex01_CardDeckAnswer.java](../src/test/java/dev/danielk/basicjava/random/exercise/Ex01_CardDeckAnswer.java): 연습 01 카드 게임 덱
- [Ex02_RandomQuizTest.java](../src/test/java/dev/danielk/basicjava/random/exercise/Ex02_RandomQuizTest.java) / [Ex02_RandomQuizAnswer.java](../src/test/java/dev/danielk/basicjava/random/exercise/Ex02_RandomQuizAnswer.java): 연습 02 랜덤 퀴즈 출제기

### 18. JSON 처리
- Gson 직렬화: toJson()
- Gson 역직렬화: fromJson(Class), fromJson(TypeToken)
- Gson 리스트 처리: TypeToken<List<T>>
- Jackson ObjectMapper 직렬화: writeValueAsString()
- Jackson ObjectMapper 역직렬화: readValue(Class), readValue(TypeReference)
- Jackson ObjectMapper 리스트 처리: TypeReference<List<T>>

**파일 링크**
- [JsonTest.java](../src/test/java/dev/danielk/basicjava/json/JsonTest.java): JSON 처리 보일러플레이트
- [Ex01_GsonBasicTest.java](../src/test/java/dev/danielk/basicjava/json/exercise/Ex01_GsonBasicTest.java) / [Ex01_GsonBasicAnswer.java](../src/test/java/dev/danielk/basicjava/json/exercise/Ex01_GsonBasicAnswer.java): 연습 01 Gson 기본
- [Ex02_JacksonBasicTest.java](../src/test/java/dev/danielk/basicjava/json/exercise/Ex02_JacksonBasicTest.java) / [Ex02_JacksonBasicAnswer.java](../src/test/java/dev/danielk/basicjava/json/exercise/Ex02_JacksonBasicAnswer.java): 연습 02 Jackson 기본

### 19. 정규식(Regex) 처리
- split 패턴: 공백(`\s+`), 숫자 추출(`\D+`), 영문자 추출(`\d+`), 복합 구분기호(`[,;|/]`)
- matches 패턴: 숫자만(`^\d+$`), 이메일, 한국 휴대폰 번호, 비밀번호 규칙
- replaceAll 패턴: 연속 공백 정규화(`\s+`)

**파일 링크**
- [RegexSplitTest.java](../src/test/java/dev/danielk/basicjava/regex/RegexSplitTest.java): 정규식 처리 보일러플레이트
- [Ex01_SplitPatternTest.java](../src/test/java/dev/danielk/basicjava/regex/exercise/Ex01_SplitPatternTest.java) / [Ex01_SplitPatternAnswer.java](../src/test/java/dev/danielk/basicjava/regex/exercise/Ex01_SplitPatternAnswer.java): 연습 01 split 패턴
- [Ex02_MatchesPatternTest.java](../src/test/java/dev/danielk/basicjava/regex/exercise/Ex02_MatchesPatternTest.java) / [Ex02_MatchesPatternAnswer.java](../src/test/java/dev/danielk/basicjava/regex/exercise/Ex02_MatchesPatternAnswer.java): 연습 02 matches/replaceAll 패턴

---

## 알고리즘

### 21. 자료구조 활용 (JDK 라이브러리 사용, 직접 구현 안함)
- 스택: ArrayDeque as Stack — push/pop/peek, 괄호 유효성 검사
- 큐: ArrayDeque / LinkedList as Queue — offer/poll/peek, BFS
- 덱: ArrayDeque — 양방향 조작, 팰린드롬, 슬라이딩 윈도우 최댓값
- 우선순위 큐(힙): PriorityQueue — min/max-heap, 커스텀 정렬, K번째 최솟값
- Thread-safe 큐: ConcurrentLinkedQueue (lock-free), LinkedBlockingQueue (블로킹), ArrayBlockingQueue (고정 크기)
- Thread-safe 덱: ConcurrentLinkedDeque (lock-free), LinkedBlockingDeque (블로킹)

**파일 링크**
- [StackTest.java](../src/test/java/dev/danielk/basicjava/datastructure/StackTest.java): 스택
- [QueueTest.java](../src/test/java/dev/danielk/basicjava/datastructure/QueueTest.java): 큐
- [DequeTest.java](../src/test/java/dev/danielk/basicjava/datastructure/DequeTest.java): 덱
- [PriorityQueueTest.java](../src/test/java/dev/danielk/basicjava/datastructure/PriorityQueueTest.java): 우선순위 큐
- [ConcurrentLinkedQueueTest.java](../src/test/java/dev/danielk/basicjava/datastructure/ConcurrentLinkedQueueTest.java): lock-free 큐
- [LinkedBlockingQueueTest.java](../src/test/java/dev/danielk/basicjava/datastructure/LinkedBlockingQueueTest.java): 블로킹 큐
- [ArrayBlockingQueueTest.java](../src/test/java/dev/danielk/basicjava/datastructure/ArrayBlockingQueueTest.java): 고정 크기 블로킹 큐
- [ConcurrentLinkedDequeTest.java](../src/test/java/dev/danielk/basicjava/datastructure/ConcurrentLinkedDequeTest.java): lock-free 덱
- [LinkedBlockingDequeTest.java](../src/test/java/dev/danielk/basicjava/datastructure/LinkedBlockingDequeTest.java): 블로킹 덱

### 22. 재귀/완전탐색
- 팩토리얼
- 피보나치
- DFS/BFS 기본 구조
- 백트래킹 기본 구조

---

## HTTP 처리

### 31. HTTP 처리 (OkHttp 단일 라이브러리)

학습 목표
1. HTTP 클라이언트 라이브러리(OkHttp) 1개를 적당히 잘 사용하도록 연습
2. REST API 서버 코드(`UserController`)에 맞춰 클라이언트 테스트 코드 작성

#### 1단계: OkHttp 클라이언트 학습 (MockWebServer 사용)

- OkHttp 클라이언트 초기화, 타임아웃, try-with-resources
- 커넥션 풀, EventListener로 소켓 재사용 관찰, 인터셉터, Dispatcher
- GET/POST 요청 (Form/JSON), 쿼리 파라미터, 헤더, 4xx/5xx 응답 처리
- 다단계 JSON array/object 응답 직렬화/역직렬화 (Gson + TypeToken)

**파일 링크**
- [OkHttp31InitTest.java](../src/test/java/dev/danielk/basicjava/http/OkHttp31InitTest.java): 클라이언트 초기화
- [OkHttp32ConfigTest.java](../src/test/java/dev/danielk/basicjava/http/OkHttp32ConfigTest.java): 라이브러리 설정 (커넥션풀/인터셉터)
- [OkHttp33GetPostTest.java](../src/test/java/dev/danielk/basicjava/http/OkHttp33GetPostTest.java): GET/POST 요청
- [OkHttp34CrudTest.java](../src/test/java/dev/danielk/basicjava/http/OkHttp34CrudTest.java): REST CRUD 응답 처리
- [OkHttp35JsonTest.java](../src/test/java/dev/danielk/basicjava/http/OkHttp35JsonTest.java): 다단계 JSON 직렬화/역직렬화

#### 2단계: HTTP 서버 + 통합 테스트

서버 구조 (`src/main/java/dev/danielk/basicjava/http/`)
- 도메인: `User`, `Product`, `WishProduct`
- DTO: `UserRequest`, `UserResponse`, `WishProductResponse`, `ProductResponse`
- Repository (CUD): `UserRepository`, `WishProductRepository`
- Query (READ): `UserQuery`, `UserWithWishProducts`
- 샘플 데이터: `SampleDataFactory`, `SampleDataCreator`, `SampleDataConfiguration`

엔드포인트
- `GET /users/{id}`, `GET /users/page` — 다단계 JSON 응답 (User + wishProducts + product)
- `POST /users`, `PUT /users/{id}` — name/email만 변경
- `DELETE /users/{id}`

**파일 링크**
- [UserController.java](../src/main/java/dev/danielk/basicjava/http/UserController.java)
- [api_response_examples.md](../src/test/java/dev/danielk/basicjava/http/api_response_examples.md)
- [OkHttp37ServerIntegrationTest.java](../src/test/java/dev/danielk/basicjava/http/OkHttp37ServerIntegrationTest.java): `@SpringBootTest(RANDOM_PORT)` 통합 테스트

#### 연습 예제 (`http/exercise/`)

- Ex01: OkHttp 기초 GET/POST/Form/헤더
- Ex02: 다단계 JSON 직렬화/역직렬화 (MockWebServer)
- Ex03: 실제 Spring 서버 통합 (`@SpringBootTest`)

**파일 링크**
- [Ex01_OkHttpBasicTest.java](../src/test/java/dev/danielk/basicjava/http/exercise/Ex01_OkHttpBasicTest.java) / [Ex01_OkHttpBasicAnswer.java](../src/test/java/dev/danielk/basicjava/http/exercise/Ex01_OkHttpBasicAnswer.java)
- [Ex02_JsonExchangeTest.java](../src/test/java/dev/danielk/basicjava/http/exercise/Ex02_JsonExchangeTest.java) / [Ex02_JsonExchangeAnswer.java](../src/test/java/dev/danielk/basicjava/http/exercise/Ex02_JsonExchangeAnswer.java)
- [Ex03_ServerIntegrationTest.java](../src/test/java/dev/danielk/basicjava/http/exercise/Ex03_ServerIntegrationTest.java) / [Ex03_ServerIntegrationAnswer.java](../src/test/java/dev/danielk/basicjava/http/exercise/Ex03_ServerIntegrationAnswer.java)
