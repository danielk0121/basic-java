package dev.danielk.basicjava.http.domain;

import java.time.LocalDateTime;

/**
 * 도메인 record — 사용자.
 * 사용자 본연의 정보(id/name/email/joinedAt)만 가진다.
 * 찜 목록은 응답 단계에서 결합된다 (UserResponse 참고).
 */
public record User(Long id, String name, String email, LocalDateTime joinedAt) {

    public User withId(Long newId) {
        return new User(newId, this.name, this.email, this.joinedAt);
    }

    public User withProfile(String newName, String newEmail) {
        return new User(this.id, newName, newEmail, this.joinedAt);
    }
}
