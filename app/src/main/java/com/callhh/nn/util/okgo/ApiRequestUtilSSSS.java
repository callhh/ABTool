package com.callhh.nn.util.okgo;

import android.content.Context;

import com.callhh.module_common.helper.HttpExceptionHelper;
import com.callhh.module_common.listener.ApiResultCallBack;
import com.callhh.module_common.listener.ResultCallBack;
import com.callhh.module_common.util.Constants;
import com.callhh.module_common.util.NetWorkUtils;
import com.callhh.module_common.util.common.DialogUtil;
import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.module_common.util.common.ToastUtil;
import com.callhh.nn.R;
import com.callhh.module_common.bean.ResponseBean;
import com.callhh.nn.bean.DemoBean;
import com.callhh.nn.util.SPUtils;
import com.callhh.nn.util.http.ApiConstants;
import com.callhh.nn.util.http.ApiRequestHelper;
import com.callhh.nn.util.okgo.callbck.JsonCallback;
import com.lzy.okgo.model.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.Map;

import okhttp3.Call;

/**
 * 网络请求工具类
 * post\get 简单封装
 * 获取请求参等常见方法
 */
public class ApiRequestUtilSSSS {

    /**
     * get请求
     *
     * @param context  上下文
     * @param url      Api接口(不包含基础网址)
     * @param callBack (请求结果回调接口)
     */
    public static <T> void get(Context context, String url, Map<String, String> params
            , boolean isShowLoadDialog, final ApiResultCallBack<T> callBack) {
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            ToastUtil.toast(context, context.getResources().getString(R.string.request_error_no_network));
            callBack.onError(new HttpExceptionHelper(-1, context.getResources().getString(R.string.request_error_no_network)));
            MyLogUtils.logI("get Request Result Msg: "
                    + context.getResources().getString(R.string.request_error_no_network));
            return;
        }
        DialogUtil.showLoadingDialog(context, isShowLoadDialog);
        OkgoUtils.postRequest(ApiConstants.BASE_URL + url, context, params
                , new JsonCallback<ResponseBean<T>>() {

                    @Override
                    public void onSuccess(Response<ResponseBean<T>> response) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        sendSuccessResultCallback(context, response, callBack);
                    }

                    @Override
                    public void onError(Response<ResponseBean<T>> response) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        callBack.onError(new HttpExceptionHelper(response.code(), response.message()));
                        MyLogUtils.logI("get Request Result Msg: " + response.message());
                    }
                });
    }

    /**
     * Post请求
     * 传递键值对参数的post请求
     * String params = new Gson.toJson(mapParams);
     *
     * @param context  上下文
     * @param url      Api接口(不包含基础网址)
     * @param params   (请求参)
     * @param callBack (请求结果回调接口)
     */
    public static <T> void post(final Context context, String url, Map<String, String> params
            , final boolean isShowLoadDialog, ApiResultCallBack<T> callBack) {
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            ToastUtil.toast(context, context.getResources().getString(R.string.request_error_no_network));
            callBack.onError(new HttpExceptionHelper(-1, context.getResources().getString(R.string.request_error_no_network)));
            MyLogUtils.logI("post Request Result Msg: "
                    + context.getResources().getString(R.string.request_error_no_network));
            return;
        }
        DialogUtil.showLoadingDialog(context, isShowLoadDialog);
        OkgoUtils.postRequest(ApiConstants.BASE_URL + url, context, params
                , new JsonCallback<ResponseBean<T>>() {

                    @Override
                    public void onSuccess(Response<ResponseBean<T>> response) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        ResponseBean<T> body = response.body();
                        T data = body.data;
                        sendSuccessResultCallback(context, response, callBack);
                    }

                    @Override
                    public void onError(Response<ResponseBean<T>> response) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        callBack.onError(new HttpExceptionHelper(response.code(), response.message()));
                        MyLogUtils.logI("post Request Result Msg: " + response.message());
                    }
                });
    }

    /**
     * PostJson请求，争对服务端是.net开发的环境
     * 传递json格式参数的post请求
     * 注意：传递JSON的时候，不要通过addHeader去设置contentType
     * 而使用.mediaType(MediaType.parse("application/json; charset=utf-8"))
     *
     * @param context  上下文
     * @param url      Api接口(不包含基础网址)
     * @param params   (请求参数类)
     * @param callBack (请求结果回调接口)
     */
    public static void postJson(final Context context, String url, String params
            , boolean isShowLoadDialog, final ResultCallBack callBack) {
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            ToastUtil.toast(context, context.getResources().getString(R.string.request_error_no_network));
            callBack.onError(new Exception(context.getResources().getString(R.string.request_error_no_network)));
            MyLogUtils.logI("get Request Result Msg: "
                    + context.getResources().getString(R.string.request_error_no_network));
            return;
        }
        DialogUtil.showLoadingDialog(context, isShowLoadDialog);
        OkHttpUtils.postString()
                .mediaType(ApiConstants.JSON)
                .url(ApiConstants.BASE_URL + url)
                .headers(ApiRequestHelper.getHeadParameters(context))
                .content(params)//将string作为请求体传入到服务端，例如json字符串
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
//                        MyLogUtils.logI(response);
//                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
//                        sendSuccessResultCallback(context, response, callBack);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        callBack.onError(new Exception(context.getResources().getString(R.string.request_error_timeout)));
                        ToastUtil.toastBottomView(context, context.getResources().getString(R.string.request_error_timeout));
                        MyLogUtils.logI("postJson Request Result Msg: " + e.toString());
                    }
                });
    }

    /**
     * 单文件上传
     */
    public static void uploadFile(Context context, String url, File file
            , boolean isShowLoadDialog, final ResultCallBack callBack) {
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            ToastUtil.toast(context, context.getResources().getString(R.string.request_error_no_network));
            callBack.onError(new Exception(context.getResources().getString(R.string.request_error_no_network)));
            MyLogUtils.logI("get Request Result Msg: "
                    + context.getResources().getString(R.string.request_error_no_network));
            return;
        }
        DialogUtil.showLoadingDialog(context, isShowLoadDialog);
        OkHttpUtils.post()
                .url(ApiConstants.BASE_URL + url)
                .addFile("image", "jpg", file) //单文件
