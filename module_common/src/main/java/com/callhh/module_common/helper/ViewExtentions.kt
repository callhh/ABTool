package com.callhh.module_common.helper

import android.view.View

/**
 *
 * Created by admin on 2020/4/28
 * View处理
 */

fun View.visible() {
    if (this.visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (this.visibility != View.GONE) {
        visibility = View.GONE
    }
}
