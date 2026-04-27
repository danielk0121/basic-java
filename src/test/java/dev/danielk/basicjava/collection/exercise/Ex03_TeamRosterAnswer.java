package dev.danielk.basicjava.collection.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03 답안: 팀 로스터 관리
 */
@DisplayName("연습 03 답안: 팀 로스터 관리")
class Ex03_TeamRosterAnswer {

    List<String> commonPlayers(List<String> teamA, List<String> teamB) {
        List<String> result = new ArrayList<>(teamA);
        result.retainAll(teamB);
        return result;
    }

    @Test
    @DisplayName("문제 1: 양 팀 공통 선수")
    void test_commonPlayers() {
        List<String> teamA = List.of("김철수", "이영희", "박민준", "최지우");
        List<String> teamB = List.of("이영희", "정도윤", "최지우", "한수진");
        assertThat(commonPlayers(teamA, teamB)).containsExactlyInAnyOrder("이영희", "최지우");
    }

    List<String> exclusivePlayers(List<String> teamA, List<String> teamB) {
        List<String> result = new ArrayList<>(teamA);
        result.removeAll(teamB);
        return result;
    }

    @Test
    @DisplayName("문제 2: A팀 단독 선수")
    void test_exclusivePlayers() {
        List<String> teamA = List.of("김철수", "이영희", "박민준", "최지우");
        List<String> teamB = List.of("이영희", "정도윤", "최지우", "한수진");
        assertThat(exclusivePlayers(teamA, teamB)).containsExactlyInAnyOrder("김철수", "박민준");
    }

    /** Collections.reverse는 인-플레이스 연산 → 원본 보호를 위해 복사본에 적용 */
    List<String> reversedRoster(List<String> roster) {
        List<String> result = new ArrayList<>(roster);
        Collections.reverse(result);
        return result;
    }

    @Test
    @DisplayName("문제 3: 선수 명단 뒤집기")
    void test_reversedRoster() {
        List<String> roster = List.of("김철수", "이영희", "박민준");
        assertThat(reversedRoster(roster)).containsExactly("박민준", "이영희", "김철수");
        assertThat(roster).containsExactly("김철수", "이영희", "박민준");
    }

    /** comparingInt(String::length)로 길이 기준, thenComparing으로 동점 처리 */
    List<String> sortByNameLength(List<String> roster) {
        List<String> result = new ArrayList<>(roster);
        result.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        return result;
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
