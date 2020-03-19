package com.callhh.nn.util.http;

import android.content.Context;

import com.callhh.nn.R;
import com.callhh.nn.bean.base.BaseBody;
import com.callhh.nn.util.SPUtils;
import com.google.gson.Gson;
import com.callhh.module_common.listener.ResultCallBack;
import com.callhh.module_common.util.Constants;
import com.callhh.module_common.util.common.GsonUtil;
import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.module_common.util.NetWorkUtils;
import com.callhh.module_common.util.common.ToastUtil;
import com.callhh.module_common.util.common.DialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 网络请求工具类
 * post\get 简单封装
 * 获取请求参等常见方法
 */
public class ApiRequestUtils {

    /**
     * get请求
     *
     * @param context  上下文
     * @param url      Api接口(不包含基础网址)
     * @param callBack (请求结果回调接口)
     */
    public static void get(Context context, String url, boolean isShowLoadDialog
            , final ResultCallBack callBack) {
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            ToastUtil.toast(context, context.getResources().getString(R.string.request_error_no_network));
            callBack.onError(new Exception(context.getResources().getString(R.string.request_error_no_network)));
            MyLogUtils.logI("get Request Result Msg: "
                    + context.getResources().getString(R.string.request_error_no_network));
            return;
        }
        DialogUtil.showLoadingDialog(context, isShowLoadDialog);
        OkHttpUtils.get()
                .url(ApiConstants.BASE_URL + url)
                .headers(ApiRequestHelper.getHeadParameters(context))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        sendSuccessResultCallback(context, response, callBack);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        callBack.onError(new Exception(context.getResources().getString(R.string.request_error_timeout)));
                        MyLogUtils.logI("get Request Result Msg: " + e.toString());
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
     * @param params   (请求参数类)
     * @param callBack (请求结果回调接口)
     */
    public static void post(final Context context, String url, Map<String, String> params
            , final boolean isShowLoadDialog, final ResultCallBack callBack) {
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
                .headers(ApiRequestHelper.getHeadParameters(context))
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        sendSuccessResultCallback(context, response, callBack);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        callBack.onError(new Exception(context.getResources().getString(R.string.request_error_timeout)));
                        MyLogUtils.logI("post Request Result Msg: " + e.toString());
                    }
                });
    }

    /**
     * PostJson请求，争对服务端是C#开发的环境
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
                        MyLogUtils.logI(response);
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        sendSuccessResultCallback(context, response, callBack);
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
                        sendSuccessResultCallback(context, response, callBack);
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
                        DialogUtil.cancleLoadingDialog(isShowLoadDialog);
                        sendSuccessResultCallback(context, response, callBack);
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
    private static void sendSuccessResultCallback(final Context context, String response
            , final ResultCallBack callBack) {
        try {
            BaseBody baseBody = GsonUtil.parseJSON(response, BaseBody.class);
            int code = baseBody.getCode();
            if (code == Constants.REQUEST_SUCCESS_CODE) {
                //code=200 请求成功
                callBack.onResponse(response);
            } else if (code == Constants.REQUEST_FAILED_CODE_302 ||
                    code == Constants.REQUEST_FAILED_CODE_401) {
                //code=302或401 Account登录超时/未授权/用户不存在等情况，去重新登录
                SPUtils.clearUserInfo();
                ToastUtil.toastCenter(context, baseBody.getMsg());
//                Intent intent = new Intent(context, LoginActivity.class);
//                Intent intent = new Intent(context, LoginSelectTypeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
                MyLogUtils.logI("responseCode: " + code + " /msg: " + baseBody.getMsg());
            } else if (code == Constants.REQUEST_FAILED_CODE_400) {
                //code=400 参数不合法
                callBack.onError(new Exception(baseBody.getMsg()));
                ToastUtil.toastCenter(context, baseBody.getMsg());
                MyLogUtils.logI("responseCode: " + code + " /msg: " + baseBody.getMsg());
            } else {
                //其他特殊的异常情况，直接弹出错误提示
                callBack.onError(new Exception(baseBody.getMsg()));
                ToastUtil.toastCenter(context, baseBody.getMsg());
                MyLogUtils.logI("responseCode: " + code + " /msg: " + baseBody.getMsg());
            }
        } catch (Exception e) {
            callBack.onError(new Exception(e.getMessage()));
            MyLogUtils.logI(e.toString());
        }
    }

    /** ============================ 获取请求参方法 start ============================ */

    /**
     * 请求参：获取userId
     *
     * @param uid uid
     */
    public static String getIdParams(int uid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        return new Gson().toJson(params);
    }

    /**
     * 请求参：获取userId
     *
     * @param uid uid
     * @return Map<String, String>集合
     */
    public static Map<String, String> getUIdParams(int uid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid + "");
        return params;
    }

    /**
     * 请求参：用户登录
     */
//    public static Map<String, String> getUserLoginParams(String loginName,String password) {
//        Map<String, String> params = new HashMap<>();
//        params.put("loginName", loginName);
//        params.put("password", password);
//        return params;
//    }

}
