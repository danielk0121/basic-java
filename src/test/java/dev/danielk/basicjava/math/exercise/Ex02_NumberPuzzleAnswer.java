package dev.danielk.basicjava.math.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: 숫자 퍼즐 검증기
 */
@DisplayName("연습 02 답안: 숫자 퍼즐 검증기")
class Ex02_NumberPuzzleAnswer {

    /** 2 미만은 소수 아님, i*i <= n 조건으로 sqrt 계산 없이 처리 */
    boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
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

    /** String.valueOf(n).chars()는 각 문자의 ASCII 코드 반환, '0'(48)을 빼면 실제 숫자 값 */
    int digitSum(int n) {
        return String.valueOf(n).chars().map(c -> c - '0').sum();
    }

    @Test
    @DisplayName("문제 2: 자릿수 합")
    void test_digitSum() {
        assertThat(digitSum(12345)).isEqualTo(15);
        assertThat(digitSum(9999)).isEqualTo(36);
        assertThat(digitSum(100)).isEqualTo(1);
    }

    /** 2진수 문자열 → StringBuilder로 뒤집기 → 원본과 equals 비교 */
    boolean isBinaryPalindrome(int n) {
        String bin = Integer.toBinaryString(n);
        return bin.equals(new StringBuilder(bin).reverse().toString());
    }

    @Test
    @DisplayName("문제 3: 2진수 회문 판별")
    void test_isBinaryPalindrome() {
        assertThat(isBinaryPalindrome(9)).isTrue();
        assertThat(isBinaryPalindrome(21)).isTrue();
        assertThat(isBinaryPalindrome(10)).isFalse();
    }

    /** isPrime과 digitSum을 조합 */
    boolean isPrimeAndOddDigitSum(int n) {
        return isPrime(n) && digitSum(n) % 2 != 0;
    }

    @Test
    @DisplayName("문제 4: 소수이면서 자릿수 합이 홀수")
    void test_isPrimeAndOddDigitSum() {
        assertThat(isPrimeAndOddDigitSum(23)).isTrue();
        assertThat(isPrimeAndOddDigitSum(29)).isTrue();
        assertThat(isPrimeAndOddDigitSum(4)).isFalse();
        assertThat(isPrimeAndOddDigitSum(11)).isFalse();
    }
}
