package com.callhh.nn.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.callhh.module_common.util.AppUtils;
import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.module_common.util.common.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment的基类
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Activity mActivity; //使用作为Context对象
    protected View mView;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(setLayoutId(), container, false);
            mUnbinder = ButterKnife.bind(this, mView);
            initView();
            addListener();
            initData();
        } else {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//		StatusBarUtil.setStatusBarColor(activityContext,R.color.white);
//		StatusBarUtil.StatusBarLightMode(activityContext);
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 查找view
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(View v, int id) {
        return (T) v.findViewById(id);
    }

    /**
     * view与数据绑定
     */
    protected void initView() {

    }

    /**
     * 设置监听
     */
    protected void addListener() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 简单的页面跳转
     *
     * @param clz 对应Activity页面
     */
    protected void jumpPage(Class<?> clz) {
        AppUtils.startActivity(mActivity, clz);
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
//        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.navigationBarWithKitkatEnable(false).init();
    }


    /**
     * print logcat : tag="callhh"
     */
    public void logPrint(String message) {
        MyLogUtils.logI(message);
    }

    /**
     * 通知提示,默认在中间
     */
    public void toastShow(String content) {
        ToastUtil.toastCenter(mActivity, content);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 统计用户访问activity时长
//        StatService.trackBeginPage(mActivity, getClass().getSimpleName());
//        StatService.onResume(mActivity);
    }

    @Override
    public void onPause() {
        super.onPause();
//        StatService.trackEndPage(mActivity, getClass().getSimpleName());
//        StatService.onPause(mActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLogUtils.logI(" BaseFragment onDestroy ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mUnbinder) mUnbinder.unbind();
        MyLogUtils.logI(" BaseFragment onDestroyView ");
    }

    @Override
    public void onClick(View view) {
        //点击监听事件
    }
}
