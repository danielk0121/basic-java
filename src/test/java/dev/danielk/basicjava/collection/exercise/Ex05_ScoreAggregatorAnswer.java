package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 05 답안: 학생 점수 집계기
 *
 * 과목별 점수 맵을 순회하고 추가/갱신/기본값 처리를 수행하세요.
 *
 * 사용해야 할 메서드:
 *   keySet, entrySet, values, getOrDefault, compute, putIfAbsent
 */
@DisplayName("연습 05 답안: 학생 점수 집계기")
class Ex05_ScoreAggregatorAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 과목별 점수 맵에서 총점을 계산하여 반환하세요.
     * 힌트: values() 스트림으로 합산
     *
     * [풀이] values()로 점수만 꺼낸 뒤 stream sum
     */
    int totalScore(Map<String, Integer> scores) {
        return scores.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Test
    @DisplayName("문제 1: 총점 계산")
    void test_totalScore() {
        assertThat(totalScore(Map.of("수학", 90, "영어", 85, "국어", 78))).isEqualTo(253);
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 맵에 없는 과목의 점수를 조회할 때 기본값 0을 반환하세요.
     * 힌트: getOrDefault
     *
     * [풀이] getOrDefault: 키가 없으면 두 번째 인수(기본값) 반환
     */
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

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 기존 점수에 보너스 점수를 더하여 업데이트하세요. 해당 과목이 없으면 보너스 점수로 등록하세요.
     * 힌트: compute(subject, (k, v) -> v == null ? bonus : v + bonus)
     *
     * [풀이] compute: v가 null이면(키 없으면) bonus, 있으면 v + bonus
     */
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

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 맵의 모든 과목명을 사전 오름차순으로 정렬하여 반환하세요.
     * 힌트: new ArrayList<>(scores.keySet()), Collections.sort
     *
     * [풀이] keySet()으로 과목명 집합 추출 → ArrayList 변환 → Collections.sort
     */
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
