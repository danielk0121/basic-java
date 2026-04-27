package dev.danielk.basicjava.array.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01: 성적 처리기
 *
 * 학생 점수 배열을 받아 통계를 계산하는 메서드를 완성하세요.
 *
 * 사용해야 할 메서드:
 *   Arrays.sort, Arrays.copyOf, Arrays.fill, Arrays.toString,
 *   stream max/min/average
 */
@DisplayName("연습 01: 성적 처리기")
class Ex01_GradeProcessorTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 점수 배열을 오름차순으로 정렬하여 반환하세요. (원본 배열을 변경하지 마세요)
     * 힌트: Arrays.copyOf로 복사 후 Arrays.sort
     */
    int[] sortedScores(int[] scores) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: 점수 오름차순 정렬 (원본 불변)")
    void test_sortedScores() {
        int[] scores = {85, 62, 90, 78, 55};
        int[] result = sortedScores(scores);
        assertThat(result).containsExactly(55, 62, 78, 85, 90);
        assertThat(scores).containsExactly(85, 62, 90, 78, 55); // 원본 불변
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 점수 배열에서 최고점, 최저점, 평균을 계산하여 int[] {최고점, 최저점, 평균(버림)} 로 반환하세요.
     * 힌트: Arrays.stream max/min/average
     */
    int[] statistics(int[] scores) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 최고점 / 최저점 / 평균 계산")
    void test_statistics() {
        int[] scores = {85, 62, 90, 78, 55};
        int[] result = statistics(scores);
        assertThat(result[0]).isEqualTo(90); // 최고점
        assertThat(result[1]).isEqualTo(55); // 최저점
        assertThat(result[2]).isEqualTo(74); // 평균 버림 (370/5=74.0)
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 점수에 따라 등급 문자를 반환하세요.
     *   90 이상 → 'A', 80 이상 → 'B', 70 이상 → 'C', 60 이상 → 'D', 그 외 → 'F'
     */
    char grade(int score) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 점수별 등급 부여")
    void test_grade() {
        assertThat(grade(95)).isEqualTo('A');
        assertThat(grade(85)).isEqualTo('B');
        assertThat(grade(75)).isEqualTo('C');
        assertThat(grade(65)).isEqualTo('D');
        assertThat(grade(50)).isEqualTo('F');
        assertThat(grade(90)).isEqualTo('A');
        assertThat(grade(60)).isEqualTo('D');
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 점수 배열 전체를 특정 값으로 초기화한 새 배열을 반환하세요.
     * 힌트: new int[size], Arrays.fill
     */
    int[] resetScores(int size, int defaultValue) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: 점수 초기화")
    void test_resetScores() {
        assertThat(resetScores(5, 0)).containsExactly(0, 0, 0, 0, 0);
        assertThat(resetScores(3, 100)).containsExactly(100, 100, 100);
    }

    // ── 문제 5 ────────────────────────────────────────────────────────────────
    /**
     * 점수 배열을 "[85, 62, 90, 78, 55]" 형태의 문자열로 반환하세요.
     * 힌트: Arrays.toString
     */
    String scoresAsString(int[] scores) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 5: 점수 배열 문자열 변환")
    void test_scoresAsString() {
        assertThat(scoresAsString(new int[]{85, 62, 90})).isEqualTo("[85, 62, 90]");
        assertThat(scoresAsString(new int[]{100})).isEqualTo("[100]");
    }
}
