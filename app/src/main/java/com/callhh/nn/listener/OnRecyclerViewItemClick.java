package com.callhh.nn.listener;

import android.view.View;

public interface OnRecyclerViewItemClick {

    /**
     * 列表 行点击 点击事件
     * @param childView 视图控件
     * @param position  某行下标位置
     */
    void onItemClick(View childView, int position);

}
