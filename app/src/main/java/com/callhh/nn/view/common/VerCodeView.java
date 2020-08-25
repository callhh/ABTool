package com.callhh.nn.view.common;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义获取手机验证码的倒计时控件（点击发送验证码，60s倒计时）
 */
public class VerCodeView extends AppCompatTextView {
    private TimeCount time;

    public VerCodeView(Context context) {
        super(context);
        initView();
    }

    public VerCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    private void initView() {
        // 定时器
        time = new TimeCount(60 * 1000, 1000);
    }

    /**
     * 请求发送发验证码，60秒倒计时
     *
     * @param phone    用户手机号
     */
    public void getSendVerCode(Context context, String phone) {
//        Map<String, String> params = ApiRequestUtils.getSendCodeParams(context,phone);
//        LogUtils.logI("getSendVerCode() reqParams: " + params);
//        ApiRequestUtils.post(context, HttpResponseStatus.URL_GET_SEND_CODE, params
//                ,true, new ResultCallBack() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    LogUtils.logI(response);
//                    startCountdown();
//                    BaseBody baseBody = JsonUtil.parseJSON(response, BaseBody.class);
//                    ToastUtil.toastCenter(context
//                            , getResources().getString(R.string.input_receive_ver_code_notice));
//                } catch (Exception e) {
//                    ToastUtil.toastCenter(context, e.getMessage());
//                    LogUtils.logI(e.toString());
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//                ToastUtil.toastCenter(context, e.getMessage());
//            }
//        });

    }

    /**
     * 开始倒计时
     */
    void startCountdown() {
        time.start();
    }

    // 定时器类
    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            setText("获取验证码");
            setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            setText(millisUntilFinished / 1000 + "s");
            setEnabled(false);
        }
    }
}
