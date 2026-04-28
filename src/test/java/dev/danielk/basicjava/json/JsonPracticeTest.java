package dev.danielk.basicjava.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JSON 처리 연습문제
 *
 * [문제] 주석: 요구사항만 서술, 풀이 코드 없음
 * [풀이] 주석: 실제 구현
 */
@DisplayName("JSON 처리 연습문제")
class JsonPracticeTest {

    // ── 샘플 POJO ─────────────────────────────────────────────────────────────

    static class User {
        private int id;
        private String username;
        private String email;

        public User() {}

        public User(int id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }

        public int getId() { return id; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }

        public void setId(int id) { this.id = id; }
        public void setUsername(String username) { this.username = username; }
        public void setEmail(String email) { this.email = email; }

        @Override
        public String toString() {
            return "User{id=" + id + ", username='" + username + "', email='" + email + "'}";
        }
    }

    private static final String USER_JSON =
            "{\"id\":2,\"username\":\"bob\",\"email\":\"bob@example.com\"}";
    private static final String USER_LIST_JSON =
            "[{\"id\":1,\"username\":\"alice\",\"email\":\"alice@example.com\"}" +
            ",{\"id\":2,\"username\":\"bob\",\"email\":\"bob@example.com\"}]";

    // ── Gson ──────────────────────────────────────────────────────────────────

    /*
     * [문제 1]
     * User 인스턴스(id=1, username="alice", email="alice@example.com")를
     * Gson으로 JSON 문자열로 변환하라.
     * 변환된 문자열에 "id":1, "username":"alice", "email":"alice@example.com" 이 포함되어야 한다.
     */
    @Test
    @DisplayName("문제1 - Gson: 객체 직렬화")
    void gson_직렬화() {
        // [풀이]
        Gson gson = new Gson();
        User user = new User(1, "alice", "alice@example.com");

        String json = gson.toJson(user);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"username\":\"alice\"");
        assertThat(json).contains("\"email\":\"alice@example.com\"");
    }

    /*
     * [문제 2]
     * 아래 JSON 문자열을 Gson으로 User 객체(Class 지정)로 변환하라.
     * json = {"id":2,"username":"bob","email":"bob@example.com"}
     * id=2, username="bob", email="bob@example.com" 이어야 한다.
     */
    @Test
    @DisplayName("문제2 - Gson: 객체 역직렬화 (Class 지정)")
    void gson_역직렬화_byClass() {
        // [풀이]
        Gson gson = new Gson();

        User user = gson.fromJson(USER_JSON, User.class);

        assertThat(user.getId()).isEqualTo(2);
        assertThat(user.getUsername()).isEqualTo("bob");
        assertThat(user.getEmail()).isEqualTo("bob@example.com");
    }

    /*
     * [문제 3]
     * 아래 JSON 배열 문자열을 Gson + TypeToken<List<User>>으로 List<User>로 변환하라.
     * json = [{"id":1,...},{"id":2,...}]
     * 사이즈가 2이고 첫 번째 원소의 username이 "alice" 이어야 한다.
     */
    @Test
    @DisplayName("문제3 - Gson: 리스트 역직렬화 (TypeToken)")
    void gson_리스트_역직렬화_byTypeToken() {
        // [풀이]
        Gson gson = new Gson();

        List<User> users = gson.fromJson(USER_LIST_JSON,
                new TypeToken<List<User>>() {}.getType());

        assertThat(users).hasSize(2);
        assertThat(users.get(0).getUsername()).isEqualTo("alice");
    }

    // ── Jackson ObjectMapper ──────────────────────────────────────────────────

    /*
     * [문제 4]
     * User 인스턴스(id=1, username="alice", email="alice@example.com")를
     * Jackson ObjectMapper로 JSON 문자열로 변환하라.
     * 변환된 문자열에 "id":1, "username":"alice", "email":"alice@example.com" 이 포함되어야 한다.
     */
    @Test
    @DisplayName("문제4 - Jackson: 객체 직렬화")
    void jackson_직렬화() throws JsonProcessingException {
        // [풀이]
        ObjectMapper om = new ObjectMapper();
        User user = new User(1, "alice", "alice@example.com");

        String json = om.writeValueAsString(user);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"username\":\"alice\"");
        assertThat(json).contains("\"email\":\"alice@example.com\"");
    }

    /*
     * [문제 5]
     * 아래 JSON 배열 문자열을 Jackson ObjectMapper + TypeReference<List<User>>로 변환하라.
     * json = [{"id":1,...},{"id":2,...}]
     * 사이즈가 2이고 두 번째 원소의 email이 "bob@example.com" 이어야 한다.
     */
    @Test
    @DisplayName("문제5 - Jackson: 리스트 역직렬화 (TypeReference)")
    void jackson_리스트_역직렬화_byTypeReference() throws JsonProcessingException {
        // [풀이]
        ObjectMapper om = new ObjectMapper();

        List<User> users = om.readValue(USER_LIST_JSON,
                new TypeReference<List<User>>() {});

        assertThat(users).hasSize(2);
        assertThat(users.get(1).getEmail()).isEqualTo("bob@example.com");
    }
}
