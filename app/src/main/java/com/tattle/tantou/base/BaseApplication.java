package com.tattle.tantou.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tattle.tantou.util.ConstUtils;
import com.tattle.tantou.util.http.HttpLoggingInterceptor;
import com.tattle.tantou.view.common.MyRefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {

    public static BaseApplication application;

    public static BaseApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initOkHttp();
        initJPush();
        initTencentMTA();
        initRefreshViewLayout();
    }

    /**
     * 初始化极光推送sdk
     */
    private void initJPush() {
//        if (BuildConfig.DEBUG) {
//            JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
//        }
//        JPushInterface.init(this);
    }

    private void initTencentMTA() {
        // [可选]设置是否打开debug输出，上线时请关闭，Logcat标签为"MtaSDK"
//        StatConfig.setDebugEnable(false);
//        // 基础统计API,注册activity生命周期，统计时长
//        StatService.registerActivityLifecycleCallbacks(application);
    }


    private void initRefreshViewLayout() {
        MyRefreshLayout.init();
    }


    /**
     * 初始化OkHttpUtils-网络请求框架
     */
    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(ConstUtils.TAG_LOG))
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(15000L, TimeUnit.MILLISECONDS)
//				.addInterceptor(interceptor) // 添加自定义拦截器,公共头参数可以封装在里面
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 分割 dex 支持分包(解决65536问题)
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
