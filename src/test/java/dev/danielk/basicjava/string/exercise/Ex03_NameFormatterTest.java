package dev.danielk.basicjava.string.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03: 이름 포매터
 *
 * 사용자 이름 데이터를 다양한 형태로 가공하는 메서드를 완성하세요.
 *
 * 사용해야 할 메서드:
 *   strip/trim, toUpperCase/toLowerCase, String.format,
 *   String.join, repeat, compareTo/equalsIgnoreCase,
 *   substring, charAt, isEmpty/isBlank
 */
@DisplayName("연습 03: 이름 포매터")
class Ex03_NameFormatterTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * "성 이름" 형태의 영문 이름을 "이름 성" 순서로 바꾸세요.
     * 앞뒤 공백이 있을 수 있습니다.
     * 예: "  Kim Gildong  " → "Gildong Kim"
     * 힌트: strip, split, String.format 또는 join
     */
    String swapName(String fullName) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: 성/이름 순서 바꾸기")
    void test_swapName() {
        assertThat(swapName("Kim Gildong")).isEqualTo("Gildong Kim");
        assertThat(swapName("  Park Jiyeon  ")).isEqualTo("Jiyeon Park");
        assertThat(swapName("Lee Minsu")).isEqualTo("Minsu Lee");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 이름 목록을 받아 "안녕하세요, {이름}님!" 형태의 인사말 목록을 반환하세요.
     * 힌트: String.format 또는 문자열 연결
     */
    List<String> greetAll(List<String> names) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 이름 목록 인사말 생성")
    void test_greetAll() {
        List<String> result = greetAll(List.of("홍길동", "김철수", "이영희"));
        assertThat(result).containsExactly(
                "안녕하세요, 홍길동님!",
                "안녕하세요, 김철수님!",
                "안녕하세요, 이영희님!"
        );
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 이름 목록을 사전순(대소문자 무시)으로 정렬한 결과를 반환하세요.
     * 힌트: List 복사 후 sort, compareTo 또는 compareToIgnoreCase
     */
    List<String> sortNames(List<String> names) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 이름 사전순 정렬")
    void test_sortNames() {
        List<String> sorted = sortNames(List.of("Charlie", "alice", "Bob"));
        assertThat(sorted).containsExactly("alice", "Bob", "Charlie");
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 이름을 이니셜로 변환하세요.
     * "Hong Gildong" → "H.G."
     * 힌트: split, charAt(0), toUpperCase, StringBuilder, repeat 불필요 → 반복으로 처리
     */
    String toInitials(String fullName) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: 이니셜 변환")
    void test_toInitials() {
        assertThat(toInitials("Hong Gildong")).isEqualTo("H.G.");
        assertThat(toInitials("kim min su")).isEqualTo("K.M.S.");
        assertThat(toInitials("Alice")).isEqualTo("A.");
    }

    // ── 문제 5 ────────────────────────────────────────────────────────────────
    /**
     * 이름 목록을 쉼표로 구분된 하나의 문자열로 만들되, 마지막 이름 앞에는 " 그리고 "를 붙이세요.
     * 예: ["Alice", "Bob", "Charlie"] → "Alice, Bob 그리고 Charlie"
     * 예: ["Alice", "Bob"]            → "Alice 그리고 Bob"
     * 예: ["Alice"]                   → "Alice"
     * 힌트: size 분기, subList, String.join
     */
    String joinWithAnd(List<String> names) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 5: 이름 목록 자연어 결합")
    void test_joinWithAnd() {
        assertThat(joinWithAnd(List.of("Alice", "Bob", "Charlie"))).isEqualTo("Alice, Bob 그리고 Charlie");
        assertThat(joinWithAnd(List.of("Alice", "Bob"))).isEqualTo("Alice 그리고 Bob");
        assertThat(joinWithAnd(List.of("Alice"))).isEqualTo("Alice");
    }

    // ── 문제 6 ────────────────────────────────────────────────────────────────
    /**
     * 이름을 최대 maxLen 글자로 줄이고 초과하면 "..."을 붙이세요.
     * 예: ("홍길동입니다", 4) → "홍길동입..."  (4글자 + "...")
     *     ("짧은", 5)        → "짧은"          (초과 안 함)
     * 힌트: length, substring
     */
    String truncate(String name, int maxLen) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 6: 이름 말줄임 처리")
    void test_truncate() {
        assertThat(truncate("홍길동입니다", 4)).isEqualTo("홍길동입...");
        assertThat(truncate("짧은", 5)).isEqualTo("짧은");
        assertThat(truncate("Hello World", 5)).isEqualTo("Hello...");
    }
}
