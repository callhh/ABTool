package com.callhh.abtool.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Describe: 屏幕适配方案
 * <p>
 * 概念
 * 1.像素：屏幕的最小单位，单位为px。 dp和px的转换公式：px = dp * density
 * 2.分辨率：整个屏幕一共有多少个点，也就是像素。例如分辨率1920*1080就是指屏幕横向和纵向分别是1920和1080个像素组成。
 * 3.像素密度（dpi）：每英寸中的像素数。假如设备分辨率为320*240，屏幕长2英寸宽1.5英寸，dpi=320/2 = 240/1.5 =160。对应于DisplayMetrics类中属性densityDpi的值。
 * 4.屏幕密度（density）：每平方英寸中的像素数，density = dpi / 160 ，对应于DisplayMetrics类中属性density的值，可用于px与px与dip的互相转换 ：dp = px / density。
 * <p>
 * 常见设备的dp、px、density的关系
 * 分辨率	density	dpi
 * hdpi     480 * 800	1.5	240
 * xhdpi    720 * 1280	2.0	320
 * xxhdpi   1080 * 1920	3.0	480
 * <p>
 * 注意：在系统中修改系统字体大小后，系统的scaledDensity会发生改变，因此我们需要监听用户修改系统字体，然后重新设置scaledDensity
 * <p>
 * 设计：假设设计图宽度是360dp，以宽维度来适配。屏幕宽2倍是720px，3倍是1080px
 * 使用：1.在BaseApplication类中调用setDensity方法即可；或在BaseActivity的onCreate方法中调用setDensity方法即可，注意的是应该在setContentView之前设置
 */
public class DensityUtils {

    //    private static final float  WIDTH = 480;//参考设备的宽，单位是dp DPI:640
    //    private static final float  WIDTH = 640;//参考设备的宽，单位是dp DPI:480
    //    private static final float  WIDTH = 960;//参考设备的宽，单位是dp DPI:320
    //参考设备的宽，单位是dp DPI:160时
    private static float WIDTH;
    private static float appDensity; //表示屏幕密度
    private static float appScaledDensity; //字体缩放比例，默认appDensity
    private static DisplayMetrics appDisplayMetrics;//获取屏幕参数对象


    public static void setDensity(final Application application, float width) {
        //获取当前app的屏幕显示信息
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        WIDTH = width;
        registerActivityLifecycleCallbacks(application);
        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;
            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
    }

    private static void setDefault(Activity activity) {
        setAppOrientation(activity);
    }

    private static void setAppOrientation(Activity activity) {
        try {
            //计算目标值density, scaleDensity, densityDpi
            float targetDensity = appDisplayMetrics.widthPixels / WIDTH;
            float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
            int targetDensityDpi = (int) (targetDensity * 160);

            // 最后在这里将修改过后的值赋给系统参数,替换Activity的density, scaleDensity, densityDpi
            DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = targetDensity;
            activityDisplayMetrics.scaledDensity = targetScaledDensity;
            activityDisplayMetrics.densityDpi = targetDensityDpi;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerActivityLifecycleCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                setDefault(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 设置屏幕密度适配 及判断ipda兼容性
     */
    public static void setDensityCompatibility(Application application, Context context) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (isPad(context)) {
            setDensity(application, 720f);
        } else {
            if (displayMetrics != null && displayMetrics.widthPixels <= 720) {
                setDensity(application, 320f);
            } else {
                setDensity(application, 360f);
            }
        }
    }

    /**
     * 设置屏幕密度适配 及判断ipda兼容性，配置dpi
     * @param widthIPad 当前设备为iPad时，取 widthIPad 值
     * @param widthA    当屏幕像素宽度小于 <= 720(px)时，取 widthA 值
     * @param widthB    当屏幕像素宽度大于720px时，取 widthB 值
     */
    public static void setDensityCompatibility(Application application, Context context,float widthIPad,float widthA,float widthB) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (isPad(context)) {
            setDensity(application, 720f);
        } else {
            if (displayMetrics != null && displayMetrics.widthPixels <= 720) {
                setDensity(application, widthA);
            } else {
                setDensity(application, widthB);
            }
        }
    }

}