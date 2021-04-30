package com.tinyurl.configuration;

import com.tinyurl.bean.URLValidator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ShortenUrlConfigurationTest {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public URLValidator getURLValidator(){
        return new URLValidator();
    }

}
