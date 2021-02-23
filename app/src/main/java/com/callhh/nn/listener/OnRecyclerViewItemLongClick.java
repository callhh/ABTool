package com.callhh.nn.listener;

import android.view.View;

public interface OnRecyclerViewItemLongClick {

    /**
     * 列表 行点击 长按事件
     * @param childView 视图控件
     * @param position  某行下标位置
     */
    void onItemLongClick(View childView, int position);

}
