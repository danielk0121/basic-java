package dev.danielk.basicjava.http;

import com.google.gson.Gson;
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
 * todo 34: 도메인 CRUD REST JSON API client side 응답 처리 학습 테스트
 *
 * MockWebServer를 사용하지만 응답 형식은 main {@link UserController}가 반환하는 형식과 동일하게 맞춘다.
 * 도메인은 main의 {@link User}, {@link ApiError} record를 그대로 재사용한다.
 *
 * 시뮬레이션하는 컨트롤러 엔드포인트:
 * - GET    /users/{id}   → 200 + body
 * - POST   /users        → 201 + Location 헤더
 * - PUT    /users/{id}   → 200
 * - DELETE /users/{id}   → 204 No Content
 * - 404 응답 시 ApiError 본문 (status, message)
 */
@DisplayName("OkHttp REST CRUD 응답 처리")
class OkHttp34CrudTest {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private MockWebServer server;
    private Gson gson;

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
    @DisplayName("GET 단건 — 200 응답을 User로 역직렬화")
    void getSingleAndDeserialize() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":1,\"name\":\"daniel\",\"email\":\"d@example.com\"}"));

        Request request = new Request.Builder()
                .url(server.url("/users/1"))
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            User user = gson.fromJson(response.body().charStream(), User.class);
            assertEquals(1L, user.id());
            assertEquals("daniel", user.name());
            assertEquals("d@example.com", user.email());
        }
    }

    @Test
    @DisplayName("POST 생성 — 201 Created + Location 헤더")
    void postCreateWithLocationHeader() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(201)
                .setHeader("Location", "/users/42")
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":42,\"name\":\"new\",\"email\":\"n@example.com\"}"));

        User newUser = new User(null, "new", "n@example.com");
        RequestBody body = RequestBody.create(gson.toJson(newUser), JSON);

        Request request = new Request.Builder()
                .url(server.url("/users"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(201, response.code());
            assertEquals("/users/42", response.header("Location"));
            User created = gson.fromJson(response.body().charStream(), User.class);
            assertEquals(42L, created.id());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("POST", recorded.getMethod());
        assertTrue(recorded.getBody().readUtf8().contains("\"name\":\"new\""));
    }

    @Test
    @DisplayName("PUT 수정 — 200 응답")
    void putUpdate() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":1,\"name\":\"updated\",\"email\":\"u@example.com\"}"));

        User update = new User(1L, "updated", "u@example.com");
        RequestBody body = RequestBody.create(gson.toJson(update), JSON);

        Request request = new Request.Builder()
                .url(server.url("/users/1"))
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            User result = gson.fromJson(response.body().charStream(), User.class);
            assertEquals("updated", result.name());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("PUT", recorded.getMethod());
        assertEquals("/users/1", recorded.getPath());
    }

    @Test
    @DisplayName("DELETE 삭제 — 204 No Content (빈 응답 본문)")
    void deleteNoContent() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setResponseCode(204));

        Request request = new Request.Builder()
                .url(server.url("/users/1"))
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(204, response.code());
            assertTrue(response.isSuccessful());
            assertEquals("", response.body().string());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("DELETE", recorded.getMethod());
    }

    @Test
    @DisplayName("4xx 에러 응답 — UserController가 반환하는 ApiError 형식 (status, message) 파싱")
    void parseErrorResponse() throws IOException {
        OkHttpClient client = new OkHttpClient();
        // UserController @ExceptionHandler가 반환하는 형식: { "status": 404, "message": "user not found: 999" }
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"status\":404,\"message\":\"user not found: 999\"}"));

        Request request = new Request.Builder()
                .url(server.url("/users/999"))
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertFalse(response.isSuccessful());
            assertEquals(404, response.code());

            ApiError error = gson.fromJson(response.body().charStream(), ApiError.class);
            assertEquals(404, error.status());
            assertEquals("user not found: 999", error.message());
        }
    }
}
