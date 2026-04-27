package dev.danielk.basicjava.array.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03: 설문 결과 분석기
 *
 * 1~5점 응답 배열에서 빈도수 집계, 복사 후 내림차순 정렬, 배열 비교를 수행하세요.
 *
 * 사용해야 할 메서드:
 *   Arrays.copyOfRange, Arrays.equals, Arrays.fill, Comparator.reverseOrder,
 *   stream boxed → toArray(Integer[]::new)
 */
@DisplayName("연습 03: 설문 결과 분석기")
class Ex03_SurveyAnalyzerTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 응답 배열에서 각 점수(1~5)의 등장 횟수를 배열로 반환하세요.
     * 반환 배열 인덱스 0 = 점수1 횟수, ..., 인덱스 4 = 점수5 횟수
     * 힌트: new int[5], responses[i]-1 인덱스 증가
     */
    int[] countFrequency(int[] responses) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 1: 점수별 빈도수 집계")
    void test_countFrequency() {
        int[] responses = {1, 3, 5, 2, 3, 3, 4, 1, 5, 2};
        int[] freq = countFrequency(responses);
        assertThat(freq[0]).isEqualTo(2); // 점수 1: 2번
        assertThat(freq[1]).isEqualTo(2); // 점수 2: 2번
        assertThat(freq[2]).isEqualTo(3); // 점수 3: 3번
        assertThat(freq[3]).isEqualTo(1); // 점수 4: 1번
        assertThat(freq[4]).isEqualTo(2); // 점수 5: 2번
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * from 이상 to 미만 범위의 응답만 복사하여 내림차순으로 반환하세요.
     * 힌트: Arrays.copyOfRange, stream boxed, Arrays.sort(arr, Comparator.reverseOrder())
     */
    Integer[] sliceAndSortDesc(int[] responses, int from, int to) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 2: 범위 복사 후 내림차순 정렬")
    void test_sliceAndSortDesc() {
        int[] responses = {1, 3, 5, 2, 3, 3, 4, 1, 5, 2};
        Integer[] result = sliceAndSortDesc(responses, 2, 7);
        assertThat(result).containsExactly(5, 4, 3, 3, 2); // 인덱스 2~6: {5,2,3,3,4} → 내림차순
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 두 응답 배열이 동일한지 비교하세요.
     * 힌트: Arrays.equals
     */
    boolean isSameResponse(int[] a, int[] b) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 3: 두 응답 배열 비교")
    void test_isSameResponse() {
        assertThat(isSameResponse(new int[]{1, 2, 3}, new int[]{1, 2, 3})).isTrue();
        assertThat(isSameResponse(new int[]{1, 2, 3}, new int[]{1, 2, 4})).isFalse();
        assertThat(isSameResponse(new int[]{5, 5}, new int[]{5, 5, 5})).isFalse();
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * int[] 를 Integer[] 로 변환하여 반환하세요.
     * 힌트: Arrays.stream(responses).boxed().toArray(Integer[]::new)
     */
    Integer[] toBoxedArray(int[] responses) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 4: int[] → Integer[] 변환")
    void test_toBoxedArray() {
        Integer[] result = toBoxedArray(new int[]{3, 1, 4, 1, 5});
        assertThat(result).containsExactly(3, 1, 4, 1, 5);
    }
}
