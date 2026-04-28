package dev.danielk.basicjava.json.exercise;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01: Gson 기본 사용법
 *
 * Gson을 이용한 객체/리스트 직렬화·역직렬화를 수행하세요.
 *
 * 사용해야 할 메서드:
 *   new Gson(), gson.toJson(obj), gson.fromJson(json, Class),
 *   gson.fromJson(json, new TypeToken<T>(){}.getType())
 */
@DisplayName("연습 01: Gson 기본 사용법")
class Ex01_GsonBasicTest {

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
            "{\"id\":2,\"username\":\"bob\",\"email\":\"bob@example.com\"}";
    private static final String USER_LIST_JSON =
            "[{\"id\":1,\"username\":\"alice\",\"email\":\"alice@example.com\"}" +
            ",{\"id\":2,\"username\":\"bob\",\"email\":\"bob@example.com\"}]";

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * User 인스턴스(id=1, username="alice", email="alice@example.com")를
     * Gson으로 JSON 문자열로 변환하세요.
     * 힌트: new Gson(), gson.toJson(obj)
     */
    String userToJson(User user) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: 객체 직렬화 - toJson()")
    void test_userToJson() {
        User user = new User(1, "alice", "alice@example.com");
        String json = userToJson(user);
        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"username\":\"alice\"");
        assertThat(json).contains("\"email\":\"alice@example.com\"");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * JSON 문자열을 Gson으로 User 객체(Class 지정)로 변환하세요.
     * 힌트: gson.fromJson(json, User.class)
     */
    User jsonToUser(String json) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 객체 역직렬화 - fromJson(Class)")
    void test_jsonToUser() {
        User user = jsonToUser(USER_JSON);
        assertThat(user.getId()).isEqualTo(2);
        assertThat(user.getUsername()).isEqualTo("bob");
        assertThat(user.getEmail()).isEqualTo("bob@example.com");
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * JSON 배열 문자열을 Gson + TypeToken<List<User>>으로 List<User>로 변환하세요.
     * 힌트: gson.fromJson(json, new TypeToken<List<User>>(){}.getType())
     */
    List<User> jsonToUserList(String json) {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 리스트 역직렬화 - fromJson(TypeToken)")
    void test_jsonToUserList() {
        List<User> users = jsonToUserList(USER_LIST_JSON);
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getUsername()).isEqualTo("alice");
    }
}
