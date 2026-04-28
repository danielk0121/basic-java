package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ConcurrentLinkedDeque — lock-free 비동기 덱 (양방향, 논블로킹)
 *
 * - CAS 기반 lock-free 구현
 * - 앞뒤 양방향 삽입·제거 가능
 * - pollFirst/pollLast 은 비어있으면 null 반환 (블로킹 없음)
 * - 크기 제한 없음 (unbounded)
 * - 적합: 여러 스레드가 양방향으로 접근하는 작업 큐, 작업 훔치기(work-stealing) 패턴
 */
@DisplayName("ConcurrentLinkedDeque (lock-free 덱)")
class ConcurrentLinkedDequeTest {

    // ── 기본 조작 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("addFirst / addLast / pollFirst / pollLast — 기본 양방향 동작")
    void basicOps() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(0);
        // 상태: [0, 1, 2]

        assertThat(deque.peekFirst()).isEqualTo(0);
        assertThat(deque.peekLast()).isEqualTo(2);
        assertThat(deque.pollFirst()).isEqualTo(0);
        assertThat(deque.pollLast()).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("pollFirst / pollLast — 비어있으면 null 반환 (블로킹 없음)")
    void pollOnEmpty() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();

        assertThat(deque.pollFirst()).isNull();
        assertThat(deque.pollLast()).isNull();
    }

    // ── 멀티스레드 ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("멀티스레드 — 앞뒤 동시 삽입해도 데이터 손실 없음")
    void concurrentAddBothEnds() throws InterruptedException {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        int count = 100;
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 홀수 스레드는 앞, 짝수 스레드는 뒤에 삽입
        for (int t = 0; t < 4; t++) {
            final int base = t * count;
            final boolean front = t % 2 == 0;
            executor.submit(() -> {
                for (int i = 0; i < count; i++) {
                    if (front) deque.addFirst(base + i);
                    else deque.addLast(base + i);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertThat(deque.size()).isEqualTo(4 * count);
    }

    @Test
    @DisplayName("작업 훔치기 패턴 — 앞에서 자신의 작업, 뒤에서 다른 스레드가 훔침")
    void workStealing() throws InterruptedException {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();

        // 소유 스레드: 앞에 작업 추가
        for (int i = 0; i < 5; i++) deque.addFirst("task-" + i);

        // 소유 스레드: 앞에서 꺼냄 (LIFO)
        String ownTask = deque.pollFirst();
        assertThat(ownTask).isEqualTo("task-4");

        // 훔치는 스레드: 뒤에서 꺼냄
        String stolenTask = deque.pollLast();
        assertThat(stolenTask).isEqualTo("task-0");

        assertThat(deque.size()).isEqualTo(3);
    }
}
