package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ConcurrentLinkedQueue — lock-free 비동기 큐 (단방향, 논블로킹)
 *
 * - 내부적으로 CAS(Compare-And-Swap) 연산으로 thread-safe 보장
 * - offer/poll/peek 은 블로킹 없이 즉시 반환
 * - poll() 은 비어있으면 null 반환 (대기 없음)
 * - 크기 제한 없음 (unbounded)
 * - 적합: 다수의 스레드가 경쟁하는 단순 작업 대기열
 */
@DisplayName("ConcurrentLinkedQueue (lock-free 큐)")
class ConcurrentLinkedQueueTest {

    // ── 기본 조작 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("offer / poll / peek — 단일 스레드 기본 동작")
    void basicOps() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        queue.offer("A");
        queue.offer("B");
        queue.offer("C");

        assertThat(queue.peek()).isEqualTo("A");
        assertThat(queue.poll()).isEqualTo("A");
        assertThat(queue.poll()).isEqualTo("B");
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("poll — 비어있으면 null 반환 (블로킹 없음)")
    void pollOnEmpty() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        assertThat(queue.poll()).isNull();
    }

    // ── 멀티스레드 ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("멀티스레드 — 여러 스레드가 동시에 offer 해도 데이터 손실 없음")
    void concurrentOffer() throws InterruptedException {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        int threadCount = 5;
        int itemsPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int t = 0; t < threadCount; t++) {
            final int base = t * itemsPerThread;
            executor.submit(() -> {
                for (int i = 0; i < itemsPerThread; i++) queue.offer(base + i);
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertThat(queue.size()).isEqualTo(threadCount * itemsPerThread);
    }

    @Test
    @DisplayName("멀티스레드 — 생산자/소비자가 동시에 동작해도 안전")
    void concurrentOfferAndPoll() throws InterruptedException {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        List<Integer> consumed = new ArrayList<>();
        int total = 200;

        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 생산자 2개
        for (int p = 0; p < 2; p++) {
            final int base = p * (total / 2);
            executor.submit(() -> {
                for (int i = 0; i < total / 2; i++) queue.offer(base + i);
            });
        }

        // 소비자 2개
        for (int c = 0; c < 2; c++) {
            executor.submit(() -> {
                for (int i = 0; i < total / 2; i++) {
                    Integer item;
                    // poll 은 논블로킹 — 아직 없으면 null, 루프로 재시도
                    while ((item = queue.poll()) == null) Thread.yield();
                    synchronized (consumed) { consumed.add(item); }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertThat(consumed).hasSize(total);
    }
}
