package com.callhh.nn.view.footer;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Smart上拉加载更多 自定义View
 */
public class SmartRefreshFooterView extends RelativeLayout implements RefreshFooter {


    public static String REFRESH_FOOTER_PULLUP = "上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = "释放立即加载";
    public static String REFRESH_HEADER_REFRESHING = "正在刷新...";
    public static String REFRESH_FOOTER_LOADING = "正在加载...";
    public static String REFRESH_FOOTER_FINISH = "加载完成";
    public static String REFRESH_FOOTER_FAILED = "加载失败";
    public static String REFRESH_FOOTER_ALLLOADED = "全部加载完成";

    private TextView mBottomText;
    private ImageView mArrowView;
    private ImageView mProgressView;
    private ProgressDrawable mProgressDrawable;
    private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private RefreshKernel mRefreshKernel;
    private int mBackgroundColor = 0;
    private boolean mLoadmoreFinished = false;

    private Animation loadRotateAnimation;

    //<editor-fold desc="LinearLayout">
    public SmartRefreshFooterView(Context context) {
        super(context);
        this.initView(context, null, 0);
    }

    public SmartRefreshFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context, attrs, 0);
    }

    public SmartRefreshFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        DensityUtil density = new DensityUtil();
        setMinimumHeight(density.dip2px(60));

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        addView(view, lp);
//        mProgressView = (ImageView) findViewById(R.id.imgSmartProgressBar);
//        mBottomText = (TextView) findViewById(R.id.tvBottomText);//文本提示
//        // 初始化加载ImageView转圈动画
//        loadRotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_animation);
//        loadRotateAnimation.setInterpolator(new LinearInterpolator());
//        mProgressView.animate().setInterpolator(new LinearInterpolator());

    }

    //</editor-fold>

    //<editor-fold desc="RefreshFooter">

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
//        mRefreshKernel = kernel;
//        mRefreshKernel.requestDrawBackgoundForFooter(mBackgroundColor);
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

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
        if (!mLoadmoreFinished) {

//            mProgressView.setVisibility(VISIBLE);
//            if (loadRotateAnimation != null) {
//                loadRotateAnimation.start();
//            } else {
//                mProgressView.animate().rotation(36000).setDuration(100000);
//            }

            if (loadRotateAnimation != null) {
                mProgressView.startAnimation(loadRotateAnimation);//开始动画
            }
        }
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        if (!mLoadmoreFinished) {
//            if (mProgressDrawable != null) {
//                mProgressDrawable.stop();
//            } else {
//                mProgressView.animate().rotation(0).setDuration(300);
//            }
//            mProgressView.setVisibility(GONE);

            if (loadRotateAnimation != null) {
                mProgressView.clearAnimation();//清除动画
            }
            return 50;//延迟500毫秒之后再弹回
        }
        return 0;
    }

    /**
     * ClassicsFooter 没有主题色
     * ClassicsFooter has no primary colors
     */
    @Override
    public void setPrimaryColors(int... colors) {
//        if (mSpinnerStyle == SpinnerStyle.FixedBehind) {
//            if (colors.length > 1) {
//                setBackgroundColor(mBackgroundColor = colors[0]);
//                if (mRefreshKernel != null) {
//                    mRefreshKernel.requestDrawBackgoundForFooter(mBackgroundColor);
//                }
//                mBottomText.setTextColor(colors[1]);
//                if (mProgressDrawable != null) {
//                    mProgressDrawable.setColor(colors[1]);
//                }
//                if (mArrowDrawable != null) {
//                    mArrowDrawable.parserColors(colors[1]);
//                }
//            } else if (colors.length > 0) {
//                setBackgroundColor(mBackgroundColor = colors[0]);
//                if (mRefreshKernel != null) {
//                    mRefreshKernel.requestDrawBackgoundForFooter(mBackgroundColor);
//                }
//                if (colors[0] == 0xffffffff) {
//                    mBottomText.setTextColor(0xff666666);
//                    if (mProgressDrawable != null) {
//                        mProgressDrawable.setColor(0xff666666);
//                    }
//                    if (mArrowDrawable != null) {
//                        mArrowDrawable.parserColors(0xff666666);
//                    }
//                } else {
//                    mBottomText.setTextColor(0xffffffff);
//                    if (mProgressDrawable != null) {
//                        mProgressDrawable.setColor(0xffffffff);
//                    }
//                    if (mArrowDrawable != null) {
//                        mArrowDrawable.parserColors(0xffffffff);
//                    }
//                }
//            }
//        }
    }

    @NonNull
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (!mLoadmoreFinished) {
            switch (newState) {
                case None:
//                    restoreRefreshLayoutBackground();
                    break;
                case PullDownToRefresh:
                    //下拉刷新
                    break;
                case PullUpToLoad:
                    //上拉加载更多
//                    mBottomText.setText(REFRESH_FOOTER_PULLUP);
                    break;
                case Loading:
                    //正在加载...
//                    mBottomText.setText(REFRESH_FOOTER_LOADING);
                    break;
                case ReleaseToLoad:
                    //释放立即加载
//                    mBottomText.setText(REFRESH_FOOTER_RELEASE);
//                    replaceRefreshLayoutBackground(refreshLayout);
                    break;
                case Refreshing:
                    //正在刷新...
//                    mBottomText.setText(REFRESH_HEADER_REFRESHING);
                    break;
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Background">
//    private Runnable restoreRunable;
//    private void restoreRefreshLayoutBackground() {
//        if (restoreRunable != null) {
//            restoreRunable.run();
//            restoreRunable = null;
//        }
//    }
//
//    private void replaceRefreshLayoutBackground(final RefreshLayout refreshLayout) {
//        if (restoreRunable == null && mSpinnerStyle == SpinnerStyle.FixedBehind) {
//            restoreRunable = new Runnable() {
//                Drawable drawable = refreshLayout.getLayout().getBackground();
//                @Override
//                public void run() {
//                    refreshLayout.getLayout().setBackgroundDrawable(drawable);
//                }
//            };
//            refreshLayout.getLayout().setBackgroundDrawable(getBackground());
//        }
//    }
    //</editor-fold>

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }
    //</editor-fold>

}
