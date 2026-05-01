package dev.danielk.basicjava.http.query;

import dev.danielk.basicjava.http.domain.User;
import dev.danielk.basicjava.http.domain.WishProduct;

import java.util.List;

/**
 * Repository 조회 결과 — User와 WishProduct를 조인해 함께 반환한다.
 * 운영 DB로 치면 JOIN 결과 row를 매핑한 result 객체.
 */
public record UserWithWishProducts(User user, List<WishProduct> wishProducts) {
}
