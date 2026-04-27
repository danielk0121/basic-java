# basic-java

자바 레퍼런스 코드 모음집

## 목적

- Java SDK를 활용한 보일러플레이트 코드 구현
- 단순한 알고리즘 구현

## 로드맵

[구현 목록 보기](doc/roadmap_구현목록.md)

## 기술 스택

- Java 17
- Spring Boot 3.5.0
- Gradle

## 코드 작성 규칙

- `src/test` — 보일러플레이트, 알고리즘 코드 작성. JUnit으로 입력/출력 검증
- `src/main` — HTTP 컨트롤러, 클라이언트 등 실행 가능한 서버 코드만 작성

## 설계 결정

- [폴더 구조 고찰: doc 분리 vs src/test 통합](doc/ref_폴더구조_고찰.md)

## 연습 예제 구조

각 주제별로 `exercise/` 패키지에 문제(`*Test.java`)와 답안(`*Answer.java`)이 쌍으로 존재합니다.

- 문제 파일: 미구현 상태(`@Disabled` 처리) — 직접 풀어보는 용도
- 답안 파일: 풀이 코드 + 설명 주석 — 참고용

## 프로젝트 구조

```
src/main/java/dev/danielk/basicjava/
└── http/
    ├── server/ # HTTP 서버 처리 (컨트롤러)
    └── client/ # HTTP 클라이언트 처리

src/test/java/dev/danielk/basicjava/
├── string/
│   ├── StringBasicTest.java
│   ├── StringBuilderTest.java
│   ├── StringTransformTest.java
│   └── exercise/          # 문자열 연습 예제
│       ├── Ex01_LogParserTest.java / Answer.java
│       ├── Ex02_WordStatTest.java / Answer.java
│       ├── Ex03_NameFormatterTest.java / Answer.java
│       └── Ex04_PasswordValidatorTest.java / Answer.java
├── array/
│   ├── ArrayTest.java
│   └── exercise/          # 배열 연습 예제
│       ├── Ex01_GradeProcessorTest.java / Answer.java
│       ├── Ex02_InventoryManagerTest.java / Answer.java
│       └── Ex03_SurveyAnalyzerTest.java / Answer.java
├── collection/
│   ├── ListTest.java
│   ├── MapTest.java
│   └── exercise/          # 리스트/맵 연습 예제
│       ├── Ex01_CartSystemTest.java / Answer.java
│       ├── Ex02_StudentGradeTest.java / Answer.java
│       ├── Ex03_TeamRosterTest.java / Answer.java
│       ├── Ex04_WordFrequencyTest.java / Answer.java
│       ├── Ex05_ScoreAggregatorTest.java / Answer.java
│       └── Ex06_GroupClassifierTest.java / Answer.java
├── math/
│   ├── MathTest.java
│   └── exercise/          # 수학 연습 예제
│       ├── Ex01_CryptoHelperTest.java / Answer.java
│       └── Ex02_NumberPuzzleTest.java / Answer.java
├── datetime/
│   ├── DateTimeTest.java
│   └── exercise/          # 날짜/시간 연습 예제
│       ├── Ex01_EventSchedulerTest.java / Answer.java
│       ├── Ex02_WorkHoursCalculatorTest.java / Answer.java
│       └── Ex03_MeetingTimeConverterTest.java / Answer.java
├── random/
│   ├── RandomTest.java
│   └── exercise/          # 랜덤 연습 예제
│       ├── Ex01_CardDeckTest.java / Answer.java
│       └── Ex02_RandomQuizTest.java / Answer.java
└── algorithm/             # 알고리즘 구현
```
