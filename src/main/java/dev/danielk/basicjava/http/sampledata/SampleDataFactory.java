package dev.danielk.basicjava.http.sampledata;

import dev.danielk.basicjava.http.domain.Product;
import dev.danielk.basicjava.http.domain.User;
import dev.danielk.basicjava.http.domain.WishProduct;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 학습용 컨트롤러에서 사용할 샘플 데이터.
 * DB 테이블에 들어있을 만한 값들(id 포함)을 하드코딩으로 보관한다.
 */
public final class SampleDataFactory {

    private SampleDataFactory() {}

    private static final List<Product> SAMPLE_PRODUCTS = List.of(
            new Product(1L, "키보드", 30000),
            new Product(2L, "마우스", 15000),
            new Product(3L, "모니터", 300000)
    );

    private static final List<WishProduct> SAMPLE_WISH_PRODUCTS = List.of(
            new WishProduct(SAMPLE_PRODUCTS.get(0)),
            new WishProduct(SAMPLE_PRODUCTS.get(2))
    );

    private static final LocalDateTime SAMPLE_JOINED_AT = LocalDateTime.of(2026, 1, 1, 9, 0, 0);

    private static final List<User> SAMPLE_USERS = List.of(
            new User(1L, "alice", "alice@example.com", SAMPLE_JOINED_AT),
            new User(2L, "bob", "bob@example.com", SAMPLE_JOINED_AT),
            new User(3L, "daniel", "daniel@example.com", SAMPLE_JOINED_AT)
    );

    public static List<Product> products() {
        return SAMPLE_PRODUCTS;
    }

    public static List<WishProduct> wishProducts() {
        return SAMPLE_WISH_PRODUCTS;
    }

    public static List<User> users() {
        return SAMPLE_USERS;
    }
}
