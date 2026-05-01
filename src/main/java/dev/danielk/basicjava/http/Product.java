package dev.danielk.basicjava.http;

/**
 * 도메인 record — 상품.
 */
public record Product(Long id, String name, long price) {
}
