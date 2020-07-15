package com.callhh.module_common.util.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.callhh.module_common.R;
import com.callhh.module_common.util.MyScreenUtils;
import com.callhh.module_common.widget.RxProgressBar;


/**
 * 弹出的对话框管理类
 */
public class DialogUtil {

    private static Handler mHandler = new Handler();
    /**
     * Dialog宽度：设置为屏幕宽度的75%
     */
    private static double SCREEN_WIDTH_PER_75 = 0.75f;
    public static double SCREEN_WIDTH_PER_80 = 0.80f;
    private static Dialog mLoadingDialog;
    private static Dialog commonTips;
    // 请求权限
    private static Dialog reqPerTips;
    // 更新版本的下载进度条Dailog
    private static Dialog mDialogPb;

    /**
     * 设置对话框的宽度 为一定比例的手机屏幕的宽度
     * 注意:父布局的id必须是ll_dialog
     */
    public static void setDialogWidth(Context context, Dialog dialog, int percent, int total) {
        if (null == dialog) return;
        LinearLayout linearLayout = dialog.findViewById(R.id.llDialog);
        int widthMetrics = MyScreenUtils.getScreenWidthMetrics((Activity) context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(widthMetrics * percent / total
                , LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 设置弹出Dialog对话框的显示风格
     * 宽度为80%屏幕，高度为适应屏幕，布局显示在中心位置
     * 可配置是否选择Dialog显影的动画
     *
     * @param dialog    对话框
     * @param animStyle 动画文件
     */
    public static void setDialogStyle(Dialog dialog, double percent
            , boolean isShowAnim, int animStyle) {
        if (null == dialog) return;
        Window window = dialog.getWindow();
        if (null != window) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;//设置宽度充满屏幕
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            Display display = window.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            lp.width = (int) (size.x * percent);
            window.setAttributes(lp);
            // 设置弹出的动画效果
            if (isShowAnim) {
                window.setWindowAnimations(animStyle);
            }
        }
    }

    /**
     * @param dialog  dialog对象
     * @param percent 百分比0.8f
     * @param gravity Gravity.CENTER
     */
    public static void setDialogStyle(Dialog dialog, double percent
            , int gravity) {
        if (null == dialog) return;
        Window window = dialog.getWindow();
        if (null != window) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(gravity);
            Display display = window.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            lp.width = (int) (size.x * percent);
            window.setAttributes(lp);
        }
    }

    /**
     * 设置弹出Dialog对话框的显示风格
     * 宽度为80%屏幕，高度为适应屏幕，布局显示在中心位置
     * 可配置是否选择Dialog显影的动画
     *
     * @param dialog 对话框
     */
    public static void setDialogDisplayStyle(Dialog dialog) {
        if (null == dialog) return;
        Window window = dialog.getWindow();
        if (null != window) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;//设置宽度充满屏幕
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            Display display = window.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            lp.width = (int) (size.x * 0.80);
            window.setAttributes(lp);
//        window.setWindowAnimations(R.style.AnimDialog);
        }
    }

    /**
     * 统一的网络请求加载ing的对话框
     *
     * @param context          页面上下文
     * @param isShowLoadDialog 是否显示加载中的对话框
     */
    public static void showLoadingDialog(Context context, boolean isShowLoadDialog) {
//        if (!SPUtils.getValue()) return;
        if (mLoadingDialog != null) mLoadingDialog.dismiss();
        if (!isShowLoadDialog) return;
        mLoadingDialog = new Dialog(context, R.style.myLoadingDialog);
        mLoadingDialog.setContentView(R.layout.layout_loading_dialog_fullscreen);
        FrameLayout view = mLoadingDialog.findViewById(R.id.flDialogLoading);
        view.getBackground().setAlpha(120);
        mLoadingDialog.setCancelable(true);// 点击返回键无效
        mLoadingDialog.setCanceledOnTouchOutside(false);  //点击对话框外区域 取消对话框
        mLoadingDialog.show();
    }

