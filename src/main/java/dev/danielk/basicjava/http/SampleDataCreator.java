package dev.danielk.basicjava.http;

import dev.danielk.basicjava.http.domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션 시작 시 SampleDataFactory의 데이터를 SampleDataRepository에 시드한다.
 *
 * 활성화: application 프로퍼티 `app.sample-data.enabled=true` (기본 활성)
 * 비활성화 예: 통합 테스트에서 `@TestPropertySource(properties = "app.sample-data.enabled=false")`
 */
@Component
@ConditionalOnProperty(name = "app.sample-data.enabled", havingValue = "true", matchIfMissing = true)
public class SampleDataCreator {

    private final SampleDataRepository repository;

    public SampleDataCreator(SampleDataRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void seed() {
        long maxId = 0;
        for (User user : SampleDataFactory.users()) {
            repository.save(user, SampleDataFactory.wishProducts());
            maxId = Math.max(maxId, user.id());
        }
        // 시드된 id와 충돌하지 않도록 id 시퀀스를 끌어올린다.
        repository.advanceUserIdAtLeast(maxId);
    }
}
