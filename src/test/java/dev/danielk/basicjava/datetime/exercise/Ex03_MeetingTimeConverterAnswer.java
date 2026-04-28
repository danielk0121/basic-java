package dev.danielk.basicjava.datetime.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03 답안: 글로벌 회의 시간 변환기
 *
 * 서울 기준 회의 시각을 여러 타임존으로 변환하세요.
 *
 * 사용해야 할 메서드:
 *   ZonedDateTime, ZoneId.of, withZoneSameInstant, Instant, format
 */
@DisplayName("연습 03 답안: 글로벌 회의 시간 변환기")
class Ex03_MeetingTimeConverterAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 서울(Asia/Seoul) 기준 LocalDateTime을 ZonedDateTime으로 생성하여 반환하세요.
     * 힌트: ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"))
     *
     * [풀이] ZonedDateTime.of: LocalDateTime + ZoneId를 결합하여 시간대 정보 부여
     */
    ZonedDateTime toSeoulZoned(LocalDateTime localDateTime) {
        return ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"));
    }

    @Test
    @DisplayName("문제 1: 서울 ZonedDateTime 생성")
    void test_toSeoulZoned() {
        LocalDateTime ldt = LocalDateTime.of(2026, 4, 27, 10, 0);
        ZonedDateTime result = toSeoulZoned(ldt);
        assertThat(result.getZone()).isEqualTo(ZoneId.of("Asia/Seoul"));
        assertThat(result.getHour()).isEqualTo(10);
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 서울 ZonedDateTime을 UTC로 변환하여 반환하세요.
     * 힌트: withZoneSameInstant(ZoneId.of("UTC"))
     *
     * [풀이] withZoneSameInstant: 동일한 절대 시각(Instant)을 유지하면서 타임존만 변환
     */
    ZonedDateTime toUtc(ZonedDateTime seoulTime) {
        return seoulTime.withZoneSameInstant(ZoneId.of("UTC"));
    }

    @Test
    @DisplayName("문제 2: 서울 → UTC 변환")
    void test_toUtc() {
        ZonedDateTime seoul = ZonedDateTime.of(
                LocalDateTime.of(2026, 4, 27, 12, 0), ZoneId.of("Asia/Seoul"));
        ZonedDateTime utc = toUtc(seoul);
        assertThat(utc.getHour()).isEqualTo(3);
        assertThat(utc.getZone()).isEqualTo(ZoneId.of("UTC"));
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 서울 ZonedDateTime을 뉴욕(America/New_York) 시간으로 변환하여 반환하세요.
     * 힌트: withZoneSameInstant(ZoneId.of("America/New_York"))
     *
     * [풀이] withZoneSameInstant로 절대 시각을 유지하면서 뉴욕 타임존으로 변환
     */
    ZonedDateTime toNewYork(ZonedDateTime seoulTime) {
        return seoulTime.withZoneSameInstant(ZoneId.of("America/New_York"));
    }

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

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] ZonedDateTime을 "yyyy-MM-dd HH:mm z" 형태 문자열로 포맷하여 반환하세요.
     * 힌트: DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z")
     *
     * [풀이] 'z' 패턴: 타임존 약어 (KST, UTC, EDT 등) 출력
     */
    String formatZoned(ZonedDateTime zdt) {
        return zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z"));
    }

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
