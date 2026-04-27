package dev.danielk.basicjava.datetime.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03: 글로벌 회의 시간 변환기
 *
 * 서울 기준 회의 시각을 여러 타임존으로 변환하세요.
 *
 * 사용해야 할 메서드:
 *   ZonedDateTime, ZoneId.of, withZoneSameInstant, Instant, format
 */
@DisplayName("연습 03: 글로벌 회의 시간 변환기")
class Ex03_MeetingTimeConverterTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 서울(Asia/Seoul) 기준 LocalDateTime을 ZonedDateTime으로 생성하여 반환하세요.
     * 힌트: ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"))
     */
    ZonedDateTime toSeoulZoned(LocalDateTime localDateTime) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: 서울 ZonedDateTime 생성")
    void test_toSeoulZoned() {
        LocalDateTime ldt = LocalDateTime.of(2026, 4, 27, 10, 0);
        ZonedDateTime result = toSeoulZoned(ldt);
        assertThat(result.getZone()).isEqualTo(ZoneId.of("Asia/Seoul"));
        assertThat(result.getHour()).isEqualTo(10);
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 서울 ZonedDateTime을 UTC로 변환하여 반환하세요.
     * 힌트: withZoneSameInstant(ZoneId.of("UTC"))
     */
    ZonedDateTime toUtc(ZonedDateTime seoulTime) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 서울 → UTC 변환")
    void test_toUtc() {
        ZonedDateTime seoul = ZonedDateTime.of(
                LocalDateTime.of(2026, 4, 27, 12, 0), ZoneId.of("Asia/Seoul"));
        ZonedDateTime utc = toUtc(seoul);
        assertThat(utc.getHour()).isEqualTo(3);
        assertThat(utc.getZone()).isEqualTo(ZoneId.of("UTC"));
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 서울 ZonedDateTime을 뉴욕(America/New_York) 시간으로 변환하여 반환하세요.
     * 힌트: withZoneSameInstant(ZoneId.of("America/New_York"))
     */
    ZonedDateTime toNewYork(ZonedDateTime seoulTime) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 서울 → 뉴욕 변환")
    void test_toNewYork() {
        ZonedDateTime seoul = ZonedDateTime.of(
                LocalDateTime.of(2026, 4, 27, 12, 0), ZoneId.of("Asia/Seoul"));
        ZonedDateTime ny = toNewYork(seoul);
        // EDT(UTC-4) 기준: 서울 12:00 → UTC 3:00 → 뉴욕 전날 23:00
        assertThat(ny.toLocalDate()).isEqualTo(LocalDate.of(2026, 4, 26));
        assertThat(ny.getHour()).isEqualTo(23);
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * ZonedDateTime을 "yyyy-MM-dd HH:mm z" 형태 문자열로 포맷하여 반환하세요.
     * 힌트: DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z")
     */
    String formatZoned(ZonedDateTime zdt) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: ZonedDateTime 포맷")
    void test_formatZoned() {
        ZonedDateTime seoul = ZonedDateTime.of(
                LocalDateTime.of(2026, 4, 27, 10, 0), ZoneId.of("Asia/Seoul"));
        String result = formatZoned(seoul);
        assertThat(result).startsWith("2026-04-27 10:00");
        assertThat(result).contains("KST");
    }
}
