package dev.danielk.basicjava.string.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02: 단어 통계 분석기
 *
 * 문장을 분석해서 다양한 통계를 반환하는 메서드를 완성하세요.
 *
 * 사용해야 할 메서드:
 *   split, toLowerCase, replaceAll, strip/isBlank/isEmpty,
 *   length, charAt, toCharArray, chars (IntStream),
 *   StringBuilder (append, reverse)
 */
@DisplayName("연습 02: 단어 통계 분석기")
class Ex02_WordStatTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 문장을 단어 목록으로 분리하세요.
     * - 대소문자 구분 없이 소문자로 통일
     * - 단어가 아닌 문자(구두점 등)는 제거
     * - 빈 문자열 항목은 제외
     * 힌트: toLowerCase, replaceAll("[^a-zA-Z가-힣\\s]", ""), split("\\s+"), isBlank
     */
    List<String> tokenize(String sentence) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 1: 문장 토큰화")
    void test_tokenize() {
        List<String> words = tokenize("Hello, World! Hello Java.");
        assertThat(words).containsExactly("hello", "world", "hello", "java");

        List<String> korean = tokenize("안녕하세요. 반갑습니다!");
        assertThat(korean).containsExactly("안녕하세요", "반갑습니다");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 문장에서 각 단어의 등장 횟수를 집계해 반환하세요. (대소문자 무시)
     * 힌트: tokenize 재사용, Map
     */
    Map<String, Integer> wordCount(String sentence) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 2: 단어 빈도수 집계")
    void test_wordCount() {
        Map<String, Integer> counts = wordCount("apple banana apple cherry banana apple");
        assertThat(counts.get("apple")).isEqualTo(3);
        assertThat(counts.get("banana")).isEqualTo(2);
        assertThat(counts.get("cherry")).isEqualTo(1);
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 문장에서 모음(a, e, i, o, u)의 총 개수를 세어 반환하세요. (대소문자 무시)
     * 힌트: toLowerCase, toCharArray 또는 chars(), contains 또는 indexOf
     */
    int countVowels(String sentence) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 3: 모음 개수 세기")
    void test_countVowels() {
        assertThat(countVowels("Hello World")).isEqualTo(3);   // e, o, o
        assertThat(countVowels("Java")).isEqualTo(2);          // a, a
        assertThat(countVowels("AEIOU")).isEqualTo(5);
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 각 단어를 뒤집어서 공백으로 이어붙인 문자열을 반환하세요.
     * 예: "Hello World" → "olleH dlroW"
     * 힌트: split, StringBuilder.reverse(), String.join
     */
    String reverseWords(String sentence) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 4: 각 단어 뒤집기")
    void test_reverseWords() {
        assertThat(reverseWords("Hello World")).isEqualTo("olleH dlroW");
        assertThat(reverseWords("Java is fun")).isEqualTo("avaJ si nuf");
        assertThat(reverseWords("a")).isEqualTo("a");
    }

    // ── 문제 5 ────────────────────────────────────────────────────────────────
    /**
     * 가장 긴 단어를 반환하세요. 길이가 같은 단어가 여러 개면 먼저 나온 단어를 반환합니다.
     * 힌트: split, length(), 반복 비교
     */
    String longestWord(String sentence) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 5: 가장 긴 단어 찾기")
    void test_longestWord() {
        assertThat(longestWord("I love programming")).isEqualTo("programming");
        assertThat(longestWord("cat bat hat")).isEqualTo("cat");   // 길이 같으면 첫 번째
    }

    // ── 문제 6 ────────────────────────────────────────────────────────────────
    /**
     * 문장의 각 단어 첫 글자를 대문자로 바꾼 문자열을 반환하세요. (Title Case)
     * 예: "hello world" → "Hello World"
     * 힌트: split, charAt(0), toUpperCase, substring, StringBuilder
     */
    String toTitleCase(String sentence) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 6: Title Case 변환")
    void test_toTitleCase() {
        assertThat(toTitleCase("hello world")).isEqualTo("Hello World");
        assertThat(toTitleCase("java is awesome")).isEqualTo("Java Is Awesome");
        assertThat(toTitleCase("a")).isEqualTo("A");
    }
}
