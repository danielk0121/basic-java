package dev.danielk.basicjava.random.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: 랜덤 퀴즈 출제기
 */
@DisplayName("연습 02 답안: 랜덤 퀴즈 출제기")
class Ex02_RandomQuizAnswer {

    /** 원본 풀을 복사 → 셔플 → 앞 n개 슬라이싱 */
    List<String> selectQuestions(List<String> questionPool, int n) {
        List<String> shuffled = new ArrayList<>(questionPool);
        Collections.shuffle(shuffled);
        return new ArrayList<>(shuffled.subList(0, n));
    }

    @Test
    @DisplayName("문제 1: 중복 없이 N개 문제 추출")
    void test_selectQuestions() {
        List<String> pool = List.of("Q1", "Q2", "Q3", "Q4", "Q5", "Q6", "Q7", "Q8", "Q9", "Q10");
        List<String> selected = selectQuestions(pool, 3);
        assertThat(selected).hasSize(3);
        assertThat(selected).doesNotHaveDuplicates();
        assertThat(pool).containsAll(selected);
    }

    /** 원본을 복사하여 셔플 → 원본 불변 보장 */
    List<String> shuffleChoices(List<String> choices) {
        List<String> result = new ArrayList<>(choices);
        Collections.shuffle(result);
        return result;
    }

    @Test
    @DisplayName("문제 2: 보기 순서 셔플")
    void test_shuffleChoices() {
        List<String> choices = List.of("A", "B", "C", "D");
        List<String> shuffled = shuffleChoices(choices);
        assertThat(shuffled).hasSize(4);
        assertThat(shuffled).containsExactlyInAnyOrder("A", "B", "C", "D");
        assertThat(choices).containsExactly("A", "B", "C", "D");
    }

    /** List.indexOf: 첫 번째로 equals()가 true인 원소의 인덱스 반환 */
    int findAnswerIndex(List<String> shuffledChoices, String answer) {
        return shuffledChoices.indexOf(answer);
    }

    @Test
    @DisplayName("문제 3: 셔플 후 정답 인덱스 탐색")
    void test_findAnswerIndex() {
        List<String> shuffled = List.of("C", "A", "D", "B");
        assertThat(findAnswerIndex(shuffled, "A")).isEqualTo(1);
        assertThat(findAnswerIndex(shuffled, "D")).isEqualTo(2);
    }

    /** ThreadLocalRandom: 멀티스레드 환경에서도 안전한 난수 생성기 */
    String randomJudge() {
        return ThreadLocalRandom.current().nextDouble() >= 0.5 ? "통과" : "재시도";
    }

    @Test
    @DisplayName("문제 4: 난수 기반 판정")
    void test_randomJudge() {
        for (int i = 0; i < 100; i++) {
            assertThat(randomJudge()).isIn("통과", "재시도");
        }
    }
}
