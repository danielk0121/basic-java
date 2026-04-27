package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 04: 단어 빈도 분석기
 *
 * 텍스트에서 단어 빈도수를 집계하고 두 텍스트의 단어 맵을 병합하세요.
 *
 * 사용해야 할 메서드:
 *   merge, getOrDefault, putIfAbsent, entrySet 정렬(값 기준)
 */
@DisplayName("연습 04: 단어 빈도 분석기")
class Ex04_WordFrequencyTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 공백으로 구분된 텍스트에서 단어별 등장 횟수를 맵으로 반환하세요.
     * 힌트: split(" "), merge(word, 1, Integer::sum)
     */
    Map<String, Integer> countWords(String text) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: 단어 빈도 집계")
    void test_countWords() {
        Map<String, Integer> result = countWords("apple banana apple cherry banana apple");
        assertThat(result.get("apple")).isEqualTo(3);
        assertThat(result.get("banana")).isEqualTo(2);
        assertThat(result.get("cherry")).isEqualTo(1);
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 빈도 맵에서 등장 횟수가 가장 많은 상위 n개 단어를 반환하세요. (빈도 내림차순)
     * 힌트: entrySet 스트림 → sorted(Map.Entry.<K,V>comparingByValue().reversed()) → limit → map(key)
     */
    List<String> topNWords(Map<String, Integer> wordCount, int n) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 상위 N개 빈도 단어")
    void test_topNWords() {
        Map<String, Integer> wordCount = countWords("apple banana apple cherry banana apple cherry cherry");
        List<String> top2 = topNWords(wordCount, 2);
        assertThat(top2).hasSize(2);
        assertThat(top2).containsAnyOf("apple", "cherry"); // apple=3, cherry=3 공동 최다
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 두 단어 빈도 맵을 병합하여 단어 등장 횟수를 합산한 맵을 반환하세요.
     * 힌트: 결과 맵에 mapA 복사 후 mapB를 merge(key, value, Integer::sum)으로 병합
     */
    Map<String, Integer> mergeCounts(Map<String, Integer> mapA, Map<String, Integer> mapB) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 두 빈도 맵 병합")
    void test_mergeCounts() {
        Map<String, Integer> a = Map.of("apple", 3, "banana", 1);
        Map<String, Integer> b = Map.of("banana", 2, "cherry", 4);
        Map<String, Integer> result = mergeCounts(a, b);
        assertThat(result.get("apple")).isEqualTo(3);
        assertThat(result.get("banana")).isEqualTo(3);
        assertThat(result.get("cherry")).isEqualTo(4);
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 단어가 맵에 없을 때만 기본값 0으로 등록하고, 해당 단어의 카운트를 반환하세요.
     * 힌트: putIfAbsent(word, 0) 후 getOrDefault(word, 0)
     */
    int getCount(Map<String, Integer> wordCount, String word) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: 없는 단어 기본값 처리")
    void test_getCount() {
        Map<String, Integer> wordCount = new java.util.HashMap<>(Map.of("apple", 5));
        assertThat(getCount(wordCount, "apple")).isEqualTo(5);
        assertThat(getCount(wordCount, "banana")).isEqualTo(0);
        assertThat(wordCount).containsKey("banana");
    }
}
