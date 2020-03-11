package com.callhh.nn.util.okgo;

/**
 * http接口请求常量类
 */
public class HttpResponseStatus {

    /**
     * code==0,请求成功
     * 请求成功true,message不为空则弹提示消息.
     */
    public static final int REQUEST_SUCCESS_CODE = 0;
    /**
     * 未登录
     */
    public static final int REQUEST_FAILED_CODE_40000 = 40000;
    /**
     * 登录超时
     */
    public static final int REQUEST_FAILED_CODE_40001 = 40001;
    /**
     * 登录授权码不正确
     */
    public static final int REQUEST_FAILED_CODE_40002 = 40002;

}
