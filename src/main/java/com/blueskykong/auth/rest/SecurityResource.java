package com.blueskykong.auth.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author keets
 * @date 2017/10/15
 */
@Path("logout")
public class SecurityResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityResource.class);


    @Autowired
    TokenStore tokenStore;

    @GET
    @Deprecated
    public Response logout(@QueryParam("token") String token) {
        Assert.notNull(tokenStore, "tokenStore must be set");
        OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
        OAuth2RefreshToken refreshToken;

        if (existingAccessToken != null) {
            if (existingAccessToken.getRefreshToken() != null) {
                LOGGER.info("remove refreshToken!");
                refreshToken = existingAccessToken.getRefreshToken();
                tokenStore.removeRefreshToken(refreshToken);
            }
            LOGGER.info("remove existingAccessToken!");
            tokenStore.removeAccessToken(existingAccessToken);
        }
        return Response.ok().build();
    }

}
