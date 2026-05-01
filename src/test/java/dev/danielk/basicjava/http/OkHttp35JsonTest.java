package dev.danielk.basicjava.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import dev.danielk.basicjava.http.dto.UserRequest;
import dev.danielk.basicjava.http.dto.UserResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo 35: 다단계 JSON array/object 응답 직렬화-역직렬화 학습 테스트 (Gson)
 *
 * UserController 응답 형식과 동일한 다단계 구조(root + 필드 + 배열)를 다룬다.
 * 보조 학습용 도메인(@SerializedName)은 컨트롤러 응답이 아닌 Gson 기능 학습 전용임을 명시한다.
 */
@DisplayName("OkHttp 다단계 JSON 응답 처리")
class OkHttp35JsonTest {

    private MockWebServer server;
    private Gson gson;

    // Gson @SerializedName 기능 학습용 도메인 (컨트롤러 응답 아님)
    static class Account {
        Long id;
        String name;
        @SerializedName("user_email")
        String email;

        Account() {}
        Account(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

    // 페이지 래퍼 (UserController GET /users/page 응답 형식과 동일 — { data, page, total })
    static class Page<T> {
        List<T> data;
        int page;
        int total;
    }

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        gson = GsonFactory.create();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    @DisplayName("Object 응답 — root + 필드 + 중첩 배열 (UserResponse) 역직렬화")
    void deserializeMultiLevelObject() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody("""
                {
                  "id": 1,
                  "name": "daniel",
                  "email": "d@example.com",
                  "joinedAt": "2026-05-02T10:00:00",
                  "wishlist": [
                    {"product": {"id": 1, "name": "키보드", "price": 30000}},
                    {"product": {"id": 3, "name": "모니터", "price": 300000}}
                  ]
                }
                """));

        Request request = new Request.Builder().url(server.url("/users/1")).build();

        try (Response response = client.newCall(request).execute()) {
            UserResponse user = gson.fromJson(response.body().charStream(), UserResponse.class);
            assertEquals(1L, user.id());
            assertEquals("daniel", user.name());
            assertEquals(2, user.wishlist().size());
            assertEquals("모니터", user.wishlist().get(1).product().name());
            assertEquals(300000, user.wishlist().get(1).product().price());
        }
    }

    @Test
    @DisplayName("Array 응답 — List<UserResponse> 다단계 역직렬화 (TypeToken)")
    void deserializeMultiLevelArrayToList() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody("""
                [
                  {
                    "id":1,"name":"a","email":"a@x.com","joinedAt":"2026-05-01T09:00:00",
                    "wishlist":[]
                  },
                  {
                    "id":2,"name":"b","email":"b@x.com","joinedAt":"2026-05-01T09:00:00",
                    "wishlist":[
                      {"product":{"id":2,"name":"마우스","price":15000}}
                    ]
                  }
                ]
                """));

        Request request = new Request.Builder().url(server.url("/users")).build();

        try (Response response = client.newCall(request).execute()) {
            Type listType = new TypeToken<List<UserResponse>>() {}.getType();
            List<UserResponse> users = gson.fromJson(response.body().charStream(), listType);
            assertEquals(2, users.size());
            assertTrue(users.get(0).wishlist().isEmpty());
            assertEquals(1, users.get(1).wishlist().size());
            assertEquals("마우스", users.get(1).wishlist().get(0).product().name());
        }
    }

    @Test
    @DisplayName("페이지 래퍼 응답 — Page<UserResponse> 제네릭 역직렬화")
    void deserializePageWrapper() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody("""
                {
                  "data": [
                    {"id":1,"name":"a","email":"a@x.com","joinedAt":"2026-05-01T09:00:00","wishlist":[]},
                    {"id":2,"name":"b","email":"b@x.com","joinedAt":"2026-05-01T09:00:00","wishlist":[]}
                  ],
                  "page": 0,
                  "total": 2
                }
                """));

        Request request = new Request.Builder().url(server.url("/users/page?page=0&size=2")).build();

        try (Response response = client.newCall(request).execute()) {
            Type pageType = new TypeToken<Page<UserResponse>>() {}.getType();
            Page<UserResponse> page = gson.fromJson(response.body().charStream(), pageType);
            assertEquals(0, page.page);
            assertEquals(2, page.total);
            assertEquals(2, page.data.size());
            assertEquals("a", page.data.get(0).name());
        }
    }

    @Test
    @DisplayName("@SerializedName 매핑 학습 — user_email ↔ email (학습 전용 Account 도메인)")
    void serializedNameMapping() {
        // 컨트롤러 응답이 아닌, Gson @SerializedName 기능 학습용 케이스
        String json = "{\"id\":1,\"name\":\"daniel\",\"user_email\":\"d@x.com\"}";
        Account account = gson.fromJson(json, Account.class);
        assertEquals(1L, account.id);
        assertEquals("daniel", account.name);
        assertEquals("d@x.com", account.email);

        String serialized = gson.toJson(account);
        assertTrue(serialized.contains("\"user_email\":\"d@x.com\""));
    }

    @Test
    @DisplayName("직렬화 — UserRequest를 JSON 문자열로 변환 (서버로 보낼 본문)")
    void serializeRequest() {
        UserRequest req = new UserRequest("daniel", "d@example.com");
        String json = gson.toJson(req);

        assertTrue(json.contains("\"name\":\"daniel\""));
        assertTrue(json.contains("\"email\":\"d@example.com\""));
        // 클라이언트는 wishlist / joinedAt / id를 보내지 않음
        assertFalse(json.contains("\"wishlist\""));
        assertFalse(json.contains("\"joinedAt\""));
        assertFalse(json.contains("\"id\""));
    }

    @Test
    @DisplayName("직렬화 — null 필드 포함 옵션 (serializeNulls)")
    void serializeNullsOption() {
        Gson gsonWithNulls = new GsonBuilder().serializeNulls().create();
        UserRequest req = new UserRequest("daniel", null);
        String json = gsonWithNulls.toJson(req);
        assertTrue(json.contains("\"name\":\"daniel\""));
        assertTrue(json.contains("\"email\":null"));
    }

    @Test
    @DisplayName("누락 필드 / 추가 필드 — 누락은 null, 추가는 무시")
    void missingAndExtraFields() {
        // wishlist 누락, extraField 추가
        String json = "{\"id\":1,\"name\":\"daniel\",\"extraField\":\"ignored\"}";
        UserResponse user = gson.fromJson(json, UserResponse.class);
        assertEquals(1L, user.id());
        assertEquals("daniel", user.name());
        assertNull(user.email());
        assertNull(user.joinedAt());
        assertNull(user.wishlist());
    }
}
