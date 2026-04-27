package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03: 팀 로스터 관리
 *
 * 두 팀 명단을 비교하고 뒤집기, 커스텀 정렬을 수행하세요.
 *
 * 사용해야 할 메서드:
 *   retainAll, removeAll, Collections.reverse, sort(Comparator)
 */
@DisplayName("연습 03: 팀 로스터 관리")
class Ex03_TeamRosterTest {

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 두 팀에 모두 속한 선수 명단(교집합)을 반환하세요.
     * 힌트: new ArrayList<>(teamA) 후 retainAll(teamB)
     */
    List<String> commonPlayers(List<String> teamA, List<String> teamB) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 1: 양 팀 공통 선수")
    void test_commonPlayers() {
        List<String> teamA = List.of("김철수", "이영희", "박민준", "최지우");
        List<String> teamB = List.of("이영희", "정도윤", "최지우", "한수진");
        assertThat(commonPlayers(teamA, teamB)).containsExactlyInAnyOrder("이영희", "최지우");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * teamA에만 있고 teamB에는 없는 선수 명단을 반환하세요.
     * 힌트: new ArrayList<>(teamA) 후 removeAll(teamB)
     */
    List<String> exclusivePlayers(List<String> teamA, List<String> teamB) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 2: A팀 단독 선수")
    void test_exclusivePlayers() {
        List<String> teamA = List.of("김철수", "이영희", "박민준", "최지우");
        List<String> teamB = List.of("이영희", "정도윤", "최지우", "한수진");
        assertThat(exclusivePlayers(teamA, teamB)).containsExactlyInAnyOrder("김철수", "박민준");
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 선수 명단의 순서를 뒤집어서 반환하세요. (원본 불변)
     * 힌트: new ArrayList<>(roster) 후 Collections.reverse
     */
    List<String> reversedRoster(List<String> roster) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 3: 선수 명단 뒤집기")
    void test_reversedRoster() {
        List<String> roster = List.of("김철수", "이영희", "박민준");
        assertThat(reversedRoster(roster)).containsExactly("박민준", "이영희", "김철수");
        assertThat(roster).containsExactly("김철수", "이영희", "박민준"); // 원본 불변
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 선수 이름을 이름 길이 오름차순으로 정렬하여 반환하세요. (길이 같으면 사전순)
     * 힌트: sort(Comparator.comparingInt(String::length).thenComparing(naturalOrder()))
     */
    List<String> sortByNameLength(List<String> roster) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Test
    @DisplayName("문제 4: 이름 길이 기준 정렬")
    void test_sortByNameLength() {
        List<String> roster = List.of("남궁민수", "이영희", "박민준", "최지우", "김철수");
        List<String> result = sortByNameLength(roster);
        assertThat(result.get(result.size() - 1)).isEqualTo("남궁민수");
        assertThat(result.subList(0, 4)).containsExactlyInAnyOrder("이영희", "박민준", "최지우", "김철수");
    }
}
