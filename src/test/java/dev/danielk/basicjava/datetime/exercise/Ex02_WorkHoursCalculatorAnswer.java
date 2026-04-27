package dev.danielk.basicjava.datetime.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: 근무 시간 계산기
 */
@DisplayName("연습 02 답안: 근무 시간 계산기")
class Ex02_WorkHoursCalculatorAnswer {

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

    /** HOURS.between: 소수점 이하 버림 (9시간 30분 → 9) */
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

    /** 총 근무분 - 기준분 > 0 이면 초과분, 아니면 0 */
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
        assertThat(overtimeMinutes(in, out, 8)).isEqualTo(90);
        assertThat(overtimeMinutes(in, out, 10)).isEqualTo(0);
    }
}
