package com.callhh.nn.util.okgo.callbck;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.Window;
import android.widget.FrameLayout;

import com.callhh.nn.R;
import com.lzy.okgo.request.base.Request;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：对于网络请求是否需要弹出进度对话框
 * 修订历史：
 * ================================================
 */
public abstract class ShowDialogCallback<T> extends JsonCallback<T> {

    private Dialog mLoadingDialog;

    public ShowDialogCallback(Activity activity, boolean isShow) {
        super();
        initDialog(activity, isShow);
    }

    private void initDialog(Activity activity, boolean isShow) {
        if (!isShow) return;
        mLoadingDialog = new Dialog(activity, R.style.myLoadingDialog);
        mLoadingDialog.setContentView(R.layout.layout_loading_dialog_fullscreen);
        FrameLayout view = mLoadingDialog.findViewById(R.id.flDialogLoading);
        view.getBackground().setAlpha(120);
        mLoadingDialog.setCancelable(true);// 点击返回键无效
        mLoadingDialog.setCanceledOnTouchOutside(false);  //点击对话框外区域 取消对话框
        mLoadingDialog.show();
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
