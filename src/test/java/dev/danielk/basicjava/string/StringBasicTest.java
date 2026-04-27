package dev.danielk.basicjava.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 문자열 기본 조작
 * - 자르기, 검색, 비교, 문자 접근, 길이/공백, 앞뒤 공백 제거
 */
@DisplayName("문자열 기본 조작")
class StringBasicTest {

    // ── 문자열 자르기 ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("substring: 인덱스 기반 자르기")
    void substring() {
        String s = "Hello, World!";

        assertThat(s.substring(7)).isEqualTo("World!");          // 7번 인덱스부터 끝까지
        assertThat(s.substring(7, 12)).isEqualTo("World");       // 7 이상 12 미만
    }

    @Test
    @DisplayName("split: 구분자로 분리")
    void split() {
        String csv = "apple,banana,cherry";
        String[] parts = csv.split(",");

        assertThat(parts).containsExactly("apple", "banana", "cherry");

        // 정규식 구분자: 공백 1개 이상
        String sentence = "Hello   World  Java";
        String[] words = sentence.split("\\s+");
        assertThat(words).containsExactly("Hello", "World", "Java");
    }

    // ── 문자열 검색 ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("indexOf / contains / startsWith / endsWith")
    void search() {
        String s = "Hello, World!";

        assertThat(s.indexOf("World")).isEqualTo(7);
        assertThat(s.indexOf("xyz")).isEqualTo(-1);              // 없으면 -1
        assertThat(s.contains("World")).isTrue();
        assertThat(s.startsWith("Hello")).isTrue();
        assertThat(s.endsWith("!")).isTrue();
    }

    // ── 문자열 비교 ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("equals / equalsIgnoreCase / compareTo")
    void compare() {
        String a = "Java";
        String b = "java";

        assertThat(a.equals(b)).isFalse();
        assertThat(a.equalsIgnoreCase(b)).isTrue();

        // compareTo: 사전순 비교. 같으면 0, 앞서면 음수, 뒤면 양수
        assertThat("apple".compareTo("banana")).isLessThan(0);
        assertThat("banana".compareTo("apple")).isGreaterThan(0);
        assertThat("apple".compareTo("apple")).isZero();
    }

    // ── 문자 접근 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("charAt / toCharArray / chars")
    void charAccess() {
        String s = "Java";

        assertThat(s.charAt(0)).isEqualTo('J');

        char[] chars = s.toCharArray();
        assertThat(chars).containsExactly('J', 'a', 'v', 'a');

        // chars(): IntStream 반환 → 각 문자의 int 값
        long uppercaseCount = s.chars()
                .filter(Character::isUpperCase)
                .count();
        assertThat(uppercaseCount).isEqualTo(1);
    }

    // ── 길이 및 공백 여부 ─────────────────────────────────────────────────

    @Test
    @DisplayName("length / isEmpty / isBlank")
    void lengthAndBlank() {
        assertThat("Hello".length()).isEqualTo(5);
        assertThat("".isEmpty()).isTrue();
        assertThat("  ".isEmpty()).isFalse();   // 공백 문자가 있어서 false
        assertThat("  ".isBlank()).isTrue();    // 공백만 있으면 true (Java 11+)
        assertThat("Hi".isBlank()).isFalse();
    }

    // ── 앞뒤 공백 제거 ────────────────────────────────────────────────────

    @Test
    @DisplayName("strip / stripLeading / stripTrailing (Java 11+)")
    void strip() {
        String s = "  Hello  ";

        assertThat(s.strip()).isEqualTo("Hello");
        assertThat(s.stripLeading()).isEqualTo("Hello  ");
        assertThat(s.stripTrailing()).isEqualTo("  Hello");

        // trim vs strip: trim은 ASCII 공백만, strip은 유니코드 공백까지 처리
        assertThat(s.trim()).isEqualTo("Hello");
    }
}
