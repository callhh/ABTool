package com.callhh.nn.util.http;

import okhttp3.MediaType;

/**
 * api相关的接口常量类
 * 大部分接口都基于POST请求
 */
public class ApiConstants {

    /**
     * 正式环境base url
     */
    public static String BASE_URL = "http://api.wexiami.com/"; //正式环境
//    public static String BASE_URL = "http://www.wexiami.cn/"; // 开发测试环境

    /**
     * 图片基础路径，正式环境
     */
    public static String BASE_IMAGE_URL = "http://www.wexiami.cn/files/";

    /**
     * OkHttpUtlis设置编码格式
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /** ============== 公共模块API Start ============== */
    /**
     * API 登录接口
     */
    public static final String URL_USER_LOGIN = "api/Authority/Auth/Login";

    public static final String URL_Test = "index/articleTypeList";


}
