package dev.danielk.basicjava.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 리스트 처리 보일러플레이트
 * - 집합 연산, 정렬, 중복 제거, 슬라이싱, 뒤집기, 최대/최소, 빈도수
 */
@DisplayName("리스트 처리")
class ListTest {

    // ── 집합 연산 ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("집합 연산: 합집합 / 교집합 / 차집합")
    void setOperations() {
        List<Integer> a = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Integer> b = new ArrayList<>(List.of(3, 4, 5, 6, 7));

        // 합집합
        List<Integer> union = new ArrayList<>(a);
        union.addAll(b);
        union = union.stream().distinct().sorted().collect(Collectors.toList());
        assertThat(union).containsExactly(1, 2, 3, 4, 5, 6, 7);

        // 교집합
        List<Integer> intersection = new ArrayList<>(a);
        intersection.retainAll(b);
        assertThat(intersection).containsExactly(3, 4, 5);

        // 차집합 (a - b)
        List<Integer> difference = new ArrayList<>(a);
        difference.removeAll(b);
        assertThat(difference).containsExactly(1, 2);
    }

    // ── 정렬 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("정렬: 오름차순 / 내림차순 / 커스텀 비교자")
    void sort() {
        List<Integer> numbers = new ArrayList<>(List.of(5, 2, 8, 1, 9));

        Collections.sort(numbers);
        assertThat(numbers).containsExactly(1, 2, 5, 8, 9);

        numbers.sort(Comparator.reverseOrder());
        assertThat(numbers).containsExactly(9, 8, 5, 2, 1);

        // 커스텀 비교자: 문자열 길이 오름차순, 길이 같으면 사전순
        List<String> words = new ArrayList<>(List.of("banana", "fig", "apple", "kiwi"));
        words.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        assertThat(words).containsExactly("fig", "kiwi", "apple", "banana");
    }

    // ── 중복 제거 ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("중복 제거: stream distinct / LinkedHashSet (순서 유지)")
    void deduplicate() {
        List<Integer> withDups = List.of(1, 2, 2, 3, 3, 3, 4);

        // stream distinct: 순서 유지
        List<Integer> distinct = withDups.stream().distinct().collect(Collectors.toList());
        assertThat(distinct).containsExactly(1, 2, 3, 4);

        // LinkedHashSet: 삽입 순서 유지하면서 중복 제거
        List<Integer> viaSet = new ArrayList<>(new LinkedHashSet<>(withDups));
        assertThat(viaSet).containsExactly(1, 2, 3, 4);
    }

    // ── 슬라이싱 ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("슬라이싱: subList (from 이상 to 미만)")
    void subList() {
        List<String> list = List.of("a", "b", "c", "d", "e");

        List<String> sub = list.subList(1, 4);
        assertThat(sub).containsExactly("b", "c", "d");

        // subList는 원본의 뷰 — 독립 복사본이 필요하면 new ArrayList<>()로 감쌈
        List<String> copy = new ArrayList<>(list.subList(1, 4));
        assertThat(copy).containsExactly("b", "c", "d");
    }

    // ── 뒤집기 ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("뒤집기: Collections.reverse")
    void reverse() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        Collections.reverse(list);
        assertThat(list).containsExactly(5, 4, 3, 2, 1);
    }

    // ── 최대/최소 ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("최대/최소: Collections.max / Collections.min")
    void maxMin() {
        List<Integer> numbers = List.of(3, 1, 4, 1, 5, 9, 2, 6);

        assertThat(Collections.max(numbers)).isEqualTo(9);
        assertThat(Collections.min(numbers)).isEqualTo(1);

        // 커스텀 비교자: 문자열 길이 기준 최대/최소
        List<String> words = List.of("hi", "hello", "hey");
        assertThat(Collections.max(words, Comparator.comparingInt(String::length))).isEqualTo("hello");
        assertThat(Collections.min(words, Comparator.comparingInt(String::length))).isEqualTo("hi");
    }

    // ── 빈도수 집계 ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("빈도수 집계: Collections.frequency / stream groupingBy")
    void frequency() {
        List<String> fruits = List.of("apple", "banana", "apple", "cherry", "banana", "apple");

        // 특정 요소의 등장 횟수
        assertThat(Collections.frequency(fruits, "apple")).isEqualTo(3);
        assertThat(Collections.frequency(fruits, "banana")).isEqualTo(2);

        // 전체 빈도수 맵으로 집계
        Map<String, Long> freqMap = fruits.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        assertThat(freqMap.get("apple")).isEqualTo(3L);
        assertThat(freqMap.get("banana")).isEqualTo(2L);
        assertThat(freqMap.get("cherry")).isEqualTo(1L);
    }
}
