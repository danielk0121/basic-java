package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * ArrayBlockingQueue — 고정 크기 블로킹 큐 (bounded buffer)
 *
 * - 생성 시 반드시 용량(capacity) 지정 필요
 * - 내부적으로 배열 사용 → 메모리 예측 가능
 * - put(e)  : 꽉 차면 블로킹
 * - take()  : 비어있으면 블로킹
 * - add(e)  : 꽉 차면 IllegalStateException (예외 버전)
 * - offer(e): 꽉 차면 false 반환 (논블로킹 버전)
 * - 적합: 메모리 제한이 있는 생산자-소비자, 스로틀링
 */
@DisplayName("ArrayBlockingQueue (고정 크기 블로킹 큐)")
class ArrayBlockingQueueTest {

    // ── 기본 조작 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("put / take — 기본 FIFO 동작")
    void basicPutTake() throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        queue.put("A");
        queue.put("B");
        queue.put("C");

        assertThat(queue.take()).isEqualTo("A");
        assertThat(queue.take()).isEqualTo("B");
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("add — 용량 초과 시 IllegalStateException")
    void addOverCapacity() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        queue.add(2);

        assertThatThrownBy(() -> queue.add(3))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("offer — 용량 초과 시 false 반환 (예외 없음)")
    void offerOverCapacity() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.offer(1);
        queue.offer(2);

        assertThat(queue.offer(3)).isFalse();
    }

    @Test
    @DisplayName("offer(timeout) — 꽉 찼을 때 타임아웃 후 false 반환")
    void offerWithTimeout() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        queue.put(1);

        boolean result = queue.offer(2, 100, TimeUnit.MILLISECONDS);
        assertThat(result).isFalse();
    }

    // ── 생산자-소비자 패턴 ───────────────────────────────────────────────────

    @Test
    @DisplayName("생산자-소비자 패턴 — 버퍼 크기 제한으로 속도 조절")
    void boundedProducerConsumer() throws InterruptedException {
        // 버퍼 크기 3 — 생산자가 너무 빠르면 소비자가 따라올 때까지 자동으로 대기
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        List<Integer> results = new ArrayList<>();
        int count = 10;

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
        assertThat(results).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
}
