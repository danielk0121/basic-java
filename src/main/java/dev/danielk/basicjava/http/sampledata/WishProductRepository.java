package dev.danielk.basicjava.http.sampledata;

import dev.danielk.basicjava.http.domain.WishProduct;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * wish_product 테이블 단위 인메모리 저장소.
 * userId를 외래키로 가진다.
 */
public class WishProductRepository {

    private final Map<Long, List<WishProduct>> store = new ConcurrentHashMap<>();

    public void saveAll(Long userId, List<WishProduct> wishProducts) {
        store.put(userId, wishProducts == null ? List.of() : wishProducts);
    }

    public List<WishProduct> findByUserId(Long userId) {
        return store.getOrDefault(userId, List.of());
    }

    public void deleteByUserId(Long userId) {
        store.remove(userId);
    }
}
