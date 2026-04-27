# todo 14_1: 맵 처리 연습 예제

> 보일러플레이트 — todo 14 맵 처리에서 다룬 JDK 메서드를 실전 문제로 연습

## 작업 목록
- [ ] Ex01 단어 빈도 분석기 — 문제/답안 작성
- [ ] Ex02 학생 점수 집계기 — 문제/답안 작성
- [ ] Ex03 그룹별 데이터 분류기 — 문제/답안 작성

## 예제별 계획

- Ex01 단어 빈도 분석기
  - 시나리오: 텍스트에서 단어 등장 횟수 집계, 상위 빈도 단어 추출, 두 텍스트의 단어 맵 병합
  - 커버 메서드: `merge`, `getOrDefault`, `putIfAbsent`, `entrySet 정렬(값 기준)`

- Ex02 학생 점수 집계기
  - 시나리오: 과목별 점수 맵 순회, 과목 추가/갱신, 기본값 처리
  - 커버 메서드: `keySet`, `entrySet`, `values`, `getOrDefault`, `compute`, `putIfAbsent`

- Ex03 그룹별 데이터 분류기
  - 시나리오: 상품을 카테고리별로 분류, 카테고리 내 상품 추가, 키 기준 정렬 출력
  - 커버 메서드: `computeIfAbsent`, `merge`, `entrySet 정렬(키 기준)`

## 구현 파일 위치
- `src/test/java/dev/danielk/basicjava/collection/exercise/`

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: 풀이 코드 + 설명 주석 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋
