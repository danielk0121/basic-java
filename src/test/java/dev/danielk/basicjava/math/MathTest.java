package dev.danielk.basicjava.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 숫자/수학 처리 보일러플레이트
 * - GCD/LCM, 소수, 진법 변환, 자릿수, 순열/조합
 */
@DisplayName("숫자/수학 처리")
class MathTest {

    // ── GCD / LCM ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("최대공약수(GCD) / 최소공배수(LCM)")
    void gcdLcm() {
        assertThat(gcd(12, 8)).isEqualTo(4);
        assertThat(gcd(100, 75)).isEqualTo(25);

        assertThat(lcm(4, 6)).isEqualTo(12);
        assertThat(lcm(12, 8)).isEqualTo(24);
    }

    /** 유클리드 호제법 */
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private long lcm(long a, long b) {
        return a / gcd((int) a, (int) b) * b;
    }

    // ── 소수 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("소수 판별 / 에라토스테네스의 체로 소수 목록 생성")
    void prime() {
        assertThat(isPrime(2)).isTrue();
        assertThat(isPrime(17)).isTrue();
        assertThat(isPrime(1)).isFalse();
        assertThat(isPrime(4)).isFalse();

        List<Integer> primes = sieve(20);
        assertThat(primes).containsExactly(2, 3, 5, 7, 11, 13, 17, 19);
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /** 에라토스테네스의 체: 2 이상 n 이하 소수 목록 반환 */
    private List<Integer> sieve(int n) {
        boolean[] isComposite = new boolean[n + 1];
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                for (int j = i * 2; j <= n; j += i) {
                    isComposite[j] = true;
                }
            }
        }
        return primes;
    }

    // ── 진법 변환 ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("진법 변환: 10진수 ↔ 2/8/16진수")
    void radixConvert() {
        // 10진수 → 다른 진수 문자열
        assertThat(Integer.toBinaryString(10)).isEqualTo("1010");
        assertThat(Integer.toOctalString(8)).isEqualTo("10");
        assertThat(Integer.toHexString(255)).isEqualTo("ff");

        // String.format으로도 가능 (%s로 변환된 2진수 문자열에 padStart 적용)
        String bin10 = String.format("%8s", Integer.toBinaryString(10)).replace(' ', '0');
        assertThat(bin10).isEqualTo("00001010");
        assertThat(String.format("%02x", 255)).isEqualTo("ff");

        // 다른 진수 문자열 → 10진수
        assertThat(Integer.parseInt("1010", 2)).isEqualTo(10);
        assertThat(Integer.parseInt("10", 8)).isEqualTo(8);
        assertThat(Integer.parseInt("ff", 16)).isEqualTo(255);
    }

    // ── 자릿수 추출 및 합계 ───────────────────────────────────────────────────

    @Test
    @DisplayName("자릿수 추출 및 합계")
    void digitSum() {
        int n = 12345;

        // 나머지 연산으로 자릿수 추출
        List<Integer> digits = new ArrayList<>();
        int temp = n;
        while (temp > 0) {
            digits.add(0, temp % 10);
            temp /= 10;
        }
        assertThat(digits).containsExactly(1, 2, 3, 4, 5);

        // 자릿수 합계
        int sum = String.valueOf(n).chars()
                .map(c -> c - '0')
                .sum();
        assertThat(sum).isEqualTo(15);
    }

    // ── 순열 / 조합 ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("순열 생성: nPr")
    void permutation() {
        List<List<Integer>> result = new ArrayList<>();
        int[] arr = {1, 2, 3};
        permute(arr, 0, result);
        assertThat(result).hasSize(6);  // 3! = 6
        assertThat(result).contains(List.of(1, 2, 3), List.of(3, 2, 1));
    }

    private void permute(int[] arr, int start, List<List<Integer>> result) {
        if (start == arr.length) {
            List<Integer> perm = new ArrayList<>();
            for (int v : arr) perm.add(v);
            result.add(perm);
            return;
        }
        for (int i = start; i < arr.length; i++) {
            swap(arr, start, i);
            permute(arr, start + 1, result);
            swap(arr, start, i);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    @Test
    @DisplayName("조합 생성: nCr")
    void combination() {
        List<List<Integer>> result = new ArrayList<>();
        int[] arr = {1, 2, 3, 4};
        combine(arr, 0, 2, new ArrayList<>(), result);
        assertThat(result).hasSize(6);  // 4C2 = 6
        assertThat(result).contains(List.of(1, 2), List.of(3, 4));
    }

    private void combine(int[] arr, int start, int r, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == r) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < arr.length; i++) {
            current.add(arr[i]);
            combine(arr, i + 1, r, current, result);
            current.remove(current.size() - 1);
        }
    }
}
