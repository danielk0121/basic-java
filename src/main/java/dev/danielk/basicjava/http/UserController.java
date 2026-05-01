package dev.danielk.basicjava.http;

import dev.danielk.basicjava.http.domain.User;
import dev.danielk.basicjava.http.dto.UserRequest;
import dev.danielk.basicjava.http.dto.UserResponse;
import dev.danielk.basicjava.http.sampledata.SampleDataFactory;
import dev.danielk.basicjava.http.sampledata.SampleDataRepository;
import dev.danielk.basicjava.http.sampledata.UserWithWishProducts;
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
 * 저장 책임은 {@link SampleDataRepository}가, 샘플 데이터 생성은 {@link SampleDataFactory}가 담당한다.
 * 컨트롤러는 요청-응답 변환 + 비즈니스 흐름만 담당한다.
 *
 * CUD 정책: name / email만 변경. joinedAt / wishProducts는 서버가 관리한다.
 * 신규 사용자에게는 동일한 샘플 wishProducts를 부여한다.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final SampleDataRepository repository;

    public UserController(SampleDataRepository repository) {
        this.repository = repository;
    }

    // GET /users (전체 목록)은 현업에서 사실상 삭제되어야 하는 형태이므로 제공하지 않는다.
    // 페이지네이션이 필요하면 GET /users/page 사용.

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        UserWithWishProducts joined = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.from(joined.user(), joined.wishProducts());
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        long newId = repository.nextUserId();
        User created = new User(newId, request.name(), request.email(), LocalDateTime.now());
        List<dev.danielk.basicjava.http.domain.WishProduct> wish = SampleDataFactory.wishProducts();
        repository.save(created, wish);
        return ResponseEntity
                .created(URI.create("/users/" + newId))
                .body(UserResponse.from(created, wish));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {
        UserWithWishProducts existing = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        // CUD는 단일 정보(name/email)만 변경. joinedAt / wishProducts는 그대로 유지.
        User updated = existing.user().withProfile(request.name(), request.email());
        repository.update(updated);
        return UserResponse.from(updated, existing.wishProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.delete(id)) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public Page<UserResponse> page(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        List<UserResponse> slice = repository.findPage(page, size).stream()
                .map(j -> UserResponse.from(j.user(), j.wishProducts()))
                .toList();
        return new Page<>(slice, page, repository.count());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(UserNotFoundException e) {
        return ResponseEntity.status(404).body(new ApiError(404, e.getMessage()));
    }

    public record Page<T>(List<T> data, int page, int total) {
    }
}
