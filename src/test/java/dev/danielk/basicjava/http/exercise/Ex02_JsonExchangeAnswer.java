package dev.danielk.basicjava.http.exercise;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.danielk.basicjava.http.GsonFactory;
import dev.danielk.basicjava.http.dto.UserRequest;
import dev.danielk.basicjava.http.dto.UserResponse;
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
 * 연습 02 답안: 다단계 JSON 직렬화/역직렬화 (MockWebServer)
 */
@DisplayName("연습 02 답안: 다단계 JSON 직렬화/역직렬화")
class Ex02_JsonExchangeAnswer {

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private MockWebServer server;
    private final Gson gson = GsonFactory.create();

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
    UserResponse fetchUser(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().charStream(), UserResponse.class);
        }
    }

    @Test
    @DisplayName("문제 1: 다단계 JSON 응답을 UserResponse로 역직렬화")
    void test_fetchUser() throws IOException {
        server.enqueue(new MockResponse().setBody("""
                {
                  "id": 7,
                  "name": "daniel",
                  "email": "d@x.com",
                  "joinedAt": "2026-05-02T10:00:00",
                  "wishlist": [
                    {"product": {"id": 1, "name": "키보드", "price": 30000}}
                  ]
                }
                """));

        UserResponse user = fetchUser(server.url("/users/7").toString());
        assertThat(user.id()).isEqualTo(7L);
        assertThat(user.wishlist()).hasSize(1);
        assertThat(user.wishlist().get(0).product().name()).isEqualTo("키보드");
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    List<UserResponse> fetchUserList(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            Type listType = new TypeToken<List<UserResponse>>() {}.getType();
            return gson.fromJson(response.body().charStream(), listType);
        }
    }

    @Test
    @DisplayName("문제 2: 다단계 JSON 배열을 List<T>로 역직렬화")
    void test_fetchUserList() throws IOException {
        server.enqueue(new MockResponse().setBody("""
                [
                  {"id":1,"name":"a","email":"a@x.com","joinedAt":"2026-05-01T09:00:00","wishlist":[]},
                  {"id":2,"name":"b","email":"b@x.com","joinedAt":"2026-05-01T09:00:00",
                    "wishlist":[{"product":{"id":2,"name":"마우스","price":15000}}]}
                ]
                """));

        List<UserResponse> users = fetchUserList(server.url("/users").toString());
        assertThat(users).hasSize(2);
        assertThat(users.get(1).wishlist().get(0).product().name()).isEqualTo("마우스");
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    UserResponse createUser(String url, UserRequest request) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(gson.toJson(request), JSON);
        Request httpRequest = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(httpRequest).execute()) {
            return gson.fromJson(response.body().charStream(), UserResponse.class);
        }
    }

    @Test
    @DisplayName("문제 3: UserRequest 직렬화하여 POST + UserResponse 역직렬화")
    void test_createUser() throws IOException, InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(201)
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                          "id": 42,
                          "name": "new",
                          "email": "n@x.com",
                          "joinedAt": "2026-05-02T10:00:00",
                          "wishlist": [
                            {"product":{"id":1,"name":"키보드","price":30000}}
                          ]
                        }
                        """));

        UserRequest req = new UserRequest("new", "n@x.com");
        UserResponse created = createUser(server.url("/users").toString(), req);
        assertThat(created.id()).isEqualTo(42L);
        assertThat(created.wishlist().get(0).product().name()).isEqualTo("키보드");

        var recorded = server.takeRequest();
        assertThat(recorded.getHeader("Content-Type")).startsWith("application/json");
        String sent = recorded.getBody().readUtf8();
        assertThat(sent).contains("\"name\":\"new\"");
        assertThat(sent).doesNotContain("\"wishlist\"");
        assertThat(sent).doesNotContain("\"joinedAt\"");
    }
}