//                .files("imagefile", files) //多文件
                .headers(ApiRequestHelper.getHeadParameters(context))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {
//                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
//                        sendSuccessResultCallback(context, response, callBack);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        callBack.onError(new Exception(context.getResources().getString(R.string.request_error_timeout)));
                        MyLogUtils.logI("postJson Request Result Msg: " + e.toString());
                    }
                });
    }

    /**
     * 多文件上传
     */
    public static void uploadFiles(Context context, String url, Map<String, File> files, String params
//    public static void fileUpload(Context context, String url, File file, String params
            , boolean isShowLoadDialog, final ResultCallBack callBack) {
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            ToastUtil.toast(context, context.getResources().getString(R.string.request_error_no_network));
            callBack.onError(new Exception(context.getResources().getString(R.string.request_error_no_network)));
            MyLogUtils.logI("get Request Result Msg: "
                    + context.getResources().getString(R.string.request_error_no_network));
            return;
        }
        DialogUtil.showLoadingDialog(context, isShowLoadDialog);
        OkHttpUtils.post()
                .url(ApiConstants.BASE_URL + url)
//                .addFile("image", "imageFile", file) //单文件
                .files("imagefile", files) //多文件
                .headers(ApiRequestHelper.getHeadParameters(context))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {
//                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
//                        sendSuccessResultCallback(context, response, callBack);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        callBack.onError(new Exception(context.getResources().getString(R.string.request_error_timeout)));
                        MyLogUtils.logI("postJson Request Result Msg: " + e.toString());
                    }
                });
    }

    /**
     * 数据请求成功的回调处理，执行在UI线程
     */
    private static <T> void sendSuccessResultCallback(Context context
            , Response<ResponseBean<T>> response, ApiResultCallBack<T> callBack) {
        try {
            int code = response.code();
            String message = response.message();
            switch (code) {
                case Constants.REQUEST_SUCCESS_CODE:
                    ResponseBean<T> body = response.body();
                    callBack.onResponse(body);
                    break;
                case Constants.REQUEST_FAILED_CODE_302:
                case Constants.REQUEST_FAILED_CODE_401:
                    //code=302或401 Account登录超时/未授权/用户不存在等情况，去重新登录
                    SPUtils.clearUserInfo();
                    ToastUtil.toastCenter(context.getApplicationContext(), message);
                    break;
                default:
                    //其他特殊的异常情况，直接弹出错误提示
                    callBack.onError(new HttpExceptionHelper(code, message));
                    ToastUtil.toastCenter(context.getApplicationContext(), message);
                    break;
            }
            MyLogUtils.logI("responseCode: " + code + " /msg: " + response.message());
        } catch (Exception e) {
            callBack.onError(new HttpExceptionHelper(-1, e.getMessage()));
            MyLogUtils.logI(e.toString());
        }
    }

}
