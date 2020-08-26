package com.callhh.abtool.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.callhh.abtool.R;
import com.callhh.abtool.util.common.MyTextUtil;

/**
 * 请求数据前的加载中布局、
 * MyLoadingLayout1中心内容居中
 */
public class MyLoadingLayout extends RelativeLayout implements OnClickListener {

    private int mShortAnimationDuration = 250;
    private LinearLayout mLlLoading;
    private ImageView mImgErrorIcon;
    private TextView mTvReload;
    private TextView mTvErrorTips;
    public OnRetryListenner onRetryListenner;
    private boolean isFailed = false;
    private LinearLayout mLlLoadView;
    private View mThisView;

    /**
     * 点击重试的接口
     */
    public interface OnRetryListenner {
        void onRetry();
    }

    /**
     * 点击重试监听事件
     *
     * @param onRetryListenner 自定义监听重试的接口
     */
    public void setOnRetryListenner(OnRetryListenner onRetryListenner) {
        this.onRetryListenner = onRetryListenner;
    }

    public MyLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public MyLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyLoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading, null);
        mLlLoadView = view.findViewById(R.id.llLoadView);
        mLlLoadView.setOnClickListener(this);//整个布局，监听全局点击重新刷新
        mLlLoading = view.findViewById(R.id.llLoading);//加载中的view
        mImgErrorIcon = view.findViewById(R.id.imgError);//发生错误或者没有内容时显示的图片
//        mPbLoading.animate().setInterpolator(new LinearInterpolator());//匀速插值器，防止卡頓
        mTvErrorTips = view.findViewById(R.id.tvErrorTips);// 无内容或无网络等异常错误提示
        mTvReload = view.findViewById(R.id.tvReload);//重新加载按钮
        mTvErrorTips.setOnClickListener(this);
        mTvReload.setOnClickListener(this);
        mTvReload.setVisibility(View.GONE);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mThisView = this;
        final ViewGroup thisGroup = this;
        thisGroup.addView(view, params);
        thisGroup.setLayoutParams(params);
        mThisView.setVisibility(View.GONE);
//        this.setGravity(Gravity.CENTER);
    }

    /**
     * 开始加载
     */
    public void setLoading() {
        if (null == mLlLoadView) return;
        mLlLoadView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        mThisView.setBackgroundColor(0XFF000000);
                        mThisView.setVisibility(View.VISIBLE);
                        mTvErrorTips.setVisibility(View.GONE);
                        mImgErrorIcon.setVisibility(View.GONE);
                        mTvReload.setVisibility(View.GONE);
                        mLlLoading.setVisibility(View.VISIBLE);
                        isFailed = false;

                    }
                });
    }

    /**
     * 设置加载的时黑色背景,这个方法的使用前提是,加载动画中的图片要是除了图的部分其他部分要是透明的.
     */
    public void setLoadingBlackBG() {
        if (null == mLlLoadView) return;
        mLlLoadView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mThisView.setBackgroundColor(0XFF000000);
                        mThisView.setVisibility(View.VISIBLE);
                        mTvErrorTips.setVisibility(View.GONE);
                        mImgErrorIcon.setVisibility(View.GONE);
                        mTvReload.setVisibility(View.GONE);
                        mLlLoading.setVisibility(View.VISIBLE);
                        isFailed = false;

                    }
                });
    }

    /**
     * 加载成功,影藏加载布局
     */
    public void setLoadingSuccess() {
        if (null == mLlLoadView) return;
        mLlLoadView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mThisView.setVisibility(View.GONE);
                        mLlLoading.setVisibility(View.GONE);
                        mTvErrorTips.setVisibility(View.GONE);
                        mImgErrorIcon.setVisibility(View.GONE);
                        mTvReload.setVisibility(View.GONE);
                        isFailed = false;
                    }
                });
//        this.setAlpha(0);
//        this.setVisibility(View.GONE);
//        mLlLoading.setVisibility(View.GONE);
//        mTvErrorTips.setVisibility(View.GONE);
//        mImgErrorIcon.setVisibility(View.GONE);
//        mTvReload.setVisibility(View.GONE);
//        isFailed = false;
    }

    /**
     * 加载失败，点击重新刷新数据
     */
    public void setLoadingFailed() {
        if (null == mLlLoadView) return;
        mLlLoadView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mThisView.setVisibility(View.VISIBLE);
                        mLlLoading.setVisibility(View.GONE);
                        mImgErrorIcon.setVisibility(View.VISIBLE);
                        mImgErrorIcon.setImageResource(R.drawable.ic_load_fail_def_nothing);
                        mTvErrorTips.setVisibility(View.VISIBLE);
                        mTvReload.setVisibility(View.VISIBLE);
                        mTvReload.setText(getResources().getString(R.string.request_error_no_wifi_to_reload));
                        isFailed = true;

                    }
                });
    }

    /**
     * 加载失败的异常文本提示，可触发重新加载按钮。
     * 场景如：网络加载超时、接口异常、json解析出错、空指针等问题
     *
     * @param hints 提示语
     */
    public void setLoadedFailHints(final String hints) {
        if (null == mLlLoadView) return;
        mLlLoadView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mThisView.setVisibility(View.VISIBLE);
                        mLlLoading.setVisibility(View.GONE);
                        mImgErrorIcon.setVisibility(View.VISIBLE);
                        mImgErrorIcon.setImageResource(R.drawable.ic_load_fail_def_nothing);
                        mTvErrorTips.setVisibility(View.VISIBLE);
                        MyTextUtil.setText(mTvErrorTips, hints);
                        mTvReload.setVisibility(View.VISIBLE);
                        mTvReload.setText(getResources().getString(R.string.request_error_no_wifi_to_reload));
                        isFailed = true;

                    }
                });
    }

    /**
     * 加载失败的提示，带图标。无重新刷新按钮
     * 场景：列表页无数据
     * @param hints 提示语
     * @param imgId 图片Id
     */
    public void setLoadedFailHints(final String hints, final int imgId) {
        if (null == mLlLoadView) return;
        mLlLoadView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mThisView.setVisibility(View.VISIBLE);
                        mLlLoading.setVisibility(View.GONE);
                        mImgErrorIcon.setVisibility(View.VISIBLE);
                        mImgErrorIcon.setImageResource(imgId);
                        mTvErrorTips.setVisibility(View.VISIBLE);
                        MyTextUtil.setText(mTvErrorTips, hints);
                        mTvReload.setVisibility(View.GONE);
                        isFailed = true;

                    }
                });
    }

    /**
     * 加载失败再次刷新
     *
     * @param hints 提示语
     */
    public void setLoadedFailToRefreshAgain(String hints) {
        mThisView.setAlpha(1);
        mThisView.setVisibility(View.VISIBLE);
        mLlLoading.setVisibility(View.GONE);
        mImgErrorIcon.setVisibility(View.GONE);
        mTvErrorTips.setVisibility(View.VISIBLE);
        MyTextUtil.setText(mTvErrorTips, hints + "\n点击空白区域刷新试试");
        mTvReload.setVisibility(View.GONE);
        isFailed = true;
    }

    /**
     * 加载中的view布局是否显示
     */
    public boolean isShowLoadingView() {
        if (null == mThisView) return false;
        return View.VISIBLE == mThisView.getVisibility();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvErrorTips || id == R.id.tvReload) {//如果加载失败，点击重新加载
            if (isFailed) {
                setLoadingSuccess();
                onRetryListenner.onRetry();
            }
        }
    }

}
