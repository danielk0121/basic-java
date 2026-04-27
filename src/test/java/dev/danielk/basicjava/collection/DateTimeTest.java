package dev.danielk.basicjava.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 날짜/시간 처리 보일러플레이트
 * - 현재 날짜/시간, 포맷 변환, 연산, 타임존
 */
@DisplayName("날짜/시간 처리")
class DateTimeTest {

    // ── 현재 날짜/시간 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("현재 날짜/시간 가져오기")
    void now() {
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zoned = ZonedDateTime.now();

        assertThat(today).isNotNull();
        assertThat(time).isNotNull();
        assertThat(dateTime.toLocalDate()).isEqualTo(today);
        assertThat(zoned.toLocalDate()).isEqualTo(today);
    }

    // ── 포맷 변환 ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("날짜 포맷 변환: LocalDate ↔ String / DateTimeFormatter")
    void format() {
        LocalDate date = LocalDate.of(2026, 4, 27);

        // LocalDate → 문자열
        String formatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertThat(formatted).isEqualTo("2026-04-27");

        String korFormat = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        assertThat(korFormat).isEqualTo("2026년 04월 27일");

        // 문자열 → LocalDate
        LocalDate parsed = LocalDate.parse("2026-04-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertThat(parsed).isEqualTo(date);

        // LocalDateTime 포맷
        LocalDateTime dt = LocalDateTime.of(2026, 4, 27, 15, 30, 0);
        String dtFormatted = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertThat(dtFormatted).isEqualTo("2026-04-27 15:30:00");

        // ISO 표준 포맷
        assertThat(date.format(DateTimeFormatter.ISO_LOCAL_DATE)).isEqualTo("2026-04-27");
    }

    // ── 날짜 연산 ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("날짜 연산: 더하기 / 빼기 / 차이 계산")
    void calculation() {
        LocalDate base = LocalDate.of(2026, 4, 27);

        // 더하기
        assertThat(base.plusDays(3)).isEqualTo(LocalDate.of(2026, 4, 30));
        assertThat(base.plusMonths(1)).isEqualTo(LocalDate.of(2026, 5, 27));
        assertThat(base.plusYears(1)).isEqualTo(LocalDate.of(2027, 4, 27));

        // 빼기
        assertThat(base.minusDays(7)).isEqualTo(LocalDate.of(2026, 4, 20));
        assertThat(base.minusMonths(1)).isEqualTo(LocalDate.of(2026, 3, 27));

        // 두 날짜 차이 계산
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 4, 27);

        long daysBetween = ChronoUnit.DAYS.between(start, end);
        assertThat(daysBetween).isEqualTo(116);

        Period period = Period.between(start, end);
        assertThat(period.getMonths()).isEqualTo(3);
        assertThat(period.getDays()).isEqualTo(26);

        // LocalDateTime 차이
        LocalDateTime from = LocalDateTime.of(2026, 4, 27, 9, 0);
        LocalDateTime to = LocalDateTime.of(2026, 4, 27, 18, 30);
        long hours = ChronoUnit.HOURS.between(from, to);
        assertThat(hours).isEqualTo(9);
    }

    // ── 타임존 처리 ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("타임존 처리: ZonedDateTime / ZoneId 변환")
    void timezone() {
        ZoneId seoul = ZoneId.of("Asia/Seoul");
        ZoneId utc = ZoneId.of("UTC");
        ZoneId newYork = ZoneId.of("America/New_York");

        // 서울 기준 특정 시각
        ZonedDateTime seoulTime = ZonedDateTime.of(
                LocalDateTime.of(2026, 4, 27, 12, 0), seoul);

        // UTC로 변환 (서울은 UTC+9)
        ZonedDateTime utcTime = seoulTime.withZoneSameInstant(utc);
        assertThat(utcTime.getHour()).isEqualTo(3);

        // 뉴욕으로 변환 (서울과 약 13~14시간 차이, EDT 기준 UTC-4)
        ZonedDateTime nyTime = seoulTime.withZoneSameInstant(newYork);
        assertThat(nyTime.toLocalDate()).isEqualTo(LocalDate.of(2026, 4, 26)); // 전날

        // Instant: 타임존 무관한 절대 시각
        Instant instant = seoulTime.toInstant();
        ZonedDateTime backToSeoul = instant.atZone(seoul);
        assertThat(backToSeoul.getHour()).isEqualTo(12);
    }
}
