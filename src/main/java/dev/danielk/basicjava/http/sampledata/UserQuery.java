package dev.danielk.basicjava.http.sampledata;

import java.util.List;
import java.util.Optional;

/**
 * 조회 조합 (CQRS의 read 측).
 * 단일 도메인 repository를 묶어 복합 응답(UserWithWishProducts)을 만든다.
 * 운영 DB로 치면 user JOIN wish_product에 해당.
 */
public class UserQuery {

    private final UserRepository userRepository;
    private final WishProductRepository wishProductRepository;

    public UserQuery(UserRepository userRepository, WishProductRepository wishProductRepository) {
        this.userRepository = userRepository;
        this.wishProductRepository = wishProductRepository;
    }

    public Optional<UserWithWishProducts> findById(Long id) {
        return userRepository.findRawById(id)
                .map(user -> new UserWithWishProducts(user, wishProductRepository.findByUserId(user.id())));
    }

    public List<UserWithWishProducts> findPage(int page, int size) {
        return userRepository.findPageRaw(page, size).stream()
                .map(user -> new UserWithWishProducts(user, wishProductRepository.findByUserId(user.id())))
                .toList();
    }

    public int count() {
        return userRepository.count();
    }
}
