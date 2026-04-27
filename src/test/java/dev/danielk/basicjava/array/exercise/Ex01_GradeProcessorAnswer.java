package dev.danielk.basicjava.array.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01 답안: 성적 처리기
 */
@DisplayName("연습 01 답안: 성적 처리기")
class Ex01_GradeProcessorAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /** Arrays.copyOf로 원본을 복사한 뒤 Arrays.sort → 원본 배열에 영향 없음 */
    int[] sortedScores(int[] scores) {
        int[] copy = Arrays.copyOf(scores, scores.length);
        Arrays.sort(copy);
        return copy;
    }

    @Test
    @DisplayName("문제 1: 점수 오름차순 정렬 (원본 불변)")
    void test_sortedScores() {
        int[] scores = {85, 62, 90, 78, 55};
        int[] result = sortedScores(scores);
        assertThat(result).containsExactly(55, 62, 78, 85, 90);
        assertThat(scores).containsExactly(85, 62, 90, 78, 55);
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /** average()는 double 반환이므로 (int) 캐스팅하면 소수점 버림 */
    int[] statistics(int[] scores) {
        int max = Arrays.stream(scores).max().getAsInt();
        int min = Arrays.stream(scores).min().getAsInt();
        int avg = (int) Arrays.stream(scores).average().getAsDouble();
        return new int[]{max, min, avg};
    }

    @Test
    @DisplayName("문제 2: 최고점 / 최저점 / 평균 계산")
    void test_statistics() {
        int[] scores = {85, 62, 90, 78, 55};
        int[] result = statistics(scores);
        assertThat(result[0]).isEqualTo(90);
        assertThat(result[1]).isEqualTo(55);
        assertThat(result[2]).isEqualTo(74);
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /** 내림차순 조건 분기: 90 이상부터 순서대로 체크 */
    char grade(int score) {
        if (score >= 90) return 'A';
        if (score >= 80) return 'B';
        if (score >= 70) return 'C';
        if (score >= 60) return 'D';
        return 'F';
    }

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

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /** new int[size] 생성 후 Arrays.fill로 defaultValue 채우기 */
    int[] resetScores(int size, int defaultValue) {
        int[] arr = new int[size];
        Arrays.fill(arr, defaultValue);
        return arr;
    }

    @Test
    @DisplayName("문제 4: 점수 초기화")
    void test_resetScores() {
        assertThat(resetScores(5, 0)).containsExactly(0, 0, 0, 0, 0);
        assertThat(resetScores(3, 100)).containsExactly(100, 100, 100);
    }

    // ── 문제 5 답안 ───────────────────────────────────────────────────────────
    /** Arrays.toString이 "[1, 2, 3]" 형태 문자열을 바로 반환 */
    String scoresAsString(int[] scores) {
        return Arrays.toString(scores);
    }

    @Test
    @DisplayName("문제 5: 점수 배열 문자열 변환")
    void test_scoresAsString() {
        assertThat(scoresAsString(new int[]{85, 62, 90})).isEqualTo("[85, 62, 90]");
        assertThat(scoresAsString(new int[]{100})).isEqualTo("[100]");
    }
}
