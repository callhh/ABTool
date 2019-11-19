package com.tattle.tantou.util.http;

import android.content.Context;

import com.tattle.tantou.util.SPUtils;

import java.util.Calendar;
import java.util.HashMap;

/**
 * APP接口请求辅助类
 * 场景：公共参、请求头、常用业务请求方法封装
 */
public class ApiRequestHelper {

    /**
     * 获取系统当前时间
     */
    public static String getTimeStamp() {
        String strTimeStamp = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            strTimeStamp = calendar.getTimeInMillis() + "";
            return strTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();
            return strTimeStamp;
        }
    }

    /**
     * 得到加密的签名
     *
     * @param deviceID  设备id
     * @param timeStamp 时间戳
     * @param url       api接口
     * @return 返回string
     */
//    private static String getSignature(String deviceID,
//                                       String timeStamp, String url) {
//        String signature = ApiConst.PASS_KEY + ApiConst.DEVICE_TYPE
//                + deviceID + timeStamp + "/" + url + ApiConst.PASS_KEY;
//        return MD5Utils.MD5(signature.toLowerCase());
//    }


    /**
     * 添加公共的请求头信息
     */
    public static HashMap<String, String> getHeadParameters(Context context) {
        HashMap<String, String> params = new HashMap<>();
//        params.put("timestamp", SPUtils.getLoginTimeStamp());//登录时的时间戳
        boolean islogin = SPUtils.getIslogin();
        if (islogin) {
            params.put("X-XSRF-TOKEN", SPUtils.getUserToken());
        }
        return params;
    }

    /**
     * 添加请求头
     */
    public static HashMap<String, String> getRusHeaders(Context context, String url) {
        HashMap<String, String> params = new HashMap<>();
//        String deviceID = AppUtils.getSystemDeviceId(context);
//        String timeStamp = getTimeStamp();
//        String signature = getSignature(deviceID, timeStamp, url);
//        params.put("apptype", ApiConst.DEVICE_TYPE);
//        params.put("AppAgent", ApiConst.MARKNAME_ANDROID);
//        params.put("appid", deviceID);
//        params.put("timestamp", timeStamp);// 这三个用来传给服务器其进行拼接和md5后和下面所给的signature进行比对，如果一致则通过
//        params.put("signature", signature);
//        params.put("CityID", SPUtils.getCityID());
//        params.put("relevantnewsnum", 5 + "");//资讯详情页需要的相关文章列表请求参
//        params.put("apiversion", 2 + "");
//        params.put("Content-Type", "application/json");// 采用传json字符串进行传输的方式进行请求
//        boolean islogin = SPUtils.getIslogin(context);
//        if (islogin) {
//            params.put("userGuid", SPUtils.getGuid(context));
//            params.put("token", SPUtils.getToken(context));
//        }
        return params;
    }


    //===========================  业务常用功能 Start ===========================

    /**
     * 共用的字典表数据
     *
     * @param activity 上下文
     */
//    public static void getDictionaryData(Activity activity) {
////      String params = ApiRequestUtils.getUserHBParams(uid, type);
//        ApiRequestUtils.postJson(activity, ApiConstants.URL_COMMON_DICTIONARY_DATA
//                , "", false, new ResultCallBack() {
//                    @Override
//                    public void onResponse(String response) {
//                        SPUtils.saveCommonDictionaryData(response);
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                    }
//                });
//    }



    //===========================  业务常用功能 End ===========================

}
