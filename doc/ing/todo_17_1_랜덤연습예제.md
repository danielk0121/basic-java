# todo 17_1: 랜덤 처리 연습 예제

> 보일러플레이트 — todo 17 랜덤 처리에서 다룬 JDK 메서드를 실전 문제로 연습

## 작업 목록
- [ ] Ex01 카드 게임 덱 — 문제/답안 작성
- [ ] Ex02 랜덤 퀴즈 출제기 — 문제/답안 작성

## 예제별 계획

- Ex01 카드 게임 덱
  - 시나리오: 52장 카드 배열 생성, 셔플, 패(hand) 5장 뽑기, 패에 중복 없음 검증
  - 커버 메서드: `Collections.shuffle`, `subList`, `Random.nextInt`, `ThreadLocalRandom`

- Ex02 랜덤 퀴즈 출제기
  - 시나리오: 문제 풀에서 중복 없이 N개 랜덤 추출, 보기 순서 셔플, 정답 인덱스 계산
  - 커버 메서드: `Collections.shuffle`, `subList`, `nextInt(bound)`, `nextDouble`

## 구현 파일 위치
- `src/test/java/dev/danielk/basicjava/collection/exercise/`

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: 풀이 코드 + 설명 주석 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋
