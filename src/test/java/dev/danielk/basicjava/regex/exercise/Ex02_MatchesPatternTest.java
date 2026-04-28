package dev.danielk.basicjava.regex.exercise;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02: matches / replaceAll 정규식 패턴
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
@DisplayName("연습 02: matches / replaceAll 정규식 패턴")
class Ex02_MatchesPatternTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 문자열이 숫자로만 이루어졌는지 확인하세요.
     * 힌트: matches("^\\d+$")
     */
    boolean isOnlyNumbers(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: ^\\d+$ — 숫자만 포함 여부 확인")
    void test_isOnlyNumbers() {
        assertThat(isOnlyNumbers("12345")).isTrue();
        assertThat(isOnlyNumbers("00001")).isTrue();
        assertThat(isOnlyNumbers("123abc")).isFalse();
        assertThat(isOnlyNumbers("")).isFalse();
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 문자열이 올바른 이메일 형식인지 확인하세요.
     * 힌트: 아이디@도메인.최상위도메인(2자 이상) 구조
     */
    boolean isValidEmail(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 이메일 형식 검증")
    void test_isValidEmail() {
        assertThat(isValidEmail("user@example.com")).isTrue();
        assertThat(isValidEmail("hello.world@mail.co.kr")).isTrue();
        assertThat(isValidEmail("invalid@")).isFalse();
        assertThat(isValidEmail("noatsign.com")).isFalse();
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 문자열이 한국 휴대폰 번호 형식(010-1234-5678)인지 확인하세요.
     * 힌트: 010·011·016~019 허용, 중간 자리 3~4자리, (?:...) 비캡처 그룹 사용
     */
    boolean isValidPhoneNumber(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 한국 휴대폰 번호 검증")
    void test_isValidPhoneNumber() {
        assertThat(isValidPhoneNumber("010-1234-5678")).isTrue();
        assertThat(isValidPhoneNumber("011-234-5678")).isTrue();
        assertThat(isValidPhoneNumber("010-12345-6789")).isFalse();  // 중간 자리 초과
        assertThat(isValidPhoneNumber("02-1234-5678")).isFalse();    // 지역번호 형식
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 문자열이 비밀번호 규칙(영문·숫자 포함 8~16자)을 만족하는지 확인하세요.
     * 힌트: (?=.*[A-Za-z]) 영문 최소 1개, (?=.*\\d) 숫자 최소 1개 — 전방 탐색
     */
    boolean isValidPassword(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: 비밀번호 규칙 검증 — 영문·숫자 포함 8~16자")
    void test_isValidPassword() {
        assertThat(isValidPassword("abcdefg1")).isTrue();
        assertThat(isValidPassword("Password1")).isTrue();
        assertThat(isValidPassword("12345678")).isFalse();   // 숫자만
        assertThat(isValidPassword("abcdefgh")).isFalse();   // 영문만
        assertThat(isValidPassword("abc123")).isFalse();     // 6자 (미달)
    }

    // ── 문제 5 ────────────────────────────────────────────────────────────────
    /**
     * 문자열의 연속된 공백·탭을 한 칸의 공백으로 정규화하세요.
     * 앞뒤 공백도 제거하세요.
     * 힌트: replaceAll("\\s+", " ") + trim()
     */
    String normalizeWhitespace(String input) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 5: \\s+ — 연속 공백 정규화")
    void test_normalizeWhitespace() {
        String result = normalizeWhitespace("안녕   하세요.  오늘도\t좋은  하루  되세요.");
        assertThat(result).isEqualTo("안녕 하세요. 오늘도 좋은 하루 되세요.");
    }
}
