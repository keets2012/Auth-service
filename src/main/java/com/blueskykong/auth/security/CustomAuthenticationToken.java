package com.blueskykong.auth.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Created by keets on 2017/8/5.
 */
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private CustomUserDetails userDetails;

    public CustomAuthenticationToken(CustomUserDetails userDetails) {
        super(null);
        this.userDetails = userDetails;
        super.setAuthenticated(true);
    }

    public Object getPrincipal() {
        return this.userDetails;
    }

    public Object getCredentials() {
        return this.userDetails.getPassword();
    }

}