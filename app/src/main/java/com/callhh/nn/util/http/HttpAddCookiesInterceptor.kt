package com.callhh.nn.util.http

import com.callhh.module_common.util.common.MyLogUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * http自定义拦截器,获取cookie信息、session信息
 */
class HttpAddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        // 获取 Cookie
        val originalResponse = chain.proceed(chain.request())
        val cookies = originalResponse.headers("Set-Cookie")
        val cookieStr: String
        if (cookies != null && cookies.isNotEmpty()) {
            val s = cookies[0]
            //sessionid值格式：JSESSIONID=AD5F5C9EEB16C71EC3725DBF209F6178，是键值对，不是单指值
            cookieStr = s.substring(0, s.indexOf(";"))

//            MySPUtils.saveUserSessionId(cookieStr)//持久化到本地
            MyLogUtils.logI("HttpAddCookiesInterceptor()  cookie SessionId == $cookieStr")
        }
        return originalResponse

    }
}