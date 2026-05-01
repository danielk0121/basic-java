package dev.danielk.basicjava.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo 37: OkHttp 클라이언트로 todo 36 Spring 서버 호출 통합 테스트
 *
 * @SpringBootTest(RANDOM_PORT)으로 실제 Spring Boot 서버를 띄우고
 * OkHttp 클라이언트가 종단간(end-to-end) 통신을 검증한다.
 *
 * 학습용이므로 OkHttpClient를 매 테스트에서 새로 생성한다.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("OkHttp 클라이언트 - Spring 서버 통합")
class OkHttp37ServerIntegrationTest {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @LocalServerPort
    int port;

    @Autowired
    UserController controller;

    private final Gson gson = new Gson();

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    @DisplayName("CRUD 시나리오 종합 — 생성, 조회, 수정, 삭제")
    void fullCrudFlow() throws IOException {
        OkHttpClient client = new OkHttpClient();

        // 1. POST 생성 → 201 + Location 헤더
        User newUser = new User(null, "daniel", "daniel@example.com");
        RequestBody createBody = RequestBody.create(gson.toJson(newUser), JSON);
        Request createReq = new Request.Builder()
                .url(baseUrl() + "/users")
                .post(createBody)
                .build();

        Long createdId;
        try (Response response = client.newCall(createReq).execute()) {
            assertEquals(201, response.code());
            String location = response.header("Location");
            assertNotNull(location);
            assertTrue(location.startsWith("/users/"));
            User created = gson.fromJson(response.body().charStream(), User.class);
            assertNotNull(created.id());
            assertEquals("daniel", created.name());
            createdId = created.id();
        }

        // 2. GET 단건 조회
        Request getReq = new Request.Builder()
                .url(baseUrl() + "/users/" + createdId)
                .get()
                .build();
        try (Response response = client.newCall(getReq).execute()) {
            assertEquals(200, response.code());
            User found = gson.fromJson(response.body().charStream(), User.class);
            assertEquals(createdId, found.id());
            assertEquals("daniel@example.com", found.email());
        }

        // 3. PUT 수정
        User updateBody = new User(null, "updated", "updated@example.com");
        Request putReq = new Request.Builder()
                .url(baseUrl() + "/users/" + createdId)
                .put(RequestBody.create(gson.toJson(updateBody), JSON))
                .build();
        try (Response response = client.newCall(putReq).execute()) {
            assertEquals(200, response.code());
            User updated = gson.fromJson(response.body().charStream(), User.class);
            assertEquals("updated", updated.name());
        }

        // 4. DELETE → 204 No Content
        Request deleteReq = new Request.Builder()
                .url(baseUrl() + "/users/" + createdId)
                .delete()
                .build();
        try (Response response = client.newCall(deleteReq).execute()) {
            assertEquals(204, response.code());
        }

        // 5. 삭제된 ID 재조회 → 404
        try (Response response = client.newCall(getReq).execute()) {
            assertEquals(404, response.code());
            ApiError error = gson.fromJson(response.body().charStream(), ApiError.class);
            assertEquals(404, error.status());
            assertTrue(error.message().contains(String.valueOf(createdId)));
        }
    }

    @Test
    @DisplayName("GET 전체 목록 — List<User>로 역직렬화")
    void getList() throws IOException {
        OkHttpClient client = new OkHttpClient();

        // 사전 준비: 2건 생성
        for (String name : List.of("a", "b")) {
            User u = new User(null, name, name + "@x.com");
            Request req = new Request.Builder()
                    .url(baseUrl() + "/users")
                    .post(RequestBody.create(gson.toJson(u), JSON))
                    .build();
            try (Response response = client.newCall(req).execute()) {
                assertEquals(201, response.code());
            }
        }

        Request listReq = new Request.Builder().url(baseUrl() + "/users").get().build();
        try (Response response = client.newCall(listReq).execute()) {
            assertEquals(200, response.code());
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(response.body().charStream(), listType);
            assertTrue(users.size() >= 2);
        }
    }

    @Test
    @DisplayName("GET 페이지 응답 — Page<User> 래퍼 역직렬화")
    void getPage() throws IOException {
        OkHttpClient client = new OkHttpClient();

        // 5건 생성
        for (int i = 0; i < 5; i++) {
            User u = new User(null, "u" + i, "u" + i + "@x.com");
            Request req = new Request.Builder()
                    .url(baseUrl() + "/users")
                    .post(RequestBody.create(gson.toJson(u), JSON))
                    .build();
            try (Response response = client.newCall(req).execute()) {
                response.close();
            }
        }

        Request pageReq = new Request.Builder()
                .url(baseUrl() + "/users/page?page=0&size=3")
                .build();

        try (Response response = client.newCall(pageReq).execute()) {
            assertEquals(200, response.code());
            Type pageType = new TypeToken<UserController.Page<User>>() {}.getType();
            UserController.Page<User> page = gson.fromJson(response.body().charStream(), pageType);
            assertEquals(0, page.page());
            assertEquals(3, page.data().size());
            assertTrue(page.total() >= 5);
        }
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회 → 404 + ApiError 본문")
    void getNotFound() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder()
                .url(baseUrl() + "/users/999999")
                .get()
                .build();

        try (Response response = client.newCall(req).execute()) {
            assertEquals(404, response.code());
            ApiError error = gson.fromJson(response.body().charStream(), ApiError.class);
            assertEquals(404, error.status());
            assertNotNull(error.message());
        }
    }

    @Test
    @DisplayName("컨트롤러 빈이 정상 주입되는지 확인")
    void controllerBeanInjected() {
        assertNotNull(controller);
    }
}
