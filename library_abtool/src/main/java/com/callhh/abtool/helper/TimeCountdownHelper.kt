package com.callhh.abtool.helper

import android.graphics.Color
import android.os.CountDownTimer
import android.widget.TextView
import com.callhh.abtool.util.common.MyTextUtil
import java.text.DecimalFormat
import kotlin.math.floor

/**
 * 倒计时(xx分钟),通过CountDownTimer来实现倒计时的功能
 */
class TimeCountdownHelper(
    millisInFuture: Long,
    countDownInterval: Long,
    private val textView: TextView
) : CountDownTimer(millisInFuture, countDownInterval) {

    private var millisUntilFinished: Long = 0

    /**
     * onTick方法来获取时间的改变
     */
    override fun onTick(millisUntilFinished: Long) {
        //计时过程显示
        this.millisUntilFinished = millisUntilFinished
        textView.setTextColor(Color.parseColor("#FFFFFF"))
        textView.isClickable = false
        textView.textSize = 11.5.toFloat()
        //通过毫秒数获取分钟
        val floor = floor(millisUntilFinished / 60000.toDouble())
        //对当前毫秒数取余获取出去分钟后的秒数，保留2位
        val dec: DecimalFormat = DecimalFormat("##.##")
        val format = dec.format(millisUntilFinished % 60000 / 1000)
        //更新tv
        MyTextUtil.setText(textView, "$floor : $format")
    }

    override fun onFinish() { //计时完毕时触发
        textView.text = "刷新"
        textView.setTextColor(Color.parseColor("#FFFFFF"))
        textView.isClickable = true
    }

}