package com.callhh.nn.util.okgo;


import android.text.format.Formatter;

import com.callhh.module_common.helper.HttpExceptionHelper;
import com.callhh.module_common.util.NetWorkUtils;
import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.module_common.util.common.ToastUtil;
import com.callhh.nn.R;
import com.callhh.nn.base.BaseApplication;
import com.callhh.nn.util.okgo.callbck.JsonCallback;
import com.callhh.nn.util.okgo.callbck.ShowDialogCallback;
import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;


/**
 * http网络框架二次封装
 */

public class OkGoUtils {

    public static boolean isNotNetwork(){
        if (!NetWorkUtils.isNetworkAvailable(BaseApplication.getInstance().getApplicationContext())) {
            ToastUtil.toast(BaseApplication.getInstance().getApplicationContext()
                    , BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.request_error_no_network));
            MyLogUtils.logI("网络不给力，请检查网络后重试");
            return true;
        }else {
            return false;
        }
    }

    /**
     *  GET 请求
     * @param url   请求路径
     * @param tag   请求标签
     * @param params    请求参数
     * @param callback  请求回调类
     * @param <T>       请求回调数据模型
     */
    public static <T> void get(String url, Object tag, Map<String, String> params
            , JsonCallback<T> callback) {
        if (isNotNetwork())return;
        // 加密 时间戳等 请求日志打印
        OkGo.<T>get(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }

    /**
     *  GET 请求，包含是否显示"请求进度对话框"
     * @param url   请求路径
     * @param tag   请求标签
     * @param params    请求参数
     * @param callback  请求回调类
     * @param <T>       请求回调数据模型
     */
    public static <T> void getRequest(String url, Object tag, Map<String, String> params
            , ShowDialogCallback<T> callback) {
        if (isNotNetwork())return;
        // 加密 时间戳等 请求日志打印
        OkGo.<T>post(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }

    /**
     *  POST 请求
     * @param url   请求路径
     * @param tag   请求标签
     * @param params    请求参数
     * @param callback  请求回调类
     * @param <T>       请求回调数据模型
     */
    public static <T> void post(String url, Object tag, Map<String, String> params
            , JsonCallback<T> callback) {
        if (isNotNetwork())return;
        // 加密 时间戳等 请求日志打印
        OkGo.<T>post(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }
    /**
     *  POST 请求，包含是否显示"请求进度对话框"
     * @param url   请求路径
     * @param tag   请求标签
     * @param params    请求参数
     * @param callback  请求回调类
     * @param <T>       请求回调数据模型
     */
    public static <T> void postRequest(String url, Object tag, Map<String, String> params
            , ShowDialogCallback<T> callback) {
        if (isNotNetwork())return;
        // 加密 时间戳等 请求日志打印
        OkGo.<T>post(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }

}
