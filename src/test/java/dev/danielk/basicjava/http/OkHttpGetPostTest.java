package dev.danielk.basicjava.http;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
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
 * todo 33: OkHttp GET / POST 요청 학습 테스트 (MockWebServer 더미 서버 사용)
 */
@DisplayName("OkHttp GET / POST 요청")
class OkHttpGetPostTest {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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

    @Test
    @DisplayName("GET 요청 — 단순 호출")
    void GET_단순호출() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody("hello world"));

        Request request = new Request.Builder()
                .url(server.url("/hello"))
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertEquals("hello world", response.body().string());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("GET", recorded.getMethod());
        assertEquals("/hello", recorded.getPath());
    }

    @Test
    @DisplayName("GET 요청 — 쿼리 파라미터 + 헤더")
    void GET_쿼리파라미터_헤더() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody("ok"));

        HttpUrl url = server.url("/search").newBuilder()
                .addQueryParameter("q", "okhttp")
                .addQueryParameter("page", "1")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Trace-Id", "abc-123")
                .addHeader("Accept", "application/json")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("/search?q=okhttp&page=1", recorded.getPath());
        assertEquals("abc-123", recorded.getHeader("X-Trace-Id"));
        assertEquals("application/json", recorded.getHeader("Accept"));
    }

    @Test
    @DisplayName("POST 요청 — Form 전송")
    void POST_Form전송() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setResponseCode(200).setBody("created"));

        RequestBody body = new FormBody.Builder()
                .add("username", "daniel")
                .add("email", "daniel@example.com")
                .build();

        Request request = new Request.Builder()
                .url(server.url("/users"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("POST", recorded.getMethod());
        assertEquals("application/x-www-form-urlencoded", recorded.getHeader("Content-Type"));
        assertEquals("username=daniel&email=daniel%40example.com", recorded.getBody().readUtf8());
    }

    @Test
    @DisplayName("POST 요청 — JSON 전송")
    void POST_JSON전송() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(201)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":1}"));

        String json = "{\"name\":\"daniel\",\"age\":30}";
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(server.url("/users"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(201, response.code());
            assertEquals("{\"id\":1}", response.body().string());
        }

        RecordedRequest recorded = server.takeRequest();
        assertEquals("POST", recorded.getMethod());
        assertTrue(recorded.getHeader("Content-Type").startsWith("application/json"));
        assertEquals(json, recorded.getBody().readUtf8());
    }

    @Test
    @DisplayName("404 응답 처리 — isSuccessful=false")
    void 응답_404_처리() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody("not found"));

        Request request = new Request.Builder().url(server.url("/missing")).build();

        try (Response response = client.newCall(request).execute()) {
            assertFalse(response.isSuccessful());
            assertEquals(404, response.code());
            assertEquals("not found", response.body().string());
        }
    }
}
