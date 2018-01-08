package com.blueskykong.auth.security;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.blueskykong.auth.client.feign.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 这里模拟的是授权码登录时候的身份认证
 * 授权码登录验证的界面可以根据需要自定义
 * Created by xuan on 2018/1/3.
 */
@Component
public class CodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserClient userClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password;

        Map map;

        password = (String) authentication.getCredentials();
        //如果你是调用user服务，这边不用注掉
        //map = userClient.checkUsernameAndPassword(getUserServicePostObject(username, password, type));
        map = checkUsernameAndPassword(getUserServicePostObject(username, password));


        String userId = (String) map.get("userId");
        if (StringUtils.isBlank(userId)) {
            String errorCode = (String) map.get("code");
            throw new BadCredentialsException(errorCode);
        }
        CustomUserDetails customUserDetails = buildCustomUserDetails(username, password, userId);
        return new CustomAuthenticationToken(customUserDetails);
    }

    private CustomUserDetails buildCustomUserDetails(String username, String password, String userId) {
        CustomUserDetails customUserDetails = new CustomUserDetails.CustomUserDetailsBuilder()
                .withUserId(userId)
                .withPassword(password)
                .withUsername(username)
                .withClientId("frontend")
                .build();
        return customUserDetails;
    }

    private Map<String, String> getUserServicePostObject(String username, String password) {
        Map<String, String> requestParam = new HashMap<String, String>();
        requestParam.put("userName", username);
        requestParam.put("password", password);
        return requestParam;
    }

    //模拟调用user服务的方法
    private Map checkUsernameAndPassword(Map map) {

        //checkUsernameAndPassword
        Map ret = new HashMap();
        ret.put("userId", UUID.randomUUID().toString());

        return ret;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
