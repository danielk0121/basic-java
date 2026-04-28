package dev.danielk.basicjava.string.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: 단어 통계 분석기
 *
 * 문장을 분석해서 다양한 통계를 반환하는 메서드를 완성하세요.
 *
 * 사용해야 할 메서드:
 *   split, toLowerCase, replaceAll, strip/isBlank/isEmpty,
 *   length, charAt, toCharArray, chars (IntStream),
 *   StringBuilder (append, reverse)
 */
@DisplayName("연습 02 답안: 단어 통계 분석기")
class Ex02_WordStatAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 문장을 단어 목록으로 분리하세요.
     * - 대소문자 구분 없이 소문자로 통일
     * - 단어가 아닌 문자(구두점 등)는 제거
     * - 빈 문자열 항목은 제외
     * 힌트: toLowerCase, replaceAll("[^a-zA-Z가-힣\\s]", ""), split("\\s+"), isBlank
     *
     * [풀이] 1. toLowerCase로 소문자 통일
     * 2. replaceAll로 영문자/한글/공백 외 제거
     * 3. strip 후 split("\\s+")으로 분리
     * 4. isBlank 체크로 빈 항목 제외
     */
    List<String> tokenize(String sentence) {
        String cleaned = sentence.toLowerCase()
                .replaceAll("[^a-zA-Z가-힣\\s]", "")
                .strip();
        List<String> result = new ArrayList<>();
        for (String token : cleaned.split("\\s+")) {
            if (!token.isBlank()) result.add(token);
        }
        return result;
    }

    @Test
    @DisplayName("문제 1: 문장 토큰화")
    void test_tokenize() {
        List<String> words = tokenize("Hello, World! Hello Java.");
        assertThat(words).containsExactly("hello", "world", "hello", "java");

        List<String> korean = tokenize("안녕하세요. 반갑습니다!");
        assertThat(korean).containsExactly("안녕하세요", "반갑습니다");
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 문장에서 각 단어의 등장 횟수를 집계해 반환하세요. (대소문자 무시)
     * 힌트: tokenize 재사용, Map
     *
     * [풀이] tokenize로 단어 목록 추출 후 Map에 누적
     * getOrDefault로 기존 값을 가져와 +1
     */
    Map<String, Integer> wordCount(String sentence) {
        Map<String, Integer> counts = new HashMap<>();
        for (String word : tokenize(sentence)) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }
        return counts;
    }

    @Test
    @DisplayName("문제 2: 단어 빈도수 집계")
    void test_wordCount() {
        Map<String, Integer> counts = wordCount("apple banana apple cherry banana apple");
        assertThat(counts.get("apple")).isEqualTo(3);
        assertThat(counts.get("banana")).isEqualTo(2);
        assertThat(counts.get("cherry")).isEqualTo(1);
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 문장에서 모음(a, e, i, o, u)의 총 개수를 세어 반환하세요. (대소문자 무시)
     * 힌트: toLowerCase, toCharArray 또는 chars(), contains 또는 indexOf
     *
     * [풀이] toLowerCase 후 chars()로 IntStream 변환
     * "aeiou".indexOf(c) >= 0 로 모음 판별
     */
    int countVowels(String sentence) {
        String vowels = "aeiou";
        return (int) sentence.toLowerCase()
                .chars()
                .filter(c -> vowels.indexOf(c) >= 0)
                .count();
    }

    @Test
    @DisplayName("문제 3: 모음 개수 세기")
    void test_countVowels() {
        assertThat(countVowels("Hello World")).isEqualTo(3);   // e, o, o
        assertThat(countVowels("Java")).isEqualTo(2);          // a, a
        assertThat(countVowels("AEIOU")).isEqualTo(5);
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 각 단어를 뒤집어서 공백으로 이어붙인 문자열을 반환하세요.
     * 예: "Hello World" → "olleH dlroW"
     * 힌트: split, StringBuilder.reverse(), String.join
     *
     * [풀이] split으로 단어 분리 후 각 단어를 StringBuilder.reverse()로 뒤집기
     * String.join으로 공백 이어붙이기
     */
    String reverseWords(String sentence) {
        String[] words = sentence.split(" ");
        List<String> reversed = new ArrayList<>();
        for (String word : words) {
            reversed.add(new StringBuilder(word).reverse().toString());
        }
        return String.join(" ", reversed);
    }

    @Test
    @DisplayName("문제 4: 각 단어 뒤집기")
    void test_reverseWords() {
        assertThat(reverseWords("Hello World")).isEqualTo("olleH dlroW");
        assertThat(reverseWords("Java is fun")).isEqualTo("avaJ si nuf");
        assertThat(reverseWords("a")).isEqualTo("a");
    }

    // ── 문제 5 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 가장 긴 단어를 반환하세요. 길이가 같은 단어가 여러 개면 먼저 나온 단어를 반환합니다.
     * 힌트: split, length(), 반복 비교
     *
     * [풀이] split으로 분리 후 length() 비교
     * 현재 최장 단어보다 길 때만 갱신 (같으면 유지 → 먼저 나온 단어 보존)
     */
    String longestWord(String sentence) {
        String longest = "";
        for (String word : sentence.split("\\s+")) {
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }

    @Test
    @DisplayName("문제 5: 가장 긴 단어 찾기")
    void test_longestWord() {
        assertThat(longestWord("I love programming")).isEqualTo("programming");
        assertThat(longestWord("cat bat hat")).isEqualTo("cat");   // 길이 같으면 첫 번째
    }

    // ── 문제 6 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 문장의 각 단어 첫 글자를 대문자로 바꾼 문자열을 반환하세요. (Title Case)
     * 예: "hello world" → "Hello World"
     * 힌트: split, charAt(0), toUpperCase, substring, StringBuilder
     *
     * [풀이] split으로 단어 분리
     * 각 단어: charAt(0).toUpperCase() + substring(1) 이어붙이기
     * String.join으로 공백 삽입
     */
    String toTitleCase(String sentence) {
        String[] words = sentence.split(" ");
        List<String> titled = new ArrayList<>();
        for (String word : words) {
            if (word.isEmpty()) continue;
            String capitalized = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            titled.add(capitalized);
        }
        return String.join(" ", titled);
    }

    @Test
    @DisplayName("문제 6: Title Case 변환")
    void test_toTitleCase() {
        assertThat(toTitleCase("hello world")).isEqualTo("Hello World");
        assertThat(toTitleCase("java is awesome")).isEqualTo("Java Is Awesome");
        assertThat(toTitleCase("a")).isEqualTo("A");
    }
}
