package com.callhh.module_common.extension

import android.view.View

/**
 *
 * View 扩展事件类
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
