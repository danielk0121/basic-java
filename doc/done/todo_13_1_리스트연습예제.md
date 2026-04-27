# todo 13_1: 리스트 처리 연습 예제

> 보일러플레이트 — todo 13 리스트 처리에서 다룬 JDK 메서드를 실전 문제로 연습

## 작업 목록
- [x] Ex01 장바구니 시스템 — 문제/답안 작성
- [x] Ex02 학생 성적 관리 — 문제/답안 작성
- [x] Ex03 팀 로스터 관리 — 문제/답안 작성

## 예제별 계획

- Ex01 장바구니 시스템
  - 시나리오: 상품 목록 합집합/교집합/차집합으로 위시리스트 비교, 가격순 정렬, 중복 제거, 상위 N개 슬라이싱
  - 커버 메서드: `addAll`, `retainAll`, `removeAll`, `sort`, `distinct`, `subList`, `Collections.reverse`, `Collections.max/min`

- Ex02 학생 성적 관리
  - 시나리오: 성적 리스트에서 상위/하위 N명 슬라이싱, 중복 점수 제거, 빈도수 집계
  - 커버 메서드: `sort`, `subList`, `Collections.frequency`, `distinct`, `Collections.max/min`

- Ex03 팀 로스터 관리
  - 시나리오: 두 팀 명단 비교(공통/차이), 명단 뒤집기, 커스텀 정렬
  - 커버 메서드: `retainAll`, `removeAll`, `Collections.reverse`, `sort(Comparator)`

## 구현 파일 위치
- `src/test/java/dev/danielk/basicjava/collection/exercise/`

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: 풀이 코드 + 설명 주석 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋
