package dev.danielk.basicjava.http.exercise;

import com.google.gson.Gson;
import dev.danielk.basicjava.http.ApiError;
import dev.danielk.basicjava.http.GsonFactory;
import dev.danielk.basicjava.http.dto.UserRequest;
import dev.danielk.basicjava.http.dto.UserResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03 답안: Spring 서버 통합 호출 (다단계 JSON)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.sample-data.enabled=false")
@DisplayName("연습 03 답안: Spring 서버 통합 호출 (다단계 JSON)")
class Ex03_ServerIntegrationAnswer {

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @LocalServerPort
    int port;

    private final Gson gson = GsonFactory.create();

    String baseUrl() {
        return "http://localhost:" + port;
    }

    UserRequest sampleRequest(String name) {
        return new UserRequest(name, name + "@x.com");
    }

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    Long createAndExtractId(UserRequest newUser) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(gson.toJson(newUser), JSON);
        Request request = new Request.Builder()
                .url(baseUrl() + "/users")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String location = response.header("Location");
            return Long.parseLong(location.substring(location.lastIndexOf('/') + 1));
        }
    }

    @Test
    @DisplayName("문제 1: POST 후 Location 헤더에서 ID 추출")
    void test_createAndExtractId() throws IOException {
        Long id = createAndExtractId(sampleRequest("ex3-user"));
        assertThat(id).isPositive();
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    UserResponse findById(Long id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl() + "/users/" + id)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 404) {
                return null;
            }
            return gson.fromJson(response.body().charStream(), UserResponse.class);
        }
    }

    @Test
    @DisplayName("문제 2: 단건 조회 — 존재 시 UserResponse / 없으면 null")
    void test_findById() throws IOException {
        Long id = createAndExtractId(sampleRequest("find-me"));

        UserResponse found = findById(id);
        assertThat(found).isNotNull();
        assertThat(found.name()).isEqualTo("find-me");
        assertThat(found.wishProducts()).hasSize(2);
        assertThat(found.wishProducts().get(0).product().name()).isEqualTo("키보드");

        UserResponse missing = findById(99999L);
        assertThat(missing).isNull();
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    int deleteById(Long id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl() + "/users/" + id)
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

    @Test
    @DisplayName("문제 3: 삭제 — 204 / 404 코드 반환")
    void test_deleteById() throws IOException {
        Long id = createAndExtractId(sampleRequest("delete-me"));
        assertThat(deleteById(id)).isEqualTo(204);
        assertThat(deleteById(id)).isEqualTo(404);
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    ApiError fetchErrorBody(Long missingId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl() + "/users/" + missingId)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().charStream(), ApiError.class);
        }
    }

    @Test
    @DisplayName("문제 4: 4xx 에러 본문을 ApiError로 파싱")
    void test_fetchErrorBody() throws IOException {
        ApiError error = fetchErrorBody(88888L);
        assertThat(error.status()).isEqualTo(404);
        assertThat(error.message()).contains("88888");
    }
}
