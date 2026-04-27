package dev.danielk.basicjava.string.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 04 답안: 비밀번호 검증기
 */
@DisplayName("연습 04 답안: 비밀번호 검증기")
class Ex04_PasswordValidatorAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * null 체크 먼저, 이후 isBlank()로 공백만 있는 경우도 처리
     */
    boolean isNullOrEmpty(String password) {
        return password == null || password.isBlank();
    }

    @Test
    @DisplayName("문제 1: null 또는 빈 문자열 확인")
    void test_isNullOrEmpty() {
        assertThat(isNullOrEmpty(null)).isTrue();
        assertThat(isNullOrEmpty("")).isTrue();
        assertThat(isNullOrEmpty("   ")).isTrue();
        assertThat(isNullOrEmpty("abc")).isFalse();
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * 각 규칙을 matches()로 검사 (전체 문자열 매칭 시 .*[패턴].* 사용)
     * "(?s)" 불필요 — 단일 줄 비밀번호 가정
     */
    List<String> validatePassword(String password) {
        List<String> violations = new ArrayList<>();
        if (password.length() < 8)
            violations.add("8자 이상이어야 합니다");
        if (!password.matches(".*[A-Z].*"))
            violations.add("대문자가 포함되어야 합니다");
        if (!password.matches(".*[a-z].*"))
            violations.add("소문자가 포함되어야 합니다");
        if (!password.matches(".*[0-9].*"))
            violations.add("숫자가 포함되어야 합니다");
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*"))
            violations.add("특수문자가 포함되어야 합니다");
        return violations;
    }

    @Test
    @DisplayName("문제 2: 비밀번호 규칙 위반 항목 반환")
    void test_validatePassword() {
        List<String> violations = validatePassword("abc");
        assertThat(violations).contains(
                "8자 이상이어야 합니다",
                "대문자가 포함되어야 합니다",
                "숫자가 포함되어야 합니다",
                "특수문자가 포함되어야 합니다"
        );
        assertThat(validatePassword("Abcdefg1!")).isEmpty();
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * length() <= 4 → "*".repeat(length())
     * 그 외 → substring(0, 2) + "*".repeat(가운데) + substring(끝-2)
     */
    String mask(String password) {
        int len = password.length();
        if (len <= 4) return "*".repeat(len);
        return password.substring(0, 2)
                + "*".repeat(len - 4)
                + password.substring(len - 2);
    }

    @Test
    @DisplayName("문제 3: 비밀번호 마스킹")
    void test_mask() {
        assertThat(mask("password123")).isEqualTo("pa*******23");
        assertThat(mask("abc")).isEqualTo("***");
        assertThat(mask("abcd")).isEqualTo("****");
        assertThat(mask("abcde")).isEqualTo("ab*de");
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * toCharArray로 순회하면서 연속 동일 문자 카운터 유지
     * count >= 3이면 즉시 true
     */
    boolean hasTripleRepeat(String password) {
        char[] chars = password.toCharArray();
        int count = 1;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == chars[i - 1]) {
                count++;
                if (count >= 3) return true;
            } else {
                count = 1;
            }
        }
        return false;
    }

    @Test
    @DisplayName("문제 4: 연속 3회 반복 문자 감지")
    void test_hasTripleRepeat() {
        assertThat(hasTripleRepeat("aaabcd")).isTrue();
        assertThat(hasTripleRepeat("aabcd")).isFalse();
        assertThat(hasTripleRepeat("abccc")).isTrue();
        assertThat(hasTripleRepeat("abcde")).isFalse();
    }

    // ── 문제 5 답안 ───────────────────────────────────────────────────────────
    /**
     * 둘 다 toLowerCase 후 contains로 부분 문자열 포함 여부 확인
     */
    boolean containsUsername(String password, String username) {
        return password.toLowerCase().contains(username.toLowerCase());
    }

    @Test
    @DisplayName("문제 5: 비밀번호에 사용자명 포함 여부 확인")
    void test_containsUsername() {
        assertThat(containsUsername("MyNameIsAlice1!", "alice")).isTrue();
        assertThat(containsUsername("Secure#Pass1", "alice")).isFalse();
        assertThat(containsUsername("ADMINPASS1!", "admin")).isTrue();
    }

    // ── 문제 6 답안 ───────────────────────────────────────────────────────────
    /**
     * length() / 2로 앞 절반 길이 계산
     * subSequence(0, half)로 추출 후 toString().matches("[0-9]+")
     */
    boolean isFirstHalfAllDigits(String password) {
        int half = password.length() / 2;
        String firstHalf = password.subSequence(0, half).toString();
        return firstHalf.matches("[0-9]+");
    }

    @Test
    @DisplayName("문제 6: 앞 절반이 숫자로만 구성되어 있는지 확인")
    void test_isFirstHalfAllDigits() {
        assertThat(isFirstHalfAllDigits("12345abc")).isTrue();
        assertThat(isFirstHalfAllDigits("abc12345")).isFalse();
        assertThat(isFirstHalfAllDigits("1234abcd")).isTrue();
    }
}
