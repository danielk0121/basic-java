package dev.danielk.basicjava.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo 31-1: OkHttp 클라이언트 초기화 학습 테스트
 *
 * 학습 목적이므로 매번 새 인스턴스를 생성한다.
 * 운영 환경에서는 단일 OkHttpClient 인스턴스 공유가 권장됨.
 */
@DisplayName("OkHttp 클라이언트 초기화")
class OkHttpInitTest {

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
    @DisplayName("기본 생성자로 OkHttpClient 인스턴스 생성")
    void 기본생성자로_인스턴스_생성() throws IOException {
        // 학습 예제: 매번 새 인스턴스 생성
        OkHttpClient client = new OkHttpClient();

        server.enqueue(new MockResponse().setBody("hello"));

        Request request = new Request.Builder()
                .url(server.url("/"))
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
            assertEquals("hello", response.body().string());
        }
    }

    @Test
    @DisplayName("Builder로 타임아웃 설정한 인스턴스 생성")
    void Builder로_타임아웃_설정() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .callTimeout(10, TimeUnit.SECONDS)
                .build();

        server.enqueue(new MockResponse().setBody("ok"));

        Request request = new Request.Builder()
                .url(server.url("/"))
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertEquals("ok", response.body().string());
        }
    }

    @Test
    @DisplayName("응답은 try-with-resources로 닫아야 소켓 누수가 발생하지 않는다")
    void 응답_close_누락방지() throws IOException {
        OkHttpClient client = new OkHttpClient();
        server.enqueue(new MockResponse().setBody("close-me"));

        Request request = new Request.Builder().url(server.url("/")).build();

        // try-with-resources 패턴
        try (Response response = client.newCall(request).execute()) {
            assertNotNull(response.body());
            // body().string()은 한 번만 호출 가능
            String body = response.body().string();
            assertEquals("close-me", body);
        }
    }

    @Test
    @DisplayName("매번 새 인스턴스를 생성하면 풀이 매번 새로 만들어진다 (학습 확인용)")
    void 매번_새_인스턴스() throws IOException {
        server.enqueue(new MockResponse().setBody("first"));
        server.enqueue(new MockResponse().setBody("second"));

        // 인스턴스 1
        OkHttpClient c1 = new OkHttpClient();
        Request r1 = new Request.Builder().url(server.url("/")).build();
        try (Response res = c1.newCall(r1).execute()) {
            assertEquals("first", res.body().string());
        }

        // 인스턴스 2 — 별도 ConnectionPool, Dispatcher 보유
        OkHttpClient c2 = new OkHttpClient();
        Request r2 = new Request.Builder().url(server.url("/")).build();
        try (Response res = c2.newCall(r2).execute()) {
            assertEquals("second", res.body().string());
        }

        // 두 인스턴스의 ConnectionPool은 서로 다른 객체
        assertNotSame(c1.connectionPool(), c2.connectionPool());
    }
}
