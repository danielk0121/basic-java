package dev.danielk.basicjava.http.sampledata;

import dev.danielk.basicjava.http.domain.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * user 테이블 단위 인메모리 저장소.
 * 빈 등록은 SampleDataConfiguration이 담당하며 id 시퀀스 시작값을 생성자로 주입한다.
 */
public class UserRepository {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen;

    public UserRepository(long idStart) {
        this.idGen = new AtomicLong(idStart);
    }

    public long nextId() {
        return idGen.incrementAndGet();
    }

    public void save(User user) {
        store.put(user.id(), user);
    }

    public void update(User user) {
        store.put(user.id(), user);
    }

    public Optional<User> findRawById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    /** 페이지 단위 조회 — id 오름차순. */
    java.util.List<User> findPageRaw(int page, int size) {
        return store.values().stream()
                .sorted((a, b) -> Long.compare(a.id(), b.id()))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    int count() {
        return store.size();
    }
}
