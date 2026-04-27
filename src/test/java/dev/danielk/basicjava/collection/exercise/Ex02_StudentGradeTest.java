package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02: 학생 성적 관리
 *
 * 성적 리스트에서 상위/하위 N명 슬라이싱, 중복 제거, 빈도수 집계를 수행하세요.
 *
 * 사용해야 할 메서드:
 *   sort, subList, Collections.frequency, distinct, Collections.max/min
 */
@DisplayName("연습 02: 학생 성적 관리")
class Ex02_StudentGradeTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 성적 리스트에서 상위 n명의 점수를 내림차순으로 반환하세요.
     * 힌트: sort(reverseOrder()), subList(0, n)
     */
    List<Integer> topN(List<Integer> scores, int n) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 1: 상위 N명 점수")
    void test_topN() {
        List<Integer> scores = List.of(75, 90, 60, 85, 95, 70, 80);
        assertThat(topN(scores, 3)).containsExactly(95, 90, 85);
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 성적 리스트에서 하위 n명의 점수를 오름차순으로 반환하세요.
     * 힌트: Collections.sort, subList(0, n)
     */
    List<Integer> bottomN(List<Integer> scores, int n) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 2: 하위 N명 점수")
    void test_bottomN() {
        List<Integer> scores = List.of(75, 90, 60, 85, 95, 70, 80);
        assertThat(bottomN(scores, 3)).containsExactly(60, 70, 75);
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 성적 리스트에서 중복 점수를 제거하고 오름차순으로 반환하세요.
     * 힌트: stream distinct + sorted
     */
    List<Integer> uniqueSorted(List<Integer> scores) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 3: 중복 제거 후 정렬")
    void test_uniqueSorted() {
        List<Integer> scores = List.of(85, 70, 85, 90, 70, 95);
        assertThat(uniqueSorted(scores)).containsExactly(70, 85, 90, 95);
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 특정 점수가 성적 리스트에 몇 번 등장하는지 반환하세요.
     * 힌트: Collections.frequency
     */
    int countScore(List<Integer> scores, int target) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 4: 특정 점수 등장 횟수")
    void test_countScore() {
        List<Integer> scores = List.of(85, 70, 85, 90, 70, 85);
        assertThat(countScore(scores, 85)).isEqualTo(3);
        assertThat(countScore(scores, 70)).isEqualTo(2);
        assertThat(countScore(scores, 100)).isEqualTo(0);
    }
}
