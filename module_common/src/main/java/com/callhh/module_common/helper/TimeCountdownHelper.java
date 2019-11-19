package com.callhh.module_common.helper;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.callhh.module_common.util.common.MyTextUtil;

import java.text.DecimalFormat;

/**
 * 倒计时(xx分钟),通过CountDownTimer来实现倒计时的功能
 */
public class TimeCountdownHelper extends CountDownTimer {

    private TextView mTextView;
    private long millisUntilFinished;

    public TimeCountdownHelper(long millisInFuture, long countDownInterval, final TextView textView) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔,要显示的按钮
        this.mTextView = textView;
    }

    /**
     * onTick方法来获取时间的改变
     */
    @Override
    public void onTick(long millisUntilFinished) {
        //计时过程显示
        this.millisUntilFinished = millisUntilFinished;
        mTextView.setTextColor(Color.parseColor("#FFFFFF"));
        //button.setBackgroundResource(R.drawable.send_code_wait);
        mTextView.setClickable(false);
        mTextView.setTextSize((float) 11.5);
        DecimalFormat dec = new DecimalFormat("##.##");
        //Math.floor(millisUntilFinished / 60000)是通过毫秒数获取分钟
        //dec.format((millisUntilFinished % 60000) / 1000)是对当前毫秒数取余获取出去分钟后的秒数，保留2位
        MyTextUtil.setText(mTextView, (int) Math.floor(millisUntilFinished / 60000) + " : "
                + dec.format((millisUntilFinished % 60000) / 1000));
    }

    @Override
    public void onFinish() {//计时完毕时触发
        mTextView.setText("刷新");
        mTextView.setTextColor(Color.parseColor("#FFFFFF"));
        // button.setBackgroundResource(R.drawable.send_code);
        mTextView.setClickable(true);
    }

}
