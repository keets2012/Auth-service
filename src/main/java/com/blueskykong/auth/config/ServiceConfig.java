package com.blueskykong.auth.config;

import com.blueskykong.auth.exception.CustomWebResponseExceptionTranslator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;


@Configuration
public class ServiceConfig {

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new CustomWebResponseExceptionTranslator();
    }

}
