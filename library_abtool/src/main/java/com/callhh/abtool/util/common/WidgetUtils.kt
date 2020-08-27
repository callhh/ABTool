package com.callhh.abtool.util.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * 组件工具类
 */
object WidgetUtils {
    fun px2dp(context: Context, px: Int): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }

    fun dp2px(context: Context, dp: Float): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }

    fun getColor(context: Context,@ColorRes resId: Int): Int {
        @Suppress("DEPRECATION")
        return if (Build.VERSION.SDK_INT < 23) {
            context.resources.getColor(resId)
        } else {
            context.getColor(resId)
        }
    }

    fun getDrawable(context: Context,@DrawableRes resId: Int): Drawable? {
        @Suppress("DEPRECATION")
        return if (Build.VERSION.SDK_INT < 21) {
            context.resources.getDrawable(resId)
        } else {
            context.getDrawable(resId)
        }
    }
}