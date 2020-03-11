package com.callhh.nn.listener;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 问题：RecyclerView在刷新数据的时候会出现以下异常：IndexOutOfBoundsException: Inconsistency detected
 *      .. Invalid view holder adapter positionViewHolder{837c511 position=3 id=-1, oldPos=-1, pLpos:-1 no parent} android.support.v7.widget.RecyclerView
 * 解决：官方bug,重新定义一个类继承LinearLayoutManager,去捕获底层异常，就不会奔溃了
 */
public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
