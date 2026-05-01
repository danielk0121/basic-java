package dev.danielk.basicjava.http.dto;

import dev.danielk.basicjava.http.domain.User;

/**
 * 클라이언트가 사용자 생성/수정 요청 시 사용.
 * id / joinedAt 은 서버가 채우므로 클라이언트는 보내지 않는다.
 */
public record UserRequest(String name, String email) {

    public User toDomain() {
        return new User(null, name, email, null);
    }
}
