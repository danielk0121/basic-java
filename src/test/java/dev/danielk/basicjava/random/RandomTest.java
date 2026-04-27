package dev.danielk.basicjava.random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 랜덤 처리 보일러플레이트
 * - 정수/실수 난수, 범위 지정, 셔플, 랜덤 요소 추출
 */
@DisplayName("랜덤 처리")
class RandomTest {

    // ── 정수 난수 생성 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("정수 난수: Random / ThreadLocalRandom")
    void intRandom() {
        Random random = new Random();

        // nextInt(): 0 이상 bound 미만
        int r1 = random.nextInt(100);
        assertThat(r1).isGreaterThanOrEqualTo(0).isLessThan(100);

        // ThreadLocalRandom: 멀티스레드 환경에서 성능 우수
        int r2 = ThreadLocalRandom.current().nextInt(100);
        assertThat(r2).isGreaterThanOrEqualTo(0).isLessThan(100);

        // Java 17+ Random.nextInt(origin, bound): 범위 지정
        int r3 = random.nextInt(10, 20);
        assertThat(r3).isGreaterThanOrEqualTo(10).isLessThan(20);
    }

    // ── 범위 지정 난수 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("범위 지정 난수: [min, max] 포함")
    void rangedRandom() {
        // [min, max] 포함 난수 공식: min + random.nextInt(max - min + 1)
        Random random = new Random();
        int min = 5, max = 10;

        for (int i = 0; i < 100; i++) {
            int r = min + random.nextInt(max - min + 1);
            assertThat(r).isGreaterThanOrEqualTo(min).isLessThanOrEqualTo(max);
        }

        // ThreadLocalRandom.nextInt(origin, bound): bound는 미포함
        for (int i = 0; i < 100; i++) {
            int r = ThreadLocalRandom.current().nextInt(min, max + 1);
            assertThat(r).isGreaterThanOrEqualTo(min).isLessThanOrEqualTo(max);
        }
    }

    // ── 실수 난수 ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("실수 난수: nextDouble / nextFloat")
    void doubleRandom() {
        Random random = new Random();

        // nextDouble(): 0.0 이상 1.0 미만
        double d = random.nextDouble();
        assertThat(d).isGreaterThanOrEqualTo(0.0).isLessThan(1.0);

        // 범위 지정 실수: [min, max)
        double min = 1.5, max = 3.5;
        double ranged = min + random.nextDouble() * (max - min);
        assertThat(ranged).isGreaterThanOrEqualTo(min).isLessThan(max);

        // ThreadLocalRandom
        double r = ThreadLocalRandom.current().nextDouble(min, max);
        assertThat(r).isGreaterThanOrEqualTo(min).isLessThan(max);
    }

    // ── 리스트/배열 셔플 ──────────────────────────────────────────────────────

    @Test
    @DisplayName("셔플: Collections.shuffle / 배열 셔플")
    void shuffle() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Integer> original = new ArrayList<>(list);

        Collections.shuffle(list);
        // 셔플 후 원소는 동일하되 순서가 달라짐 (극히 드물게 동일할 수 있음)
        assertThat(list).containsExactlyInAnyOrderElementsOf(original);

        // 배열 셔플: Integer[] 로 변환 후 Collections.shuffle
        Integer[] arr = {1, 2, 3, 4, 5};
        List<Integer> arrList = Arrays.asList(arr);
        Collections.shuffle(arrList);
        assertThat(arrList).containsExactlyInAnyOrder(1, 2, 3, 4, 5);
    }

    // ── 랜덤 요소 추출 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("랜덤 요소 추출: 단일 / 중복 없이 n개")
    void randomPick() {
        List<String> pool = List.of("apple", "banana", "cherry", "date", "elderberry");
        Random random = new Random();

        // 단일 요소 추출
        String picked = pool.get(random.nextInt(pool.size()));
        assertThat(pool).contains(picked);

        // 중복 없이 3개 추출 (셔플 후 앞에서 자르기)
        List<String> shuffled = new ArrayList<>(pool);
        Collections.shuffle(shuffled);
        List<String> sampled = shuffled.subList(0, 3);
        assertThat(sampled).hasSize(3);
        assertThat(pool).containsAll(sampled);
        // 중복 없음 확인
        assertThat(sampled).doesNotHaveDuplicates();
    }
}
