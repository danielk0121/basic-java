package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01: 장바구니 시스템
 *
 * 두 위시리스트를 비교하고 가격순 정렬, 중복 제거, 슬라이싱을 수행하세요.
 *
 * 사용해야 할 메서드:
 *   addAll, retainAll, removeAll, sort, distinct, subList,
 *   Collections.reverse, Collections.max/min
 */
@DisplayName("연습 01: 장바구니 시스템")
class Ex01_CartSystemTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 두 위시리스트의 합집합(중복 제거)을 이름 오름차순으로 반환하세요.
     * 힌트: addAll 후 stream distinct + sorted
     */
    List<String> union(List<String> a, List<String> b) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 1: 위시리스트 합집합")
    void test_union() {
        List<String> a = List.of("노트북", "마우스", "키보드");
        List<String> b = List.of("마우스", "모니터", "키보드");
        assertThat(union(a, b)).containsExactly("노트북", "마우스", "모니터", "키보드");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 두 위시리스트에서 공통으로 담긴 상품(교집합)을 반환하세요.
     * 힌트: new ArrayList<>(a) 후 retainAll(b)
     */
    List<String> intersection(List<String> a, List<String> b) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 2: 위시리스트 교집합")
    void test_intersection() {
        List<String> a = List.of("노트북", "마우스", "키보드");
        List<String> b = List.of("마우스", "모니터", "키보드");
        assertThat(intersection(a, b)).containsExactlyInAnyOrder("마우스", "키보드");
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * a에는 있지만 b에는 없는 상품(차집합)을 반환하세요.
     * 힌트: new ArrayList<>(a) 후 removeAll(b)
     */
    List<String> difference(List<String> a, List<String> b) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 3: 위시리스트 차집합")
    void test_difference() {
        List<String> a = List.of("노트북", "마우스", "키보드");
        List<String> b = List.of("마우스", "모니터", "키보드");
        assertThat(difference(a, b)).containsExactly("노트북");
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 가격 리스트를 내림차순으로 정렬 후 상위 n개만 반환하세요.
     * 힌트: sort(Comparator.reverseOrder()), subList(0, n)
     */
    List<Integer> topNPrices(List<Integer> prices, int n) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 4: 가격 상위 N개 슬라이싱")
    void test_topNPrices() {
        List<Integer> prices = List.of(30000, 150000, 25000, 80000, 60000);
        assertThat(topNPrices(prices, 3)).containsExactly(150000, 80000, 60000);
    }

    // ── 문제 5 ────────────────────────────────────────────────────────────────
    /**
     * 가격 리스트에서 최고가와 최저가를 int[] {최고가, 최저가} 로 반환하세요.
     * 힌트: Collections.max / Collections.min
     */
    int[] maxMinPrice(List<Integer> prices) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
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
