package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 덱 (Deque — Double-Ended Queue) — ArrayDeque
 *
 * 앞(First)과 뒤(Last) 양쪽에서 삽입·제거 가능
 * 스택과 큐를 모두 대체할 수 있는 범용 자료구조
 *
 * addFirst/addLast       — 삽입 (실패 시 예외)
 * offerFirst/offerLast   — 삽입 (실패 시 false)
 * removeFirst/removeLast — 제거 (실패 시 예외)
 * pollFirst/pollLast     — 제거 (실패 시 null)
 * peekFirst/peekLast     — 조회 (제거 안 함, 실패 시 null)
 */
@DisplayName("덱 (ArrayDeque)")
class DequeTest {

    // ── 기본 조작 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("addFirst / addLast — 앞뒤 삽입")
    void addFirstAndLast() {
        Deque<Integer> deque = new ArrayDeque<>();

        deque.addLast(2);
        deque.addFirst(1);
        deque.addLast(3);
        // 상태: [1, 2, 3]

        assertThat(deque.peekFirst()).isEqualTo(1);
        assertThat(deque.peekLast()).isEqualTo(3);
        assertThat(deque.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("removeFirst / removeLast — 앞뒤 제거")
    void removeFirstAndLast() {
        Deque<String> deque = new ArrayDeque<>();
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        assertThat(deque.removeFirst()).isEqualTo("A");
        assertThat(deque.removeLast()).isEqualTo("C");
        assertThat(deque.peekFirst()).isEqualTo("B");
    }

    @Test
    @DisplayName("pollFirst / pollLast — 비어있으면 null 반환")
    void pollOnEmpty() {
        Deque<Integer> deque = new ArrayDeque<>();

        assertThat(deque.pollFirst()).isNull();
        assertThat(deque.pollLast()).isNull();
    }

    // ── 스택·큐 대체 ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("덱을 스택으로 사용 — addFirst / removeFirst (LIFO)")
    void dequeAsStack() {
        Deque<Integer> stack = new ArrayDeque<>();

        stack.addFirst(1);
        stack.addFirst(2);
        stack.addFirst(3);

        assertThat(stack.removeFirst()).isEqualTo(3);
        assertThat(stack.removeFirst()).isEqualTo(2);
        assertThat(stack.removeFirst()).isEqualTo(1);
    }

    @Test
    @DisplayName("덱을 큐로 사용 — addLast / removeFirst (FIFO)")
    void dequeAsQueue() {
        Deque<Integer> queue = new ArrayDeque<>();

        queue.addLast(1);
        queue.addLast(2);
        queue.addLast(3);

        assertThat(queue.removeFirst()).isEqualTo(1);
        assertThat(queue.removeFirst()).isEqualTo(2);
        assertThat(queue.removeFirst()).isEqualTo(3);
    }

    // ── 활용 예시 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("활용: 팰린드롬 검사")
    void palindromeCheck() {
        assertThat(isPalindrome("racecar")).isTrue();
        assertThat(isPalindrome("level")).isTrue();
        assertThat(isPalindrome("hello")).isFalse();
    }

    private boolean isPalindrome(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        for (char c : s.toCharArray()) deque.addLast(c);

        while (deque.size() > 1) {
            if (!deque.removeFirst().equals(deque.removeLast())) return false;
        }
        return true;
    }

    @Test
    @DisplayName("활용: 슬라이딩 윈도우 최댓값 (크기 k)")
    void slidingWindowMax() {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        // 윈도우: [1,3,-1]=3, [3,-1,-3]=3, [-1,-3,5]=5, [-3,5,3]=5, [5,3,6]=6, [3,6,7]=7
        int[] expected = {3, 3, 5, 5, 6, 7};

        Deque<Integer> deque = new ArrayDeque<>(); // 인덱스 저장
        int[] result = new int[nums.length - k + 1];
        int ri = 0;

        for (int i = 0; i < nums.length; i++) {
            // 윈도우 밖으로 나간 인덱스 제거
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) deque.removeFirst();
            // 현재 값보다 작은 뒤쪽 인덱스 제거 (단조 감소 유지)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.removeLast();
            deque.addLast(i);
            if (i >= k - 1) result[ri++] = nums[deque.peekFirst()];
        }

        assertThat(result).containsExactly(expected);
    }
}
