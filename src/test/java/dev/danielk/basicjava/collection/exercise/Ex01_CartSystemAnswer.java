package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01 답안: 장바구니 시스템
 */
@DisplayName("연습 01 답안: 장바구니 시스템")
class Ex01_CartSystemAnswer {

    /** addAll 후 stream distinct + sorted */
    List<String> union(List<String> a, List<String> b) {
        List<String> combined = new ArrayList<>(a);
        combined.addAll(b);
        return combined.stream().distinct().sorted().collect(Collectors.toList());
    }

    @Test
    @DisplayName("문제 1: 위시리스트 합집합")
    void test_union() {
        List<String> a = List.of("노트북", "마우스", "키보드");
        List<String> b = List.of("마우스", "모니터", "키보드");
        assertThat(union(a, b)).containsExactly("노트북", "마우스", "모니터", "키보드");
    }

    /** retainAll: 호출한 리스트에서 인수 컬렉션에 없는 원소를 모두 제거 → 교집합 */
    List<String> intersection(List<String> a, List<String> b) {
        List<String> result = new ArrayList<>(a);
        result.retainAll(b);
        return result;
    }

    @Test
    @DisplayName("문제 2: 위시리스트 교집합")
    void test_intersection() {
        List<String> a = List.of("노트북", "마우스", "키보드");
        List<String> b = List.of("마우스", "모니터", "키보드");
        assertThat(intersection(a, b)).containsExactlyInAnyOrder("마우스", "키보드");
    }

    /** removeAll: 호출한 리스트에서 인수 컬렉션에 있는 원소를 모두 제거 → 차집합 */
    List<String> difference(List<String> a, List<String> b) {
        List<String> result = new ArrayList<>(a);
        result.removeAll(b);
        return result;
    }

    @Test
    @DisplayName("문제 3: 위시리스트 차집합")
    void test_difference() {
        List<String> a = List.of("노트북", "마우스", "키보드");
        List<String> b = List.of("마우스", "모니터", "키보드");
        assertThat(difference(a, b)).containsExactly("노트북");
    }

    /** sort(reverseOrder()) 후 subList(0, n) */
    List<Integer> topNPrices(List<Integer> prices, int n) {
        List<Integer> sorted = new ArrayList<>(prices);
        sorted.sort(Comparator.reverseOrder());
        return new ArrayList<>(sorted.subList(0, n));
    }

    @Test
    @DisplayName("문제 4: 가격 상위 N개 슬라이싱")
    void test_topNPrices() {
        List<Integer> prices = List.of(30000, 150000, 25000, 80000, 60000);
        assertThat(topNPrices(prices, 3)).containsExactly(150000, 80000, 60000);
    }

    /** Collections.max / min: Comparable 구현 타입에 바로 사용 가능 */
    int[] maxMinPrice(List<Integer> prices) {
        return new int[]{Collections.max(prices), Collections.min(prices)};
    }

    @Test
    @DisplayName("문제 5: 최고가 / 최저가")
    void test_maxMinPrice() {
        List<Integer> prices = List.of(30000, 150000, 25000, 80000, 60000);
        int[] result = maxMinPrice(prices);
        assertThat(result[0]).isEqualTo(150000);
        assertThat(result[1]).isEqualTo(25000);
    }
}
