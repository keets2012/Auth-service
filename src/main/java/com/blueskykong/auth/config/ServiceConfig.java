package com.blueskykong.auth.config;

import com.blueskykong.auth.exception.CustomWebResponseExceptionTranslator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
/**
 * Created by keets on 2016/12/5.
 */
@Configuration
@MapperScan("com.blueskykong.auth.dao.mapper")
public class ServiceConfig {

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new CustomWebResponseExceptionTranslator();
    }

}
