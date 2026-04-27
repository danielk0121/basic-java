package dev.danielk.basicjava.datetime.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01: 이벤트 일정 관리기
 *
 * 이벤트 시작/종료 날짜 파싱, D-day 계산, 기간(Period) 출력을 수행하세요.
 *
 * 사용해야 할 메서드:
 *   LocalDate.parse, DateTimeFormatter, ChronoUnit.DAYS.between,
 *   Period.between, plusDays/minusDays
 */
@DisplayName("연습 01: 이벤트 일정 관리기")
class Ex01_EventSchedulerTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * "yyyy-MM-dd" 형식 문자열을 LocalDate로 파싱하여 반환하세요.
     * 힌트: LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
     */
    LocalDate parseDate(String dateStr) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: 날짜 문자열 파싱")
    void test_parseDate() {
        assertThat(parseDate("2026-04-27")).isEqualTo(LocalDate.of(2026, 4, 27));
        assertThat(parseDate("2026-01-01")).isEqualTo(LocalDate.of(2026, 1, 1));
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 오늘(today)부터 이벤트 날짜(eventDate)까지 남은 일수(D-day)를 반환하세요.
     * 이벤트가 지났으면 음수, 오늘이면 0, 미래면 양수
     * 힌트: ChronoUnit.DAYS.between(today, eventDate)
     */
    long dday(LocalDate today, LocalDate eventDate) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: D-day 계산")
    void test_dday() {
        LocalDate today = LocalDate.of(2026, 4, 27);
        assertThat(dday(today, LocalDate.of(2026, 5, 1))).isEqualTo(4);
        assertThat(dday(today, LocalDate.of(2026, 4, 27))).isEqualTo(0);
        assertThat(dday(today, LocalDate.of(2026, 4, 20))).isEqualTo(-7);
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 두 날짜 사이의 기간을 Period로 계산하여 "X년 Y개월 Z일" 형태로 반환하세요.
     * 힌트: Period.between(start, end), getYears(), getMonths(), getDays()
     */
    String periodString(LocalDate start, LocalDate end) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 기간 문자열 반환")
    void test_periodString() {
        assertThat(periodString(LocalDate.of(2024, 1, 1), LocalDate.of(2026, 4, 27)))
                .isEqualTo("2년 3개월 26일");
        assertThat(periodString(LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 27)))
                .isEqualTo("0년 0개월 26일");
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 이벤트 시작일에서 n일 후의 종료일을 반환하세요.
     * 힌트: startDate.plusDays(n)
     */
    LocalDate endDate(LocalDate startDate, int days) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: 이벤트 종료일 계산")
    void test_endDate() {
        assertThat(endDate(LocalDate.of(2026, 4, 27), 7)).isEqualTo(LocalDate.of(2026, 5, 4));
        assertThat(endDate(LocalDate.of(2026, 12, 28), 5)).isEqualTo(LocalDate.of(2027, 1, 2));
    }
}
