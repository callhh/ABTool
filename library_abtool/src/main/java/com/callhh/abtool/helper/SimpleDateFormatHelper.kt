package com.callhh.abtool.helper

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间->String 转换类
 */
object SimpleDateFormatHelper {

    /**
     * 年-月 格式转换，输出：2020-01
     */
    val yearMonthFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM", Locale.CHINA)
    }

    /**
     * 年-月-日 格式转换，输出：2020-09-09
     */
    val yearMonthDayFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    }

    val yearMonthDayHourMinuteFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
    }

    val yearMonthDayHourMinuteSecondFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    }

    val hourMinuteFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("HH:mm", Locale.CHINA)
    }
}