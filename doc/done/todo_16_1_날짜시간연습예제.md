# todo 16_1: 날짜/시간 처리 연습 예제

> 보일러플레이트 — todo 16 날짜/시간 처리에서 다룬 JDK 메서드를 실전 문제로 연습

## 작업 목록
- [x] Ex01 이벤트 일정 관리기 — 문제/답안 작성
- [x] Ex02 근무 시간 계산기 — 문제/답안 작성
- [x] Ex03 글로벌 회의 시간 변환기 — 문제/답안 작성

## 예제별 계획

- Ex01 이벤트 일정 관리기
  - 시나리오: 이벤트 시작/종료 날짜 파싱, D-day 계산, 기간(Period) 출력
  - 커버 메서드: `LocalDate.parse`, `DateTimeFormatter`, `ChronoUnit.DAYS.between`, `Period.between`, `plusDays/minusDays`

- Ex02 근무 시간 계산기
  - 시나리오: 출근/퇴근 시각 문자열을 파싱하여 근무 시간(시/분) 계산, 초과근무 판별
  - 커버 메서드: `LocalDateTime.parse`, `ChronoUnit.HOURS/MINUTES.between`, `DateTimeFormatter.ofPattern`

- Ex03 글로벌 회의 시간 변환기
  - 시나리오: 서울 기준 회의 시각을 여러 타임존으로 변환하여 출력
  - 커버 메서드: `ZonedDateTime`, `ZoneId.of`, `withZoneSameInstant`, `Instant`, `format`

## 구현 파일 위치
- `src/test/java/dev/danielk/basicjava/collection/exercise/`

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: 풀이 코드 + 설명 주석 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋
