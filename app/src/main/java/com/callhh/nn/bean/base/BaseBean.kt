package com.callhh.nn.bean.base

/**
 * 通用的模型类(服务器返回的基本jsonData格式)
 * code:为0时，请求成功；为-1、40000等其他值时，未登录或其他错误;具体咨询后台
 */
data class BaseBean(val code: Int, val msg: String)