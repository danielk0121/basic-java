package dev.danielk.basicjava.http.dto;

import dev.danielk.basicjava.http.User;

/**
 * 클라이언트가 사용자 생성/수정 요청 시 사용.
 * id / joinedAt / wishlist는 서버가 채우므로 클라이언트는 보내지 않는다.
 */
public record UserRequest(String name, String email) {

    /**
     * 신규 생성용 — id/joinedAt/wishlist는 컨트롤러에서 채운다.
     */
    public User toDomain() {
        return new User(null, name, email, null, java.util.List.of());
    }
}
