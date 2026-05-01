package dev.danielk.basicjava.http.dto;

import dev.danielk.basicjava.http.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDateTime joinedAt,
        List<WishProductResponse> wishProducts
) {
    public static UserResponse from(User user) {
        List<WishProductResponse> wishProducts = user.wishProducts() == null
                ? List.of()
                : user.wishProducts().stream().map(WishProductResponse::from).toList();
        return new UserResponse(user.id(), user.name(), user.email(), user.joinedAt(), wishProducts);
    }
}
