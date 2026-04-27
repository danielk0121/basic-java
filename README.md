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

## 프로젝트 구조

```
src/main/java/dev/danielk/basicjava/
└── http/
    ├── server/ # HTTP 서버 처리 (컨트롤러)
    └── client/ # HTTP 클라이언트 처리

src/test/java/dev/danielk/basicjava/
├── string/     # 문자열 처리
├── array/      # 배열/컬렉션 처리
└── algorithm/  # 알고리즘 구현
```

## 실행 방법

```bash
./gradlew bootRun
```

## 빌드 및 테스트

```bash
./gradlew build
./gradlew test
```
