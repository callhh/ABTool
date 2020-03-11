package com.callhh.module_common.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;

import com.callhh.module_common.util.common.ToastUtil;

/**
 * 系统设置工具类
 *
 * @author callhh
 */

public class MySystemUtils {

    public static final String SETTINGS_ACTION =
            "android.settings.APPLICATION_DETAILS_SETTINGS";
    // 两次点击间隔不能少于xxx ms
    private static final int MIN_CLICK_DELAY_TIME = 3000;
    private static long lastClickTime = 0;

    /**
     * 防止快速点击，连续点击间隔不小于3s
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 判断APP系统消息通知是否打开
     *
     * @param context 上下文
     * @return true:已打开；false:未打开
     */
    public static boolean isNotificationEnabled(Context context) {
        try {
            NotificationManagerCompat notification = NotificationManagerCompat.from(context);
            return notification.areNotificationsEnabled();
        } catch (Exception e) {
            Log.e(Constants.TAG_LOG, e.toString());
            return false;
        }
    }

    /**
     * 打开系统设置
     *
     * @param context 上下文
     */
    public static void openSystemSetting(Context context) {
        try {
            // 6.0以上系统才可以判断权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
                Intent intent = new Intent();
                intent.setAction(SETTINGS_ACTION);
                intent.setData(Uri.fromParts("package",
                        context.getApplicationContext().getPackageName(), null));
                context.startActivity(intent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 运行系统在5.x环境使用
                Intent intent = new Intent();
                intent.setAction(SETTINGS_ACTION);
                intent.setData(Uri.fromParts("package",
                        context.getApplicationContext().getPackageName(), null));
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文本到系统剪贴板
     *
     * @param activity 上下文
     * @param content  需要复制的内容
     */
    public static void setClipboardManager(Activity activity, String content, String toastTips) {
        try {
            // 获取系统剪贴板
            ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            //两个参数：label:标签，随便写；text：复制的文本内容
            ClipData clipData = ClipData.newPlainText(null, content);
            // 把数据集设置（复制）到剪贴板
            cm.setPrimaryClip(clipData);
//            SPUtils.saveCopyContents(content);
            if (!TextUtils.isEmpty(toastTips))
                ToastUtil.toastCenter(activity, toastTips);
        } catch (Exception e) {
            Log.e(Constants.TAG_LOG, e.toString());
        }
    }

    /**
     * 获取系统剪贴板内容
     *
     * @param activity 上下文
     */
    public static String getClipboardContent(Activity activity) {
        String content = "";
        try {
            // 获取系统剪贴板
            ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            // 检查剪贴板是否有内容
            if (cm.hasPrimaryClip()) {
                ClipData clipData = cm.getPrimaryClip();
                if (null != clipData && clipData.getItemCount() > 0) {
                    // 从数据集中获取（粘贴）第一条文本数据
                    CharSequence text = clipData.getItemAt(0).getText();
                    if (null != clipData.getItemAt(0).getText())
                        content = text.toString();
                }
            }
        } catch (Exception e) {
            Log.e(Constants.TAG_LOG, e.toString());
        }
        return content;
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context context
     * @param pkgName 应用包名
     * @return true:已安装；false：未安装
     */
    public static boolean checkIsInstalled(Context context, String pkgName) {
        if (TextUtils.isEmpty(pkgName)) return false;
        try {
            //手机已安装，返回true
            context.getPackageManager().getApplicationInfo(pkgName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            //手机未安装，跳转到应用商店下载，并返回false
            Uri uri = Uri.parse("market://details?id=" + pkgName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
            return false;
        }
    }

    /**
     * 打开其他APP-首页
     *
     * @param activity    上下文
     * @param packageName APP包名
     */
    public static void openApp(Activity activity, String packageName, String toastTips) {
        try {
            if (checkIsInstalled(activity, packageName)) {
                Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
                activity.startActivity(intent);
            } else {
                ToastUtil.toastCenter(activity, toastTips);
            }
        } catch (Exception e) {
            Log.e(Constants.TAG_LOG, e.toString());
        }
    }


}
