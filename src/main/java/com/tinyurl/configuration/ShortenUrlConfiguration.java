package com.tinyurl.configuration;

import com.tinyurl.bean.URLValidator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Getter
public class ShortenUrlConfiguration {

    @Value("${hash.expiry.day: 365}")
    private int day;

    @Bean
    // @Scope("singleton")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public URLValidator getURLValidator() {
        return new URLValidator();
    }

}
