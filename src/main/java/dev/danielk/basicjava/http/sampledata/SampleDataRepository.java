package dev.danielk.basicjava.http.sampledata;

import dev.danielk.basicjava.http.domain.User;
import dev.danielk.basicjava.http.domain.WishProduct;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 인메모리 저장소 — 학습 목적의 단순한 Map 기반 구현.
 * 컨트롤러는 이 클래스만 의존하고 저장 자료구조에는 직접 접근하지 않는다.
 *
 * 빈 등록은 SampleDataConfiguration이 담당한다.
 * userIdGen 시작값은 생성자 인자로 주입받는다.
 */
public class SampleDataRepository {

    private final Map<Long, User> userStore = new ConcurrentHashMap<>();
    private final Map<Long, List<WishProduct>> wishStore = new ConcurrentHashMap<>();
    private final AtomicLong userIdGen;

    public SampleDataRepository(long userIdStart) {
        this.userIdGen = new AtomicLong(userIdStart);
    }

    public long nextUserId() {
        return userIdGen.incrementAndGet();
    }

    public void save(User user, List<WishProduct> wishProducts) {
        userStore.put(user.id(), user);
        wishStore.put(user.id(), wishProducts == null ? List.of() : wishProducts);
    }

    public void update(User user) {
        userStore.put(user.id(), user);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userStore.get(id));
    }

    /**
     * 페이지 단위로 사용자를 조회한다. id 오름차순.
     * 운영 DB에서는 SELECT ... ORDER BY id LIMIT ? OFFSET ? 에 해당.
     */
    public List<User> findPage(int page, int size) {
        return userStore.values().stream()
                .sorted((a, b) -> Long.compare(a.id(), b.id()))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    /** 전체 user 개수 (페이지 응답의 total). */
    public int count() {
        return userStore.size();
    }

    public List<WishProduct> findWishProducts(Long userId) {
        return wishStore.getOrDefault(userId, List.of());
    }

    public boolean delete(Long id) {
        boolean removed = userStore.remove(id) != null;
        wishStore.remove(id);
        return removed;
    }

    public boolean exists(Long id) {
        return userStore.containsKey(id);
    }
}
