package com.callhh.module_common.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.callhh.module_common.R;

import java.util.List;

/**
 * Activity工具类
 */
public class AppUtils {

    /**
     * 系统中有两个定义好的Activity跳转动画：fade_in、fade_out、slide_in_left、slide_out_right
     * overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
     * overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
     */

    /**
     * 单纯的跳转Activity
     *
     * @param clz 目标Activity的类对象
     */
    public static void startActivity(Activity activity, Class<?> clz) {
        activity.startActivity(new Intent(activity, clz));
        activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
    }

    /**
     * 跳转Activity,从底部滑入
     *
     * @param clz 目标Activity的类对象
     */
    public static void startActivityFromBottomOpen(Activity activity, Class<?> clz) {
        activity.startActivity(new Intent(activity, clz));
        activity.overridePendingTransition(R.anim.activity_open, 0);
    }

    /**
     * 跳转Activity,从底部滑入,并传参
     */
    public static void startActivityFromBottomWithIntent(Activity activity, Class<?> clz, Intent intent) {
        activity.startActivity(new Intent(activity, clz).putExtras(intent));
        activity.overridePendingTransition(R.anim.activity_open, 0);
    }

    /**
     * 结束页面，从顶部向下滑出
     *
     * @param activity 目标Activity的类对象
     */
    public static void endActivityFromTopClose(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(0, R.anim.activity_close);
    }

