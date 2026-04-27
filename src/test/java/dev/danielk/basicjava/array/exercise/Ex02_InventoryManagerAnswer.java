package dev.danielk.basicjava.array.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: 재고 관리 시스템
 */
@DisplayName("연습 02 답안: 재고 관리 시스템")
class Ex02_InventoryManagerAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /** 각 행의 [0]이 productId와 일치하면 [1](재고량) 반환, 없으면 -1 */
    int findStock(int[][] inventory, int productId) {
        for (int[] row : inventory) {
            if (row[0] == productId) return row[1];
        }
        return -1;
    }

    @Test
    @DisplayName("문제 1: 상품ID로 재고 검색")
    void test_findStock() {
        int[][] inventory = {{101, 50}, {102, 0}, {103, 30}, {104, 0}, {105, 15}};
        assertThat(findStock(inventory, 103)).isEqualTo(30);
        assertThat(findStock(inventory, 101)).isEqualTo(50);
        assertThat(findStock(inventory, 999)).isEqualTo(-1);
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /** 각 행의 [1](재고량)을 합산 */
    int totalStock(int[][] inventory) {
        int sum = 0;
        for (int[] row : inventory) sum += row[1];
        return sum;
    }

    @Test
    @DisplayName("문제 2: 전체 재고 합계")
    void test_totalStock() {
        int[][] inventory = {{101, 50}, {102, 0}, {103, 30}, {104, 0}, {105, 15}};
        assertThat(totalStock(inventory)).isEqualTo(95);
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /** Arrays.stream(2d array) → 각 행을 스트림으로 처리 */
    List<Integer> outOfStockIds(int[][] inventory) {
        return Arrays.stream(inventory)
                .filter(row -> row[1] == 0)
                .map(row -> row[0])
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("문제 3: 품절 상품 ID 목록")
    void test_outOfStockIds() {
        int[][] inventory = {{101, 50}, {102, 0}, {103, 30}, {104, 0}, {105, 15}};
        assertThat(outOfStockIds(inventory)).containsExactlyInAnyOrder(102, 104);
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /** Arrays.deepToString은 다차원 배열을 중첩 대괄호 형태로 변환 */
    String inventoryAsString(int[][] inventory) {
        return Arrays.deepToString(inventory);
    }

    @Test
    @DisplayName("문제 4: 재고 배열 문자열 변환")
    void test_inventoryAsString() {
        assertThat(inventoryAsString(new int[][]{{101, 10}, {102, 20}})).isEqualTo("[[101, 10], [102, 20]]");
    }
}
