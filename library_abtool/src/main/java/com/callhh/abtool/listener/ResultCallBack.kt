package com.callhh.abtool.listener

/**
 * http请求成功的回调接口
 */
interface ResultCallBack<T> {

    fun onResponse(response: String?)

    fun onError(e: Exception?)

}