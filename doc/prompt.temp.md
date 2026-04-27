RandomTest DateTimeTest MathTest 3개 파일은 왜 collection 패키지에 있나 ? 다른 곳으로 옮겨라

=========
❯ /Users/user/study/basic-java/src/test/java/dev/danielk/basicjava/string jdk 사용 연습할 할 수 있도록, 이 패키지에
포함된 내용들을 모두 다 사용할 수 있는 예제를 만들어보자. 예제를 1-4개 정도로 만들어서 test 패키지에 소개된
문자열 jdk 메소드 사용에 익숙해 질 수 있도록 해보자.
문제 작성 자바 파일, 문제 답안 예시 자바 파일 2개를 쌍으로 파일 이름이 비슷하도록 작성하자.

⏺ 3개 파일에서 사용된 메서드를 모두 파악했습니다. 예제 구성을 먼저 설계하겠습니다.

예제 4개 계획:
1. 로그 파싱 — split, substring, indexOf, contains, startsWith, strip, matches, Pattern/Matcher
2. 단어 통계 — toCharArray, chars, charAt, length, isBlank, toLowerCase, replaceAll, StringBuilder
3. 이름 포매터 — trim/strip, toUpperCase/toLowerCase, String.format, join, repeat, compareTo, equals
4. 암호 검증기 — isBlank, isEmpty, matches(정규식), charAt, toCharArray, subSequence, contains



============
❯ 예제 문제와 답안 코드 작성 작업에 대한 todo_xxx 문서를 만들어줘. 그리고 todo_xxx 10번대 작업들에 대해서도
동일하게 처리할껀데, 작업 계획 문서를 먼저 만들어줘



================
