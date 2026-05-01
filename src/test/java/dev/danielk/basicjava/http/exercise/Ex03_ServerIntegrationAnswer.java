package dev.danielk.basicjava.http.exercise;

import com.google.gson.Gson;
import dev.danielk.basicjava.http.ApiError;
import dev.danielk.basicjava.http.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03 답안: Spring 서버 통합 호출
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("연습 03 답안: Spring 서버 통합 호출")
class Ex03_ServerIntegrationAnswer {

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @LocalServerPort
    int port;

    private final Gson gson = new Gson();

    String baseUrl() {
        return "http://localhost:" + port;
    }

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] POST 후 Location 헤더에서 ID 추출.
     * [풀이] Location은 "/users/42" 형태이므로 마지막 '/' 뒤 토큰을 Long으로 파싱.
     */
    Long createAndExtractId(User newUser) throws IOException {
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
        Long id = createAndExtractId(new User(null, "ex3-user", "ex3@x.com"));
        assertThat(id).isPositive();
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 단건 조회 — 200이면 객체, 404면 null.
     * [풀이] response.code() 분기. 4xx에도 본문은 존재하지만 여기선 null만 반환.
     */
    User findById(Long id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl() + "/users/" + id)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 404) {
                return null;
            }
            return gson.fromJson(response.body().charStream(), User.class);
        }
    }

    @Test
    @DisplayName("문제 2: 단건 조회 — 존재 시 객체 / 없으면 null")
    void test_findById() throws IOException {
        Long id = createAndExtractId(new User(null, "find-me", "f@x.com"));

        User found = findById(id);
        assertThat(found).isNotNull();
        assertThat(found.name()).isEqualTo("find-me");

        User missing = findById(99999L);
        assertThat(missing).isNull();
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] DELETE 후 응답 코드 반환.
     * [풀이] Request.Builder.delete()로 DELETE 메서드 명시.
     */
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
        Long id = createAndExtractId(new User(null, "delete-me", "d@x.com"));
        assertThat(deleteById(id)).isEqualTo(204);
        assertThat(deleteById(id)).isEqualTo(404);
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 4xx 에러 응답 본문을 ApiError로 파싱.
     * [풀이] OkHttp는 4xx여도 예외를 던지지 않음. response.body()를 그대로 Gson에 전달.
     */
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
