package com.callhh.abtool.bean

import java.io.Serializable

/**
 *  http响应成功 实体基类
 */
class ResponseBean<T> : Serializable {

    var code = 0
    var msg: String = ""
    var data: T? = null

}