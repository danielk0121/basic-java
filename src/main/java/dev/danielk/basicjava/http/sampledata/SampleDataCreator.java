package dev.danielk.basicjava.http.sampledata;

import dev.danielk.basicjava.http.domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션 시작 시 SampleDataFactory의 데이터를 SampleDataRepository에 시드한다.
 *
 * 활성화: application 프로퍼티 `app.sample-data.enabled=true` (기본 활성)
 * 비활성화 예: 통합 테스트에서 `@TestPropertySource(properties = "app.sample-data.enabled=false")`
 *
 * userIdGen 시작값은 SampleDataConfiguration이 결정하므로 여기서는 신경쓰지 않는다.
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
        for (User user : SampleDataFactory.users()) {
            repository.save(user, SampleDataFactory.wishProducts());
        }
    }
}