    /**
     * 启动activity，淡入淡出动画
     */
    public static void startActivityNoAnimation(Activity activity, Class<?> clz) {
        activity.startActivity(new Intent(activity, clz));
//		activity.overridePendingTransition(0, 0);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 跳转Activity时传递数据
     *
     * @param clz 目标Activity的类对象
     */
    public static void startActivityWithData(Activity activity, Class<?> clz, Bundle bundle) {
        activity.startActivity(new Intent(activity, clz).putExtras(bundle));
        activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
    }

    /**
     * 跳转Activity时用Intent传递数据
     *
     * @param clz 目标Activity的类对象
     */
    public static void startActivityDataWithIntent(Activity activity, Class<?> clz, Intent intent) {
        activity.startActivity(new Intent(activity, clz).putExtras(intent));
//		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

    }

    /**
     * 跳转Activity 需要返回数据
     *
     * @param
     * @param clz 目标Activity的类对象
     */
    public static void startActivityForResult(Activity activity, Class<?> clz, int requestCode) {
        activity.startActivityForResult(new Intent(activity, clz), requestCode);
        activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
    }

    /**
     * 跳转Activity时use bundle传递数据,
     * 并且需要返回数据
     *
     * @param bundle 目标Activity的类对象
     */
    public static void startActivityBundleWithDataForResult(Activity activity, Class<?> clz, Bundle bundle,
                                                            int requestCode) {
        activity.startActivityForResult(new Intent(activity, clz).putExtras(bundle), requestCode);
        activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
    }

    /**
     * 跳转Activity时use intent传递数据,
     * 并且需要返回数据
     */
    public static void startActivityIntentWithDataForResult(Activity activity, Class<?> clz, Intent intent,
                                                            int requestCode) {
        activity.startActivityForResult(new Intent(activity, clz).putExtras(intent), requestCode);
        activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
    }

    /**
     * 处理自定义的广播接收类的相关页面跳转
     * @param context   上下文
     * @param clz       跳转页面
     * @param key1      传值1的key名称
     * @param value1    传值1的value名称
     * @param key2      传值2的key名称
     * @param value2    传值2的value名称
     */
    public static void startActivityWithReceiver(Context context, Class<?> clz
            , String key1, String value1, String key2, String value2) {
        Intent intent = new Intent(context, clz);
        intent.putExtra(key1, value1);
        intent.putExtra(key2, value2);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开外部存储相册
     *
     * @param requestCode 请求码
     */
    public static void openExternalGallery(Activity activity, int requestCode) {
        activity.startActivityForResult(
                new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                requestCode);
    }

    /**
     * 隐藏软键盘 跳转 Activity的时候 若已经打开软键盘 进行关闭
     * 记得在清单文件加这句属性:android:windowSoftInputMode="adjustUnspecified|stateHidden"
     * Activity销毁,跳转新的Activity 将已经打开的软键盘 关闭 在onPause()方法中调用
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm)
            if (imm.isActive() && activity.getCurrentFocus() != null) {
                if (activity.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
    }

    /**
     * 隐藏软键盘 跳转 Activity的时候 若已经打开软键盘 进行关闭
     * 记得在清单文件加这句属性:android:windowSoftInputMode="adjustUnspecified|stateHidden"
     * Activity销毁,跳转新的Activity 将已经打开的软键盘 关闭 在onPause()方法中调用
     */
    public static void hideIputKeyboard(final Context context) {
        final Activity activity = (Activity) context;
        activity.runOnUiThread(() -> {
            InputMethodManager mInputKeyBoard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null) {
                mInputKeyBoard.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        });
    }


    /**
     * 点击空白区域，隐藏软键盘
     */
    public static void hideSoftKeyboardOnClickBlankArea(Activity activity) {
        if (activity == null) return;
        //获取当前获得焦点的View
        View v = activity.getCurrentFocus();
        //判断当前的焦点所在控件是否属于输入框EditTextView或者AppCompatEditText，不是就隐藏键盘
        if (v != null && (v instanceof AppCompatEditText)) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert im != null;
            im.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken()
                    , InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    //===========================  获取系统功能 Start ===========================

    /**
     * 获得手机的设备IMEI序列号,需要权限动态申请
     */
    public static String getSystemDeviceId(Context context) {
        String deviceId = null;
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (TextUtils.isEmpty(deviceId)) {
                if (null != tm)
                    if (PermissionUtils.checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                        deviceId = tm.getDeviceId();
                    }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    /**
     * 获取手机系列号
     */
    public static String getSystemSimSerialNumber(Context context) {
        String simSerialNumber = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                if (PermissionUtils.checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                    simSerialNumber = tm.getSimSerialNumber();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simSerialNumber;
    }

    /**
     * 获取手机厂商
     * @return 手机厂商名称
     */
    public static String getSystemBrand() {
        String brand = "";
        try {
            brand = Build.BRAND;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brand;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取本地版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            String packageName = getPackageName(context);
            versionCode = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取软件版本名称
     */
    public static String getVersionName(Context context) {
        String versionName;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取包名
     */
    public static String getPackageName(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            // 当前应用的版本名称
//			String versionName = info.versionName;
            // 当前版本的版本号
//			int versionCode = info.versionCode;
            // 当前版本的包名
            String packageNames = info.packageName;
            return packageNames;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断应用是否在后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null != am) {
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                if (!topActivity.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     */
    public static boolean isGpsEnable(Context context) {
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }

    /**
     * 获取进程名称
     * 通过Process.myPid()和 RunningAppProcessInfo来取得当前的进程名
     */
    public String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null != manager) {
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == pid) {
                    processName = process.processName;
                }
            }
        }
        return processName;
    }

    /**
     * 打开手机系统的拨号界面
     */
    public static void openDial(Activity activity, String phone) {
        try {
            Uri uri = Uri.parse("tel:" + phone);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
        } catch (Exception e) {
            Log.e(Constants.TAG_LOG,e.toString());
        }
    }

    /**
     * 使用手机系统直接拨打电话
     *
     * @param phone 手机号
     */
    public static void makeCallPhone(Activity activity, String phone) {
        try {

            if (Build.VERSION.SDK_INT >= 23) {
                if (PermissionUtils.checkPermission(activity, Manifest.permission.CALL_PHONE)) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phone));
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
                } else {
                    PermissionUtils.showToAppSettingDialog(activity
                            ,activity.getResources().getString(R.string.permission_apply)
                            ,activity.getResources().getString(R.string.permission_to_setting_tips));
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
            }
        } catch (Exception e) {
            Log.e(Constants.TAG_LOG,e.toString());
        }
    }

    /**
     * 打开系统的发送短信界面
     *
     * @param ac          当前上下文
     * @param phoneNumber 可以是"" 此时无默认的收信人
     * @param message     短信的内容
     */
    public static void sendShortMessage(Activity ac, String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);
        ac.startActivity(intent);
    }

    /**
     * 判断是否有sd卡
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡根目录路径
     */
    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdpath;
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdpath = "不适用";
        }
        return sdpath;
    }

    //===========================  获取系统功能 End ===========================

    /***
     * 使用默认的浏览器打开指定的网址
     */
    public static void openUrlByDefaultBrowser(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        } catch (Exception e) {

            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }

}
