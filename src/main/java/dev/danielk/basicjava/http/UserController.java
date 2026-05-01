package dev.danielk.basicjava.http;

import dev.danielk.basicjava.http.domain.User;
import dev.danielk.basicjava.http.dto.UserRequest;
import dev.danielk.basicjava.http.dto.UserResponse;
import dev.danielk.basicjava.http.sampledata.SampleDataFactory;
import dev.danielk.basicjava.http.sampledata.UserQuery;
import dev.danielk.basicjava.http.sampledata.UserRepository;
import dev.danielk.basicjava.http.sampledata.UserWithWishProducts;
import dev.danielk.basicjava.http.sampledata.WishProductRepository;
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
import java.util.List;

/**
 * 학습용 User REST 컨트롤러.
 *
 * 응답 JSON은 root + 필드 + 배열을 가진 다단계 구조:
 * { id, name, email, joinedAt, wishProducts: [ { product: { id, name, price } } ] }
 *
 * CUD는 단일 도메인 단위 repository를 직접 사용한다.
 * READ는 복합 도메인 응답을 만드는 UserQuery를 사용한다.
 *
 * CUD 정책: name / email만 변경. joinedAt / wishProducts는 서버가 관리한다.
 * 신규 사용자에게는 동일한 샘플 wishProducts를 부여한다.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final WishProductRepository wishProductRepository;
    private final UserQuery userQuery;

    public UserController(UserRepository userRepository,
                          WishProductRepository wishProductRepository,
                          UserQuery userQuery) {
        this.userRepository = userRepository;
        this.wishProductRepository = wishProductRepository;
        this.userQuery = userQuery;
    }

    // GET /users (전체 목록)은 현업에서 사실상 삭제되어야 하는 형태이므로 제공하지 않는다.

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        UserWithWishProducts joined = userQuery.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.from(joined.user(), joined.wishProducts());
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        long newId = userRepository.nextId();
        User created = new User(newId, request.name(), request.email(), LocalDateTime.now());
        userRepository.save(created);
        wishProductRepository.saveAll(newId, SampleDataFactory.wishProducts());

        UserWithWishProducts joined = userQuery.findById(newId)
                .orElseThrow(() -> new IllegalStateException("created user not found: " + newId));
        return ResponseEntity
                .created(URI.create("/users/" + newId))
                .body(UserResponse.from(joined.user(), joined.wishProducts()));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {
        User existing = userRepository.findRawById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        // CUD는 단일 정보(name/email)만 변경.
        userRepository.update(existing.withProfile(request.name(), request.email()));

        UserWithWishProducts joined = userQuery.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.from(joined.user(), joined.wishProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.delete(id)) {
            throw new UserNotFoundException(id);
        }
        wishProductRepository.deleteByUserId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public Page<UserResponse> page(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        List<UserResponse> slice = userQuery.findPage(page, size).stream()
                .map(j -> UserResponse.from(j.user(), j.wishProducts()))
                .toList();
        return new Page<>(slice, page, userQuery.count());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(UserNotFoundException e) {
        return ResponseEntity.status(404).body(new ApiError(404, e.getMessage()));
    }

    public record Page<T>(List<T> data, int page, int total) {
    }
}
