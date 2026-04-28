# todo 19_1: 정규식(Regex) 연습 예제

> 보일러플레이트 — todo 19 정규식 처리에서 다룬 split / matches / replaceAll 패턴을 실전 문제로 연습

## 작업 목록
- [x] Ex01 split 패턴 — 문제/답안 작성
- [x] Ex02 matches 패턴 — 문제/답안 작성

## 예제별 계획

- Ex01 split 패턴
  - 시나리오: 연속 공백 구분, 숫자 추출, 복합 구분기호 분리
  - 커버 메서드: `str.split("\\s+")`, `str.split("\\D+")`, `str.split("[,;|/]")`

- Ex02 matches / replaceAll 패턴
  - 시나리오: 숫자만 확인, 이메일 검증, 전화번호 검증, 비밀번호 검증, 공백 정규화
  - 커버 메서드: `str.matches(pattern)`, `str.replaceAll(pattern, replacement)`

## 구현 파일
- `src/test/java/dev/danielk/basicjava/regex/exercise/`

## 파일 링크
- [Ex01_SplitPatternTest.java](../../src/test/java/dev/danielk/basicjava/regex/exercise/Ex01_SplitPatternTest.java) / [Ex01_SplitPatternAnswer.java](../../src/test/java/dev/danielk/basicjava/regex/exercise/Ex01_SplitPatternAnswer.java)
- [Ex02_MatchesPatternTest.java](../../src/test/java/dev/danielk/basicjava/regex/exercise/Ex02_MatchesPatternTest.java) / [Ex02_MatchesPatternAnswer.java](../../src/test/java/dev/danielk/basicjava/regex/exercise/Ex02_MatchesPatternAnswer.java)

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: `[문제]` / `[풀이]` 주석 분리 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋

## 완료일
2026-04-29
