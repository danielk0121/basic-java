package dev.danielk.basicjava.http;

import dev.danielk.basicjava.http.domain.Product;
import dev.danielk.basicjava.http.domain.WishProduct;

import java.util.List;

/**
 * 학습용 컨트롤러에서 사용할 샘플 데이터.
 * 컨트롤러 본체(요청 처리 로직)와 분리하여 데이터 생성 책임을 격리한다.
 */
public final class SampleData {

    private SampleData() {}

    private static final List<Product> SAMPLE_PRODUCTS = List.of(
            new Product(1L, "키보드", 30000),
            new Product(2L, "마우스", 15000),
            new Product(3L, "모니터", 300000)
    );

    private static final List<WishProduct> SAMPLE_WISH_PRODUCTS = List.of(
            new WishProduct(SAMPLE_PRODUCTS.get(0)),
            new WishProduct(SAMPLE_PRODUCTS.get(2))
    );

    public static List<Product> products() {
        return SAMPLE_PRODUCTS;
    }

    public static List<WishProduct> wishProducts() {
        return SAMPLE_WISH_PRODUCTS;
    }
}
