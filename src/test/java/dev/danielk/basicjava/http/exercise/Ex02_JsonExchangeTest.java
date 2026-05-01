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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 02: 다단계 JSON 직렬화/역직렬화 (MockWebServer)
 *
 * UserController가 다루는 다단계 응답(User + wishlist + product)을 처리하세요.
 * DTO는 main의 {@link UserRequest}, {@link UserResponse}를 재사용합니다.
 */
@DisplayName("연습 02: 다단계 JSON 직렬화/역직렬화")
class Ex02_JsonExchangeTest {

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

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 URL로 GET 요청을 보내고 다단계 JSON 응답을 UserResponse로 역직렬화하세요.
     * 응답에는 wishlist 배열이 있고, 각 요소는 product 중첩 객체를 가집니다.
     * 힌트: gson.fromJson(response.body().charStream(), UserResponse.class)
     */
    UserResponse fetchUser(String url) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
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

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 URL로 GET 요청을 보내고 JSON 배열 응답을 List<UserResponse>로 역직렬화하세요.
     * 각 User에는 wishlist 배열이 포함됩니다.
     * 힌트: new TypeToken<List<UserResponse>>() {}.getType()
     */
    List<UserResponse> fetchUserList(String url) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
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

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * UserRequest(name/email)를 JSON으로 직렬화하여 POST 전송하고, 응답 JSON을 UserResponse로 역직렬화하세요.
     * 클라이언트는 wishlist/joinedAt을 보내지 않고, 서버가 채운 응답을 받아옵니다.
     * 힌트: gson.toJson(request) 후 RequestBody.create(json, JSON)
     */
    UserResponse createUser(String url, UserRequest request) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
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
        // 클라이언트가 보낸 본문에는 wishlist/joinedAt이 없어야 함
        assertThat(sent).contains("\"name\":\"new\"");
        assertThat(sent).doesNotContain("\"wishlist\"");
        assertThat(sent).doesNotContain("\"joinedAt\"");
    }
}
