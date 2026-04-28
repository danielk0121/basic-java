package dev.danielk.basicjava.json.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: Jackson ObjectMapper 기본 사용법
 *
 * Jackson ObjectMapper를 이용한 객체/리스트 직렬화·역직렬화를 수행하세요.
 *
 * 사용해야 할 메서드:
 *   new ObjectMapper(), om.writeValueAsString(obj),
 *   om.readValue(json, Class), om.readValue(json, new TypeReference<T>(){})
 *
 * 주의: 직렬화 시 getter 필요, 역직렬화 시 기본 생성자 + setter 필요
 */
@DisplayName("연습 02 답안: Jackson ObjectMapper 기본 사용법")
class Ex02_JacksonBasicAnswer {

    static class User {
        private int id;
        private String username;
        private String email;

        public User() {}
        public User(int id, String username, String email) {
            this.id = id; this.username = username; this.email = email;
        }
        public int getId() { return id; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public void setId(int id) { this.id = id; }
        public void setUsername(String username) { this.username = username; }
        public void setEmail(String email) { this.email = email; }
    }

    private static final String USER_JSON =
            "{\"id\":1,\"username\":\"alice\",\"email\":\"alice@example.com\"}";
    private static final String USER_LIST_JSON =
            "[{\"id\":1,\"username\":\"alice\",\"email\":\"alice@example.com\"}" +
            ",{\"id\":2,\"username\":\"bob\",\"email\":\"bob@example.com\"}]";

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] User 인스턴스를 Jackson ObjectMapper로 JSON 문자열로 변환하세요.
     * 힌트: new ObjectMapper(), om.writeValueAsString(obj)
     *
     * [풀이] ObjectMapper는 getter를 기준으로 직렬화한다.
     *        getter가 없으면 UnrecognizedPropertyException이 발생하므로 주의.
     */
    String userToJson(User user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }

    @Test
    @DisplayName("문제 1: 객체 직렬화 - writeValueAsString()")
    void test_userToJson() throws JsonProcessingException {
        User user = new User(1, "alice", "alice@example.com");
        String json = userToJson(user);
        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"username\":\"alice\"");
        assertThat(json).contains("\"email\":\"alice@example.com\"");
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] JSON 배열 문자열을 Jackson ObjectMapper + TypeReference<List<User>>로
     * List<User>로 변환하세요.
     * 힌트: om.readValue(json, new TypeReference<List<User>>(){})
     *
     * [풀이] Gson의 TypeToken과 같은 역할 — 런타임 제네릭 타입 소거를 우회한다.
     *        역직렬화 대상 클래스에 기본 생성자와 setter가 있어야 한다.
     */
    List<User> jsonToUserList(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, new TypeReference<List<User>>() {});
    }

    @Test
    @DisplayName("문제 2: 리스트 역직렬화 - readValue(TypeReference)")
    void test_jsonToUserList() throws JsonProcessingException {
        List<User> users = jsonToUserList(USER_LIST_JSON);
        assertThat(users).hasSize(2);
        assertThat(users.get(1).getEmail()).isEqualTo("bob@example.com");
    }
}
