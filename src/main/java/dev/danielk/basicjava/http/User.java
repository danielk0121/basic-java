package dev.danielk.basicjava.http;

/**
 * 학습용 도메인 — REST 컨트롤러가 직접 사용한다.
 * record로 단순하게 구성. 생성 요청 시 id는 null로 들어옴.
 */
public record User(Long id, String name, String email) {

    public User withId(Long newId) {
        return new User(newId, this.name, this.email);
    }
}
