# 계획: 10번대 연습 예제 작업

> 각 보일러플레이트 todo(12~17)에 대응하는 연습 예제를 todo 18(문자열) 방식과 동일하게 작성한다.
> 작업 단위는 todo별 1개씩, 총 6개.

---

## 작업 단위 목록

- [ ] todo 19: 배열 처리 연습 예제
- [ ] todo 20: 리스트/맵/수학 연습 예제
- [ ] todo 23: 날짜/시간 연습 예제
- [ ] todo 24: 랜덤 처리 연습 예제

> 번호 부여 원칙: 20번대는 알고리즘(21, 22) 예약 → 리스트/맵/수학은 20번으로 묶어 처리.
> 날짜/시간(23), 랜덤(24)은 21~22 뒤에 배치.

---

## todo 19: 배열 처리 연습 예제

**대응 보일러플레이트:** todo 12 (`ArrayTest.java`)

**예제 주제 (3~4개)**

- Ex01 성적 처리기
  - 시나리오: 학생 점수 배열을 받아 정렬, 최고/최저/평균 계산, 등급 부여
  - 커버 메서드: `Arrays.sort`, `Arrays.stream.max/min/average`, `Arrays.copyOf`, `Arrays.fill`, `Arrays.toString`

- Ex02 재고 관리 시스템
  - 시나리오: 상품 재고 2차원 배열에서 특정 상품 검색, 재고 합산, 품절 항목 필터링
  - 커버 메서드: `Arrays.binarySearch`, 다차원 배열 순회, `stream filter/map`, `Arrays.deepToString`

- Ex03 설문 결과 분석기
  - 시나리오: 1~5점 응답 배열에서 빈도수 집계, 배열 복사 후 내림차순 정렬, 배열 비교
  - 커버 메서드: `Arrays.copyOfRange`, `Arrays.equals`, `Arrays.fill`, `Comparator.reverseOrder`, `Arrays.asList → toArray`

**구현 파일 위치**
- `src/test/java/dev/danielk/basicjava/array/exercise/`

---

## todo 20: 리스트/맵/수학 연습 예제

**대응 보일러플레이트:** todo 13 (`ListTest.java`), todo 14 (`MapTest.java`), todo 15 (`MathTest.java`)

**예제 주제 (4개)**

- Ex01 장바구니 시스템 (List)
  - 시나리오: 상품 목록 합집합/교집합/차집합으로 위시리스트 비교, 가격순 정렬, 중복 제거, 상위 N개 슬라이싱
  - 커버 메서드: `addAll/retainAll/removeAll`, `sort`, `distinct`, `subList`, `Collections.reverse`, `Collections.max/min`

- Ex02 단어 빈도 분석기 (Map)
  - 시나리오: 텍스트에서 단어 등장 횟수 집계, 상위 빈도 단어 추출, 두 텍스트의 단어 맵 병합
  - 커버 메서드: `merge`, `compute`, `computeIfAbsent`, `getOrDefault`, `putIfAbsent`, `entrySet 정렬`

- Ex03 암호화 도우미 (수학)
  - 시나리오: 두 수의 GCD/LCM으로 암호 키 생성, 소수 목록으로 인덱스 테이블 구성, 진법 변환으로 인코딩
  - 커버 메서드: `gcd`, `lcm`, `sieve`, `Integer.toBinaryString/toHexString/parseInt`

- Ex04 복권 번호 생성기 (수학 + 순열/조합)
  - 시나리오: 1~45 중 6개 조합 수 계산, 특정 번호 집합의 순열 생성, 자릿수 합 기반 당첨 판별
  - 커버 메서드: `combination`, `permutation`, 자릿수 추출, `String.valueOf.chars`

**구현 파일 위치**
- `src/test/java/dev/danielk/basicjava/collection/exercise/`

---

## todo 23: 날짜/시간 연습 예제

**대응 보일러플레이트:** todo 16 (`DateTimeTest.java`)

**예제 주제 (3개)**

- Ex01 이벤트 일정 관리기
  - 시나리오: 이벤트 시작/종료 날짜 파싱, D-day 계산, 기간(Period) 출력
  - 커버 메서드: `LocalDate.parse`, `DateTimeFormatter`, `ChronoUnit.DAYS.between`, `Period.between`, `plusDays/minusDays`

- Ex02 근무 시간 계산기
  - 시나리오: 출근/퇴근 시각 문자열을 파싱하여 근무 시간(시/분) 계산, 초과근무 판별
  - 커버 메서드: `LocalDateTime.parse`, `ChronoUnit.HOURS/MINUTES.between`, `DateTimeFormatter.ofPattern`

- Ex03 글로벌 회의 시간 변환기
  - 시나리오: 서울 기준 회의 시각을 여러 타임존으로 변환하여 출력
  - 커버 메서드: `ZonedDateTime`, `ZoneId.of`, `withZoneSameInstant`, `Instant`, `format`

**구현 파일 위치**
- `src/test/java/dev/danielk/basicjava/collection/exercise/` (날짜/시간 포함)

---

## todo 24: 랜덤 처리 연습 예제

**대응 보일러플레이트:** todo 17 (`RandomTest.java`)

**예제 주제 (2~3개)**

- Ex01 카드 게임 덱
  - 시나리오: 52장 카드 배열 생성, 셔플, 패(hand) 5장 뽑기, 패에 중복 없음 검증
  - 커버 메서드: `Collections.shuffle`, `subList`, `Random.nextInt`, `ThreadLocalRandom`

- Ex02 랜덤 퀴즈 출제기
  - 시나리오: 문제 풀에서 중복 없이 N개 랜덤 추출, 보기 순서 셔플, 정답 인덱스 계산
  - 커버 메서드: `Collections.shuffle`, `subList`, `nextInt(bound)`, `nextDouble`

**구현 파일 위치**
- `src/test/java/dev/danielk/basicjava/collection/exercise/` (랜덤 포함)

---

## 작업 방식 (todo 18과 동일)

- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스만 작성, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: 풀이 코드 + 설명 주석 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋
- todo 완료 시 `doc/ing/` → `doc/done/` 이동
