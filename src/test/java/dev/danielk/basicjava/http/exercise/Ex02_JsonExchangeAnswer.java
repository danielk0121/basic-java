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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02 답안: JSON 직렬화/역직렬화 (MockWebServer)
 *
 * 도메인은 main {@link User} record를 재사용 (UserController 응답 형식과 동일).
 */
@DisplayName("연습 02 답안: JSON 직렬화/역직렬화")
class Ex02_JsonExchangeAnswer {

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

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] JSON 응답을 User 객체로 역직렬화.
     * [풀이] body().charStream()을 직접 Gson에 넘기면 별도 String 변환 없이 처리 가능.
     */
    User fetchUser(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().charStream(), User.class);
        }
    }

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

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] JSON 배열을 List<User>로 역직렬화.
     * [풀이] 제네릭 타입은 런타임에 소실되므로 TypeToken으로 Type을 캡처하여 전달해야 함.
     */
    List<User> fetchUserList(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(response.body().charStream(), listType);
        }
    }

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

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 객체 직렬화하여 POST 전송 + 응답 역직렬화.
     * [풀이] gson.toJson()으로 문자열 생성 후 RequestBody.create(json, mediaType)로 본문 구성.
     *        Content-Type은 RequestBody의 MediaType이 자동 설정.
     */
    User createUser(String url, User newUser) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(gson.toJson(newUser), JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().charStream(), User.class);
        }
    }

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
