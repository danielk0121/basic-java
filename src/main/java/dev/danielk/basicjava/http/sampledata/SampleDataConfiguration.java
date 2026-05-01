package dev.danielk.basicjava.http.sampledata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 샘플 데이터 관련 빈 구성.
 * SampleDataRepository의 userIdGen 시작값을 시드 활성화 여부에 따라 결정한다.
 *
 * - app.sample-data.enabled=true  → 시드 user 개수만큼 미리 끌어올린 시작값
 * - app.sample-data.enabled=false → 0부터 시작 (테스트 등 깨끗한 저장소)
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
}
