package com.callhh.sx.reception.view

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.flyco.tablayout.utils.FragmentChangeManager

/**
 * TabLayout封装
 */
class TabLayout: CommonTabLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    fun setViewPager(viewpager: ViewPager) {
        viewpager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentTab = position
            }

        })

        setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewpager.currentItem = position
            }

            override fun onTabReselect(position: Int) {
                viewpager.currentItem = position
            }
        })
    }

    fun setTabCurrentItem(position : Int) {
        currentTab = position
    }

    fun setData(tabDataList: ArrayList<CustomTabEntity>) {
        setTabData(tabDataList)
    }

    /** 关联数据支持同时切换fragments  */
    fun setTabData(tabEntities: ArrayList<CustomTabEntity>
                   , fragmentManager: FragmentManager, containerViewId: Int
                   , fragments: java.util.ArrayList<Fragment>) {
        val field = CommonTabLayout::class.java.getDeclaredField("mFragmentChangeManager")
        field.isAccessible = true
        field.set(this, FragmentChangeManager(fragmentManager, containerViewId, fragments))
        setTabData(tabEntities)
    }

    fun setOnTabSelect(listener: (position: Int) -> Unit) {
        setOnTabSelectListener(object: OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                listener(position)
            }

            override fun onTabReselect(position: Int) {
                listener(position)
            }
        })
    }

    data class TabData(val title: String): CustomTabEntity {

        constructor(title: String, selectRes: Int, unSelectRes: Int): this(title) {
            this.selectRes = selectRes
            this.unSelectRes = unSelectRes
        }

        private var selectRes: Int = 0
        private var unSelectRes: Int = 0

        override fun getTabUnselectedIcon(): Int {
            return unSelectRes
        }

        override fun getTabSelectedIcon(): Int {
            return selectRes
        }

        override fun getTabTitle(): String {
            return title
        }

        fun setTabUnselectedIcon(res: Int): TabData {
            unSelectRes = res
            return this
        }

        fun setTabSelectedIcon(res: Int): TabData {
            selectRes = res
            return this
        }
    }
}