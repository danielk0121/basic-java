package dev.danielk.basicjava.string.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 04 답안: 비밀번호 검증기
 *
 * 비밀번호 문자열을 검사하고 마스킹하는 메서드를 완성하세요.
 *
 * 사용해야 할 메서드:
 *   isBlank/isEmpty, length, matches (정규식),
 *   charAt/toCharArray, subSequence, contains,
 *   StringBuilder (append), repeat, substring
 */
@DisplayName("연습 04 답안: 비밀번호 검증기")
class Ex04_PasswordValidatorAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 비밀번호가 null이거나 빈 문자열인지 확인하세요.
     * 힌트: isBlank 또는 isEmpty
     *
     * [풀이] null 체크 먼저, 이후 isBlank()로 공백만 있는 경우도 처리
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
     * [문제] 비밀번호 강도 규칙을 위반하는 항목 목록을 반환하세요.
     * 규칙:
     *   1. 길이 8자 이상 → 위반 시 "8자 이상이어야 합니다"
     *   2. 영문 대문자 포함 → 위반 시 "대문자가 포함되어야 합니다"
     *   3. 영문 소문자 포함 → 위반 시 "소문자가 포함되어야 합니다"
     *   4. 숫자 포함       → 위반 시 "숫자가 포함되어야 합니다"
     *   5. 특수문자 포함   → 위반 시 "특수문자가 포함되어야 합니다"
     * 힌트: length, matches("[.*[A-Z].*]" 같은 패턴), chars() + filter
     *
     * [풀이] 각 규칙을 matches()로 검사 (전체 문자열 매칭 시 .*[패턴].* 사용)
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
     * [문제] 비밀번호를 마스킹하세요.
     * - 앞 2자리와 뒤 2자리는 원문 유지, 나머지는 '*'로 치환
     * - 비밀번호가 4자 이하이면 전체를 '*'로 마스킹
     * 예: "password123" → "pa*******23"
     *     "abc"         → "***"
     * 힌트: length, substring, repeat, StringBuilder
     *
     * [풀이] length() <= 4 → "*".repeat(length())
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
     * [문제] 비밀번호에서 연속으로 같은 문자가 3번 이상 반복되는지 확인하세요.
     * 예: "aaabcd" → true, "aabcd" → false
     * 힌트: charAt 또는 toCharArray, 반복문으로 인접 문자 비교
     *
     * [풀이] toCharArray로 순회하면서 연속 동일 문자 카운터 유지
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
     * [문제] 비밀번호에 사용자 이름이 부분 문자열로 포함되어 있으면 안됩니다. (대소문자 무시)
     * 예: 비밀번호 "MyNameIsAlice1!" 에 이름 "alice"가 포함 → true (위험)
     * 힌트: toLowerCase, contains
     *
     * [풀이] 둘 다 toLowerCase 후 contains로 부분 문자열 포함 여부 확인
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
     * [문제] 비밀번호의 앞 절반을 subSequence로 추출하고, 그 부분이 숫자로만 이루어졌는지 확인하세요.
     * 예: "12345abc" → 앞 절반 "1234" → 숫자만 → true
     *     "abc12345" → 앞 절반 "abc1" → 숫자 아닌 문자 포함 → false
     * 힌트: length, subSequence, matches("[0-9]+")
     *
     * [풀이] length() / 2로 앞 절반 길이 계산
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
