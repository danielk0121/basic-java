package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * LinkedBlockingDeque — 블로킹 덱 (양방향 생산자-소비자 패턴)
 *
 * - putFirst(e) / putLast(e)   : 앞/뒤 삽입, 꽉 차면 블로킹
 * - takeFirst() / takeLast()   : 앞/뒤 제거, 비어있으면 블로킹
 * - offerFirst/offerLast(e, timeout, unit) : 타임아웃 삽입
 * - pollFirst/pollLast(timeout, unit)      : 타임아웃 제거
 * - new LinkedBlockingDeque<>(capacity)   : 크기 제한 가능
 * - 적합: 양방향 생산자-소비자, 우선순위 없는 양끝 처리
 */
@DisplayName("LinkedBlockingDeque (블로킹 덱)")
class LinkedBlockingDequeTest {

    // ── 기본 조작 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("putFirst / putLast / takeFirst / takeLast — 기본 양방향 동작")
    void basicOps() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();

        deque.putLast(1);
        deque.putLast(2);
        deque.putFirst(0);
        // 상태: [0, 1, 2]

        assertThat(deque.takeFirst()).isEqualTo(0);
        assertThat(deque.takeLast()).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("offerFirst(timeout) — 꽉 찼을 때 타임아웃 후 false 반환")
    void offerFirstWithTimeout() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(2);
        deque.putLast(1);
        deque.putLast(2);

        boolean result = deque.offerFirst(0, 100, TimeUnit.MILLISECONDS);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("pollLast(timeout) — 비어있을 때 타임아웃 후 null 반환")
    void pollLastWithTimeout() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();

        Integer result = deque.pollLast(100, TimeUnit.MILLISECONDS);
        assertThat(result).isNull();
    }

    // ── 양방향 생산자-소비자 ─────────────────────────────────────────────────

    @Test
    @DisplayName("양방향 생산자-소비자 — 앞/뒤 소비자가 동시에 처리")
    void bidirectionalProducerConsumer() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(20);
        List<Integer> fromFront = new ArrayList<>();
        List<Integer> fromBack = new ArrayList<>();
        int count = 10;

        // 생산자: 뒤에 추가
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < count * 2; i++) deque.putLast(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 소비자 A: 앞에서 꺼냄
        Thread consumerFront = new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (fromFront) { fromFront.add(deque.takeFirst()); }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 소비자 B: 뒤에서 꺼냄
        Thread consumerBack = new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (fromBack) { fromBack.add(deque.takeLast()); }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumerFront.start();
        consumerBack.start();
        producer.join(3000);
        consumerFront.join(3000);
        consumerBack.join(3000);

        assertThat(fromFront).hasSize(count);
        assertThat(fromBack).hasSize(count);
        assertThat(fromFront.size() + fromBack.size()).isEqualTo(count * 2);
    }
}
