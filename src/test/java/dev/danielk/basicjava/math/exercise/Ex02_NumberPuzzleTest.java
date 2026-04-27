package dev.danielk.basicjava.math.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02: 숫자 퍼즐 검증기
 *
 * 소수 판별, 자릿수 합 조건, 진법 변환 후 회문 여부를 검증하세요.
 *
 * 사용해야 할 메서드:
 *   isPrime 직접 구현, String.valueOf.chars,
 *   Integer.toBinaryString, StringBuilder.reverse
 */
@DisplayName("연습 02: 숫자 퍼즐 검증기")
class Ex02_NumberPuzzleTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 수가 소수인지 판별하세요.
     * 힌트: 2부터 sqrt(n)까지 나눠지는지 확인 (i*i <= n 조건 사용)
     */
    boolean isPrime(int n) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 1: 소수 판별")
    void test_isPrime() {
        assertThat(isPrime(2)).isTrue();
        assertThat(isPrime(17)).isTrue();
        assertThat(isPrime(97)).isTrue();
        assertThat(isPrime(1)).isFalse();
        assertThat(isPrime(4)).isFalse();
        assertThat(isPrime(100)).isFalse();
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 자릿수 합을 반환하세요.
     * 힌트: String.valueOf(n).chars().map(c -> c - '0').sum()
     */
    int digitSum(int n) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 2: 자릿수 합")
    void test_digitSum() {
        assertThat(digitSum(12345)).isEqualTo(15);
        assertThat(digitSum(9999)).isEqualTo(36);
        assertThat(digitSum(100)).isEqualTo(1);
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 수를 2진수 문자열로 변환했을 때 회문인지 판별하세요.
     * 힌트: Integer.toBinaryString(n), new StringBuilder(bin).reverse().toString()
     */
    boolean isBinaryPalindrome(int n) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 3: 2진수 회문 판별")
    void test_isBinaryPalindrome() {
        assertThat(isBinaryPalindrome(9)).isTrue();   // 1001
        assertThat(isBinaryPalindrome(21)).isTrue();  // 10101
        assertThat(isBinaryPalindrome(10)).isFalse(); // 1010
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 수가 소수이고 자릿수 합이 홀수인 경우 true를 반환하세요.
     */
    boolean isPrimeAndOddDigitSum(int n) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 4: 소수이면서 자릿수 합이 홀수")
    void test_isPrimeAndOddDigitSum() {
        assertThat(isPrimeAndOddDigitSum(23)).isTrue();  // 소수, 2+3=5 홀수
        assertThat(isPrimeAndOddDigitSum(29)).isTrue();  // 소수, 2+9=11 홀수
        assertThat(isPrimeAndOddDigitSum(4)).isFalse();  // 소수 아님
        assertThat(isPrimeAndOddDigitSum(11)).isFalse(); // 소수, 1+1=2 짝수
    }
}
