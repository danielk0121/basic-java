package dev.danielk.basicjava.http.sampledata;

import dev.danielk.basicjava.http.query.UserQuery;
import dev.danielk.basicjava.http.repository.UserRepository;
import dev.danielk.basicjava.http.repository.WishProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 샘플 데이터 관련 빈 구성.
 *
 * - UserRepository / WishProductRepository / UserQuery: 항상 등록.
 * - SampleDataCreator: app.sample-data.enabled=true 일 때만 등록.
 * - UserRepository의 id 시퀀스 시작값은 시드 활성화 여부에 따라 결정한다.
 *
 * 활성화 여부 판단을 한 곳(이 Configuration)에 모아둔다.
 */
@Configuration
public class SampleDataConfiguration {

    @Bean
    public UserRepository userRepository(
            @Value("${app.sample-data.enabled:true}") boolean sampleDataEnabled
    ) {
        long start = sampleDataEnabled ? SampleDataFactory.users().size() : 0L;
        return new UserRepository(start);
    }

    @Bean
    public WishProductRepository wishProductRepository() {
        return new WishProductRepository();
    }

    @Bean
    public UserQuery userQuery(UserRepository userRepository, WishProductRepository wishProductRepository) {
        return new UserQuery(userRepository, wishProductRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "app.sample-data.enabled", havingValue = "true", matchIfMissing = true)
    public SampleDataCreator sampleDataCreator(UserRepository userRepository,
                                               WishProductRepository wishProductRepository) {
        return new SampleDataCreator(userRepository, wishProductRepository);
    }
}
