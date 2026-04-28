package dev.danielk.basicjava.regex.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01 답안: split 정규식 패턴
 *
 * 정규식을 이용한 문자열 분리를 수행하세요.
 *
 * 사용해야 할 패턴:
 *   "\\s+"     — 연속 공백·탭
 *   "\\D+"     — 숫자가 아닌 문자 (숫자 추출용)
 *   "\\d+"     — 숫자 (영문자 추출용)
 *   "[,;|/]"   — 복합 구분기호 중 하나
 */
@DisplayName("연습 01 답안: split 정규식 패턴")
class Ex01_SplitPatternAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 연속된 공백과 탭이 섞인 문자열을 단어 단위로 분리하세요.
     * 힌트: split("\\s+")
     *
     * [풀이] \s 는 스페이스·탭·줄바꿈을 모두 포함하고,
     *        + 수량자로 연속된 공백을 하나의 구분자로 처리한다.
     */
    String[] splitByWhitespace(String input) {
        return input.split("\\s+");
    }

    @Test
    @DisplayName("문제 1: \\s+ — 연속 공백·탭 구분")
    void test_splitByWhitespace() {
        String[] result = splitByWhitespace("사과  바나나\t포도   딸기");
        assertThat(result).containsExactly("사과", "바나나", "포도", "딸기");
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 영문자와 숫자가 섞인 문자열에서 숫자만 추출하세요.
     * 힌트: split("\\D+"), 빈 토큰 제거
     *
     * [풀이] \D+ 로 비숫자 문자 연속을 구분자로 삼으면 숫자 덩어리만 남는다.
     *        문자열이 비숫자로 시작하면 split 결과 첫 토큰이 빈 문자열이 되므로
     *        filter 로 제거한 뒤 배열로 변환한다.
     */
    String[] extractNumbers(String input) {
        return java.util.Arrays.stream(input.split("\\D+"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    @Test
    @DisplayName("문제 2: \\D+ — 숫자 추출")
    void test_extractNumbers() {
        String[] result = extractNumbers("abc123def456ghi789");
        assertThat(result).containsExactly("123", "456", "789");
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 영문자와 숫자가 섞인 문자열에서 영문자만 추출하세요.
     * 힌트: split("\\d+")
     *
     * [풀이] \d+ 로 숫자 연속을 구분자로 삼으면 남은 토큰이 영문자 덩어리가 된다.
     */
    String[] extractLetters(String input) {
        return input.split("\\d+");
    }

    @Test
    @DisplayName("문제 3: \\d+ — 영문자 추출")
    void test_extractLetters() {
        String[] result = extractLetters("abc123DEF456ghi789XYZ");
        assertThat(result).containsExactly("abc", "DEF", "ghi", "XYZ");
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 쉼표, 세미콜론, 파이프, 슬래시 중 어느 것이든 구분기호로 분리하세요.
     * 힌트: split("[,;|/]")
     *
     * [풀이] [] 문자 클래스 안에 나열된 문자 중 하나와 일치하면 구분자로 처리한다.
     */
    String[] splitByMultipleDelimiters(String input) {
        return input.split("[,;|/]");
    }

    @Test
    @DisplayName("문제 4: [,;|/] — 복합 구분기호 분리")
    void test_splitByMultipleDelimiters() {
        String[] result = splitByMultipleDelimiters("서울,부산;대구|광주/인천");
        assertThat(result).containsExactly("서울", "부산", "대구", "광주", "인천");
    }
}
