package dev.danielk.basicjava.http.exercise;

import com.google.gson.Gson;
import dev.danielk.basicjava.http.ApiError;
import dev.danielk.basicjava.http.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03: Spring 서버 통합 호출
 *
 * todo 36에서 만든 실제 Spring REST 서버를 OkHttp로 호출하세요.
 *
 * 사용해야 할 메서드:
 *   @SpringBootTest(RANDOM_PORT), @LocalServerPort,
 *   OkHttp + Gson 통합 활용, Location 헤더로 ID 추출, 4xx 에러 본문 파싱
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("연습 03: Spring 서버 통합 호출")
class Ex03_ServerIntegrationTest {

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @LocalServerPort
    int port;

    private final Gson gson = new Gson();

    String baseUrl() {
        return "http://localhost:" + port;
    }

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 새 User를 생성한 뒤 응답의 Location 헤더에서 ID를 파싱하여 반환하세요.
     * 힌트: POST /users → 201 + Location: /users/{id}
     *       Location 헤더의 마지막 path segment가 id
     */
    Long createAndExtractId(User newUser) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: POST 후 Location 헤더에서 ID 추출")
    void test_createAndExtractId() throws IOException {
        Long id = createAndExtractId(new User(null, "ex3-user", "ex3@x.com"));
        assertThat(id).isPositive();
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 ID로 User를 조회하여 반환하세요. 없으면 null 반환.
     * 힌트: 200이면 본문 역직렬화, 404면 null
     */
    User findById(Long id) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
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

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 ID로 User를 삭제하세요. 응답 코드(204 또는 404)를 반환하세요.
     * 힌트: Request.Builder().delete()
     */
    int deleteById(Long id) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 삭제 — 204 / 404 코드 반환")
    void test_deleteById() throws IOException {
        Long id = createAndExtractId(new User(null, "delete-me", "d@x.com"));
        assertThat(deleteById(id)).isEqualTo(204);
        assertThat(deleteById(id)).isEqualTo(404); // 두 번째는 이미 없음
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 존재하지 않는 ID 조회 시 4xx 응답 본문(JSON)을 ApiError로 파싱하여 반환하세요.
     * 힌트: response.body()는 4xx여도 사용 가능. Gson으로 ApiError.class 역직렬화.
     */
    ApiError fetchErrorBody(Long missingId) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: 4xx 에러 본문을 ApiError로 파싱")
    void test_fetchErrorBody() throws IOException {
        ApiError error = fetchErrorBody(88888L);
        assertThat(error.status()).isEqualTo(404);
        assertThat(error.message()).contains("88888");
    }
}
