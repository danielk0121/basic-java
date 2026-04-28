package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 큐 (Queue)
 *
 * - ArrayDeque  : 일반 용도. 메모리·성능 모두 우수 — 기본 선택
 * - LinkedList  : null 삽입이 필요한 특수 상황에서만 사용
 *
 * offer/poll/peek — 실패 시 false/null 반환 (예외 없음)
 * add/remove      — 실패 시 예외 던지는 버전
 */
@DisplayName("큐 (Queue)")
class QueueTest {

    // ── ArrayDeque as Queue ──────────────────────────────────────────────────

    @Test
    @DisplayName("offer / poll / peek — 기본 FIFO 동작 (ArrayDeque)")
    void arrayDequeQueue() {
        Queue<String> queue = new ArrayDeque<>();

        queue.offer("첫째");
        queue.offer("둘째");
        queue.offer("셋째");

        assertThat(queue.peek()).isEqualTo("첫째");      // 머리 조회 (제거 안 함)
        assertThat(queue.poll()).isEqualTo("첫째");      // 머리 제거
        assertThat(queue.poll()).isEqualTo("둘째");
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("poll / peek — 비어있으면 null 반환")
    void pollPeekOnEmpty() {
        Queue<Integer> queue = new ArrayDeque<>();

        assertThat(queue.poll()).isNull();
        assertThat(queue.peek()).isNull();
    }

    @Test
    @DisplayName("remove — 비어있으면 NoSuchElementException")
    void removeOnEmpty() {
        Queue<Integer> queue = new ArrayDeque<>();

        assertThatThrownBy(queue::remove)
                .isInstanceOf(java.util.NoSuchElementException.class);
    }

    // ── LinkedList as Queue ──────────────────────────────────────────────────

    @Test
    @DisplayName("offer / poll / peek — LinkedList (null 삽입 가능)")
    void linkedListQueue() {
        Queue<String> queue = new LinkedList<>();

        queue.offer("a");
        queue.offer(null);   // ArrayDeque 는 null 불가, LinkedList 는 가능
        queue.offer("b");

        assertThat(queue.poll()).isEqualTo("a");
        assertThat(queue.poll()).isNull();
        assertThat(queue.poll()).isEqualTo("b");
    }

    // ── 활용 예시 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("활용: BFS — 레벨 순서 탐색 시뮬레이션")
    void bfsLevelOrder() {
        // 트리: 1 → {2, 3}, 2 → {4, 5}, 3 → {6}
        //         1
        //        / \
        //       2   3
        //      / \ /
        //     4  5 6
        java.util.Map<Integer, int[]> graph = java.util.Map.of(
                1, new int[]{2, 3},
                2, new int[]{4, 5},
                3, new int[]{6}
        );

        Queue<Integer> queue = new ArrayDeque<>();
        java.util.List<Integer> visited = new java.util.ArrayList<>();

        queue.offer(1);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            visited.add(node);
            int[] children = graph.getOrDefault(node, new int[]{});
            for (int child : children) queue.offer(child);
        }

        assertThat(visited).containsExactly(1, 2, 3, 4, 5, 6);
    }
}
