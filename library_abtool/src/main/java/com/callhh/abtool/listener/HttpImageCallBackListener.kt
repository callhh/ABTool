package com.callhh.abtool.listener

import android.graphics.Bitmap

/**
 * 图片请求回调的事件监听接口
 */
interface HttpImageCallBackListener {

    fun onSuccess(bitmap: Bitmap)

    fun onFailure(e: Exception)

}