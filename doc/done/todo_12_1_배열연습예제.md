# todo 12_1: 배열 처리 연습 예제

> 보일러플레이트 — todo 12 배열 처리에서 다룬 JDK 메서드를 실전 문제로 연습

## 작업 목록
- [x] Ex01 성적 처리기 — 문제/답안 작성
- [x] Ex02 재고 관리 시스템 — 문제/답안 작성
- [x] Ex03 설문 결과 분석기 — 문제/답안 작성

## 예제별 계획

- Ex01 성적 처리기
  - 시나리오: 학생 점수 배열을 받아 정렬, 최고/최저/평균 계산, 등급 부여
  - 커버 메서드: `Arrays.sort`, `stream max/min/average`, `Arrays.copyOf`, `Arrays.fill`, `Arrays.toString`

- Ex02 재고 관리 시스템
  - 시나리오: 상품 재고 2차원 배열에서 특정 상품 검색, 재고 합산, 품절 항목 필터링
  - 커버 메서드: `Arrays.binarySearch`, 다차원 배열 순회, `stream filter/map`, `Arrays.deepToString`

- Ex03 설문 결과 분석기
  - 시나리오: 1~5점 응답 배열에서 빈도수 집계, 배열 복사 후 내림차순 정렬, 배열 비교
  - 커버 메서드: `Arrays.copyOfRange`, `Arrays.equals`, `Arrays.fill`, `Comparator.reverseOrder`, `Arrays.asList → toArray`

## 구현 파일
- `src/test/java/dev/danielk/basicjava/array/exercise/`

## 파일 링크
- [Ex01_GradeProcessorTest.java](../../src/test/java/dev/danielk/basicjava/array/exercise/Ex01_GradeProcessorTest.java) / [Ex01_GradeProcessorAnswer.java](../../src/test/java/dev/danielk/basicjava/array/exercise/Ex01_GradeProcessorAnswer.java)
- [Ex02_InventoryManagerTest.java](../../src/test/java/dev/danielk/basicjava/array/exercise/Ex02_InventoryManagerTest.java) / [Ex02_InventoryManagerAnswer.java](../../src/test/java/dev/danielk/basicjava/array/exercise/Ex02_InventoryManagerAnswer.java)
- [Ex03_SurveyAnalyzerTest.java](../../src/test/java/dev/danielk/basicjava/array/exercise/Ex03_SurveyAnalyzerTest.java) / [Ex03_SurveyAnalyzerAnswer.java](../../src/test/java/dev/danielk/basicjava/array/exercise/Ex03_SurveyAnalyzerAnswer.java)

## 작업 방식
- 파일명: `Ex0N_주제Test.java` (문제) / `Ex0N_주제Answer.java` (답안)
- 문제 파일: 메서드 시그니처 + Javadoc 힌트 + 테스트 케이스, 구현부는 `throw new UnsupportedOperationException("구현 필요")`
- 답안 파일: 풀이 코드 + 설명 주석 + 동일 테스트 케이스
- 답안 테스트 전체 통과 확인 후 커밋
