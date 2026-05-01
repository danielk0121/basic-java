# basic-java

자바 레퍼런스 코드 모음집

## 목적

자바 **함수 본문 안에서 일어나는 작업**(변수/컬렉션/문자열/날짜/숫자/JSON/HTTP 등)을 막힘없이 작성할 수 있는 수준의 보일러플레이트를 모아둔다.

- Java SDK를 활용한 보일러플레이트 코드 구현
- 단순한 알고리즘 구현
- HTTP 클라이언트(OkHttp) / Spring REST 서버 학습 테스트

### 다루는 범위

- 한 함수 안에서 끝나는 작업의 관용구 (문자열/배열/컬렉션/날짜/정규식/수학/랜덤/JSON/HTTP/자료구조)
- DB CRUD를 제외한 일반적인 WAS 코드 작성에 필요한 기본기

### 다루지 않는 범위 (의도적 제외)

- **로직 설계 영역** (동시성 제어, 스트림 활용 등) — 보일러플레이트가 아니라 그때그때 패턴을 골라 쓰는 영역
- **스프링 프레임워크 관용구** (로깅, 유효성 검증, 비동기, 환경설정, 테스트 도구) — 자바 문법 연습이 아니라 프레임워크 사용법으로 익힐 영역
- **함수/클래스/패키지 설계** (책임 분리, 의존성 방향, 추상화 시점) — 별개의 큰 주제로, 실제 도메인 프로젝트에서 리팩터링 반복으로 익힐 영역

## 로드맵

[구현 목록 보기](ref/roadmap_구현목록.md)

## 기술 스택

- Java 17
- Spring Boot 3.5.0
- Gradle
- OkHttp 4.12.0 + MockWebServer
- Gson 2.12.1

## 코드 작성 규칙

- `src/test` — 보일러플레이트, 알고리즘, HTTP 학습 테스트 코드. JUnit으로 입력/출력 검증
- `src/main` — HTTP 서버(REST 컨트롤러) 등 실행 가능한 서버 코드만 작성

## 설계 결정

- [폴더 구조 고찰: doc 분리 vs src/test 통합](ref/ref_폴더구조_고찰.md)
- [HTTP 클라이언트 라이브러리 비교](ref/http_클라이언트_라이브러리_비교.md)

## 참고 문서

- [ref_큐_덱_차이점.md](ref/ref_큐_덱_차이점.md)
- [review_자바쓰레기.md](ref/review_자바쓰레기.md)

## 연습 예제 구조

각 주제별로 `exercise/` 패키지에 문제(`*Test.java`)와 답안(`*Answer.java`)이 쌍으로 존재합니다.

- 문제 파일: 미구현 상태(`@Disabled` 처리) — 직접 풀어보는 용도
- 답안 파일: 풀이 코드 + 설명 주석 — 참고용

## 프로젝트 구조

```
src/main/java/dev/danielk/basicjava/
└── http/
    ├── UserController.java       # REST 컨트롤러
    ├── ApiError.java             # 에러 응답 DTO
    ├── UserNotFoundException.java
    ├── domain/                   # 도메인 record (User, Product, WishProduct)
    ├── dto/                      # 요청/응답 DTO (UserRequest, UserResponse, ...)
    ├── repository/               # 단일 도메인 CUD (UserRepository, WishProductRepository)
    ├── query/                    # 복합 도메인 read (UserQuery, UserWithWishProducts)
    └── sampledata/               # 시드 데이터 (Factory, Creator, Configuration)

src/test/java/dev/danielk/basicjava/
├── string/                # 문자열 처리 보일러플레이트
│   └── exercise/          # 문자열 연습 예제 (로그 파서, 단어 통계, 이름 포매터, 비밀번호 검증)
├── array/                 # 배열 처리 보일러플레이트
│   └── exercise/          # 배열 연습 예제 (성적 처리, 재고 관리, 설문 분석)
├── collection/            # 리스트/맵 처리 보일러플레이트
│   └── exercise/          # 리스트/맵 연습 예제 (장바구니, 성적 관리, 팀 로스터, 단어 빈도, 점수 집계, 그룹 분류)
├── math/                  # 숫자/수학 처리 보일러플레이트
│   └── exercise/          # 수학 연습 예제 (암호화 도우미, 숫자 퍼즐)
├── datetime/              # 날짜/시간 처리 보일러플레이트
│   └── exercise/          # 날짜/시간 연습 예제 (이벤트 일정, 근무 시간, 회의 시간 변환)
├── random/                # 랜덤 처리 보일러플레이트
│   └── exercise/          # 랜덤 연습 예제 (카드 덱, 랜덤 퀴즈)
├── json/                  # JSON 처리 보일러플레이트 (Gson, Jackson)
│   └── exercise/          # JSON 연습 예제 (Gson 기본, Jackson 기본)
├── regex/                 # 정규식 처리 보일러플레이트 (split, matches, replaceAll)
│   └── exercise/          # 정규식 연습 예제 (split 패턴, matches/replaceAll 패턴)
├── datastructure/         # 자료구조 활용 (스택, 큐, 덱, 우선순위 큐, thread-safe 큐/덱)
├── algorithm/             # 알고리즘 구현
└── http/                  # OkHttp 클라이언트 학습 + Spring 서버 통합 테스트
    ├── OkHttp31InitTest.java     # 클라이언트 초기화
    ├── OkHttp32ConfigTest.java   # 커넥션풀, 인터셉터, 타임아웃
    ├── OkHttp33GetPostTest.java  # GET/POST + MockWebServer
    ├── OkHttp34CrudTest.java     # CRUD 응답 처리
    ├── OkHttp35JsonTest.java     # 다단계 JSON 직렬화/역직렬화
    ├── OkHttp37ServerIntegrationTest.java  # 실제 Spring 서버 통합
    ├── api_response_examples.md  # UserController 응답 예시
    └── exercise/                 # OkHttp 연습 예제 (기초/JSON 교환/서버 통합)
```

## 서버 구조 요점 (http 패키지)

- **CUD는 단일 도메인**: `UserRepository`, `WishProductRepository`로 분리
- **READ는 복합 도메인**: `UserQuery`가 두 repository를 조합하여 `UserWithWishProducts` 반환
- **응답 DTO가 결합 책임**: `UserResponse`가 `User` + `List<WishProduct>`를 합쳐 다단계 JSON 생성
- **샘플 데이터**: `SampleDataFactory` (정의), `SampleDataCreator` (시드), `SampleDataConfiguration` (빈 구성)
  - `app.sample-data.enabled=false` 로 시드 비활성화 가능 (테스트에서 사용)
