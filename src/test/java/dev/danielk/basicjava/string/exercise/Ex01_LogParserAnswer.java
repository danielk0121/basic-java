package dev.danielk.basicjava.string.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01 답안: 로그 파서
 */
@DisplayName("연습 01 답안: 로그 파서")
class Ex01_LogParserAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * '[' 다음부터 첫 번째 공백 전까지 = 날짜 부분
     * "[2026-04-27 15:30:00] ..." → indexOf('[')=0, indexOf(' ')=11
     * substring(1, 11) → "2026-04-27"
     */
    String extractDate(String logLine) {
        int start = logLine.indexOf('[') + 1;        // '[' 바로 다음
        int end = logLine.indexOf(' ', start);       // 첫 공백 위치
        return logLine.substring(start, end);
    }

    @Test
    @DisplayName("문제 1: 날짜 추출")
    void test_extractDate() {
        assertThat(extractDate("[2026-04-27 15:30:00] ERROR  UserService - 사용자를 찾을 수 없습니다"))
                .isEqualTo("2026-04-27");
        assertThat(extractDate("[2026-01-01 00:00:00] INFO   AppServer  - 시작"))
                .isEqualTo("2026-01-01");
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * ']' 이후 문자열을 split("\\s+")으로 나누면:
     * " ERROR  UserService - ..." → ["", "ERROR", "UserService", ...]
     * strip() 후 split하면 인덱스 1이 레벨
     */
    String extractLevel(String logLine) {
        int closeBracket = logLine.indexOf(']');
        String afterBracket = logLine.substring(closeBracket + 1).strip();   // 앞뒤 공백 제거
        return afterBracket.split("\\s+")[0];                                // 첫 번째 토큰
    }

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

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * extractLevel로 레벨을 꺼낸 뒤 "ERROR"와 equals 비교
     * 또는 contains("ERROR")로 단순하게 처리 가능 (레벨이 고정 위치일 때)
     */
    boolean isErrorLog(String logLine) {
        return extractLevel(logLine).equals("ERROR");
    }

    @Test
    @DisplayName("문제 3: 에러 로그 판별")
    void test_isErrorLog() {
        assertThat(isErrorLog("[2026-04-27 15:30:00] ERROR  UserService - 오류 발생")).isTrue();
        assertThat(isErrorLog("[2026-04-27 09:00:01] INFO   AppServer  - 시작")).isFalse();
        assertThat(isErrorLog("[2026-04-27 23:59:59] WARN   PayService - 지연")).isFalse();
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * 각 로그 줄에서 레벨을 추출해 level과 equals 비교 후 필터링
     */
    List<String> filterByLevel(List<String> logs, String level) {
        List<String> result = new ArrayList<>();
        for (String log : logs) {
            if (extractLevel(log).equals(level)) {
                result.add(log);
            }
        }
        return result;
    }

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

    // ── 문제 5 답안 ───────────────────────────────────────────────────────────
    /**
     * 로그 줄 전체가 "[yyyy-MM-dd HH:mm:ss]"로 시작하는지 matches로 검증
     * matches()는 전체 문자열 매칭이므로 앞부분 패턴 + ".*" 필요
     */
    boolean isValidTimestamp(String logLine) {
        return logLine.matches("\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}].*");
    }

    @Test
    @DisplayName("문제 5: 타임스탬프 형식 검증")
    void test_isValidTimestamp() {
        assertThat(isValidTimestamp("[2026-04-27 15:30:00] ERROR - 오류")).isTrue();
        assertThat(isValidTimestamp("[2026-4-27 15:30:00] ERROR - 오류")).isFalse();
        assertThat(isValidTimestamp("2026-04-27 15:30:00 ERROR - 오류")).isFalse();
    }

    // ── 문제 6 답안 ───────────────────────────────────────────────────────────
    /**
     * Pattern으로 "(\w+)=(\S+)" 패턴을 반복 검색
     * group(1) = 키, group(2) = 값
     * String.format으로 "key: value" 형태로 조합
     */
    List<String> extractParams(String logLine) {
        List<String> result = new ArrayList<>();
        // [\w.]+ : 영문자, 숫자, 밑줄, 점만 허용 → 괄호 등 문장부호 제외
        Pattern pattern = Pattern.compile("(\\w+)=([\\w.]+)");
        Matcher matcher = pattern.matcher(logLine);
        while (matcher.find()) {
            result.add(String.format("%s: %s", matcher.group(1), matcher.group(2)));
        }
        return result;
    }

    @Test
    @DisplayName("문제 6: key=value 파라미터 추출")
    void test_extractParams() {
        List<String> result1 = extractParams(
                "[2026-04-27 15:30:00] ERROR  UserService - 오류 (userId=42)");
        assertThat(result1).containsExactly("userId: 42");

        List<String> result2 = extractParams(
                "[2026-04-27 23:59:59] WARN   PayService - 지연 (elapsed=3200ms retryCount=3)");
        assertThat(result2).containsExactly("elapsed: 3200ms", "retryCount: 3");

        List<String> result3 = extractParams(
                "[2026-04-27 09:00:01] INFO   AppServer  - 서버가 시작되었습니다");
        assertThat(result3).isEmpty();
    }
}
