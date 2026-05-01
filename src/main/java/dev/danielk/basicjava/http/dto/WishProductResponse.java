package dev.danielk.basicjava.http.dto;

import dev.danielk.basicjava.http.domain.WishProduct;

public record WishProductResponse(ProductResponse product) {
    public static WishProductResponse from(WishProduct wishProduct) {
        return new WishProductResponse(ProductResponse.from(wishProduct.product()));
    }
}
