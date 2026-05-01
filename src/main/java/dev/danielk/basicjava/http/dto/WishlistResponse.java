package dev.danielk.basicjava.http.dto;

import dev.danielk.basicjava.http.Wishlist;

public record WishlistResponse(ProductResponse product) {
    public static WishlistResponse from(Wishlist wishlist) {
        return new WishlistResponse(ProductResponse.from(wishlist.product()));
    }
}
