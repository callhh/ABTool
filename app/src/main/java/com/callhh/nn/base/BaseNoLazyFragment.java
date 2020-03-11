package com.callhh.nn.base;

/**
 * Fragment的基类,禁止懒加载功能
 */
public abstract class BaseNoLazyFragment extends BaseLazyFragment {

    @Override
    protected boolean isLazyLoad() {
        return false;
    }
}
