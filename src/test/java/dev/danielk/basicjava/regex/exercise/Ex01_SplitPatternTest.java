package dev.danielk.basicjava.regex.exercise;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01: split 정규식 패턴
 *
 * 정규식을 이용한 문자열 분리를 수행하세요.
 *
 * 사용해야 할 패턴:
 *   "\\s+"     — 연속 공백·탭
 *   "\\D+"     — 숫자가 아닌 문자 (숫자 추출용)
 *   "\\d+"     — 숫자 (영문자 추출용)
 *   "[,;|/]"   — 복합 구분기호 중 하나
 */
@DisplayName("연습 01: split 정규식 패턴")
class Ex01_SplitPatternTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 연속된 공백과 탭이 섞인 문자열을 단어 단위로 분리하세요.
     * 힌트: split("\\s+")
     */
    String[] splitByWhitespace(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: \\s+ — 연속 공백·탭 구분")
    void test_splitByWhitespace() {
        String[] result = splitByWhitespace("사과  바나나\t포도   딸기");
        assertThat(result).containsExactly("사과", "바나나", "포도", "딸기");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 영문자와 숫자가 섞인 문자열에서 숫자만 추출하세요.
     * 빈 문자열 토큰은 제외하세요.
     * 힌트: split("\\D+") — 비숫자 문자를 구분자로 사용
     *       Arrays.stream().filter(s -> !s.isEmpty()) 로 빈 토큰 제거
     */
    String[] extractNumbers(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: \\D+ — 숫자 추출")
    void test_extractNumbers() {
        String[] result = extractNumbers("abc123def456ghi789");
        assertThat(result).containsExactly("123", "456", "789");
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 영문자와 숫자가 섞인 문자열에서 영문자만 추출하세요.
     * 힌트: split("\\d+") — 숫자를 구분자로 사용
     */
    String[] extractLetters(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: \\d+ — 영문자 추출")
    void test_extractLetters() {
        String[] result = extractLetters("abc123DEF456ghi789XYZ");
        assertThat(result).containsExactly("abc", "DEF", "ghi", "XYZ");
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 쉼표, 세미콜론, 파이프, 슬래시 중 어느 것이든 구분기호로 사용해 분리하세요.
     * 힌트: split("[,;|/]") — 문자 클래스로 복합 구분기호 지정
     */
    String[] splitByMultipleDelimiters(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: [,;|/] — 복합 구분기호 분리")
    void test_splitByMultipleDelimiters() {
        String[] result = splitByMultipleDelimiters("서울,부산;대구|광주/인천");
        assertThat(result).containsExactly("서울", "부산", "대구", "광주", "인천");
    }
}
