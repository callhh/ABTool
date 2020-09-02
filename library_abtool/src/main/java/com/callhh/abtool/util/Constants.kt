package com.callhh.abtool.util

/**
 * 常量工具类
 */
object Constants {

    /**
     * 统一的日志标签key
     */
    const val TAG_LOG = "callhh"

    /**
     * 动画显示时长/默认2000毫秒
     */
    const val DELAY_MILLIS = 1500
    const val TIME_OUT = 15
    /**
     * 统一的页码\页数
     */
    const val PAGE_INDEX = 1
    const val PAGE_SIZE = 10

    /**
     * 权限被拒绝的统一提示
     */
    const val TIP_PERMISSION = "操作被拒绝，请到手机设置开启应用权限！"
    const val DIALOG_TIP_CONTENT = "当前为非wifi状态，是否继续下载更新应用？"
    const val DIALOG_TIP_TITLE = "温馨提示"
    const val DIALOG_GO_ON_DOWNLOAD = "继续下载"
    const val DIALOG_WAIT_DOWNLOAD = "稍后下载"
    const val DIALOG_TIP_DOWNLOAD = "正在后台为您下载更新包，稍后请安装使用"
    /**
     * code=0,请求成功
     */
    const val REQUEST_SUCCESS_CODE = 200
    /**
     * 请求失败:参数不合法，参数错误，入参为空等等
     */
    const val REQUEST_FAILED_CODE_400 = 400
    /**
     * 请求失败，Token过期，登录超时/用户不存在,重新登录
     */
    const val REQUEST_FAILED_CODE_302 = 302
    /**
     * 请求失败，Token过期，登录超时/账号未授权,重新登录
     */
    const val REQUEST_FAILED_CODE_401 = 401
    /**
     * 请求失败，业务场景出错
     */
    const val REQUEST_FAILED_CODE_NAGETIVE_1 = -1

}