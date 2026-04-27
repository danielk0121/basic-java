# 구현 목록

## 프로젝트 목표
- Java SDK를 활용한 보일러플레이트 코드 구현
- 단순한 알고리즘 구현

## 범위 정의
- 포함: HTTP IO 처리
- 제외: 파일 IO, JDBC IO 등 그 외 IO 처리

---

## 1. 보일러플레이트

### 문자열 처리
- 문자열 자르기: substring, split
- 문자열 검색: indexOf, contains, startsWith, endsWith
- 문자열 변환: 대소문자, trim, replace
- 문자열 뒤집기
- 문자열 반복 및 합치기: repeat, join, concat
- 문자열 숫자 변환: parseInt, valueOf, toString
- 문자열 포맷: String.format, printf
- 정규식 매칭 및 추출

### 배열 처리
- 배열 정렬: 오름차순, 내림차순, 커스텀 비교자
- 배열 검색: 선형 탐색, 이진 탐색
- 배열 복사: 얕은 복사, 깊은 복사
- 배열 변환: 배열 → 리스트, 리스트 → 배열
- 배열 필터링 및 매핑: stream filter, map
- 다차원 배열 순회
- 배열 최대/최소/합계

### 컬렉션 처리
- 리스트 집합 연산: 합집합, 교집합, 차집합
- 리스트 정렬: 오름차순, 내림차순, 커스텀 비교자
- 리스트 중복 제거
- 맵 순회: keySet, entrySet, values
- 맵 빈도수 집계: 문자/단어 등장 횟수
- 맵 정렬: 키 기준, 값 기준
- 스택/큐/덱 기본 사용
- 우선순위 큐(힙) 기본 사용

### 숫자/수학 처리
- 최대공약수(GCD), 최소공배수(LCM)
- 소수 판별 및 소수 목록 생성
- 진법 변환: 10진수 ↔ 2/8/16진수
- 숫자 자릿수 추출 및 합계
- 순열, 조합 생성

### 입출력 처리
- 표준 입력 읽기: Scanner, BufferedReader
- 빠른 출력: BufferedWriter, StringBuilder 누적 출력

### HTTP 처리
- RestTemplate GET/POST 요청
- HttpClient GET/POST 요청 (Java 11+)
- JSON 직렬화/역직렬화: ObjectMapper
- 쿼리 파라미터 및 헤더 설정
- REST 컨트롤러 기본 구조: GET/POST/PUT/DELETE

### 날짜/시간 처리
- 현재 날짜/시간 가져오기
- 날짜 포맷 변환: LocalDate, LocalDateTime, DateTimeFormatter
- 날짜 연산: 더하기, 빼기, 차이 계산
- 타임존 처리

### 랜덤 처리
- 정수 난수 생성: Random, ThreadLocalRandom
- 범위 지정 난수 생성
- 실수 난수 생성
- 리스트/배열 셔플
- 랜덤 요소 추출

---

## 2. 알고리즘

### 재귀/완전탐색
- 팩토리얼
- 피보나치
- DFS/BFS 기본 구조
- 백트래킹 기본 구조
