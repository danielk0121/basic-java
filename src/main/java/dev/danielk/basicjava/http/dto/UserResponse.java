package dev.danielk.basicjava.http.dto;

import dev.danielk.basicjava.http.User;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDateTime joinedAt,
        List<WishlistResponse> wishlist
) {
    public static UserResponse from(User user) {
        List<WishlistResponse> wishlistResponses = user.wishlist() == null
                ? List.of()
                : user.wishlist().stream().map(WishlistResponse::from).toList();
        return new UserResponse(user.id(), user.name(), user.email(), user.joinedAt(), wishlistResponses);
    }
}
