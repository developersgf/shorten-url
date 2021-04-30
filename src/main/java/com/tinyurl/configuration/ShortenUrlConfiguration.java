package com.tinyurl.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ShortenUrlConfiguration {

    @Value("${hash.expiry.day}")
    private int day;

}
