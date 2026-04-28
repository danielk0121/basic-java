package dev.danielk.basicjava.random.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01 답안: 카드 게임 덱
 *
 * 52장 카드 덱을 생성하고 셔플, 패 뽑기, 중복 검증을 수행하세요.
 *
 * 사용해야 할 메서드:
 *   Collections.shuffle, subList, Random.nextInt, ThreadLocalRandom
 */
@DisplayName("연습 01 답안: 카드 게임 덱")
class Ex01_CardDeckAnswer {

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 52장 카드 덱을 생성하여 반환하세요.
     * 카드 형식: "무늬-숫자" — 무늬는 스페이드/하트/다이아/클럽, 숫자는 1~13
     * 예: "스페이드-1", "하트-13"
     * 힌트: 무늬 배열 × 1~13 이중 for문으로 52개 생성
     *
     * [풀이] 4가지 무늬 × 13장 = 52장, "무늬-숫자" 형태로 리스트에 추가
     */
    List<String> createDeck() {
        String[] suits = {"스페이드", "하트", "다이아", "클럽"};
        List<String> deck = new ArrayList<>();
        for (String suit : suits) {
            for (int num = 1; num <= 13; num++) {
                deck.add(suit + "-" + num);
            }
        }
        return deck;
    }

    @Test
    @DisplayName("문제 1: 52장 카드 덱 생성")
    void test_createDeck() {
        List<String> deck = createDeck();
        assertThat(deck).hasSize(52);
        assertThat(deck).contains("스페이드-1", "하트-13", "다이아-7", "클럽-1");
        assertThat(deck).doesNotHaveDuplicates();
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 덱을 셔플한 뒤 앞에서 5장을 뽑아 반환하세요.
     * 힌트: new ArrayList<>(deck), Collections.shuffle, subList(0, 5)
     *
     * [풀이] 원본 덱을 변경하지 않도록 복사본을 셔플한 뒤 앞 5장 반환
     */
    List<String> drawHand(List<String> deck) {
        List<String> shuffled = new ArrayList<>(deck);
        Collections.shuffle(shuffled);
        return new ArrayList<>(shuffled.subList(0, 5));
    }

    @Test
    @DisplayName("문제 2: 셔플 후 패 5장 뽑기")
    void test_drawHand() {
        List<String> deck = createDeck();
        List<String> hand = drawHand(deck);
        assertThat(hand).hasSize(5);
        assertThat(hand).doesNotHaveDuplicates();
        assertThat(deck).containsAll(hand);
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 덱에서 중복 없이 n장을 랜덤 추출하여 반환하세요.
     * 힌트: Collections.shuffle 후 subList(0, n)
     *
     * [풀이] 원본 덱을 복사 후 셔플 → subList(0, n)으로 n장 추출
     */
    List<String> drawRandom(List<String> deck, int n) {
        List<String> shuffled = new ArrayList<>(deck);
        Collections.shuffle(shuffled);
        return new ArrayList<>(shuffled.subList(0, n));
    }

    @Test
    @DisplayName("문제 3: 중복 없이 N장 랜덤 추출")
    void test_drawRandom() {
        List<String> deck = createDeck();
        List<String> drawn = drawRandom(deck, 7);
        assertThat(drawn).hasSize(7);
        assertThat(drawn).doesNotHaveDuplicates();
        assertThat(deck).containsAll(drawn);
    }
}
