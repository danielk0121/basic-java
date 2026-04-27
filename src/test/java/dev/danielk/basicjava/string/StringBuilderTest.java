package dev.danielk.basicjava.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StringBuilder / CharSequence
 * - 가변 문자열 처리, 문자 시퀀스 조작
 */
@DisplayName("StringBuilder / CharSequence")
class StringBuilderTest {

    // ── StringBuilder 기본 조작 ───────────────────────────────────────────

    @Test
    @DisplayName("append / insert / delete / replace / reverse")
    void stringBuilderBasic() {
        StringBuilder sb = new StringBuilder("Hello");

        sb.append(" World");
        assertThat(sb.toString()).isEqualTo("Hello World");

        sb.insert(5, ",");
        assertThat(sb.toString()).isEqualTo("Hello, World");

        sb.delete(5, 6);                          // 5 이상 6 미만 삭제
        assertThat(sb.toString()).isEqualTo("Hello World");

        sb.replace(6, 11, "Java");
        assertThat(sb.toString()).isEqualTo("Hello Java");

        sb.reverse();
        assertThat(sb.toString()).isEqualTo("avaJ olleH");
    }

    @Test
    @DisplayName("indexOf / length / charAt / deleteCharAt")
    void stringBuilderSearch() {
        StringBuilder sb = new StringBuilder("abcabc");

        assertThat(sb.indexOf("bc")).isEqualTo(1);
        assertThat(sb.length()).isEqualTo(6);
        assertThat(sb.charAt(2)).isEqualTo('c');

        sb.deleteCharAt(0);
        assertThat(sb.toString()).isEqualTo("bcabc");
    }

    @Test
    @DisplayName("루프에서 문자열 누적: + 대신 StringBuilder 사용")
    void stringBuilderLoop() {
        // String + 반복은 매 iteration마다 새 객체 생성 → StringBuilder 권장
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append(i);
            if (i < 5) sb.append(",");
        }
        assertThat(sb.toString()).isEqualTo("1,2,3,4,5");
    }

    // ── CharSequence subSequence ──────────────────────────────────────────

    @Test
    @DisplayName("CharSequence subSequence: String, StringBuilder 공통 인터페이스")
    void charSequenceSubSequence() {
        // String은 CharSequence를 구현
        CharSequence cs = "Hello, World!";
        CharSequence sub = cs.subSequence(7, 12);
        assertThat(sub.toString()).isEqualTo("World");

        // StringBuilder도 CharSequence를 구현
        CharSequence sbSeq = new StringBuilder("abcdef");
        assertThat(sbSeq.subSequence(2, 5).toString()).isEqualTo("cde");

        // CharSequence를 파라미터로 받는 유틸 메서드 예시
        assertThat(containsDigit("abc1")).isTrue();
        assertThat(containsDigit("abcd")).isFalse();
    }

    /** CharSequence를 인터페이스로 받아 처리하는 예시 메서드 */
    private boolean containsDigit(CharSequence cs) {
        for (int i = 0; i < cs.length(); i++) {
            if (Character.isDigit(cs.charAt(i))) return true;
        }
        return false;
    }
}
