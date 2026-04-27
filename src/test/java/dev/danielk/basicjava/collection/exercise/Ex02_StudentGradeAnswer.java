package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: 학생 성적 관리
 */
@DisplayName("연습 02 답안: 학생 성적 관리")
class Ex02_StudentGradeAnswer {

    List<Integer> topN(List<Integer> scores, int n) {
        List<Integer> sorted = new ArrayList<>(scores);
        sorted.sort(Comparator.reverseOrder());
        return new ArrayList<>(sorted.subList(0, n));
    }

    @Test
    @DisplayName("문제 1: 상위 N명 점수")
    void test_topN() {
        assertThat(topN(List.of(75, 90, 60, 85, 95, 70, 80), 3)).containsExactly(95, 90, 85);
    }

    List<Integer> bottomN(List<Integer> scores, int n) {
        List<Integer> sorted = new ArrayList<>(scores);
        Collections.sort(sorted);
        return new ArrayList<>(sorted.subList(0, n));
    }

    @Test
    @DisplayName("문제 2: 하위 N명 점수")
    void test_bottomN() {
        assertThat(bottomN(List.of(75, 90, 60, 85, 95, 70, 80), 3)).containsExactly(60, 70, 75);
    }

    /** stream distinct()로 중복 제거, sorted()로 오름차순 정렬 */
    List<Integer> uniqueSorted(List<Integer> scores) {
        return scores.stream().distinct().sorted().collect(Collectors.toList());
    }

    @Test
    @DisplayName("문제 3: 중복 제거 후 정렬")
    void test_uniqueSorted() {
        assertThat(uniqueSorted(List.of(85, 70, 85, 90, 70, 95))).containsExactly(70, 85, 90, 95);
    }

    /** Collections.frequency: target과 equals()가 true인 원소 개수 반환 */
    int countScore(List<Integer> scores, int target) {
        return Collections.frequency(scores, target);
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