    /**
     * 取消统一的接口加载对话框
     *
     * @param isHide 是：隐藏；否：不执行
     */
    public static void cancleLoadingDialog(boolean isHide) {
        if (mLoadingDialog != null && isHide) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 通用的提示对话框
     * 场景：提示框，呼叫电话，
     *
     * @param context         上下文
     * @param content         提示内容
     * @param onClickListener 注册监听事件
     */
    public static void showCommonTips(final Context context, String title,
                                      String content, View.OnClickListener onClickListener) {
        commonTips = new Dialog(context, R.style.myDialog);
        commonTips.setContentView(R.layout.dialog_common_hint);
        TextView tvTitle = commonTips.findViewById(R.id.tvDialogTitle);
        TextView tvContent = commonTips.findViewById(R.id.tvDialogContent);
        TextView tvCancle = commonTips.findViewById(R.id.tvDialogCancle);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            MyTextUtil.setText(tvTitle, title);
        }
        MyTextUtil.setText(tvContent, content);
        commonTips.findViewById(R.id.tvDialogSure).setOnClickListener(onClickListener);// 注册监听事件
        //lambda写法
        tvCancle.setOnClickListener((view) ->
                commonTips.dismiss());
        //设置弹窗显示样式
        setDialogStyle(commonTips, DialogUtil.SCREEN_WIDTH_PER_80, false, 0);
        commonTips.setCanceledOnTouchOutside(false);
        commonTips.show();
    }

    /**
     * 取消通用的提示对话框
     */
    public static void cancleCommonTips() {
        if (commonTips != null) {
            commonTips.dismiss();
        }
    }

    private static Dialog dialogCall;

    /**
     * 通用的dialog对话框
     * 如：拨打电话、提示操作等
     *
     * @param context     上下文
     * @param contentTips 提示语
     * @param phoneNum    手机号
     */
    public static void callPhoneDialog(final Context context, String contentTips, String phoneNum) {
//        if (dialogCall != null) dialogCall.dismiss();
//        dialogCall = new Dialog(context, R.style.myDialog);
//        dialogCall.setContentView(R.layout.layout_dialog_call_phone_hint);
//        TextView tvDialogContent = dialogCall.findViewById(R.id.tvDialogContent);
//        tvDialogContent.setText(contentTips + phoneNum);//内容
//        TextView tvSure = dialogCall.findViewById(R.id.tvDialogSure);
//        TextView tvCancle = dialogCall.findViewById(R.id.tvDialogCancle);
//        tvSure.setOnClickListener(v -> {
//            CommonMethods.makeCallPhone((Activity) context, phoneNum);
//            dialogCall.dismiss();
//        });
//        tvCancle.setOnClickListener(v -> dialogCall.dismiss());
//        setDialogDisplayStyle(dialogCall);
//        dialogCall.setCanceledOnTouchOutside(false);
//        dialogCall.show();
    }

    /**
     * 请求权限的dialog弹窗
     *
     * @param context         上下文
     * @param title           标题
     * @param content         内容
     * @param onClickListener 监听者
     */
    public static void showPermissionsTips(Context context, String title, String content,
                                           View.OnClickListener onClickListener) {
        reqPerTips = new Dialog(context, R.style.myDialog);
        reqPerTips.setContentView(R.layout.dialog_common_hint);
        TextView tvTitle = reqPerTips.findViewById(R.id.tvDialogTitle);
        TextView tvContent = reqPerTips.findViewById(R.id.tvDialogContent);
        MyTextUtil.setText(tvTitle, title);
        MyTextUtil.setText(tvContent, content);
        TextView tvSure = reqPerTips.findViewById(R.id.tvDialogSure);
        tvSure.setOnClickListener(onClickListener);
        reqPerTips.findViewById(R.id.tvDialogCancle).setOnClickListener(view ->
                reqPerTips.dismiss()
        );
        //设置弹窗显示样式
        setDialogStyle(reqPerTips, DialogUtil.SCREEN_WIDTH_PER_80, false, -1);
        reqPerTips.setCanceledOnTouchOutside(false);
        reqPerTips.show();
//        LogUtils.logI("dialog被调用了！");
    }

    /**
     * 取消请求权限的对话框
     */
    public static void canclePermissionsTips() {
        if (reqPerTips != null) {
            reqPerTips.dismiss();
        }
    }

    /**
     * 修改性别Dialog
     *
     * @param activity 上下文
     * @param listener 监听事件
     */
    public static void selectGender(Activity activity, View.OnClickListener listener) {
//        sexDialog = new Dialog(activity, R.style.myDialog);
//        sexDialog.setContentView(R.layout.dialog_sex_select);
//        setDialogWidth(activity, sexDialog, 17, 18);
//        Button btnSelectOne = sexDialog.findViewById(R.id.btnSelectOne);// 男
//        Button btnSelectTwo = sexDialog.findViewById(R.id.btnSelectTwo);// 女
//        sexDialog.findViewById(R.id.btnCancle).setOnClickListener(view ->
//                sexDialog.dismiss()
//        );
//        btnSelectOne.setOnClickListener(listener);
//        btnSelectTwo.setOnClickListener(listener);
//        sexDialog.setCanceledOnTouchOutside(true);
//        Window window = sexDialog.getWindow();
//        if (null != window) {
//            window.setGravity(Gravity.BOTTOM);  //显示在底部
//            window.setWindowAnimations(R.style.dialog_get_pic_style);
//        }
//        sexDialog.show();
    }

