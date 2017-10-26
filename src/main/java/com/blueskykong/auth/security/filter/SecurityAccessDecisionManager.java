package com.blueskykong.auth.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author keets
 */
@Component
public class SecurityAccessDecisionManager implements AccessDecisionManager {
    private Logger logger = LoggerFactory.getLogger(SecurityAccessDecisionManager.class);

    /**
     * @param authentication 用户权限
     * @param o              url
     * @param collection     所需要的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        logger.info("decide url and permission");
        if (collection == null) {
            return;
        }

        Iterator<ConfigAttribute> ite = collection.iterator();
        //判断用户所拥有的权限，是否符合对应的Url权限，如果实现了UserDetailsService，则用户权限是loadUserByUsername返回用户所对应的权限
        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String needRole = ca.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                logger.info("GrantedAuthority: {}", ga);
                if (needRole.equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        logger.error("AccessDecisionManager: no right!");
        throw new AccessDeniedException("no right!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    //是否支持FilterInvocation需要将这里的false改为true
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

