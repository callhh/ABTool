package com.callhh.nn.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.callhh.abtool.util.ActivityManager;
import com.callhh.abtool.util.MyAppUtils;
import com.callhh.abtool.util.common.MyLogUtils;
import com.callhh.abtool.util.common.ToastUtil;
import com.callhh.nn.R;

import butterknife.ButterKnife;

/**
 * Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Activity mActivity; //使用作为Context对象
    protected Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        //绑定控件
        ButterKnife.bind(this);
        mActivity = this;
        ActivityManager.getInstance().addActivity(mActivity);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            MyLogUtils.logI("OpenActivity ====== " + this.getClass().getSimpleName());
        }
        initView();
        initData();
        initListener();
    }

    protected abstract int setLayoutId();

    /**
     * 查找view
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(View v, int id) {
        return (T) v.findViewById(id);
    }

    /**
     * 初始化xml视图
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {
    }

    /**
     * 跳转页面
     *
     * @param clz 对应Activity页面
     */
    protected void jumpPage(Class<?> clz) {
        MyAppUtils.startActivity(mActivity, clz);
    }

    /**
     * 通知提示,默认在中间
     */
    protected void toastShow(String content) {
        ToastUtil.toastCenter(mActivity, content);
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 固定加载框(无提示语句)
     */
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.myDialog);
        }
        progressDialog.setContentView(R.layout.layout_loading_dialog_fullscreen);
        progressDialog.setCancelable(false);// 不可以用“返回键”取消
        if (null != progressDialog.getWindow())
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
    }

    /**
     * 取消加载框
     */
    public void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 统计用户访问activity时长
//        StatService.trackBeginPage(mActivity, getClass().getSimpleName());
//        StatService.onResume(mActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // onResume和onPause或trackBeginPage和trackEndPage需要成对使用才能正常统计activity
//        StatService.trackEndPage(mActivity, getClass().getSimpleName());
//        StatService.onPause(mActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }

    @Override
    public void onClick(View view) {
    }
}
