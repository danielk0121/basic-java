package dev.danielk.basicjava.regex.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: matches / replaceAll 정규식 패턴
 *
 * 입력값 검증과 문자열 치환을 수행하세요.
 *
 * 사용해야 할 패턴:
 *   "^\\d+$"                                    — 숫자만
 *   "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"  — 이메일
 *   "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"             — 휴대폰 번호
 *   "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$"             — 비밀번호
 *   "\\s+"                                      — 공백 정규화
 */
@DisplayName("연습 02 답안: matches / replaceAll 정규식 패턴")
class Ex02_MatchesPatternAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 문자열이 숫자로만 이루어졌는지 확인하세요.
     * 힌트: matches("^\\d+$")
     *
     * [풀이] matches()는 전체 문자열에 대해 매칭하므로 ^ $ 없이도 동작하지만,
     *        명시적으로 ^ $ 를 붙이면 의도가 명확해진다.
     */
    boolean isOnlyNumbers(String input) {
        return input.matches("^\\d+$");
    }

    @Test
    @DisplayName("문제 1: ^\\d+$ — 숫자만 포함 여부 확인")
    void test_isOnlyNumbers() {
        assertThat(isOnlyNumbers("12345")).isTrue();
        assertThat(isOnlyNumbers("00001")).isTrue();
        assertThat(isOnlyNumbers("123abc")).isFalse();
        assertThat(isOnlyNumbers("")).isFalse();
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 문자열이 올바른 이메일 형식인지 확인하세요.
     *
     * [풀이] 아이디 부분(@앞): 알파벳·숫자·특수문자(._%+-)
     *        도메인 부분(@뒤): 알파벳·숫자·하이픈·점
     *        최상위도메인: 알파벳 2자 이상
     */
    boolean isValidEmail(String input) {
        String pattern = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";
        return input.matches(pattern);
    }

    @Test
    @DisplayName("문제 2: 이메일 형식 검증")
    void test_isValidEmail() {
        assertThat(isValidEmail("user@example.com")).isTrue();
        assertThat(isValidEmail("hello.world@mail.co.kr")).isTrue();
        assertThat(isValidEmail("invalid@")).isFalse();
        assertThat(isValidEmail("noatsign.com")).isFalse();
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 문자열이 한국 휴대폰 번호 형식인지 확인하세요.
     *
     * [풀이] (?:0|1|[6-9]) — 비캡처 그룹으로 세 번째 자리를 OR 조건 처리
     *        (?:\\d{3}|\\d{4}) — 중간 자리 3자리 또는 4자리 허용
     */
    boolean isValidPhoneNumber(String input) {
        String pattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        return input.matches(pattern);
    }

    @Test
    @DisplayName("문제 3: 한국 휴대폰 번호 검증")
    void test_isValidPhoneNumber() {
        assertThat(isValidPhoneNumber("010-1234-5678")).isTrue();
        assertThat(isValidPhoneNumber("011-234-5678")).isTrue();
        assertThat(isValidPhoneNumber("010-12345-6789")).isFalse();
        assertThat(isValidPhoneNumber("02-1234-5678")).isFalse();
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 문자열이 비밀번호 규칙(영문·숫자 포함 8~16자)을 만족하는지 확인하세요.
     *
     * [풀이] (?=.*[A-Za-z]) — 전방 탐색: 영문자가 최소 1개 존재해야 진행 허용
     *        (?=.*\\d)       — 전방 탐색: 숫자가 최소 1개 존재해야 진행 허용
     *        두 조건을 AND로 강제하면서 전체 길이 8~16자를 검사한다.
     */
    boolean isValidPassword(String input) {
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
        return input.matches(pattern);
    }

    @Test
    @DisplayName("문제 4: 비밀번호 규칙 검증 — 영문·숫자 포함 8~16자")
    void test_isValidPassword() {
        assertThat(isValidPassword("abcdefg1")).isTrue();
        assertThat(isValidPassword("Password1")).isTrue();
        assertThat(isValidPassword("12345678")).isFalse();
        assertThat(isValidPassword("abcdefgh")).isFalse();
        assertThat(isValidPassword("abc123")).isFalse();
    }

    // ── 문제 5 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 연속된 공백·탭을 한 칸의 공백으로 정규화하고 앞뒤 공백을 제거하세요.
     *
     * [풀이] replaceAll("\\s+", " ") 으로 연속 공백을 단일 공백으로 치환한 후
     *        trim() 으로 앞뒤 공백을 제거한다.
     */
    String normalizeWhitespace(String input) {
        return input.replaceAll("\\s+", " ").trim();
    }

    @Test
    @DisplayName("문제 5: \\s+ — 연속 공백 정규화")
    void test_normalizeWhitespace() {
        String result = normalizeWhitespace("안녕   하세요.  오늘도\t좋은  하루  되세요.");
        assertThat(result).isEqualTo("안녕 하세요. 오늘도 좋은 하루 되세요.");
    }
}
