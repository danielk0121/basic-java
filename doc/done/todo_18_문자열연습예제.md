# todo 18: 문자열 처리 연습 예제

> 보일러플레이트 — todo 11 문자열 처리에서 다룬 JDK 메서드를 실전 문제로 연습

## 작업 목록
- [x] Ex01 로그 파서 — 문제/답안 작성
- [x] Ex02 단어 통계 분석기 — 문제/답안 작성
- [x] Ex03 이름 포매터 — 문제/답안 작성
- [x] Ex04 비밀번호 검증기 — 문제/답안 작성

## 구현 파일
- `src/test/java/dev/danielk/basicjava/string/exercise/Ex01_LogParserTest.java`
- `src/test/java/dev/danielk/basicjava/string/exercise/Ex01_LogParserAnswer.java`
- `src/test/java/dev/danielk/basicjava/string/exercise/Ex02_WordStatTest.java`
- `src/test/java/dev/danielk/basicjava/string/exercise/Ex02_WordStatAnswer.java`
- `src/test/java/dev/danielk/basicjava/string/exercise/Ex03_NameFormatterTest.java`
- `src/test/java/dev/danielk/basicjava/string/exercise/Ex03_NameFormatterAnswer.java`
- `src/test/java/dev/danielk/basicjava/string/exercise/Ex04_PasswordValidatorTest.java`
- `src/test/java/dev/danielk/basicjava/string/exercise/Ex04_PasswordValidatorAnswer.java`

## 예제별 커버 메서드

- Ex01 로그 파서
  - `indexOf`, `substring`, `split`, `strip`, `contains`, `startsWith`, `endsWith`
  - `matches`, `Pattern.compile`, `Matcher.find`, `Matcher.group`

- Ex02 단어 통계 분석기
  - `toLowerCase`, `replaceAll`, `isBlank`, `split`
  - `chars` (IntStream), `toCharArray`, `charAt`, `length`
  - `StringBuilder.reverse`, `String.join`

- Ex03 이름 포매터
  - `strip`, `toUpperCase`, `String.format`, `String.join`
  - `substring`, `charAt`, `length`, `compareToIgnoreCase`
  - `repeat`

- Ex04 비밀번호 검증기
  - `isBlank`, `isEmpty`, `length`, `matches`
  - `charAt`, `toCharArray`, `subSequence`, `contains`
  - `repeat`, `substring`
