package com.callhh.abtool.util.common;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.callhh.abtool.R;
import com.callhh.abtool.util.MyScreenUtils;


/**
 * Toast通知(吐司提示)工具类
 */
public class ToastUtil {

    public static Toast toast;
    public static Toast centerToast;
    public static Toast centerLongToast;

    private ToastUtil() {
        super();
    }

    /**
     * Default短时通知
     */
    public static void toast(Context context, String content) {
        if (TextUtils.isEmpty(content)) return;
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义toast短时通知,显示在中间
     */
    public static void toastCenter(Context context, String content) {
        try {
            if (null != centerToast) {
                centerToast.cancel();
            }
            if (TextUtils.isEmpty(content)) return;
            centerToast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_toast,
                    null);
            TextView tv_content = view.findViewById(R.id.tv_toast_content);
            MyTextUtil.setText(tv_content, content);
            centerToast.setView(view);
            centerToast.setGravity(Gravity.CENTER, 0, 0);
            centerToast.setDuration(Toast.LENGTH_SHORT);
            centerToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义toast长时通知,显示在中间
     */
    public static void toastOnCenterLong(Context context, String content) {
        try {
            if (null != centerLongToast) {
                centerLongToast.cancel();
            }
            if (TextUtils.isEmpty(content)) return;
            centerLongToast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_toast,
                    null);
            TextView tv_content = view.findViewById(R.id.tv_toast_content);
            MyTextUtil.setText(tv_content, content);
            centerLongToast.setView(view);
            centerLongToast.setGravity(Gravity.CENTER, 0, 0);
            centerLongToast.setDuration(Toast.LENGTH_LONG);
            centerLongToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义toast通知,显示在底部
     */
    public static void toastBottomView(Context context, String content) {
        try {
            if (null != toast) {
                toast.cancel();
            }
            if (TextUtils.isEmpty(content)) return;
            toast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_toast,
                    null);
            TextView tv_content = view.findViewById(R.id.tv_toast_content);
            MyTextUtil.setText(tv_content, content);
            toast.setView(view);
            toast.setGravity(Gravity.BOTTOM, 0, 210);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Top Center短时通知
     */
    public static void toastTop(Context context, String content) {
        // 获取屏幕高度
        if (TextUtils.isEmpty(content)) return;
        int metricsHeight = MyScreenUtils.getScreenHeightMetrics((Activity) context);
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        // 这里给了一个1/4屏幕高度的y轴偏移量
        toast.setGravity(Gravity.TOP, 0, metricsHeight / 4);
        toast.show();
    }

    /**
     * 长时通知
     */
    public static void toastLong(Context context, String content) {
        if (TextUtils.isEmpty(content)) return;
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }


    public static void toastForCancleLong(Context context, String content) {
        if (TextUtils.isEmpty(content)) return;
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void cancleToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void toastForCancleShort(Context context, String content) {
        if (TextUtils.isEmpty(content)) return;
        toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.show();
    }

}
