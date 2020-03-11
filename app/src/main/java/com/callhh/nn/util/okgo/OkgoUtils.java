package com.callhh.nn.util.okgo;


import com.callhh.nn.util.okgo.callbck.JsonCallback;
import com.lzy.okgo.OkGo;

import java.util.Map;


/**
 * http网络框架二次封装
 */

public class OkgoUtils {
    /**
     *
     * @param url
     * @param tag
     * @param map
     * @param callback
     * @param <T>
     */
    public static <T> void getRequest(String url, Object tag, Map<String, String> map
            , JsonCallback<T> callback) {
        // 加密 时间戳等 请求日志打印
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }
    public static <T> void postRequest(String url, Object tag, Map<String, String> map
            , JsonCallback<T> callback) {
        // 加密 时间戳等 请求日志打印
        OkGo.<T>post(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }

}
