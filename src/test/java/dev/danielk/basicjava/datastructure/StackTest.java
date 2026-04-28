package dev.danielk.basicjava.datastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 스택 (Stack) — ArrayDeque as Stack
 *
 * Stack 클래스는 레거시 (Vector 기반, synchronized → 느림) — 신규 코드에서는 사용 금지
 * Deque 인터페이스를 스택처럼 사용: push/pop/peek 은 앞(First) 쪽을 조작
 */
@DisplayName("스택 (ArrayDeque as Stack)")
class StackTest {

    // ── 기본 조작 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("push / pop / peek — 기본 LIFO 동작")
    void pushPopPeek() {
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertThat(stack.peek()).isEqualTo(3);       // 맨 위 조회 (제거 안 함)
        assertThat(stack.pop()).isEqualTo(3);        // 맨 위 제거
        assertThat(stack.pop()).isEqualTo(2);
        assertThat(stack.peek()).isEqualTo(1);
        assertThat(stack.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("isEmpty — 비어있는지 확인")
    void isEmpty() {
        Deque<String> stack = new ArrayDeque<>();

        assertThat(stack.isEmpty()).isTrue();
        stack.push("a");
        assertThat(stack.isEmpty()).isFalse();
        stack.pop();
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("pop — 비어있으면 NoSuchElementException")
    void popOnEmpty() {
        Deque<Integer> stack = new ArrayDeque<>();

        assertThatThrownBy(stack::pop)
                .isInstanceOf(NoSuchElementException.class);
    }

    // ── 활용 예시 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("활용: 괄호 유효성 검사")
    void bracketValidation() {
        assertThat(isValidBrackets("()[]{}")).isTrue();
        assertThat(isValidBrackets("([{}])")).isTrue();
        assertThat(isValidBrackets("(]")).isFalse();
        assertThat(isValidBrackets("([)]")).isFalse();
        assertThat(isValidBrackets("{[]")).isFalse();
    }

    private boolean isValidBrackets(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }
        return stack.isEmpty();
    }

    @Test
    @DisplayName("활용: 문자열 뒤집기 (되돌리기 패턴)")
    void reverseString() {
        String input = "Java";
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : input.toCharArray()) stack.push(c);

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());

        assertThat(sb.toString()).isEqualTo("avaJ");
    }
}
