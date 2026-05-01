package dev.danielk.basicjava.http;

import com.google.gson.Gson;
import dev.danielk.basicjava.http.dto.UserRequest;
import dev.danielk.basicjava.http.dto.UserResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo 34: 다단계 JSON 응답 CRUD 학습 테스트
 *
 * MockWebServer가 UserController가 반환할 만한 다단계 응답을 흉내낸다.
 * 응답 형식: { id, name, email, joinedAt, wishlist: [ { product: { id, name, price } } ] }
 *
 * 클라이언트는 DTO(UserRequest / UserResponse)를 통해 직렬화/역직렬화한다.
 * UserRequest는 name/email만 가진 단순 구조 (CUD 정책상 wishlist는 변경 불가).
 */
@DisplayName("OkHttp REST CRUD 응답 처리 (다단계 JSON)")
class OkHttp34CrudTest {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private MockWebServer server;
    private Gson gson;

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
    @DisplayName("GET 단건 — 다단계 응답을 UserResponse로 역직렬화")
    void getSingleAndDeserialize() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("""
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

        Request request = new Request.Builder().url(server.url("/users/1")).get().build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            UserResponse user = gson.fromJson(response.body().charStream(), UserResponse.class);
            assertEquals(1L, user.id());
            assertEquals("daniel", user.name());
            assertNotNull(user.joinedAt());
            assertEquals(2, user.wishlist().size());
            assertEquals("키보드", user.wishlist().get(0).product().name());
            assertEquals(30000, user.wishlist().get(0).product().price());
            assertEquals("모니터", user.wishlist().get(1).product().name());
        }
    }

    @Test
    @DisplayName("POST 생성 — UserRequest(name/email) 전송, 201 + Location 응답 처리")
    void postCreateWithLocationHeader() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(201)
                .setHeader("Location", "/users/42")
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                          "id": 42,
                          "name": "new",
                          "email": "n@example.com",
                          "joinedAt": "2026-05-02T10:00:00",
                          "wishlist": [
                            {"product": {"id": 1, "name": "키보드", "price": 30000}}
                          ]
                        }
                        """));

        UserRequest reqBody = new UserRequest("new", "n@example.com");
        RequestBody body = RequestBody.create(gson.toJson(reqBody), JSON);
        Request request = new Request.Builder().url(server.url("/users")).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(201, response.code());
            assertEquals("/users/42", response.header("Location"));
            UserResponse created = gson.fromJson(response.body().charStream(), UserResponse.class);
            assertEquals(42L, created.id());
            assertEquals(1, created.wishlist().size());
            assertEquals("키보드", created.wishlist().get(0).product().name());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("POST", recorded.getMethod());
        String sent = recorded.getBody().readUtf8();
        // 클라이언트 요청은 name/email만 포함
        assertTrue(sent.contains("\"name\":\"new\""));
        assertTrue(sent.contains("\"email\":\"n@example.com\""));
        assertFalse(sent.contains("\"wishlist\""));
        assertFalse(sent.contains("\"joinedAt\""));
    }

    @Test
    @DisplayName("PUT 수정 — name/email만 변경, wishlist 유지")
    void putUpdate() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                          "id": 1,
                          "name": "updated",
                          "email": "u@example.com",
                          "joinedAt": "2026-05-02T10:00:00",
                          "wishlist": [
                            {"product": {"id": 2, "name": "마우스", "price": 15000}}
                          ]
                        }
                        """));

        UserRequest update = new UserRequest("updated", "u@example.com");
        RequestBody body = RequestBody.create(gson.toJson(update), JSON);
        Request request = new Request.Builder().url(server.url("/users/1")).put(body).build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            UserResponse result = gson.fromJson(response.body().charStream(), UserResponse.class);
            assertEquals("updated", result.name());
            // wishlist는 서버가 유지하므로 응답에 그대로 포함
            assertEquals(1, result.wishlist().size());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("PUT", recorded.getMethod());
        assertEquals("/users/1", recorded.getPath());
    }

    @Test
    @DisplayName("DELETE 삭제 — 204 No Content")
    void deleteNoContent() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setResponseCode(204));

        Request request = new Request.Builder().url(server.url("/users/1")).delete().build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(204, response.code());
            assertTrue(response.isSuccessful());
            assertEquals("", response.body().string());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("DELETE", recorded.getMethod());
    }

    @Test
    @DisplayName("4xx 에러 응답 — UserController가 반환하는 ApiError 형식")
    void parseErrorResponse() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"status\":404,\"message\":\"user not found: 999\"}"));

        Request request = new Request.Builder().url(server.url("/users/999")).get().build();

        try (Response response = client.newCall(request).execute()) {
            assertFalse(response.isSuccessful());
            assertEquals(404, response.code());
            ApiError error = gson.fromJson(response.body().charStream(), ApiError.class);
            assertEquals(404, error.status());
            assertEquals("user not found: 999", error.message());
        }
    }
}
