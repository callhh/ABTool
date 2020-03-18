package com.callhh.module_common.listener;

import android.graphics.Bitmap;

/**

/**
 * 处理网络图片URL定义的接口
 */
public interface HttpCallBackListener {

    void onFinish(Bitmap bitmap);
    void onError(Exception e);

}
