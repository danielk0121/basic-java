# todo_19: 정규식(Regex) 처리

## 목표
Java 환경에서 자주 사용하는 정규식 패턴을 `split / matches / replaceAll`로 학습

## 구현 내용

### 테스트 파일
- `src/test/java/dev/danielk/basicjava/regex/`

### 파일 링크
- [RegexSplitTest.java](../../src/test/java/dev/danielk/basicjava/regex/RegexSplitTest.java)

### 기본 문자 클래스
- `.` — 임의의 한 문자 (줄바꿈 제외)
- `\d` / `\D` — 숫자 / 숫자가 아닌 것
- `\w` / `\W` — 알파벳·숫자·언더바 / 그 외
- `\s` / `\S` — 공백 문자 / 공백이 아닌 것

### 수량자
- `*` 0번 이상, `+` 1번 이상, `?` 0 또는 1번
- `{n}` 정확히 n번, `{n,}` n번 이상, `{n,m}` n~m번

### 경계/위치
- `^` 문자열 시작, `$` 문자열 끝, `\b` 단어 경계, `|` OR

### split 기본 패턴 (6개)
- `splitByWhitespace` — 단순 공백 구분
- `splitByMultipleWhitespace` — `\s+` 연속 공백·탭 구분
- `extractNumbers` — `\D+`로 분리해 숫자 추출
- `extractLetters` — `\d+`로 분리해 영문자 추출
- `splitByComma` — 쉼표 구분
- `splitByMultipleDelimiters` — `[,;|/]` 복합 구분기호

### 실전 패턴 (5개)
- `validateOnlyNumbers` — `^\d+$` 숫자만 확인
- `validateEmail` — 이메일 형식 검증
- `validatePhoneNumber` — 한국 휴대폰 번호 (`(?:...)` 비캡처 그룹 활용)
- `validatePassword` — 영문·숫자 포함 8~16자 (`(?=...)` 전방 탐색)
- `normalizeWhitespace` — `\s+` + `replaceAll` 공백 정규화

### 주의사항
- 자바에서 역슬래시 두 번 필요: 정규식 `\d` → 자바 문자열 `"\\d"`
- `matches()`는 전체 문자열이 패턴과 일치해야 `true` (암묵적 `^...$`)

## 완료일
2026-04-29