    /**
     * 取消修改性别dialog
     */
//    public static void cancleSelectGender() {
//        if (sexDialog != null) {
//            sexDialog.dismiss();
//        }
//    }

    /**
     * 创建对话框：下载进度条
     */
    public static void downloadProgressDialog(final Context context) {
        mDialogPb = new Dialog(context, R.style.myDialog);
        mDialogPb.setContentView(R.layout.dialog_download_progressbar);
        setDialogDisplayStyle(mDialogPb);//弹出对话框Dialog充满屏幕宽度
        mDialogPb.setCanceledOnTouchOutside(false);
        mDialogPb.show();
    }

    /**
     * 获取：下载进度条控件
     */
    public static RxProgressBar getRxProgressBar() {
        if (mDialogPb != null) {
            RxProgressBar mRxProgressBar = mDialogPb.findViewById(R.id.round_fliker_progressbar);
            return mRxProgressBar;
        } else {
            return null;
        }
    }

    /**
     * 取消下载Apk的弹窗
     */
    public static void cancleProgressDialog() {
        if (mDialogPb != null) {
            mDialogPb.dismiss();
        }
    }


    /**
     * 调起本地地图App进行路线导航
     *
     * @param activity 上下文
     * @param listener 监听事件
     */
    public static void switchMapNavigationDialog(Activity activity, View.OnClickListener listener) {
        Dialog dialogSelectMap = new Dialog(activity, R.style.myDialog);
        dialogSelectMap.setContentView(R.layout.layout_dialog_swtich_map_navigation);
        setDialogWidth(activity, dialogSelectMap, 17, 18);
        Button btnMapGaode = dialogSelectMap.findViewById(R.id.btnMapGaode);
        Button btnMapBaidu = dialogSelectMap.findViewById(R.id.btnMapBaidu);
        Button btnMapTencent = dialogSelectMap.findViewById(R.id.btnMapTencent);
        dialogSelectMap.findViewById(R.id.btnCancle).setOnClickListener(view -> {
            dialogSelectMap.cancel();//取消
        });
        btnMapGaode.setOnClickListener(listener);
        btnMapBaidu.setOnClickListener(listener);
        btnMapTencent.setOnClickListener(listener);
        btnMapGaode.setVisibility(View.GONE); // 隐藏高德地图，因为不能设置途经点;而百度地图设置途经点最多3个
        btnMapTencent.setVisibility(View.GONE); // 隐藏腾讯地图
        dialogSelectMap.setCanceledOnTouchOutside(true);
        Window window = dialogSelectMap.getWindow();
        if (null != window) {
            window.setGravity(Gravity.BOTTOM);  //显示在底部
            window.setWindowAnimations(R.style.dialog_get_pic_style);
        }
        dialogSelectMap.show();
    }

    /**
     * 弹出简单的对话框
     * import android.support.v7.app.AlertDialog;
     *
     * @param context 上下文
     */
    public static void showAlertDialog(Context context) {
        new AlertDialog.Builder(context)
                .setPositiveButton(R.string.sure, (dialog, which) -> {
                    //确认按钮
                    ToastUtil.toast(context, "操作成功");
                })
                .setNegativeButton(R.string.cancel, null)//取消
                .setCancelable(false)
                .setTitle("提示")
//                .setIcon(R.mipmap.ic_logo)
                .setMessage("确定要退出吗？")
                .create().show();
    }

    /**
     * 弹出简单的对话框
     * import android.support.v7.app.AlertDialog;
     *
     * @param context 上下文
     */
    public static void showAlertDialog1(final Context context, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.create();
        builder
                .setPositiveButton(R.string.sure, (dialog, which) -> {
                    //确认按钮
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                    if (null != alertDialog)
                        alertDialog.dismiss();
                    //取消按钮
                })
                .setCancelable(false)
                .setTitle("提示")
//                .setIcon(R.mipmap.ic_logo)
                .setMessage("确定要退出吗？");

        alertDialog.show();
    }


}
