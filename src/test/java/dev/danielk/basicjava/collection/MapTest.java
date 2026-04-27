package dev.danielk.basicjava.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * 맵 처리 보일러플레이트
 * - 순회, 빈도수 집계, 정렬, 기본값 처리, 병합
 */
@DisplayName("맵 처리")
class MapTest {

    // ── 순회 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("순회: keySet / entrySet / values")
    void iterate() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("apple", 3);
        map.put("banana", 1);
        map.put("cherry", 2);

        // keySet: 키만 순회
        List<String> keys = new ArrayList<>(map.keySet());
        assertThat(keys).containsExactly("apple", "banana", "cherry");

        // values: 값만 순회
        List<Integer> values = new ArrayList<>(map.values());
        assertThat(values).containsExactly(3, 1, 2);

        // entrySet: 키-값 쌍 순회 (가장 효율적)
        Map<String, Integer> collected = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            collected.put(entry.getKey(), entry.getValue());
        }
        assertThat(collected).containsExactly(
                entry("apple", 3), entry("banana", 1), entry("cherry", 2)
        );
    }

    // ── 빈도수 집계 ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("빈도수 집계: 문자 등장 횟수 / 단어 등장 횟수")
    void frequency() {
        // 문자 빈도수
        String text = "hello world";
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : text.toCharArray()) {
            charCount.merge(c, 1, Integer::sum);
        }
        assertThat(charCount.get('l')).isEqualTo(3);
        assertThat(charCount.get('o')).isEqualTo(2);

        // 단어 빈도수
        String sentence = "apple banana apple cherry banana apple";
        Map<String, Long> wordCount = Arrays.stream(sentence.split(" "))
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        assertThat(wordCount.get("apple")).isEqualTo(3L);
        assertThat(wordCount.get("banana")).isEqualTo(2L);
    }

    // ── 정렬 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("정렬: 키 기준 / 값 기준")
    void sort() {
        Map<String, Integer> map = new HashMap<>();
        map.put("banana", 2);
        map.put("apple", 5);
        map.put("cherry", 1);

        // 키 기준 오름차순
        Map<String, Integer> byKey = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
        assertThat(new ArrayList<>(byKey.keySet())).containsExactly("apple", "banana", "cherry");

        // 값 기준 내림차순
        Map<String, Integer> byValueDesc = map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
        assertThat(new ArrayList<>(byValueDesc.values())).containsExactly(5, 2, 1);
    }

    // ── 기본값 처리 ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("기본값 처리: getOrDefault / putIfAbsent")
    void defaultValue() {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 3);

        // getOrDefault: 키가 없으면 기본값 반환
        assertThat(map.getOrDefault("apple", 0)).isEqualTo(3);
        assertThat(map.getOrDefault("banana", 0)).isEqualTo(0);

        // putIfAbsent: 키가 없을 때만 삽입
        map.putIfAbsent("banana", 5);
        map.putIfAbsent("apple", 99);  // 이미 존재하므로 무시
        assertThat(map.get("banana")).isEqualTo(5);
        assertThat(map.get("apple")).isEqualTo(3);   // 기존 값 유지
    }

    // ── 병합 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("병합: merge / compute / computeIfAbsent")
    void merge() {
        // merge: 키가 없으면 삽입, 있으면 remapping 함수 적용
        Map<String, Integer> map = new HashMap<>();
        map.merge("apple", 1, Integer::sum);   // 없음 → 1
        map.merge("apple", 1, Integer::sum);   // 있음 → 1+1=2
        map.merge("apple", 1, Integer::sum);   // 있음 → 2+1=3
        assertThat(map.get("apple")).isEqualTo(3);

        // compute: 키의 현재 값을 기반으로 새 값 계산
        Map<String, Integer> scoreMap = new HashMap<>(Map.of("a", 10, "b", 20));
        scoreMap.compute("a", (k, v) -> v == null ? 1 : v + 5);
        assertThat(scoreMap.get("a")).isEqualTo(15);

        // computeIfAbsent: 키가 없을 때만 계산하여 삽입 (캐싱 패턴)
        Map<String, List<Integer>> groupMap = new HashMap<>();
        groupMap.computeIfAbsent("odd", k -> new ArrayList<>()).add(1);
        groupMap.computeIfAbsent("odd", k -> new ArrayList<>()).add(3);
        assertThat(groupMap.get("odd")).containsExactly(1, 3);
    }
}
