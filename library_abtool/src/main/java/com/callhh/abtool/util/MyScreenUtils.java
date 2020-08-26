package com.callhh.abtool.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * 获取手机屏幕参数的功能工具类
 */

public class MyScreenUtils {

    /**
     * 安卓小屏幕手机的px高度,如5.0、5.2尺寸
     */
    public static final int PX_HEIGHT_1920 = 1920;
    private MyScreenUtils() {
        throw new AssertionError();
    }

    /**
     * 根据手机分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue,float fValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dpValue * scale + fValue);
        return px;
    }

    /**
     * 根据手机分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //屏幕距离（dp）转换成像素距离（px）
    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    /**
     * 获取手机屏幕的像素宽度
     */
    public static int getScreenWidthMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取手机屏幕的像素高度
     */
    public static int getScreenHeightMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     * DisplayMetrics 这是 一个系统 用来 专门 管理 手机 像素 屏幕尺寸的类
     */
    public static int getScreenWidth(Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenHeight = dm.widthPixels;
        return screenHeight;
    }

    /**
     * 获取手机屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    /**
     * 获取手机屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 动态设置View的高度,高度=屏幕宽度,效果：正方形
     */
    public static void setViewHeight(Context context, View view, double value) {
        Double dWidth = getScreenWidth(context) * value;
        int screenWidth = dWidth.intValue();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = screenWidth;
        view.setLayoutParams(lp);
    }

    /**
     * 动态设置View的宽高比例,场景：海报
     * @param context   上下文
     * @param view      视图
     * @param value     宽度比例
     * @param ratio     高度比例值
     */
    public static void setViewHeight(Context context, View view
            , double value,int ratio) {
        Double dWidth = getScreenWidth(context) * value;
        int screenWidth = dWidth.intValue();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = screenWidth;
        lp.height = screenWidth * ratio;
        view.setLayoutParams(lp);
    }

    /**
     * 动态设置ImageView的高度,高度=屏幕宽度,效果：正方形
     */
    public static void setImageViewHeight(Context context, ImageView imageView, double value) {
        Double dWidth = getScreenWidth(context) * value;
        int screenWidth = dWidth.intValue();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = screenWidth;
        imageView.setLayoutParams(lp);
    }

    /**
     * 获取状态栏的高
     */
    public static int getStatusBarHeight(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        if (0 == statusBarHeight) {
            statusBarHeight = getStatusBarHeightByReflection(context);
        }
        return statusBarHeight;
    }

    public static int getStatusBarHeight1(Activity context) {
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statuBarHeight = rect.top;
        if (0 == statuBarHeight) {
            statuBarHeight = getStatusBarHeightByReflection(context);
        }
        return statuBarHeight;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeightByReflection(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        // 默认为38，貌似大部分是这样的
        int x, statusBarHeight = 38;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 判断顶部透明效果是否显示
     */
    public static void isShow(AbsListView view, ImageView ivtop, ImageView ivbottom) {
        if (view.getFirstVisiblePosition() == 0) {//
            ivtop.setVisibility(View.VISIBLE);// 隐藏顶部iv
            ivbottom.setVisibility(View.GONE);// 显示底部iv
        } else if (view.getFirstVisiblePosition() > 1) {
            ivtop.setVisibility(View.GONE);// 隐藏顶部iv
            ivbottom.setVisibility(View.VISIBLE);// 显示底部iv
        }
    }

}