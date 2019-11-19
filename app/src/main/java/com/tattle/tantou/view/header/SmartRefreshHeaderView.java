package com.tattle.tantou.view.header;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.module_common.util.common.MyTextUtil;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.tattle.tantou.R;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

/**
 * Smart下拉刷新自定义HeaderView
 */
public class SmartRefreshHeaderView extends LinearLayout implements RefreshHeader {

    public static String REFRESH_HEADER_PULLING = "下拉可以刷新";
    public static String REFRESH_HEADER_REFRESHING = "正在刷新...";
    public static String REFRESH_HEADER_LOADING = "正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    public static String REFRESH_HEADER_FINISH = "刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";
    public static String REFRESH_HEADER_UPDATE = "上次更新 M-d HH:mm";
    public static String REFRESH_HEADER_SECONDARY = "释放进入二楼";//;
    //    public static String REFRESH_HEADER_UPDATE = "'Last update' M-d HH:mm";
    protected int mFinishDuration = 100;
    private TextView mTvTitleText;
    private TextView mTvSubtitle;
    private ImageView mImgArrow;
    private ImageView mImgProgressView;

    //<editor-fold desc="RelativeLayout">
    public SmartRefreshHeaderView(Context context) {
        super(context);
        this.initView(context);
    }

    public SmartRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public SmartRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }


    private void initView(Context context) {
        setMinimumHeight(DensityUtil.dp2px(80));
        setGravity(CENTER_IN_PARENT);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        View view = View.inflate(context, R.layout.view_head_refresh, null);
        addView(view, params);
        mTvTitleText = findViewById(R.id.tvTitleText);
        mTvSubtitle = findViewById(R.id.tvSubtitle);
        mImgArrow = findViewById(R.id.imgArrow);
        mImgProgressView = findViewById(R.id.imgProgressView);
        mImgProgressView.animate().setInterpolator(new LinearInterpolator());
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
//        MyTextUtil.setText(mTvSubtitle, ConstUtils.REFRESH_TIPS_LIST[
//                new Random().nextInt(ConsUtils.REFRESH_TIPS_LIST.length)]);
        MyTextUtil.setText(mTvSubtitle, getResources().getString(R.string.app_slogan));
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        onStartAnimator(refreshLayout, height, maxDragHeight);
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
        final View progressView = mImgProgressView;
        if (progressView.getVisibility() != VISIBLE) {
            progressView.setVisibility(VISIBLE);
            Drawable drawable = mImgProgressView.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            } else {
                progressView.animate().rotation(36000).setDuration(100000);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            final View arrowView = mImgArrow;
            final View progressView = mImgProgressView;
            arrowView.animate().cancel();
            progressView.animate().cancel();
        }
        final Drawable drawable = mImgProgressView.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        }
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        final View progressView = mImgProgressView;
        Drawable drawable = mImgProgressView.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        } else {
            progressView.animate().rotation(0).setDuration(0);
        }
        progressView.setVisibility(GONE);
        return mFinishDuration;//延迟xx毫秒之后再弹回
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    /**
     * 下拉刷新状态改变监听，参考smartrefresh源码
     */
    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        final View arrowView = mImgArrow;
        switch (newState) {
            case None:
            case PullDownToRefresh:
//                mTvTitleText.setText(REFRESH_HEADER_PULLING);
                arrowView.setVisibility(VISIBLE);
                arrowView.animate().rotation(0);
                break;
            case Refreshing:
//                LogUtils.logI(" -- --- Refreshing  - -- ");
                arrowView.setVisibility(GONE);
                break;
            case RefreshReleased:
//                mTvTitleText.setText(REFRESH_HEADER_REFRESHING);
                arrowView.setVisibility(GONE);
                break;
            case ReleaseToRefresh:
//                mTvTitleText.setText(REFRESH_HEADER_RELEASE);
                arrowView.animate().rotation(180);
                break;
            case ReleaseToTwoLevel:
//                mTvTitleText.setText(REFRESH_HEADER_SECONDARY);
                arrowView.animate().rotation(0);
                break;
            case Loading:
//                mTvTitleText.setText(REFRESH_HEADER_LOADING);
                arrowView.setVisibility(GONE);
                break;
            case RefreshFinish:
//                MyTextUtil.setText(mTvSubtitle, "刷新完了 Refreshing");
                if (null != mTvSubtitle)
                    try {
                        mTvSubtitle.postDelayed(() ->
//                                        MyTextUtil.setText(mTvSubtitle, ConsUtils.REFRESH_TIPS_LIST[
//                                                new Random().nextInt(ConsUtils.REFRESH_TIPS_LIST.length)])
                                MyTextUtil.setText(mTvSubtitle, getResources().getString(R.string.app_slogan))
                                , 300);

                    } catch (Exception e) {
                        MyLogUtils.logI(e.toString());
                    }
                break;
        }
    }

}
