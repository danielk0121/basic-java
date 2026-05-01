package dev.danielk.basicjava.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
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
 * todo 35: JSON array / object 응답 직렬화-역직렬화 학습 테스트 (Gson)
 */
@DisplayName("OkHttp JSON array/object 응답 처리")
class OkHttpJsonTest {

    private MockWebServer server;
    private Gson gson;

    static class User {
        Long id;
        String name;
        @SerializedName("user_email")
        String email;

        User() {}
        User(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

    static class Item {
        String sku;
        int quantity;
    }

    static class Order {
        Long id;
        User user;
        List<Item> items;
    }

    static class Page<T> {
        List<T> data;
        int page;
        int total;
    }

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        gson = new Gson();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    @DisplayName("Object 응답 — 단일 객체 역직렬화 + @SerializedName 매핑")
    void deserializeSingleObject() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setBody("{\"id\":1,\"name\":\"daniel\",\"user_email\":\"d@example.com\"}"));

        Request request = new Request.Builder().url(server.url("/users/1")).build();

        try (Response response = client.newCall(request).execute()) {
            User user = gson.fromJson(response.body().charStream(), User.class);
            assertEquals(1L, user.id);
            assertEquals("daniel", user.name);
            // user_email → email 매핑 확인
            assertEquals("d@example.com", user.email);
        }
    }

    @Test
    @DisplayName("Array 응답 — List<T>로 역직렬화 (TypeToken)")
    void deserializeArrayToList() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody(
                "[" +
                "{\"id\":1,\"name\":\"a\",\"user_email\":\"a@x.com\"}," +
                "{\"id\":2,\"name\":\"b\",\"user_email\":\"b@x.com\"}," +
                "{\"id\":3,\"name\":\"c\",\"user_email\":\"c@x.com\"}" +
                "]"));

        Request request = new Request.Builder().url(server.url("/users")).build();

        try (Response response = client.newCall(request).execute()) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(response.body().charStream(), listType);

            assertEquals(3, users.size());
            assertEquals("a", users.get(0).name);
            assertEquals("c@x.com", users.get(2).email);
        }
    }

    @Test
    @DisplayName("Array 응답 — T[] 배열로 역직렬화")
    void deserializeArrayToTypedArray() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody(
                "[{\"id\":10,\"name\":\"x\"},{\"id\":20,\"name\":\"y\"}]"));

        Request request = new Request.Builder().url(server.url("/users")).build();

        try (Response response = client.newCall(request).execute()) {
            User[] users = gson.fromJson(response.body().charStream(), User[].class);
            assertEquals(2, users.length);
            assertEquals(10L, users[0].id);
            assertEquals("y", users[1].name);
        }
    }

    @Test
    @DisplayName("중첩 객체 역직렬화 — Order { user, items }")
    void deserializeNestedObject() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody(
                "{\"id\":100," +
                "\"user\":{\"id\":1,\"name\":\"daniel\",\"user_email\":\"d@x.com\"}," +
                "\"items\":[{\"sku\":\"A1\",\"quantity\":2},{\"sku\":\"B2\",\"quantity\":5}]}"));

        Request request = new Request.Builder().url(server.url("/orders/100")).build();

        try (Response response = client.newCall(request).execute()) {
            Order order = gson.fromJson(response.body().charStream(), Order.class);
            assertEquals(100L, order.id);
            assertEquals("daniel", order.user.name);
            assertEquals("d@x.com", order.user.email);
            assertEquals(2, order.items.size());
            assertEquals("A1", order.items.get(0).sku);
            assertEquals(5, order.items.get(1).quantity);
        }
    }

    @Test
    @DisplayName("페이지 래퍼 응답 — Page<User> 제네릭 역직렬화")
    void deserializePageWrapper() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody(
                "{\"data\":[" +
                "{\"id\":1,\"name\":\"a\"}," +
                "{\"id\":2,\"name\":\"b\"}" +
                "],\"page\":0,\"total\":2}"));

        Request request = new Request.Builder().url(server.url("/users?page=0")).build();

        try (Response response = client.newCall(request).execute()) {
            Type pageType = new TypeToken<Page<User>>() {}.getType();
            Page<User> page = gson.fromJson(response.body().charStream(), pageType);

            assertEquals(0, page.page);
            assertEquals(2, page.total);
            assertEquals(2, page.data.size());
            assertEquals("a", page.data.get(0).name);
        }
    }

    @Test
    @DisplayName("직렬화 — 도메인 객체를 JSON 문자열로 변환")
    void serializeToJson() {
        User user = new User(7L, "daniel", "d@example.com");
        String json = gson.toJson(user);

        // @SerializedName으로 user_email 키로 직렬화되어야 함
        assertTrue(json.contains("\"user_email\":\"d@example.com\""));
        assertTrue(json.contains("\"name\":\"daniel\""));
        assertTrue(json.contains("\"id\":7"));
    }

    @Test
    @DisplayName("직렬화 — null 필드 포함 옵션 (serializeNulls)")
    void serializeNullsOption() {
        Gson gsonWithNulls = new GsonBuilder().serializeNulls().create();

        User user = new User(null, "daniel", null);
        String json = gsonWithNulls.toJson(user);

        assertTrue(json.contains("\"id\":null"));
        assertTrue(json.contains("\"user_email\":null"));
    }

    @Test
    @DisplayName("누락 필드 / 추가 필드 — 누락은 null, 추가는 무시")
    void missingAndExtraFields() {
        // name 누락, extraField 추가
        String json = "{\"id\":1,\"extraField\":\"ignored\"}";

        User user = gson.fromJson(json, User.class);
        assertEquals(1L, user.id);
        assertNull(user.name);
        assertNull(user.email);
    }
}
