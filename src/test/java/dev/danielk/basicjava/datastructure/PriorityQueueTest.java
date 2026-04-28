package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 우선순위 큐 (PriorityQueue — 힙)
 *
 * 기본: 최솟값 우선 (min-heap, 자연 순서)
 * max-heap: PriorityQueue<>(Comparator.reverseOrder())
 * 커스텀: PriorityQueue<>(Comparator.comparingInt(...))
 *
 * 시간 복잡도: offer/poll O(log n), peek O(1)
 */
@DisplayName("우선순위 큐 (PriorityQueue)")
class PriorityQueueTest {

    // ── min-heap (기본) ──────────────────────────────────────────────────────

    @Test
    @DisplayName("기본 min-heap — 작은 값이 먼저 나옴")
    void minHeap() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.offer(5);
        pq.offer(1);
        pq.offer(3);

        assertThat(pq.peek()).isEqualTo(1);          // 최솟값 조회
        assertThat(pq.poll()).isEqualTo(1);
        assertThat(pq.poll()).isEqualTo(3);
        assertThat(pq.poll()).isEqualTo(5);
    }

    // ── max-heap ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("max-heap — Comparator.reverseOrder() 으로 큰 값이 먼저 나옴")
    void maxHeap() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());

        pq.offer(5);
        pq.offer(1);
        pq.offer(3);

        assertThat(pq.poll()).isEqualTo(5);
        assertThat(pq.poll()).isEqualTo(3);
        assertThat(pq.poll()).isEqualTo(1);
    }

    // ── 커스텀 정렬 ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("커스텀 정렬 — 문자열 길이 기준 오름차순")
    void customComparator() {
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(String::length));

        pq.offer("banana");
        pq.offer("hi");
        pq.offer("apple");

        assertThat(pq.poll()).isEqualTo("hi");       // 길이 2
        assertThat(pq.poll()).isEqualTo("apple");    // 길이 5
        assertThat(pq.poll()).isEqualTo("banana");   // 길이 6
    }

    @Test
    @DisplayName("커스텀 정렬 — 객체 필드 기준 (나이 오름차순)")
    void customComparatorByField() {
        record Person(String name, int age) {}

        PriorityQueue<Person> pq = new PriorityQueue<>(Comparator.comparingInt(Person::age));
        pq.offer(new Person("Charlie", 30));
        pq.offer(new Person("Alice", 25));
        pq.offer(new Person("Bob", 28));

        assertThat(pq.poll().name()).isEqualTo("Alice");
        assertThat(pq.poll().name()).isEqualTo("Bob");
        assertThat(pq.poll().name()).isEqualTo("Charlie");
    }

    // ── 활용 예시 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("활용: K번째 최솟값")
    void kthSmallest() {
        int[] nums = {7, 10, 4, 3, 20, 15};
        int k = 3;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int n : nums) pq.offer(n);
        int result = 0;
        for (int i = 0; i < k; i++) result = pq.poll();

        assertThat(result).isEqualTo(7);  // 3, 4, 7 순으로 나옴
    }

    @Test
    @DisplayName("활용: 작업 스케줄링 — 마감 시간 오름차순 처리")
    void taskScheduling() {
        record Task(String name, int deadline) {}

        PriorityQueue<Task> pq = new PriorityQueue<>(Comparator.comparingInt(Task::deadline));
        pq.offer(new Task("보고서", 3));
        pq.offer(new Task("미팅 준비", 1));
        pq.offer(new Task("코드 리뷰", 2));

        assertThat(pq.poll().name()).isEqualTo("미팅 준비");
        assertThat(pq.poll().name()).isEqualTo("코드 리뷰");
        assertThat(pq.poll().name()).isEqualTo("보고서");
    }
}
