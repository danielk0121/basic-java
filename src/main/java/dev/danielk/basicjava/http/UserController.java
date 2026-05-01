package dev.danielk.basicjava.http;

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
 * { id, name, email, joinedAt, wishlist: [ { product: { id, name, price } } ] }
 *
 * CUD 정책: name / email만 변경. joinedAt / wishlist는 서버가 관리한다.
 * 신규 사용자에게는 동일한 샘플 wishlist를 부여한다 (하드코딩).
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong userIdGen = new AtomicLong();

    // 샘플 상품 마스터 (하드코딩)
    private static final List<Product> SAMPLE_PRODUCTS = List.of(
            new Product(1L, "키보드", 30000),
            new Product(2L, "마우스", 15000),
            new Product(3L, "모니터", 300000)
    );

    // 신규 가입 시 부여할 샘플 찜 목록 (하드코딩)
    private static final List<Wishlist> SAMPLE_WISHLIST = List.of(
            new Wishlist(SAMPLE_PRODUCTS.get(0)),
            new Wishlist(SAMPLE_PRODUCTS.get(2))
    );

    @GetMapping
    public List<UserResponse> list() {
        return store.values().stream().map(UserResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        return UserResponse.from(requireUser(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        long newId = userIdGen.incrementAndGet();
        User created = new User(
                newId,
                request.name(),
                request.email(),
                LocalDateTime.now(),
                SAMPLE_WISHLIST
        );
        store.put(newId, created);
        return ResponseEntity
                .created(URI.create("/users/" + newId))
                .body(UserResponse.from(created));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {
        User existing = requireUser(id);
        // CUD는 단일 정보(name/email)만 변경. joinedAt / wishlist는 그대로 유지.
        User updated = existing.withProfile(request.name(), request.email());
        store.put(id, updated);
        return UserResponse.from(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (store.remove(id) == null) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public Page<UserResponse> page(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        List<User> all = new ArrayList<>(store.values());
        int from = Math.min(page * size, all.size());
        int to = Math.min(from + size, all.size());
        List<UserResponse> slice = all.subList(from, to).stream()
                .map(UserResponse::from)
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

    public record Page<T>(List<T> data, int page, int total) {
    }
}
