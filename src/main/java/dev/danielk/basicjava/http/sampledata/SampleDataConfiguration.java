package dev.danielk.basicjava.http.sampledata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 샘플 데이터 관련 빈 구성.
 *
 * - SampleDataRepository: 항상 등록. userIdGen 시작값을 시드 활성화 여부에 따라 결정.
 * - SampleDataCreator: app.sample-data.enabled=true 일 때만 등록.
 *
 * 활성화 여부 판단을 한 곳(이 Configuration)에 모아둔다.
 */
@Configuration
public class SampleDataConfiguration {

    @Bean
    public SampleDataRepository sampleDataRepository(
            @Value("${app.sample-data.enabled:true}") boolean sampleDataEnabled
    ) {
        long start = sampleDataEnabled ? SampleDataFactory.users().size() : 0L;
        return new SampleDataRepository(start);
    }

    @Bean
    @ConditionalOnProperty(name = "app.sample-data.enabled", havingValue = "true", matchIfMissing = true)
    public SampleDataCreator sampleDataCreator(SampleDataRepository repository) {
        return new SampleDataCreator(repository);
    }
}
