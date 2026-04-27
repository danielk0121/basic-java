# basic-java

자바 보일러플레이트 코드 모음집

## 목적

자주 사용하는 자바 코드 블럭을 스프링 부트 환경에서 정리한 레퍼런스 프로젝트

- 문자열 변환 처리
- 배열 처리
- HTTP 서버/클라이언트 처리

## 기술 스택

- Java 17
- Spring Boot 3.5.0
- Gradle

## 프로젝트 구조

```
src/main/java/com/example/basicjava/
├── string/     # 문자열 변환 처리
├── array/      # 배열 처리
└── http/
    ├── server/ # HTTP 서버 처리
    └── client/ # HTTP 클라이언트 처리
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
