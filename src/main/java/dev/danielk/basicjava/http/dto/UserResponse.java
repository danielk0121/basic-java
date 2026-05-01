package dev.danielk.basicjava.http.dto;

import dev.danielk.basicjava.http.domain.User;
import dev.danielk.basicjava.http.domain.WishProduct;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 응답 DTO — User 도메인과 별도 관리되는 wishProducts를 결합한다.
 * 도메인은 wishProducts를 알지 못하고, 응답 단계에서만 합쳐진다.
 */
public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDateTime joinedAt,
        List<WishProductResponse> wishProducts
) {
    public static UserResponse from(User user, List<WishProduct> wishProducts) {
        List<WishProductResponse> wishlist = wishProducts == null
                ? List.of()
                : wishProducts.stream().map(WishProductResponse::from).toList();
        return new UserResponse(user.id(), user.name(), user.email(), user.joinedAt(), wishlist);
    }
}
