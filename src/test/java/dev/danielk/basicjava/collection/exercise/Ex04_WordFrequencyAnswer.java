package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 04 답안: 단어 빈도 분석기
 */
@DisplayName("연습 04 답안: 단어 빈도 분석기")
class Ex04_WordFrequencyAnswer {

    /** merge: 키 없으면 value(1) 삽입, 있으면 기존값 + 1 */
    Map<String, Integer> countWords(String text) {
        Map<String, Integer> result = new HashMap<>();
        for (String word : text.split(" ")) {
            result.merge(word, 1, Integer::sum);
        }
        return result;
    }

    @Test
    @DisplayName("문제 1: 단어 빈도 집계")
    void test_countWords() {
        Map<String, Integer> result = countWords("apple banana apple cherry banana apple");
        assertThat(result.get("apple")).isEqualTo(3);
        assertThat(result.get("banana")).isEqualTo(2);
        assertThat(result.get("cherry")).isEqualTo(1);
    }

    /** entrySet 스트림 → 값 내림차순 정렬 → limit(n) → 키만 추출 */
    List<String> topNWords(Map<String, Integer> wordCount, int n) {
        return wordCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("문제 2: 상위 N개 빈도 단어")
    void test_topNWords() {
        Map<String, Integer> wordCount = countWords("apple banana apple cherry banana apple cherry cherry");
        List<String> top2 = topNWords(wordCount, 2);
        assertThat(top2).hasSize(2);
        assertThat(top2).containsAnyOf("apple", "cherry");
    }

    /** mapA를 결과 맵에 복사 후 mapB의 각 항목을 merge로 합산 */
    Map<String, Integer> mergeCounts(Map<String, Integer> mapA, Map<String, Integer> mapB) {
        Map<String, Integer> result = new HashMap<>(mapA);
        mapB.forEach((k, v) -> result.merge(k, v, Integer::sum));
        return result;
    }

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

    /** putIfAbsent: 키 없을 때만 삽입 → getOrDefault로 값 반환 */
    int getCount(Map<String, Integer> wordCount, String word) {
        wordCount.putIfAbsent(word, 0);
        return wordCount.getOrDefault(word, 0);
    }

    @Test
    @DisplayName("문제 4: 없는 단어 기본값 처리")
    void test_getCount() {
        Map<String, Integer> wordCount = new HashMap<>(Map.of("apple", 5));
        assertThat(getCount(wordCount, "apple")).isEqualTo(5);
        assertThat(getCount(wordCount, "banana")).isEqualTo(0);
        assertThat(wordCount).containsKey("banana");
    }
}
