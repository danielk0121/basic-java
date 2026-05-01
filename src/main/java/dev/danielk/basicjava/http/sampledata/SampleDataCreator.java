package dev.danielk.basicjava.http.sampledata;

import dev.danielk.basicjava.http.domain.User;
import jakarta.annotation.PostConstruct;

/**
 * 애플리케이션 시작 시 SampleDataFactory의 데이터를 두 repository에 시드한다.
 * 빈 등록과 활성화 조건은 SampleDataConfiguration이 결정한다.
 */
public class SampleDataCreator {

    private final UserRepository userRepository;
    private final WishProductRepository wishProductRepository;

    public SampleDataCreator(UserRepository userRepository, WishProductRepository wishProductRepository) {
        this.userRepository = userRepository;
        this.wishProductRepository = wishProductRepository;
    }

    @PostConstruct
    public void seed() {
        for (User user : SampleDataFactory.users()) {
            userRepository.save(user);
            wishProductRepository.saveAll(user.id(), SampleDataFactory.wishProducts());
        }
    }
}
