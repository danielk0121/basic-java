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

## 참고 문서

- [ref_큐_덱_차이점.md](doc/ref_큐_덱_차이점.md)

## 연습 예제 구조

각 주제별로 `exercise/` 패키지에 문제(`*Test.java`)와 답안(`*Answer.java`)이 쌍으로 존재합니다.

- 문제 파일: 미구현 상태(`@Disabled` 처리) — 직접 풀어보는 용도
- 답안 파일: 풀이 코드 + 설명 주석 — 참고용

## 프로젝트 구조

```
src/main/java/dev/danielk/basicjava/
└── http/
    ├── server/            # HTTP 서버 처리 (컨트롤러)
    └── client/            # HTTP 클라이언트 처리

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
└── algorithm/             # 알고리즘 구현
```
