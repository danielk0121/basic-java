package dev.danielk.basicjava.http;

import dev.danielk.basicjava.http.domain.User;
import dev.danielk.basicjava.http.domain.WishProduct;
import dev.danielk.basicjava.http.dto.UserRequest;
import dev.danielk.basicjava.http.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 학습용 User REST 컨트롤러.
 *
 * 응답 JSON은 root + 필드 + 배열을 가진 다단계 구조:
 * { id, name, email, joinedAt, wishProducts: [ { product: { id, name, price } } ] }
 *
 * User 도메인은 wishProducts를 갖지 않는다.
 * 사용자별 찜 목록은 별도 저장소(wishStore)에서 관리하고, 응답 시 UserResponse가 결합한다.
 *
 * CUD 정책: name / email만 변경. joinedAt / wishProducts는 서버가 관리한다.
 * 신규 사용자에게는 동일한 샘플 wishProducts를 부여한다.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final Map<Long, List<WishProduct>> wishStore = new ConcurrentHashMap<>();
    private final AtomicLong userIdGen = new AtomicLong();

    @GetMapping
    public List<UserResponse> list() {
        return store.values().stream()
                .map(u -> UserResponse.from(u, wishlistOf(u.id())))
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        User user = requireUser(id);
        return UserResponse.from(user, wishlistOf(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        long newId = userIdGen.incrementAndGet();
        User created = new User(newId, request.name(), request.email(), LocalDateTime.now());
        store.put(newId, created);
        wishStore.put(newId, SampleData.wishProducts());
        return ResponseEntity
                .created(URI.create("/users/" + newId))
                .body(UserResponse.from(created, wishlistOf(newId)));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {
        User existing = requireUser(id);
        // CUD는 단일 정보(name/email)만 변경. joinedAt / wishProducts는 그대로 유지.
        User updated = existing.withProfile(request.name(), request.email());
        store.put(id, updated);
        return UserResponse.from(updated, wishlistOf(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (store.remove(id) == null) {
            throw new UserNotFoundException(id);
        }
        wishStore.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public Page<UserResponse> page(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        List<User> all = new ArrayList<>(store.values());
        int from = Math.min(page * size, all.size());
        int to = Math.min(from + size, all.size());
        List<UserResponse> slice = all.subList(from, to).stream()
                .map(u -> UserResponse.from(u, wishlistOf(u.id())))
                .toList();
        return new Page<>(slice, page, all.size());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(UserNotFoundException e) {
        return ResponseEntity.status(404).body(new ApiError(404, e.getMessage()));
    }

    private User requireUser(Long id) {
        User user = store.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    private List<WishProduct> wishlistOf(Long userId) {
        return wishStore.getOrDefault(userId, List.of());
    }

    public record Page<T>(List<T> data, int page, int total) {
    }
}
