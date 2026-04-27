package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 06: 그룹별 데이터 분류기
 *
 * 상품을 카테고리별로 분류하고 키 기준 정렬 출력을 수행하세요.
 *
 * 사용해야 할 메서드:
 *   computeIfAbsent, merge, entrySet 정렬(키 기준)
 */
@DisplayName("연습 06: 그룹별 데이터 분류기")
class Ex06_GroupClassifierTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 상품 목록을 카테고리별로 분류하여 Map<카테고리, List<상품명>> 로 반환하세요.
     * 입력: "전자기기:노트북", "식품:사과", "전자기기:마우스", ...
     * 힌트: computeIfAbsent(category, k -> new ArrayList<>()).add(product)
     */
    Map<String, List<String>> groupByCategory(List<String> items) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: 카테고리별 상품 분류")
    void test_groupByCategory() {
        List<String> items = List.of(
                "전자기기:노트북", "식품:사과", "전자기기:마우스", "식품:바나나", "전자기기:키보드"
        );
        Map<String, List<String>> result = groupByCategory(items);
        assertThat(result.get("전자기기")).containsExactlyInAnyOrder("노트북", "마우스", "키보드");
        assertThat(result.get("식품")).containsExactlyInAnyOrder("사과", "바나나");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 카테고리별 상품 수를 집계하여 Map<카테고리, 상품수> 로 반환하세요.
     * 힌트: merge(category, 1, Integer::sum)
     */
    Map<String, Integer> countByCategory(List<String> items) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 카테고리별 상품 수 집계")
    void test_countByCategory() {
        List<String> items = List.of(
                "전자기기:노트북", "식품:사과", "전자기기:마우스", "식품:바나나", "전자기기:키보드"
        );
        Map<String, Integer> result = countByCategory(items);
        assertThat(result.get("전자기기")).isEqualTo(3);
        assertThat(result.get("식품")).isEqualTo(2);
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 카테고리 맵을 키(카테고리명) 기준 오름차순으로 정렬하여 반환하세요.
     * 힌트: entrySet 스트림 → sorted(Map.Entry.comparingByKey()) → LinkedHashMap으로 수집
     */
    Map<String, Integer> sortByCategory(Map<String, Integer> categoryCount) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 카테고리명 기준 정렬")
    void test_sortByCategory() {
        Map<String, Integer> input = new java.util.HashMap<>();
        input.put("전자기기", 3);
        input.put("가구", 1);
        input.put("식품", 2);
        Map<String, Integer> result = sortByCategory(input);
        assertThat(new java.util.ArrayList<>(result.keySet())).containsExactly("가구", "식품", "전자기기");
    }
}
