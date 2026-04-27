package dev.danielk.basicjava.string.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01: 로그 파서
 *
 * 서버 로그 문자열에서 필요한 정보를 추출하는 유틸 메서드를 완성하세요.
 *
 * 사용해야 할 메서드:
 *   split, substring, indexOf, contains, startsWith, endsWith,
 *   strip, matches, Pattern/Matcher (그룹 추출)
 *
 * 로그 형식 예시:
 *   "[2026-04-27 15:30:00] ERROR  UserService - 사용자를 찾을 수 없습니다 (userId=42)"
 *   "[2026-04-27 09:00:01] INFO   AppServer  - 서버가 시작되었습니다"
 *   "[2026-04-27 23:59:59] WARN   PayService - 결제 응답 지연 (elapsed=3200ms)"
 */
@DisplayName("연습 01: 로그 파서")
class Ex01_LogParserTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 로그 한 줄에서 날짜(yyyy-MM-dd) 부분만 추출하세요.
     * 힌트: indexOf('['), indexOf(' '), substring
     */
    String extractDate(String logLine) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: 날짜 추출")
    void test_extractDate() {
        assertThat(extractDate("[2026-04-27 15:30:00] ERROR  UserService - 사용자를 찾을 수 없습니다"))
                .isEqualTo("2026-04-27");
        assertThat(extractDate("[2026-01-01 00:00:00] INFO   AppServer  - 시작"))
                .isEqualTo("2026-01-01");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 로그 한 줄에서 로그 레벨(ERROR / INFO / WARN)을 추출하세요.
     * 로그 레벨은 ']' 이후 공백으로 구분된 첫 번째 토큰입니다.
     * 힌트: indexOf(']'), substring, split, strip
     */
    String extractLevel(String logLine) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 로그 레벨 추출")
    void test_extractLevel() {
        assertThat(extractLevel("[2026-04-27 15:30:00] ERROR  UserService - 사용자를 찾을 수 없습니다"))
                .isEqualTo("ERROR");
        assertThat(extractLevel("[2026-04-27 09:00:01] INFO   AppServer  - 서버가 시작되었습니다"))
                .isEqualTo("INFO");
        assertThat(extractLevel("[2026-04-27 23:59:59] WARN   PayService - 결제 응답 지연"))
                .isEqualTo("WARN");
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 로그 한 줄이 에러 로그인지 판별하세요.
     * 힌트: contains 또는 startsWith 활용
     */
    boolean isErrorLog(String logLine) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 에러 로그 판별")
    void test_isErrorLog() {
        assertThat(isErrorLog("[2026-04-27 15:30:00] ERROR  UserService - 오류 발생")).isTrue();
        assertThat(isErrorLog("[2026-04-27 09:00:01] INFO   AppServer  - 시작")).isFalse();
        assertThat(isErrorLog("[2026-04-27 23:59:59] WARN   PayService - 지연")).isFalse();
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 로그 목록에서 특정 레벨의 로그만 필터링하여 반환하세요.
     * 힌트: extractLevel 재사용, equals
     */
    List<String> filterByLevel(List<String> logs, String level) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: 레벨별 로그 필터링")
    void test_filterByLevel() {
        List<String> logs = List.of(
                "[2026-04-27 09:00:00] INFO   AppServer  - 시작",
                "[2026-04-27 10:00:00] ERROR  UserService - 오류",
                "[2026-04-27 11:00:00] INFO   AppServer  - 요청 처리",
                "[2026-04-27 12:00:00] WARN   PayService - 지연"
        );

        List<String> errorLogs = filterByLevel(logs, "ERROR");
        assertThat(errorLogs).hasSize(1);
        assertThat(errorLogs.get(0)).contains("UserService");

        List<String> infoLogs = filterByLevel(logs, "INFO");
        assertThat(infoLogs).hasSize(2);
    }

    // ── 문제 5 ────────────────────────────────────────────────────────────────
    /**
     * 로그 한 줄의 타임스탬프([yyyy-MM-dd HH:mm:ss])가 올바른 형식인지 검증하세요.
     * 힌트: matches 또는 Pattern/Matcher
     * 패턴: 대괄호 안에 "yyyy-MM-dd HH:mm:ss" 형식
     */
    boolean isValidTimestamp(String logLine) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 5: 타임스탬프 형식 검증")
    void test_isValidTimestamp() {
        assertThat(isValidTimestamp("[2026-04-27 15:30:00] ERROR - 오류")).isTrue();
        assertThat(isValidTimestamp("[2026-4-27 15:30:00] ERROR - 오류")).isFalse();   // 월이 한 자리
        assertThat(isValidTimestamp("2026-04-27 15:30:00 ERROR - 오류")).isFalse();    // 대괄호 없음
    }

    // ── 문제 6 ────────────────────────────────────────────────────────────────
    /**
     * 로그 문자열에서 "key=value" 형태의 파라미터를 모두 추출해 "key: value" 형태의 목록으로 반환하세요.
     * 예: "userId=42 elapsed=3200ms" → ["userId: 42", "elapsed: 3200ms"]
     * 힌트: Pattern/Matcher, group 추출, String.format 또는 문자열 조합
     */
    List<String> extractParams(String logLine) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 6: key=value 파라미터 추출")
    void test_extractParams() {
        List<String> result1 = extractParams(
                "[2026-04-27 15:30:00] ERROR  UserService - 오류 (userId=42)");
        assertThat(result1).containsExactly("userId: 42");   // 괄호는 포함하지 않음

        List<String> result2 = extractParams(
                "[2026-04-27 23:59:59] WARN   PayService - 지연 (elapsed=3200ms retryCount=3)");
        assertThat(result2).containsExactly("elapsed: 3200ms", "retryCount: 3");  // 괄호 미포함

        List<String> result3 = extractParams(
                "[2026-04-27 09:00:01] INFO   AppServer  - 서버가 시작되었습니다");
        assertThat(result3).isEmpty();
    }
}
