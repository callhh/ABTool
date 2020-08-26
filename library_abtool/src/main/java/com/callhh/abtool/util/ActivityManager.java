package com.callhh.abtool.util;

import android.app.Activity;

import com.callhh.abtool.util.common.ToastUtil;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * App的Activity页面管理类
 */

public class ActivityManager {

    /**
     * 所有打开的Activity
     */
    private static CopyOnWriteArrayList<Activity> mActivities = null;
    private static volatile ActivityManager mInstance;

    private ActivityManager() {
        mActivities = new CopyOnWriteArrayList<>();
    }

    /**
     * 双重检测单例模式
     */
    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 保存已打开过的activity到容器里
     */
    public void addActivity(Activity activity) {
        if (mActivities != null) {
            if (!mActivities.contains(activity)) {
                mActivities.add(activity);
            }
        }
    }

    /**
     * 移除解绑Act - 防止内存泄漏
     */
    public synchronized void removeActivity(Activity activity) {
        if (activity != null) {
            mActivities.remove(activity);
        }
    }

    /**
     * 根据Activity的类名关闭 Activity
     */
    public void finish(Class<? extends Activity> activityClass) {
        for (int i = 0; i < mActivities.size(); i++) {
            Activity activity = mActivities.get(i);
            if (activity.getClass().getCanonicalName().equals(activityClass.getCanonicalName())) {
                activity.finish();
                break;
            }
        }
    }

    /**
     * 关闭所有activity
     */
    public void finishAll() {
        if (mActivities != null && mActivities.size() > 0) {
            for (Activity activity : mActivities) {
                activity.finish();
            }
        }
    }

    /**
     * 获取当前的Activity（最前面）
     */
    public Activity getCurrentActivity() {
        return mActivities.get(mActivities.size() - 1);
    }

    /**
     * 退出登陆
     */
    public void exitLogin() {
        ToastUtil.cancleToast();  //退出应用不再显示Toast
        finishAll();
    }

    /**
     * 退出应用 finish所有创建的Activity
     */
    public void exitApp() {
        //退出应用不再显示Toast
        ToastUtil.cancleToast();
        finishAll();
        //销毁当前进程
        android.os.Process.killProcess(android.os.Process.myPid());
//        System.gc();
    }
}
