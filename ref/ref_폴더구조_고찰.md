# 폴더 구조 고찰: doc 분리 vs src/test 통합

> 작성일: 2026-04-27
> 배경: basic-java 완성 후 Python, Go 등 다른 언어 대상 프로젝트를 추가로 만들 예정.
> 각 프로젝트는 동일한 학습 구조(보일러플레이트 + 학습 테스트 + 연습 문제/답안)를 따른다.

---

## 비교한 두 구조

### 안 A — 현재 채택: doc과 src 분리

```
doc/
  ing/todo_12_1_배열연습예제.md
  done/todo_12_배열처리.md
src/test/java/.../array/
  ArrayTest.java
  exercise/
    Ex01_GradeProcessorTest.java
    Ex01_GradeProcessorAnswer.java
```

### 안 B — 대안: src/test 안에 md 포함

```
src/test/java/.../array/
  README.md
  ArrayTest.java
  exercise/
    README.md
    Ex01_GradeProcessorTest.java
    Ex01_GradeProcessorAnswer.java
```

---

## 결론: 안 A (분리) 채택

---

## 근거

- 언어 독립적 구조
  - `doc/` 구조는 언어가 바뀌어도 그대로 재사용 가능
  - todo 파일 형식, `ing → done` 흐름, 번호 체계가 Python/Go 프로젝트에서도 동일하게 적용됨
  - 안 B는 언어마다 소스 트리 경로 규칙이 달라서(Python은 `src/` 없음, Go는 패키지 구조 다름) 구조가 달라짐

- 빌드 도구와의 충돌 없음
  - Gradle/Maven은 `src/test`를 Java 소스 트리로 간주
  - 해당 경로에 md를 두면 빌드 경고 발생 가능, `.gitignore`나 IDE 설정에서 예외 처리 필요

- todo와 코드의 생명주기 분리
  - 코드는 작성 후 그 자리에 유지됨
  - todo는 `ing → done` 으로 이동하는 흐름이 있음
  - 이 이동이 `src/` 안에서 일어나면 코드 트리가 오염되고 어색함

---

## 현재 구조의 약점과 보완 방법

약점: todo 문서 → 구현 파일은 링크로 연결되지만, 반대 방향(코드 → todo)은 연결이 없음.

보완: 각 테스트 파일 상단 Javadoc에 대응 todo 번호를 한 줄 명시

```java
/**
 * 배열 처리 보일러플레이트
 * @see <a href="../../../../../doc/done/todo_12_배열처리.md">todo 12</a>
 */
```

이 패턴은 Python/Go 프로젝트에서도 docstring/주석으로 동일하게 적용 가능.
현재는 필수 적용하지 않으나, 파일이 많아질 때 도입을 검토한다.

---

## 다른 언어 프로젝트 적용 시 참고

동일한 `doc/` 구조를 그대로 사용한다.

```
doc/
  ing/    — 진행 중인 todo
  done/   — 완료된 todo
  hold/   — 보류 중인 todo
  roadmap_구현목록.md
ref/      — 리서치 결과, 비교 문서
```

todo 번호 체계도 동일하게 유지한다.
- `XX` — 보일러플레이트 (예: 11 문자열, 12 배열)
- `XX_1` — 해당 보일러플레이트의 연습 예제
