package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 06 답안: 그룹별 데이터 분류기
 */
@DisplayName("연습 06 답안: 그룹별 데이터 분류기")
class Ex06_GroupClassifierAnswer {

    /** computeIfAbsent: 키가 없으면 람다로 새 리스트 생성, 있으면 기존 리스트 반환 후 add */
    Map<String, List<String>> groupByCategory(List<String> items) {
        Map<String, List<String>> result = new HashMap<>();
        for (String item : items) {
            String[] parts = item.split(":");
            result.computeIfAbsent(parts[0], k -> new ArrayList<>()).add(parts[1]);
        }
        return result;
    }

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

    /** merge: 키 없으면 1, 있으면 기존값 + 1 */
    Map<String, Integer> countByCategory(List<String> items) {
        Map<String, Integer> result = new HashMap<>();
        for (String item : items) {
            result.merge(item.split(":")[0], 1, Integer::sum);
        }
        return result;
    }

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

    /** LinkedHashMap은 삽입 순서를 보장 → 정렬 결과 유지 */
    Map<String, Integer> sortByCategory(Map<String, Integer> categoryCount) {
        return categoryCount.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    @Test
    @DisplayName("문제 3: 카테고리명 기준 정렬")
    void test_sortByCategory() {
        Map<String, Integer> input = new HashMap<>();
        input.put("전자기기", 3);
        input.put("가구", 1);
        input.put("식품", 2);
        assertThat(new ArrayList<>(sortByCategory(input).keySet())).containsExactly("가구", "식품", "전자기기");
    }
}
