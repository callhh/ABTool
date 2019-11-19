package com.tattle.tantou.view.footer;


import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.tattle.tantou.R;

/**
 * 自定义上拉加载更多的UI布局
 */

public final class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
