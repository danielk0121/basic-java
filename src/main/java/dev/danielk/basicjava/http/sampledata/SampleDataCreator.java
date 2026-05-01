package dev.danielk.basicjava.http.sampledata;

import dev.danielk.basicjava.http.domain.User;
import jakarta.annotation.PostConstruct;

/**
 * 애플리케이션 시작 시 SampleDataFactory의 데이터를 SampleDataRepository에 시드한다.
 *
 * 빈 등록과 활성화 조건은 SampleDataConfiguration이 결정한다.
 */
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
