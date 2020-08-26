package com.callhh.nn.ui

import android.view.KeyEvent
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.callhh.abtool.util.common.ToastUtil
import com.callhh.nn.R
import com.callhh.nn.base.BaseActivity
import com.callhh.nn.ui.fragment.TabOneFragment
import com.callhh.nn.ui.fragment.TabThreeFragment
import com.callhh.nn.ui.fragment.TabTwoFragment
import kotlinx.android.synthetic.main.layout_home_tab_bottom.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * App首页
 * 底部tab菜单：首页1、首页2、首页3
 */
class MainActivity : BaseActivity() {

    private lateinit var tvList: ArrayList<TextView>
    private lateinit var fragments: ArrayList<Fragment>
    private var oldIndex = 0
    private var isExit = false

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        tvTabOne.isSelected = true
        initFragments()
    }

    override fun initData() {
        checkVerisonAutoUpdate()
    }

    override fun initListener() {
        tvTabOne.setOnClickListener {
            setTabSelected(0)
        }
        tvTabTwo.setOnClickListener {
            setTabSelected(1)
        }
        tvTabThree.setOnClickListener {
            setTabSelected(2)
        }
    }

    private fun checkVerisonAutoUpdate() {
        GlobalScope.launch {
            delay(1500)
            launch(Dispatchers.Main) {
                //                UpdateAppHelper(this@MainActivity).updateAPP()
            }
        }
    }

    private fun initFragments() {
        tvList = ArrayList<TextView>().apply {
            add(tvTabOne)
            add(tvTabTwo)
            add(tvTabThree)
        }
        val tabOneFragment = TabOneFragment()
        val tabTwoFragment = TabTwoFragment()
        val tabThreeFragment = TabThreeFragment()
        fragments = ArrayList<Fragment>().apply {
            add(tabOneFragment)
            add(tabTwoFragment)
            add(tabThreeFragment)
        }
        //默认加载前两个Fragment，其中第一个展示，第二个隐藏
        supportFragmentManager.beginTransaction().add(R.id.content, tabOneFragment)
            .add(R.id.content, tabTwoFragment).hide(tabTwoFragment).show(tabOneFragment).commit()
    }

    /**
     * tab菜单选择
     *
     * @param curIndex 当前tab下标
     */
    private fun setTabSelected(curIndex: Int) {
        if (curIndex != oldIndex) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            tvTabOne.isSelected = false
            tvList[oldIndex].isSelected = false
            tvList[curIndex].isSelected = true
            ft.hide(fragments[oldIndex])
            //判断是否添加
            if (!fragments[curIndex].isAdded) {
                ft.add(R.id.content, fragments[curIndex]).show(fragments[curIndex]).commit()
            } else {
                ft.show(fragments[curIndex]).commit()
            }
        }
        oldIndex = curIndex
    }

    /**
     * 双击退出
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (isExit) {
                finish()
            } else {
                ToastUtil.toastBottomView(mActivity, resources.getString(R.string.app_exit_tips))
                GlobalScope.launch {
                    delay(2000)
                    isExit = false
                }
                isExit = true
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}