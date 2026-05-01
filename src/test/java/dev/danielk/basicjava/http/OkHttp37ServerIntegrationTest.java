package dev.danielk.basicjava.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.danielk.basicjava.http.dto.UserRequest;
import dev.danielk.basicjava.http.dto.UserResponse;
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
 * 다단계 JSON 응답(User + wishProducts + product)을 종단간으로 검증한다.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("OkHttp 클라이언트 - Spring 서버 통합 (다단계 JSON)")
class OkHttp37ServerIntegrationTest {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @LocalServerPort
    int port;

    @Autowired
    UserController controller;

    private final Gson gson = GsonFactory.create();

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    @DisplayName("CRUD 시나리오 종합 — 생성 시 샘플 wishProducts 부여, 수정은 name/email만")
    void fullCrudFlow() throws IOException {
        OkHttpClient client = new OkHttpClient();

        // 1. POST 생성 — 서버가 joinedAt / 샘플 wishProducts를 채워서 응답
        UserRequest createReqBody = new UserRequest("daniel", "daniel@example.com");
        Request createReq = new Request.Builder()
                .url(baseUrl() + "/users")
                .post(RequestBody.create(gson.toJson(createReqBody), JSON))
                .build();

        Long createdId;
        try (Response response = client.newCall(createReq).execute()) {
            assertEquals(201, response.code());
            String location = response.header("Location");
            assertNotNull(location);
            UserResponse created = gson.fromJson(response.body().charStream(), UserResponse.class);
            assertNotNull(created.id());
            assertEquals("daniel", created.name());
            assertNotNull(created.joinedAt());
            // 샘플 wishProducts: 키보드, 모니터
            assertEquals(2, created.wishProducts().size());
            assertEquals("키보드", created.wishProducts().get(0).product().name());
            assertEquals(30000, created.wishProducts().get(0).product().price());
            assertEquals("모니터", created.wishProducts().get(1).product().name());
            createdId = created.id();
        }

        // 2. GET 단건
        Request getReq = new Request.Builder().url(baseUrl() + "/users/" + createdId).get().build();
        try (Response response = client.newCall(getReq).execute()) {
            assertEquals(200, response.code());
            UserResponse found = gson.fromJson(response.body().charStream(), UserResponse.class);
            assertEquals(createdId, found.id());
            assertEquals(2, found.wishProducts().size());
        }

        // 3. PUT 수정 — name/email만 변경, wishProducts는 유지되어야 함
        UserRequest updateReq = new UserRequest("updated", "u@example.com");
        Request putReq = new Request.Builder()
                .url(baseUrl() + "/users/" + createdId)
                .put(RequestBody.create(gson.toJson(updateReq), JSON))
                .build();
        try (Response response = client.newCall(putReq).execute()) {
            assertEquals(200, response.code());
            UserResponse updated = gson.fromJson(response.body().charStream(), UserResponse.class);
            assertEquals("updated", updated.name());
            assertEquals("u@example.com", updated.email());
            // wishProducts는 기존값 유지
            assertEquals(2, updated.wishProducts().size());
            assertEquals("키보드", updated.wishProducts().get(0).product().name());
        }

        // 4. DELETE → 204
        Request deleteReq = new Request.Builder().url(baseUrl() + "/users/" + createdId).delete().build();
        try (Response response = client.newCall(deleteReq).execute()) {
            assertEquals(204, response.code());
        }

        // 5. 삭제된 ID 재조회 → 404 + ApiError
        try (Response response = client.newCall(getReq).execute()) {
            assertEquals(404, response.code());
            ApiError error = gson.fromJson(response.body().charStream(), ApiError.class);
            assertEquals(404, error.status());
            assertTrue(error.message().contains(String.valueOf(createdId)));
        }
    }

    @Test
    @DisplayName("GET 페이지 응답 — Page<UserResponse> 래퍼 역직렬화")
    void getPage() throws IOException {
        OkHttpClient client = new OkHttpClient();

        for (int i = 0; i < 5; i++) {
            UserRequest req = new UserRequest("u" + i, "u" + i + "@x.com");
            Request createReq = new Request.Builder()
                    .url(baseUrl() + "/users")
                    .post(RequestBody.create(gson.toJson(req), JSON))
                    .build();
            try (Response response = client.newCall(createReq).execute()) {
                response.close();
            }
        }

        Request pageReq = new Request.Builder()
                .url(baseUrl() + "/users/page?page=0&size=3")
                .build();

        try (Response response = client.newCall(pageReq).execute()) {
            assertEquals(200, response.code());
            Type pageType = new TypeToken<UserController.Page<UserResponse>>() {}.getType();
            UserController.Page<UserResponse> page =
                    gson.fromJson(response.body().charStream(), pageType);
            assertEquals(0, page.page());
            assertEquals(3, page.data().size());
            assertTrue(page.total() >= 5);
        }
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회 → 404 + ApiError 본문")
    void getNotFound() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(baseUrl() + "/users/999999").get().build();

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
