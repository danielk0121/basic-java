package dev.danielk.basicjava.array;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 배열 처리 보일러플레이트
 * - 정렬, 검색, 복사, 변환, 필터링, 다차원 순회, 최대/최소/합계, 채우기, 비교, 문자열 변환
 */
@DisplayName("배열 처리")
class ArrayTest {

    // ── 정렬 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("정렬: 오름차순 / 내림차순 / 커스텀 비교자")
    void sort() {
        int[] arr = {5, 2, 8, 1, 9};
        Arrays.sort(arr);
        assertThat(arr).containsExactly(1, 2, 5, 8, 9);

        // 내림차순: Integer 배열 필요 (기본형 배열은 Comparator 사용 불가)
        Integer[] boxed = {5, 2, 8, 1, 9};
        Arrays.sort(boxed, Comparator.reverseOrder());
        assertThat(boxed).containsExactly(9, 8, 5, 2, 1);

        // 커스텀 비교자: 문자열 길이 오름차순
        String[] words = {"banana", "fig", "apple", "kiwi"};
        Arrays.sort(words, Comparator.comparingInt(String::length));
        assertThat(words).containsExactly("fig", "kiwi", "apple", "banana");
    }

    // ── 검색 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("검색: 선형 탐색 / 이진 탐색 (Arrays.binarySearch)")
    void search() {
        // 선형 탐색
        int[] arr = {10, 20, 30, 40, 50};
        int linearResult = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 30) { linearResult = i; break; }
        }
        assertThat(linearResult).isEqualTo(2);

        // 이진 탐색: 반드시 정렬된 배열에서 사용
        int binaryResult = Arrays.binarySearch(arr, 30);
        assertThat(binaryResult).isEqualTo(2);

        // 없는 값: 음수 반환 (삽입 위치의 -(index+1))
        assertThat(Arrays.binarySearch(arr, 35)).isNegative();
    }

    // ── 복사 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("복사: Arrays.copyOf / Arrays.copyOfRange / clone")
    void copy() {
        int[] origin = {1, 2, 3, 4, 5};

        // copyOf: 앞에서부터 n개 복사 (길이가 더 길면 0으로 채움)
        int[] copy3 = Arrays.copyOf(origin, 3);
        assertThat(copy3).containsExactly(1, 2, 3);

        int[] copy7 = Arrays.copyOf(origin, 7);
        assertThat(copy7).containsExactly(1, 2, 3, 4, 5, 0, 0);

        // copyOfRange: 범위 복사 (from 이상 to 미만)
        int[] range = Arrays.copyOfRange(origin, 1, 4);
        assertThat(range).containsExactly(2, 3, 4);

        // 얕은 복사 vs 깊은 복사 (참조형 배열)
        String[] src = {"a", "b", "c"};
        String[] shallow = src.clone();        // 얕은 복사: 원소 참조 공유
        shallow[0] = "X";
        assertThat(src[0]).isEqualTo("a");     // String은 불변이라 원본 영향 없음
    }

    // ── 변환 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("변환: 배열 → 리스트 / 리스트 → 배열")
    void convert() {
        // 배열 → 리스트 (고정 크기, add/remove 불가)
        String[] arr = {"apple", "banana", "cherry"};
        List<String> fixedList = Arrays.asList(arr);
        assertThat(fixedList).containsExactly("apple", "banana", "cherry");

        // 가변 리스트로 변환
        List<String> mutableList = new java.util.ArrayList<>(Arrays.asList(arr));
        mutableList.add("date");
        assertThat(mutableList).hasSize(4);

        // 리스트 → 배열
        String[] backToArray = mutableList.toArray(new String[0]);
        assertThat(backToArray).containsExactly("apple", "banana", "cherry", "date");
    }

    // ── 필터링 및 매핑 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("필터링 및 매핑: stream filter / map")
    void filterAndMap() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // 짝수만 필터링
        int[] evens = Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .toArray();
        assertThat(evens).containsExactly(2, 4, 6, 8, 10);

        // 각 요소를 제곱으로 매핑
        int[] squared = Arrays.stream(numbers)
                .map(n -> n * n)
                .toArray();
        assertThat(squared).containsExactly(1, 4, 9, 16, 25, 36, 49, 64, 81, 100);

        // 문자열 배열: 대문자 변환
        String[] words = {"hello", "world"};
        String[] upper = Arrays.stream(words)
                .map(String::toUpperCase)
                .toArray(String[]::new);
        assertThat(upper).containsExactly("HELLO", "WORLD");
    }

    // ── 다차원 배열 순회 ──────────────────────────────────────────────────────

    @Test
    @DisplayName("다차원 배열 순회")
    void multiDimensional() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // 행 우선 순회
        int sum = 0;
        for (int[] row : matrix) {
            for (int val : row) {
                sum += val;
            }
        }
        assertThat(sum).isEqualTo(45);

        // 대각선 합계
        int diagonal = 0;
        for (int i = 0; i < matrix.length; i++) {
            diagonal += matrix[i][i];
        }
        assertThat(diagonal).isEqualTo(15); // 1 + 5 + 9
    }

    // ── 최대/최소/합계 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("최대/최소/합계: stream 활용")
    void maxMinSum() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};

        int max = Arrays.stream(arr).max().getAsInt();
        int min = Arrays.stream(arr).min().getAsInt();
        int sum = Arrays.stream(arr).sum();
        double avg = Arrays.stream(arr).average().getAsDouble();

        assertThat(max).isEqualTo(9);
        assertThat(min).isEqualTo(1);
        assertThat(sum).isEqualTo(31);
        assertThat(avg).isEqualTo(31.0 / 8);
    }

    // ── 채우기 ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("채우기: Arrays.fill")
    void fill() {
        int[] arr = new int[5];
        Arrays.fill(arr, 7);
        assertThat(arr).containsExactly(7, 7, 7, 7, 7);

        // 범위 채우기: from 이상 to 미만
        Arrays.fill(arr, 1, 4, 0);
        assertThat(arr).containsExactly(7, 0, 0, 0, 7);
    }

    // ── 비교 ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("비교: Arrays.equals / Arrays.deepEquals")
    void equals() {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        int[] c = {1, 2, 4};

        assertThat(Arrays.equals(a, b)).isTrue();
        assertThat(Arrays.equals(a, c)).isFalse();

        // deepEquals: 다차원 배열 비교
        int[][] m1 = {{1, 2}, {3, 4}};
        int[][] m2 = {{1, 2}, {3, 4}};
        assertThat(Arrays.deepEquals(m1, m2)).isTrue();
    }

    // ── 문자열 변환 ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("문자열 변환: Arrays.toString / Arrays.deepToString")
    void toStringTest() {
        int[] arr = {1, 2, 3, 4, 5};
        assertThat(Arrays.toString(arr)).isEqualTo("[1, 2, 3, 4, 5]");

        int[][] matrix = {{1, 2}, {3, 4}};
        assertThat(Arrays.deepToString(matrix)).isEqualTo("[[1, 2], [3, 4]]");
    }
}
