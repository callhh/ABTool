package com.callhh.nn.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.callhh.abtool.util.Density
import com.callhh.abtool.util.common.MyLogUtils
import com.callhh.nn.BuildConfig
import com.callhh.nn.util.ConstUtils
import com.callhh.nn.util.SPUtils
import com.callhh.nn.util.http.HttpLoggingInterceptor
import com.callhh.nn.view.common.MyRefreshLayout
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.HttpHeaders
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class BaseApplication : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        MyLogUtils.setLogEnabled(BuildConfig.DEBUG)
        initOkHttp()
        initOkgoHttp()
        initJPush()
        initTencentMTA()
        initRefreshViewLayout()
    }

    /*
 * 设置 屏幕  适配
 */
    private fun setDensity() {
        val appDisplayMetrics = context.resources.displayMetrics
        if (Density.isPad(applicationContext)) {
            Density.setDensity(this, 750f)
        } else {
            if (appDisplayMetrics != null && appDisplayMetrics.widthPixels <= 720) {
                Density.setDensity(this, 325f)
            } else {
                Density.setDensity(this, 350f)
            }
        }
    }

    /**
     * 初始化极光推送sdk
     */
    private fun initJPush() {
        //        if (BuildConfig.DEBUG) {
//            JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
//        }
//        JPushInterface.init(this);
    }

    private fun initTencentMTA() { // [可选]设置是否打开debug输出，上线时请关闭，Logcat标签为"MtaSDK"
//        StatConfig.setDebugEnable(false);
//        // 基础统计API,注册activity生命周期，统计时长
//        StatService.registerActivityLifecycleCallbacks(application);
    }

    private fun initRefreshViewLayout() {
        MyRefreshLayout.init()
    }

    /**
     * 初始化OkHttpUtils-网络请求框架
     */
    private fun initOkHttp() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(ConstUtils.TAG_LOG))
            .connectTimeout(15000L, TimeUnit.MILLISECONDS)
            .readTimeout(
                15000L,
                TimeUnit.MILLISECONDS
            ) //				.addInterceptor(interceptor) // 添加自定义拦截器,公共头参数可以封装在里面
//其他配置
            .build()
        OkHttpUtils.initClient(okHttpClient)
    }

    private fun initOkgoHttp() {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15000L, TimeUnit.MILLISECONDS)
            .readTimeout(15000L, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor(ConstUtils.TAG_LOG))
            //				.addInterceptor(interceptor) // 添加自定义拦截器,公共头参数可以封装在里面
            .build()
        val headers = HttpHeaders()
        if (SPUtils.getIslogin()) {
            headers.put("token", SPUtils.getUserToken())
        }
        OkGo.getInstance().init(this) //必须调用初始化
            .setOkHttpClient(okHttpClient) //建议设置OkHttpClient，不设置会使用默认的
            .setCacheMode(CacheMode.NO_CACHE) //全局统一缓存模式，默认不使用缓存，可以不传
            .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE) //全局统一缓存时间，默认永不过期，可以不传
            .setRetryCount(3) //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
            .addCommonHeaders(headers) //全局公共头
    }

    /**
     * 分割 dex 支持分包(解决65536问题)
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}