package com.blueskykong.auth.client.feign;


import com.blueskykong.auth.client.fallback.UserFallbackClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author keets
 * @date 2017/9/25
 */
@FeignClient(name = "user", fallback = UserFallbackClient.class)
public interface UserClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/users/exists", consumes = {"application/json"}, produces = {"application/json"})
    Map checkUsernameAndPassword(Map userMap);

    @RequestMapping(method = RequestMethod.POST, value = "/api/users/phoneExists", consumes = {"application/json"}, produces = {"application/json"})
    Map getUserInformation(Map userCheck);

}
