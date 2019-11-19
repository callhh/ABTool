package com.callhh.module_common.util.common;


import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.callhh.module_common.R;


/**
 * 标题栏工具类
 */
public class TitleBarUtils {

    /**
     * 设置标题栏，右边的显示按钮是文本
     *
     * @param activity      上下文
     * @param title         标题名
     * @param rightTitle    右边显示的文本内容
     * @param rightListener 右边的文本监听事件
     */
    public static void setCommonTitle(final Activity activity, String title,
                                      String rightTitle, OnClickListener rightListener) {
        try {
            initView(activity, title);
            activity.findViewById(R.id.imgRightButton).setVisibility(View.GONE);
            TextView tvRightBtn = activity.findViewById(R.id.tvRightButton);
            MyTextUtil.setText(tvRightBtn, rightTitle);
            if (null != rightListener) {
                tvRightBtn.setOnClickListener(rightListener);
            }
        } catch (Exception e) {
            MyLogUtils.logI(e.toString());
        }
    }

    /**
     * 设置标题栏，右边的显示按钮是图标
     *
     * @param activity      上下文
     * @param title         标题
     * @param imageId       图标本地资源Id
     * @param rightListener 监听器
     */
    public static void setCommonIconTitle(final Activity activity, String title,
                                          int imageId, OnClickListener rightListener) {
        try {
            initView(activity, title);
            //影藏右边的文本控件，显示ImageView图标控件
            activity.findViewById(R.id.tvRightButton).setVisibility(View.GONE);
            ImageView imgRightBtn = activity.findViewById(R.id.imgRightButton);
            imgRightBtn.setVisibility(View.VISIBLE);
            if (imageId != -1)
                imgRightBtn.setImageResource(imageId);
            if (null != rightListener) {
                imgRightBtn.setOnClickListener(rightListener);
            }
        } catch (Exception e) {
            MyLogUtils.logI(e.toString());
        }
    }

    /**
     * 设置标题栏，影藏左边和右边的按钮
     * （场景：针对tab、fragment的标题栏等）
     *
     * @param activity      上下文
     * @param title         标题名
     * @param leftIsHide    左边返回按钮：true默认显示，false影藏按钮
     * @param rightIsHide   右边按钮：true默认显示，false影藏按钮
     * @param rightTitle    右边显示的文本内容
     * @param rightListener 右边的文本监听事件
     */
    public static void setTitleBarAndHideButton(final Activity activity, String title,
                                                boolean leftIsHide, boolean rightIsHide, String rightTitle,
                                                OnClickListener rightListener) {
        try {
            ImageView imgBack = activity.findViewById(R.id.imgBackButton);
            TextView tvTitle = activity.findViewById(R.id.tvTitlebarTitle);
            activity.findViewById(R.id.imgRightButton).setVisibility(View.GONE);
            TextView tvRightBtn = activity.findViewById(R.id.tvRightButton);
            MyTextUtil.setText(tvTitle, title);
            if (leftIsHide) {
                imgBack.setVisibility(View.VISIBLE);
                //返回键监听处理
                imgBack.setOnClickListener(view -> {
                    activity.setResult(Activity.RESULT_OK);
                    activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
                    activity.finish();
                });
            } else {
                imgBack.setVisibility(View.GONE);
            }
            if (rightIsHide) {
                tvRightBtn.setVisibility(View.VISIBLE);
                MyTextUtil.setText(tvRightBtn, rightTitle);
                if (null != rightListener) {
                    tvRightBtn.setOnClickListener(rightListener);
                }
            } else {
                tvRightBtn.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            MyLogUtils.logI(e.toString());
        }
    }

    /**
     * 初始化view,设置通用的标题和返回键监听处理
     *
     * @param activity 上下文
     * @param title    标题内容
     */
    private static void initView(final Activity activity, String title) {
        ImageView imgBack = activity.findViewById(R.id.imgBackButton);
        TextView tvTitle = activity.findViewById(R.id.tvTitlebarTitle);
        MyTextUtil.setText(tvTitle, title);
        //返回键监听处理
        imgBack.setOnClickListener(view -> {
            activity.setResult(Activity.RESULT_OK);
            activity.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
            activity.finish();
        });
    }

}
