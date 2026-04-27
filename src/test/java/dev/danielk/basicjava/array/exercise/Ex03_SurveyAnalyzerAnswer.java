package dev.danielk.basicjava.array.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03 답안: 설문 결과 분석기
 */
@DisplayName("연습 03 답안: 설문 결과 분석기")
class Ex03_SurveyAnalyzerAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /** responses[i] 값이 1~5이므로 responses[i]-1 을 인덱스로 사용 */
    int[] countFrequency(int[] responses) {
        int[] freq = new int[5];
        for (int r : responses) freq[r - 1]++;
        return freq;
    }

    @Test
    @DisplayName("문제 1: 점수별 빈도수 집계")
    void test_countFrequency() {
        int[] responses = {1, 3, 5, 2, 3, 3, 4, 1, 5, 2};
        int[] freq = countFrequency(responses);
        assertThat(freq[0]).isEqualTo(2);
        assertThat(freq[1]).isEqualTo(2);
        assertThat(freq[2]).isEqualTo(3);
        assertThat(freq[3]).isEqualTo(1);
        assertThat(freq[4]).isEqualTo(2);
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /** Arrays.copyOfRange → boxed → reverseOrder 정렬 */
    Integer[] sliceAndSortDesc(int[] responses, int from, int to) {
        int[] slice = Arrays.copyOfRange(responses, from, to);
        Integer[] boxed = Arrays.stream(slice).boxed().toArray(Integer[]::new);
        Arrays.sort(boxed, Comparator.reverseOrder());
        return boxed;
    }

    @Test
    @DisplayName("문제 2: 범위 복사 후 내림차순 정렬")
    void test_sliceAndSortDesc() {
        int[] responses = {1, 3, 5, 2, 3, 3, 4, 1, 5, 2};
        assertThat(sliceAndSortDesc(responses, 2, 7)).containsExactly(5, 4, 3, 3, 2);
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /** Arrays.equals: 길이 및 각 원소를 순서대로 비교 */
    boolean isSameResponse(int[] a, int[] b) {
        return Arrays.equals(a, b);
    }

    @Test
    @DisplayName("문제 3: 두 응답 배열 비교")
    void test_isSameResponse() {
        assertThat(isSameResponse(new int[]{1, 2, 3}, new int[]{1, 2, 3})).isTrue();
        assertThat(isSameResponse(new int[]{1, 2, 3}, new int[]{1, 2, 4})).isFalse();
        assertThat(isSameResponse(new int[]{5, 5}, new int[]{5, 5, 5})).isFalse();
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /** stream boxed(): IntStream → Stream<Integer> 변환 */
    Integer[] toBoxedArray(int[] responses) {
        return Arrays.stream(responses).boxed().toArray(Integer[]::new);
    }

    @Test
    @DisplayName("문제 4: int[] → Integer[] 변환")
    void test_toBoxedArray() {
        assertThat(toBoxedArray(new int[]{3, 1, 4, 1, 5})).containsExactly(3, 1, 4, 1, 5);
    }
}
