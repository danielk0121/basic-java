package dev.danielk.basicjava.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 문자열 변환/가공
 * - 대소문자, 치환, 뒤집기, 반복/합치기, 숫자 변환, 포맷, 정규식
 */
@DisplayName("문자열 변환/가공")
class StringTransformTest {

    // ── 대소문자 변환 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("toUpperCase / toLowerCase")
    void caseConvert() {
        assertThat("hello".toUpperCase()).isEqualTo("HELLO");
        assertThat("HELLO".toLowerCase()).isEqualTo("hello");
    }

    // ── 치환 및 삭제 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("replace / replaceFirst / replaceAll")
    void replace() {
        String s = "aabbcc";

        // replace: 문자 또는 문자열 치환 (모든 매칭)
        assertThat(s.replace("bb", "XX")).isEqualTo("aaXXcc");

        // replaceFirst: 첫 번째 매칭만 치환 (정규식)
        assertThat("aabbaa".replaceFirst("aa", "ZZ")).isEqualTo("ZZbbaa");

        // replaceAll: 모든 매칭 치환 (정규식)
        assertThat("a1b2c3".replaceAll("[0-9]", "")).isEqualTo("abc");   // 숫자 제거

        // 특정 문자 삭제: 빈 문자열로 치환
        assertThat("Hello, World!".replace(",", "").replace("!", "")).isEqualTo("Hello World");
    }

    // ── 문자열 뒤집기 ─────────────────────────────────────────────────────

    @Test
    @DisplayName("문자열 뒤집기: StringBuilder.reverse()")
    void reverse() {
        String reversed = new StringBuilder("Hello").reverse().toString();
        assertThat(reversed).isEqualTo("olleH");
    }

    // ── 반복 및 합치기 ────────────────────────────────────────────────────

    @Test
    @DisplayName("repeat / join / concat")
    void repeatAndJoin() {
        // repeat (Java 11+)
        assertThat("ab".repeat(3)).isEqualTo("ababab");

        // String.join: 구분자로 여러 문자열 합치기
        assertThat(String.join(", ", "apple", "banana", "cherry"))
                .isEqualTo("apple, banana, cherry");

        // join with Iterable
        assertThat(String.join("-", java.util.List.of("a", "b", "c")))
                .isEqualTo("a-b-c");

        // concat: 단순 이어붙이기 (+ 연산자와 동일)
        assertThat("Hello".concat(" World")).isEqualTo("Hello World");
    }

    // ── 숫자 변환 ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("parseInt / valueOf / toString: 문자열 ↔ 숫자 변환")
    void numberConvert() {
        // 문자열 → 숫자
        int i = Integer.parseInt("42");
        double d = Double.parseDouble("3.14");
        assertThat(i).isEqualTo(42);
        assertThat(d).isEqualTo(3.14);

        // 숫자 → 문자열
        String fromInt = Integer.toString(42);
        String fromDouble = String.valueOf(3.14);
        assertThat(fromInt).isEqualTo("42");
        assertThat(fromDouble).isEqualTo("3.14");
    }

    // ── 문자열 포맷 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("String.format: 형식 지정 문자열 생성")
    void format() {
        String result = String.format("이름: %s, 나이: %d, 점수: %.2f", "홍길동", 30, 95.5);
        assertThat(result).isEqualTo("이름: 홍길동, 나이: 30, 점수: 95.50");

        // 정수 자릿수 맞추기 (앞에 0 채우기)
        assertThat(String.format("%05d", 42)).isEqualTo("00042");

        // 16진수 출력
        assertThat(String.format("%x", 255)).isEqualTo("ff");
    }

    // ── 정규식 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("정규식 매칭 및 추출: matches / Pattern / Matcher")
    void regex() {
        // matches: 전체 문자열이 패턴과 일치하는지
        assertThat("12345".matches("[0-9]+")).isTrue();
        assertThat("123a5".matches("[0-9]+")).isFalse();

        // 이메일 간단 검증
        String emailPattern = "^[\\w.+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";
        assertThat("user@example.com".matches(emailPattern)).isTrue();
        assertThat("not-an-email".matches(emailPattern)).isFalse();

        // 패턴에서 그룹 추출
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
        java.util.regex.Matcher m = p.matcher("오늘은 2026-04-27 입니다.");
        assertThat(m.find()).isTrue();
        assertThat(m.group(1)).isEqualTo("2026");
        assertThat(m.group(2)).isEqualTo("04");
        assertThat(m.group(3)).isEqualTo("27");

        // 모든 숫자 추출
        java.util.regex.Matcher numMatcher = java.util.regex.Pattern.compile("\\d+").matcher("a1 b22 c333");
        java.util.List<String> numbers = new java.util.ArrayList<>();
        while (numMatcher.find()) {
            numbers.add(numMatcher.group());
        }
        assertThat(numbers).containsExactly("1", "22", "333");
    }
}
