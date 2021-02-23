package com.callhh.nn.ui.fragment

import com.callhh.abtool.util.common.MyLogUtils
import com.callhh.nn.R
import com.callhh.nn.base.BaseFragment

/**
 * 首页Tab4 fourth fragment
 * 【购物车】
 */
class TabFourFragment : BaseFragment() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_tab_four
    }

    override fun initView() {
        MyLogUtils.logI("start TabMyFragment- 首页tab4 ")
    }
}