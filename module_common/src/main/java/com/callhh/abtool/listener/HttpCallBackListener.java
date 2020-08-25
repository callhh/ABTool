package com.callhh.abtool.listener;

import android.graphics.Bitmap;

/**

/**
 * 处理网络图片URL定义的接口
 */
public interface HttpCallBackListener {

    void onFinish(Bitmap bitmap);
    void onError(Exception e);

}
