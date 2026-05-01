package dev.danielk.basicjava.http.exercise;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01: OkHttp 기초 요청
 *
 * GET / POST 요청을 만들고 상태 코드, 본문, 헤더를 추출하세요.
 *
 * 사용해야 할 메서드:
 *   OkHttpClient, Request.Builder, RequestBody.create, FormBody.Builder,
 *   Response.code/body/header, try-with-resources
 *
 * 더미 서버: MockWebServer
 */
@DisplayName("연습 01: OkHttp 기초 요청")
class Ex01_OkHttpBasicTest {

    private MockWebServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    // ── 문제 1 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 URL로 GET 요청을 보내고 응답 본문 문자열을 반환하세요.
     * 힌트: new Request.Builder().url(url).get().build()
     *       try-with-resources로 Response를 닫고 body().string() 호출
     */
    String getBody(String url) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 1: GET 본문 가져오기")
    void test_getBody() throws IOException {
        server.enqueue(new MockResponse().setBody("hello"));
        String body = getBody(server.url("/").toString());
        assertThat(body).isEqualTo("hello");
    }

    // ── 문제 2 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 URL로 GET 요청을 보내고 HTTP 상태 코드를 반환하세요.
     * 힌트: response.code()
     */
    int getStatusCode(String url) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 2: 상태 코드 추출")
    void test_getStatusCode() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(404).setBody("not found"));
        assertThat(getStatusCode(server.url("/missing").toString())).isEqualTo(404);
    }

    // ── 문제 3 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 URL로 GET 요청을 보내고 응답의 특정 헤더 값을 반환하세요.
     * 힌트: response.header(headerName)
     */
    String getHeader(String url, String headerName) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 3: 응답 헤더 추출")
    void test_getHeader() throws IOException {
        server.enqueue(new MockResponse()
                .setHeader("X-Trace-Id", "trace-001")
                .setBody("ok"));
        assertThat(getHeader(server.url("/").toString(), "X-Trace-Id")).isEqualTo("trace-001");
    }

    // ── 문제 4 ────────────────────────────────────────────────────────────────
    /**
     * 주어진 URL로 Form 데이터를 POST 전송하고 상태 코드를 반환하세요.
     * 힌트: new FormBody.Builder().add(key, value)... .build()
     *       Request.Builder().post(formBody)
     */
    int postForm(String url, String key, String value) throws IOException {
        // TODO: 구현하세요
        throw new UnsupportedOperationException("구현 필요");
    }

    @Disabled("구현 필요")
    @Test
    @DisplayName("문제 4: Form POST 전송")
    void test_postForm() throws IOException, InterruptedException {
        server.enqueue(new MockResponse().setResponseCode(201));
        int code = postForm(server.url("/submit").toString(), "name", "daniel");
        assertThat(code).isEqualTo(201);

        var recorded = server.takeRequest();
        assertThat(recorded.getMethod()).isEqualTo("POST");
        assertThat(recorded.getBody().readUtf8()).isEqualTo("name=daniel");
    }
}
