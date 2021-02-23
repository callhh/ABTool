package com.callhh.nn.ui.fragment

import com.callhh.abtool.util.common.MyLogUtils
import com.callhh.nn.R
import com.callhh.nn.base.BaseFragment

/**
 * 首页Tab5 fifth fragment
 * 【我的】
 */
class TabFiveFragment : BaseFragment() {

    override fun setLayoutId(): Int {
        return R.layout.fragment_tab_five
    }

    override fun initView() {
        MyLogUtils.logI("start TabMyFragment- 首页tab5 ")
    }

}