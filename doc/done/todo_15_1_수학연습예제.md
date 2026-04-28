# todo 15_1: 숫자/수학 처리 연습 예제

> 보일러플레이트 — todo 15 숫자/수학 처리에서 다룬 JDK 메서드를 실전 문제로 연습

## 작업 목록
- [x] Ex01 암호화 도우미 — 문제/답안 작성
- [x] Ex02 복권 번호 생성기 — 문제/답안 작성
- [x] Ex03 숫자 퍼즐 검증기 — 문제/답안 작성

## 예제별 계획

- Ex01 암호화 도우미
  - 시나리오: 두 수의 GCD/LCM으로 암호 키 생성, 소수 목록으로 인덱스 테이블 구성, 진법 변환으로 인코딩
  - 커버 메서드: `gcd`, `lcm`, `sieve(에라토스테네스의 체)`, `Integer.toBinaryString/toHexString/parseInt`

- Ex02 복권 번호 생성기
  - 시나리오: 1~45 중 6개 조합 수 계산, 특정 번호 집합의 순열 생성, 자릿수 합 기반 당첨 판별
  - 커버 메서드: `combination`, `permutation`, 자릿수 추출, `String.valueOf.chars`

- Ex03 숫자 퍼즐 검증기
  - 시나리오: 주어진 수가 소수인지, 자릿수 합이 특정 조건 만족인지, 진법 변환 후 회문인지 판별
  - 커버 메서드: `isPrime`, 자릿수 합, `Integer.toBinaryString`, `StringBuilder.reverse`

## 구현 파일
- `src/test/java/dev/danielk/basicjava/math/exercise/`

## 파일 링크
- [Ex01_CryptoHelperTest.java](../../src/test/java/dev/danielk/basicjava/math/exercise/Ex01_CryptoHelperTest.java) / [Ex01_CryptoHelperAnswer.java](../../src/test/java/dev/danielk/basicjava/math/exercise/Ex01_CryptoHelperAnswer.java)
- [Ex02_NumberPuzzleTest.java](../../src/test/java/dev/danielk/basicjava/math/exercise/Ex02_NumberPuzzleTest.java) / [Ex02_NumberPuzzleAnswer.java](../../src/test/java/dev/danielk/basicjava/math/exercise/Ex02_NumberPuzzleAnswer.java)

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: 풀이 코드 + 설명 주석 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋
