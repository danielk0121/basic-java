package dev.danielk.basicjava.http.exercise;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.danielk.basicjava.http.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02: JSON 직렬화/역직렬화 (MockWebServer)
 *
 * Gson과 OkHttp을 사용하여 객체 ↔ JSON 변환을 익히세요.
 * 도메인은 main {@link User} record를 재사용 (UserController 응답 형식과 동일).
 *
 * 사용해야 할 메서드:
 *   Gson.toJson/fromJson, TypeToken<List<T>>, MediaType.parse,
 *   RequestBody.create(json, mediaType)
 */
@DisplayName("연습 02: JSON 직렬화/역직렬화")
class Ex02_JsonExchangeTest {

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private MockWebServer server;
    private final Gson gson = new Gson();

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 URL로 GET 요청을 보내고 JSON 응답을 User 객체로 역직렬화하세요.
     * 힌트: gson.fromJson(response.body().charStream(), User.class)
     */
    User fetchUser(String url) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: JSON 응답을 객체로 역직렬화")
    void test_fetchUser() throws IOException {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":7,\"name\":\"daniel\",\"email\":\"d@x.com\"}"));

        User user = fetchUser(server.url("/users/7").toString());
        assertThat(user.id()).isEqualTo(7L);
        assertThat(user.name()).isEqualTo("daniel");
        assertThat(user.email()).isEqualTo("d@x.com");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 URL로 GET 요청을 보내고 JSON 배열 응답을 List<User>로 역직렬화하세요.
     * 힌트: new TypeToken<List<User>>() {}.getType()
     */
    List<User> fetchUserList(String url) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: JSON 배열을 List<T>로 역직렬화")
    void test_fetchUserList() throws IOException {
        server.enqueue(new MockResponse().setBody(
                "[" +
                "{\"id\":1,\"name\":\"a\",\"email\":\"a@x.com\"}," +
                "{\"id\":2,\"name\":\"b\",\"email\":\"b@x.com\"}" +
                "]"));

        List<User> users = fetchUserList(server.url("/users").toString());
        assertThat(users).hasSize(2);
        assertThat(users.get(0).name()).isEqualTo("a");
        assertThat(users.get(1).email()).isEqualTo("b@x.com");
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * User 객체를 JSON으로 직렬화하여 POST 전송하고, 응답 JSON을 User로 역직렬화하여 반환하세요.
     * 힌트: gson.toJson(user) 후 RequestBody.create(json, JSON)으로 본문 생성
     */
    User createUser(String url, User newUser) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 객체 직렬화하여 POST + 응답 역직렬화")
    void test_createUser() throws IOException, InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(201)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":42,\"name\":\"new\",\"email\":\"n@x.com\"}"));

        User created = createUser(
                server.url("/users").toString(),
                new User(null, "new", "n@x.com")
        );
        assertThat(created.id()).isEqualTo(42L);
        assertThat(created.name()).isEqualTo("new");

        var recorded = server.takeRequest();
        assertThat(recorded.getHeader("Content-Type")).startsWith("application/json");
        assertThat(recorded.getBody().readUtf8()).contains("\"name\":\"new\"");
    }
}
