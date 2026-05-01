package dev.danielk.basicjava.http;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 학습용 User REST 컨트롤러.
 * Service / Repository 레이어 없이 컨트롤러에서 인메모리 저장소를 직접 다룬다.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong();

    @GetMapping
    public List<User> list() {
        return new ArrayList<>(store.values());
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        User user = store.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User request) {
        long newId = idGen.incrementAndGet();
        User created = new User(newId, request.name(), request.email());
        store.put(newId, created);
        return ResponseEntity
                .created(URI.create("/users/" + newId))
                .body(created);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User request) {
        if (!store.containsKey(id)) {
            throw new UserNotFoundException(id);
        }
        User updated = new User(id, request.name(), request.email());
        store.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (store.remove(id) == null) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public Page<User> page(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        List<User> all = new ArrayList<>(store.values());
        int from = Math.min(page * size, all.size());
        int to = Math.min(from + size, all.size());
        List<User> slice = all.subList(from, to);
        return new Page<>(slice, page, all.size());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(UserNotFoundException e) {
        return ResponseEntity.status(404).body(new ApiError(404, e.getMessage()));
    }

    public record Page<T>(List<T> data, int page, int total) {
    }
}
