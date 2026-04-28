package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * LinkedBlockingQueue — 블로킹 큐 (생산자-소비자 패턴)
 *
 * - put(e)  : 꽉 차면 공간이 생길 때까지 블로킹
 * - take()  : 비어있으면 원소가 생길 때까지 블로킹
 * - offer(e, timeout, unit) / poll(timeout, unit) : 타임아웃 버전
 * - 크기 제한 없음 (기본) 또는 new LinkedBlockingQueue<>(capacity) 로 제한
 * - 적합: 생산자-소비자 패턴, 스레드 간 작업 전달
 */
@DisplayName("LinkedBlockingQueue (블로킹 큐)")
class LinkedBlockingQueueTest {

    // ── 기본 조작 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("offer / poll / peek — 단일 스레드 기본 동작")
    void basicOps() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        queue.put("A");
        queue.put("B");
        queue.put("C");

        assertThat(queue.peek()).isEqualTo("A");
        assertThat(queue.take()).isEqualTo("A");
        assertThat(queue.take()).isEqualTo("B");
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("offer(timeout) — 꽉 찼을 때 지정 시간 후 false 반환")
    void offerWithTimeout() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(2);

        queue.put(1);
        queue.put(2);

        // 꽉 찬 상태에서 100ms 대기 후 실패
        boolean result = queue.offer(3, 100, TimeUnit.MILLISECONDS);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("poll(timeout) — 비어있을 때 지정 시간 후 null 반환")
    void pollWithTimeout() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        String result = String.valueOf(queue.poll(100, TimeUnit.MILLISECONDS));
        assertThat(result).isEqualTo("null");
    }

    // ── 생산자-소비자 패턴 ───────────────────────────────────────────────────

    @Test
    @DisplayName("생산자-소비자 패턴 — put/take 로 안전하게 데이터 전달")
    void producerConsumer() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
        List<Integer> results = new ArrayList<>();
        int count = 20;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) queue.put(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (results) { results.add(queue.take()); }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join(3000);
        consumer.join(3000);

        assertThat(results).hasSize(count);
        assertThat(results).containsExactlyInAnyOrder(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14, 15, 16, 17, 18, 19
        );
    }
}
