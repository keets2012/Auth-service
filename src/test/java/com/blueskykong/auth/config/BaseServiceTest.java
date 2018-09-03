package com.blueskykong.auth.config;

import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = {CustomContextInitializer.class}, classes = {TestJdbcConfig
        .class})
public abstract class BaseServiceTest implements InitializingBean, DisposableBean {

    public BaseServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.destroy();
    }

    @Override
    public void destroy() throws Exception {
        SecurityContextHolder.clearContext();
    }
}
