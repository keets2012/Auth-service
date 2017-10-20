package com.blueskykong.auth.client.feign;

import com.blueskykong.auth.client.fallback.UtilFallbackClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author keets
 * @date 2017/9/26
 */
@FeignClient(name = "id-generator", fallback = UtilFallbackClient.class)
public interface UtilClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/id")
    long generateId();
}
