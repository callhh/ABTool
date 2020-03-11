package com.callhh.module_common.listener;

/**
 * http请求成功的回调接口
 */

public interface ResultCallBack<T> {

    void onResponse(String response);

    void onError(Exception e);

}
