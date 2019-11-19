package com.callhh.module_common.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import com.callhh.module_common.R;


public class AnimUtils {

    private static int ANIMATION_DURATION_300 = 300;
    private static int ANIMATION_DURATION_500 = 500;

    /**
     * 晃动动画
     *
     * @param counts   晃动次数
     * @param duration 动画持续时间
     * @return 动画Animation
     */
    public static Animation shakeAnimation(int counts, int duration) {
        Animation translateAnimation = new TranslateAnimation(0
                , 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @param duration 动画持续时间
     */
    public static TranslateAnimation moveToViewBottom(int duration) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(ANIMATION_DURATION_500);
        return mHiddenAction;
    }

    public static void setScaleView(View view) {
        // 以view中心为缩放点，由初始状态放大两倍
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(3000);
        view.startAnimation(animation);
    }

    /**
     * 属性动画：向下平移，影藏布局
     */
    public static void setTranslationYToBottomHideView(View view, int scrollHeight) {
        ObjectAnimator.ofFloat(view, "translationY", scrollHeight)
                .setDuration(ANIMATION_DURATION_300).start();
    }

    /**
     * 属性动画：向上平移，显示布局
     */
    public static void setTranslationYToBottomShowView(View view, int scrollHeight) {
        // Y轴，从某个值变化到某个值
        ObjectAnimator.ofFloat(view, "translationY", scrollHeight)
                .setDuration(ANIMATION_DURATION_300).start();
    }

    /**
     * 展示View视图
     *
     * @param view     移动控件
     * @param distance 移动距离
     */
    public static void showTabView(final View view, int distance
            , int duration) {
        if (distance <= 0) distance = view.getHeight();
        // 从某个值变化到某个值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, distance);
        // 动画更新的监听
        valueAnimator.addUpdateListener(animator -> {
            // 启动动画之后, 会不断回调此方法来获取最新的值
            // 获取最新的高度
            Integer height = (Integer) animator.getAnimatedValue();
            ViewGroup.LayoutParams mParams = view.getLayoutParams();
            // 重新修改布局高度
            mParams.height = height;
            view.setLayoutParams(mParams);
        });
        valueAnimator.setDuration(duration).start();
    }

    /**
     * 隱藏View视图
     *
     * @param view     移动控件
     * @param distance 移动距离
     */
    public static void hideTabView(final View view, int distance
            , int duration) {
        if (distance <= 0) distance = view.getHeight();
        // 从某个值变化到某个值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(distance, 0);
        // 动画更新的监听
        valueAnimator.addUpdateListener(animator -> {
            // 启动动画之后, 会不断回调此方法来获取最新的值
            // 获取最新的高度
            Integer height = (Integer) animator.getAnimatedValue();
            ViewGroup.LayoutParams mParams = view.getLayoutParams();
            // 重新修改布局高度
            mParams.height = height;
            view.setLayoutParams(mParams);
        });
        valueAnimator.setDuration(duration).start();
    }

    /**
     * 旋转动画：0 --> 180度
     */
    public static void setRoate_0_180(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.roate_0_180);
        animation.setInterpolator(new LinearInterpolator());
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    /**
     * 旋转动画：180 --> 360度
     */
    public static void setRoate_180_360(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.roate_180_360);
        animation.setInterpolator(new LinearInterpolator());
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

}
