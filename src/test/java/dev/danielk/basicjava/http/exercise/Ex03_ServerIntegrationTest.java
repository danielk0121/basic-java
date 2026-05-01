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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 03: Spring 서버 통합 호출 (다단계 JSON)
 *
 * todo 36에서 만든 실제 Spring REST 서버를 OkHttp로 호출하세요.
 * 다단계 JSON(User + wishProducts + product)을 서버와 주고받습니다.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.sample-data.enabled=false")
@DisplayName("연습 03: Spring 서버 통합 호출 (다단계 JSON)")
class Ex03_ServerIntegrationTest {

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

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 새 User를 생성한 뒤 응답의 Location 헤더에서 ID를 파싱하여 반환하세요.
     * 힌트: POST /users → 201 + Location: /users/{id}
     */
    Long createAndExtractId(UserRequest newUser) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: POST 후 Location 헤더에서 ID 추출")
    void test_createAndExtractId() throws IOException {
        Long id = createAndExtractId(sampleRequest("ex3-user"));
        assertThat(id).isPositive();
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 ID로 User를 조회하여 반환하세요. 없으면 null 반환.
     * 서버는 신규 가입자에게 샘플 wishProducts (키보드, 모니터)를 부여합니다.
     * 힌트: 200이면 UserResponse 역직렬화, 404면 null
     */
    UserResponse findById(Long id) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 단건 조회 — 존재 시 UserResponse / 없으면 null")
    void test_findById() throws IOException {
        Long id = createAndExtractId(sampleRequest("find-me"));

        UserResponse found = findById(id);
        assertThat(found).isNotNull();
        assertThat(found.name()).isEqualTo("find-me");
        // 샘플 wishProducts 검증
        assertThat(found.wishProducts()).hasSize(2);
        assertThat(found.wishProducts().get(0).product().name()).isEqualTo("키보드");

        UserResponse missing = findById(99999L);
        assertThat(missing).isNull();
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 ID로 User를 삭제하세요. 응답 코드(204 또는 404)를 반환하세요.
     */
    int deleteById(Long id) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 삭제 — 204 / 404 코드 반환")
    void test_deleteById() throws IOException {
        Long id = createAndExtractId(sampleRequest("delete-me"));
        assertThat(deleteById(id)).isEqualTo(204);
        assertThat(deleteById(id)).isEqualTo(404);
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 존재하지 않는 ID 조회 시 4xx 응답 본문(JSON)을 ApiError로 파싱하여 반환하세요.
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
