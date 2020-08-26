package com.callhh.abtool.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.callhh.abtool.R;
import com.callhh.abtool.util.common.ToastUtil;

/**
 * 网络相关的工具类
 */
public class NetWorkUtils {

    public static final int NETWORKTYPE_INVALID = 0;// 没有网络
    public static final int NETWORKTYPE_WAP = 1;// wap网络
    public static final int NETWORKTYPE_2G = 2;// 2G网络
    public static final int NETWORKTYPE_3G = 3;// 3G和3G以上网络，或统称为快速网络
    public static final int NETWORKTYPE_WIFI = 4;// wifi网络
    private static int mNetWorkType;

    /**
     * 检查网络，无网络就弹出提示，终止程序
     */
    public static void checkNetWord(Context context){
        if (!isNetworkAvailable(context)) {
            ToastUtil.toastCenter(context, context.getResources().getString(R.string.request_error_no_network));
            return;
        }
    }

    /**
     * 判断是否有网络
     *
     * @return 返回值
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null){
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有网络
     * @param context 上下文
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G}, *
     * {@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}*
     * <p>
     * {@link #NETWORKTYPE_WIFI}
     */
    public static boolean isWIFI(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {// wifi网络
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {// 移动网络下
                mNetWorkType = NETWORKTYPE_3G;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;// 网络不可用
        }
        if (mNetWorkType == NETWORKTYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
//        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
