package dev.danielk.basicjava.math.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01 답안: 암호화 도우미
 *
 * GCD/LCM, 소수 목록, 진법 변환을 활용해 암호 키를 생성하세요.
 *
 * 사용해야 할 메서드:
 *   gcd(유클리드), lcm, 에라토스테네스의 체,
 *   Integer.toBinaryString / toHexString / toOctalString
 */
@DisplayName("연습 01 답안: 암호화 도우미")
class Ex01_CryptoHelperAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 두 수의 최대공약수(GCD)를 반환하세요.
     * 힌트: 유클리드 호제법 — gcd(a, b) = b==0 ? a : gcd(b, a%b)
     *
     * [풀이] 재귀 유클리드: b가 0이면 a가 GCD
     */
    int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    @Test
    @DisplayName("문제 1: 최대공약수(GCD)")
    void test_gcd() {
        assertThat(gcd(48, 18)).isEqualTo(6);
        assertThat(gcd(100, 75)).isEqualTo(25);
        assertThat(gcd(7, 13)).isEqualTo(1);
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 두 수의 최소공배수(LCM)를 반환하세요.
     * 힌트: lcm = a / gcd(a, b) * b  (오버플로 방지를 위해 나누기 먼저)
     *
     * [풀이] a / gcd 먼저 수행하면 중간 결과의 오버플로 방지
     */
    long lcm(long a, long b) {
        return a / gcd((int) a, (int) b) * b;
    }

    @Test
    @DisplayName("문제 2: 최소공배수(LCM)")
    void test_lcm() {
        assertThat(lcm(4, 6)).isEqualTo(12);
        assertThat(lcm(12, 8)).isEqualTo(24);
        assertThat(lcm(7, 13)).isEqualTo(91);
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 에라토스테네스의 체로 2 이상 n 이하 소수 목록을 반환하세요.
     * 힌트: boolean[] isComposite = new boolean[n+1]; 합성수 표시 후 미표시 인덱스 수집
     *
     * [풀이] isComposite[i]=true이면 합성수 → false인 인덱스가 소수
     */
    List<Integer> sieve(int n) {
        boolean[] isComposite = new boolean[n + 1];
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                for (int j = i * 2; j <= n; j += i) isComposite[j] = true;
            }
        }
        return primes;
    }

    @Test
    @DisplayName("문제 3: 에라토스테네스의 체")
    void test_sieve() {
        assertThat(sieve(20)).containsExactly(2, 3, 5, 7, 11, 13, 17, 19);
        assertThat(sieve(10)).containsExactly(2, 3, 5, 7);
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 10진수 정수를 2진수, 8진수, 16진수 문자열로 변환하여 "bin:oct:hex" 형태로 반환하세요.
     * 힌트: Integer.toBinaryString, toOctalString, toHexString
     *
     * [풀이] Integer.toBinaryString / toOctalString / toHexString 을 콜론으로 연결
     */
    String encodeRadix(int n) {
        return Integer.toBinaryString(n) + ":" + Integer.toOctalString(n) + ":" + Integer.toHexString(n);
    }

    @Test
    @DisplayName("문제 4: 진법 변환 인코딩")
    void test_encodeRadix() {
        assertThat(encodeRadix(10)).isEqualTo("1010:12:a");
        assertThat(encodeRadix(255)).isEqualTo("11111111:377:ff");
    }

    // ── 문제 5 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] gcd(a,b) * lcm(a,b) == a * b 가 성립하는지 검증하세요.
     *
     * [풀이] GCD × LCM = a × b는 항상 성립하는 수학적 성질
     */
    boolean verifyGcdLcm(int a, int b) {
        return (long) gcd(a, b) * lcm(a, b) == (long) a * b;
    }

    @Test
    @DisplayName("문제 5: GCD × LCM = a × b 검증")
    void test_verifyGcdLcm() {
        assertThat(verifyGcdLcm(12, 8)).isTrue();
        assertThat(verifyGcdLcm(100, 75)).isTrue();
        assertThat(verifyGcdLcm(7, 13)).isTrue();
    }
}
