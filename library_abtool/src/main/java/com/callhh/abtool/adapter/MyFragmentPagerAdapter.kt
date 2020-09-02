package com.callhh.abtool.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * viewpager 通用适配器
 */
@SuppressLint("WrongConstant")
class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<String>()

    constructor(fm: FragmentManager, fragments: List<Fragment>, titles: List<String>) : this(fm) {
        if (fragments.size != titles.size) {
            throw IllegalArgumentException("传入的 fragment 和 title 数量不一致")
        }
        this.fragments.addAll(fragments)
        this.titles.addAll(titles)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}