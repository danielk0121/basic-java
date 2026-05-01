package dev.danielk.basicjava.http;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.EventListener;
import okhttp3.Interceptor;
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
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo 32: OkHttp 라이브러리 설정 학습 테스트
 *
 * - 커넥션 풀
 * - keep-alive
 * - 소켓 재사용 (EventListener)
 * - 타임아웃
 * - 인터셉터
 * - 디스패처
 */
@DisplayName("OkHttp 라이브러리 설정")
class OkHttpConfigTest {

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
    @DisplayName("커넥션 풀 설정 — maxIdleConnections, keepAliveDuration")
    void 커넥션풀_설정() throws IOException {
        ConnectionPool pool = new ConnectionPool(
                3,                  // 최대 유휴 커넥션 수
                30, TimeUnit.SECONDS // keep-alive 시간
        );

        OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(pool)
                .build();

        server.enqueue(new MockResponse().setBody("ok"));

        Request request = new Request.Builder().url(server.url("/")).build();
        try (Response response = client.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
        }

        // 풀 통계 확인 — 요청 후 idle 커넥션이 1개 이상 존재해야 함
        assertEquals(1, pool.connectionCount());
    }

    @Test
    @DisplayName("keep-alive로 동일 호스트에 대한 소켓 재사용 — EventListener 관찰")
    void 소켓_재사용_확인() throws IOException {
        AtomicInteger acquiredCount = new AtomicInteger();
        AtomicInteger connectionStartCount = new AtomicInteger();

        EventListener listener = new EventListener() {
            @Override
            public void connectionAcquired(Call call, Connection connection) {
                acquiredCount.incrementAndGet();
            }

            @Override
            public void connectStart(Call call, java.net.InetSocketAddress inetSocketAddress, java.net.Proxy proxy) {
                connectionStartCount.incrementAndGet();
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 60, TimeUnit.SECONDS))
                .eventListener(listener)
                .build();

        server.enqueue(new MockResponse().setBody("a"));
        server.enqueue(new MockResponse().setBody("b"));
        server.enqueue(new MockResponse().setBody("c"));

        for (int i = 0; i < 3; i++) {
            Request request = new Request.Builder().url(server.url("/")).build();
            try (Response response = client.newCall(request).execute()) {
                response.body().string();
            }
        }

        // 3번 요청했지만 실제 connectStart는 1번만 발생 (소켓 재사용)
        assertEquals(3, acquiredCount.get());
        assertEquals(1, connectionStartCount.get(), "동일 호스트 재요청은 풀의 소켓을 재사용해야 한다");
    }

    @Test
    @DisplayName("타임아웃 설정 — connectTimeout / readTimeout")
    void 타임아웃_설정() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .build();

        assertEquals(2000, client.connectTimeoutMillis());
        assertEquals(2000, client.readTimeoutMillis());
        assertEquals(2000, client.writeTimeoutMillis());
    }

    @Test
    @DisplayName("Application Interceptor — 모든 요청에 헤더 자동 추가")
    void 인터셉터_헤더추가() throws IOException, InterruptedException {
        Interceptor authInterceptor = chain -> {
            Request original = chain.request();
            Request withAuth = original.newBuilder()
                    .header("Authorization", "Bearer test-token")
                    .build();
            return chain.proceed(withAuth);
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();

        server.enqueue(new MockResponse().setBody("ok"));

        Request request = new Request.Builder().url(server.url("/")).build();
        try (Response response = client.newCall(request).execute()) {
            assertTrue(response.isSuccessful());
        }

        // 서버가 받은 요청에 헤더가 있는지 확인
        var recorded = server.takeRequest();
        assertEquals("Bearer test-token", recorded.getHeader("Authorization"));
    }

    @Test
    @DisplayName("retryOnConnectionFailure 설정 — 기본값 true")
    void 재시도_옵션() {
        OkHttpClient defaultClient = new OkHttpClient();
        assertTrue(defaultClient.retryOnConnectionFailure());

        OkHttpClient noRetry = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .build();
        assertFalse(noRetry.retryOnConnectionFailure());
    }

    @Test
    @DisplayName("Dispatcher 동시성 제어 — maxRequests / maxRequestsPerHost")
    void 디스패처_동시성() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(64);
        dispatcher.setMaxRequestsPerHost(8);

        OkHttpClient client = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .build();

        assertEquals(64, client.dispatcher().getMaxRequests());
        assertEquals(8, client.dispatcher().getMaxRequestsPerHost());
    }
}
