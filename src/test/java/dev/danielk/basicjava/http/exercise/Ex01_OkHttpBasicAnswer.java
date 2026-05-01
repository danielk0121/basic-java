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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 연습 01 답안: OkHttp 기초 요청
 *
 * GET / POST 요청을 만들고 상태 코드, 본문, 헤더를 추출하세요.
 *
 * 사용해야 할 메서드:
 *   OkHttpClient, Request.Builder, RequestBody.create, FormBody.Builder,
 *   Response.code/body/header, try-with-resources
 */
@DisplayName("연습 01 답안: OkHttp 기초 요청")
class Ex01_OkHttpBasicAnswer {

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

    // ── 문제 1 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] GET 요청 본문 추출.
     * [풀이] Request.Builder.get()으로 GET 메서드 명시. body().string()은 한 번만 호출 가능.
     */
    String getBody(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Test
    @DisplayName("문제 1: GET 본문 가져오기")
    void test_getBody() throws IOException {
        server.enqueue(new MockResponse().setBody("hello"));
        String body = getBody(server.url("/").toString());
        assertThat(body).isEqualTo("hello");
    }

    // ── 문제 2 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] HTTP 상태 코드 추출.
     * [풀이] response.code()로 정수 상태 코드 직접 조회.
     */
    int getStatusCode(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

    @Test
    @DisplayName("문제 2: 상태 코드 추출")
    void test_getStatusCode() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(404).setBody("not found"));
        assertThat(getStatusCode(server.url("/missing").toString())).isEqualTo(404);
    }

    // ── 문제 3 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] 특정 응답 헤더 값 추출.
     * [풀이] response.header(name) — 해당 헤더가 없으면 null 반환.
     */
    String getHeader(String url, String headerName) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            return response.header(headerName);
        }
    }

    @Test
    @DisplayName("문제 3: 응답 헤더 추출")
    void test_getHeader() throws IOException {
        server.enqueue(new MockResponse()
                .setHeader("X-Trace-Id", "trace-001")
                .setBody("ok"));
        assertThat(getHeader(server.url("/").toString(), "X-Trace-Id")).isEqualTo("trace-001");
    }

    // ── 문제 4 답안 ───────────────────────────────────────────────────────────
    /**
     * [문제] Form 데이터 POST 전송.
     * [풀이] FormBody.Builder로 application/x-www-form-urlencoded 본문 생성 후 .post() 전달.
     */
    int postForm(String url, String key, String value) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add(key, value).build();
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

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
