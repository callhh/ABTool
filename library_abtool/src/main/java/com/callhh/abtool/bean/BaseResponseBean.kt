package com.callhh.abtool.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 基础http响应实体
 */
@Parcelize
open class BaseResponseBean : Parcelable {

    var code = 0
    var msg: String = ""

    fun toResponseBean(): ResponseBean<*> {
        val responseBean: ResponseBean<*> = ResponseBean<Any>()
        responseBean.code = code
        responseBean.msg = msg
        return responseBean
    }

}