package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 05 답안: 학생 점수 집계기
 */
@DisplayName("연습 05 답안: 학생 점수 집계기")
class Ex05_ScoreAggregatorAnswer {

    /** values()로 점수만 꺼낸 뒤 stream sum */
    int totalScore(Map<String, Integer> scores) {
        return scores.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Test
    @DisplayName("문제 1: 총점 계산")
    void test_totalScore() {
        assertThat(totalScore(Map.of("수학", 90, "영어", 85, "국어", 78))).isEqualTo(253);
    }

    int getScore(Map<String, Integer> scores, String subject) {
        return scores.getOrDefault(subject, 0);
    }

    @Test
    @DisplayName("문제 2: 없는 과목 기본값 반환")
    void test_getScore() {
        Map<String, Integer> scores = Map.of("수학", 90, "영어", 85);
        assertThat(getScore(scores, "수학")).isEqualTo(90);
        assertThat(getScore(scores, "과학")).isEqualTo(0);
    }

    /** compute: v가 null이면(키 없으면) bonus, 있으면 v + bonus */
    Map<String, Integer> addBonus(Map<String, Integer> scores, String subject, int bonus) {
        scores.compute(subject, (k, v) -> v == null ? bonus : v + bonus);
        return scores;
    }

    @Test
    @DisplayName("문제 3: 보너스 점수 추가")
    void test_addBonus() {
        Map<String, Integer> scores = new HashMap<>(Map.of("수학", 90, "영어", 85));
        addBonus(scores, "수학", 5);
        addBonus(scores, "과학", 10);
        assertThat(scores.get("수학")).isEqualTo(95);
        assertThat(scores.get("과학")).isEqualTo(10);
    }

    /** keySet()으로 과목명 집합 추출 → ArrayList 변환 → Collections.sort */
    List<String> sortedSubjects(Map<String, Integer> scores) {
        List<String> subjects = new ArrayList<>(scores.keySet());
        Collections.sort(subjects);
        return subjects;
    }

    @Test
    @DisplayName("문제 4: 과목명 정렬")
    void test_sortedSubjects() {
        Map<String, Integer> scores = Map.of("수학", 90, "국어", 78, "영어", 85, "과학", 92);
        List<String> result = sortedSubjects(scores);
        assertThat(result).isSorted();
        assertThat(result).hasSize(4);
    }
}
