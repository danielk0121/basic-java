package dev.danielk.basicjava.http.dto;

import dev.danielk.basicjava.http.domain.Product;

public record ProductResponse(Long id, String name, long price) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.id(), product.name(), product.price());
    }
}
