package com.blueskykong.auth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by keets on 2017/1/17.
 */
public class JsonUtil {
    /**
     * Json字符串转对象
     * @param <T>
     * @param jsonStr
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T jsonStrToBean(String jsonStr, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, clazz);
    }

    /**
     * 对象转Json字符串
     * @param bean
     * @return
     * @throws Exception
     */
    public static String beanToJsonStr(Object bean) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bean);
    }
}
