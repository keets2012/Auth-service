package com.blueskykong.auth.exception;


/**
 * Created by keets on 2016/12/5.
 */


public class ErrorCodes {

    public static final String CLIENT_ID_IS_NULL_STR = "CLIENT_ID_IS_NULL";
    public static final String CLIENT_SECRET_IS_NULL_STR = "CLIENT_SECRET_IS_NULL";

    public static final String ORGANIZAITON_ID_IS_NULL_STR = "ORGANIZAITON_ID_IS_NULL";

    /* 500 Internal Server Error */

    public static final ErrorCode DEVICE_ID_IS_NULL_STR = new ErrorCode("DEVICE_ID_IS_NULL_STR", "设备 ID 非法");
    public static final ErrorCode NOT_LOGIN_YET = new ErrorCode("NOT_LOGIN_YET", "没有用户已登录当前设备");

    public static final ErrorCode USER_SERVICE_CALL_FAILURE = new ErrorCode("USER_SERVICE_CALL_FAILURE", "无法调用远程用户系统，或调用失败");
    public static final ErrorCode ID_SERVICE_CALL_FAILURE = new ErrorCode("ID_SERVICE_CALL_FAILURE", "无法调用远程用ID_SERVICE，或调用失败");

    public static final ErrorCode INVALID_AUTHENTICATION_ERROR = new ErrorCode("INVALID_AUTHENTICATION", "用户证书校验失败");

}
