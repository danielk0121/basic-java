package dev.danielk.basicjava.datetime.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: 근무 시간 계산기
 *
 * 출근/퇴근 시각을 파싱하여 근무 시간과 초과근무를 계산하세요.
 *
 * 사용해야 할 메서드:
 *   LocalDateTime.parse, ChronoUnit.HOURS/MINUTES.between, DateTimeFormatter.ofPattern
 */
@DisplayName("연습 02 답안: 근무 시간 계산기")
class Ex02_WorkHoursCalculatorAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] "yyyy-MM-dd HH:mm" 형식 문자열을 LocalDateTime으로 파싱하여 반환하세요.
     * 힌트: LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
     *
     * [풀이] ofPattern("yyyy-MM-dd HH:mm")으로 포매터 생성 후 parse에 전달
     */
    LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Test
    @DisplayName("문제 1: 날짜시간 파싱")
    void test_parseDateTime() {
        assertThat(parseDateTime("2026-04-27 09:00"))
                .isEqualTo(LocalDateTime.of(2026, 4, 27, 9, 0));
        assertThat(parseDateTime("2026-04-27 18:30"))
                .isEqualTo(LocalDateTime.of(2026, 4, 27, 18, 30));
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 출근 시각부터 퇴근 시각까지 총 근무 시간(시 단위, 버림)을 반환하세요.
     * 힌트: ChronoUnit.HOURS.between(checkIn, checkOut)
     *
     * [풀이] HOURS.between: 소수점 이하 버림 (9시간 30분 → 9)
     */
    long workHours(LocalDateTime checkIn, LocalDateTime checkOut) {
        return ChronoUnit.HOURS.between(checkIn, checkOut);
    }

    @Test
    @DisplayName("문제 2: 근무 시간(시) 계산")
    void test_workHours() {
        assertThat(workHours(
                LocalDateTime.of(2026, 4, 27, 9, 0),
                LocalDateTime.of(2026, 4, 27, 18, 30))).isEqualTo(9);
        assertThat(workHours(
                LocalDateTime.of(2026, 4, 27, 8, 30),
                LocalDateTime.of(2026, 4, 27, 17, 0))).isEqualTo(8);
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 총 근무 분을 계산하세요.
     * 힌트: ChronoUnit.MINUTES.between
     *
     * [풀이] MINUTES.between: 두 시각 사이의 분 단위 차이 반환
     */
    long workMinutes(LocalDateTime checkIn, LocalDateTime checkOut) {
        return ChronoUnit.MINUTES.between(checkIn, checkOut);
    }

    @Test
    @DisplayName("문제 3: 근무 시간(분) 계산")
    void test_workMinutes() {
        assertThat(workMinutes(
                LocalDateTime.of(2026, 4, 27, 9, 0),
                LocalDateTime.of(2026, 4, 27, 18, 30))).isEqualTo(570);
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 기준 근무시간(standardHours)을 초과하면 초과 시간(분)을 반환하고, 아니면 0을 반환하세요.
     * 힌트: workMinutes 활용, standardHours * 60과 비교
     *
     * [풀이] 총 근무분 - 기준분 > 0 이면 초과분, 아니면 0
     */
    long overtimeMinutes(LocalDateTime checkIn, LocalDateTime checkOut, int standardHours) {
        long worked = workMinutes(checkIn, checkOut);
        long standard = (long) standardHours * 60;
        return worked > standard ? worked - standard : 0;
    }

    @Test
    @DisplayName("문제 4: 초과근무 분 계산")
    void test_overtimeMinutes() {
        LocalDateTime in = LocalDateTime.of(2026, 4, 27, 9, 0);
        LocalDateTime out = LocalDateTime.of(2026, 4, 27, 18, 30);
        assertThat(overtimeMinutes(in, out, 8)).isEqualTo(90); // 570 - 480 = 90
        assertThat(overtimeMinutes(in, out, 10)).isEqualTo(0); // 570 < 600 → 초과 없음
    }
}
