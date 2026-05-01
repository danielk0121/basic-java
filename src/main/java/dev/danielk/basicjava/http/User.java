package dev.danielk.basicjava.http;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 도메인 record — 사용자.
 * id / joinedAt / wishlist는 서버가 채우며, 컨트롤러 CUD는 name/email만 변경한다.
 */
public record User(Long id, String name, String email, LocalDateTime joinedAt, List<Wishlist> wishlist) {

    public User withId(Long newId) {
        return new User(newId, this.name, this.email, this.joinedAt, this.wishlist);
    }

    public User withProfile(String newName, String newEmail) {
        return new User(this.id, newName, newEmail, this.joinedAt, this.wishlist);
    }
}
