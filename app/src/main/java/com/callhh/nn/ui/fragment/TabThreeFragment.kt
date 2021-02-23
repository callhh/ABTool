package com.callhh.nn.ui.fragment

import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.CancellationSignal
import androidx.recyclerview.widget.RecyclerView
import com.callhh.abtool.util.common.MyLogUtils
import com.callhh.nn.R
import com.callhh.nn.base.BaseFragment
import com.callhh.nn.ui.common.WebActivity
import kotlinx.android.synthetic.main.fragment_tab_three.*

/**
 * 首页Tab3 third fragment
 * 【发现】
 */
class TabThreeFragment : BaseFragment() {

    private val webUrl = "http://ssp.iport.com.cn:8080/easyphoto/wap/wx/newcreate.html"

    override fun setLayoutId(): Int {
        return R.layout.fragment_tab_three
    }

    override fun initView() {
        MyLogUtils.logI("start TabThreeFragment- 首页tab3 ")

    }

    override fun addListener() {
        tvOpenWebBtn.setOnClickListener {
            WebActivity.openWebActivity(activity, webUrl, "WEB信息")
        }
        tvTestBtn1.setOnClickListener {
            startBiometricPrompt()
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })


    }

    /**
     * 开启指纹识别测试
     */
    private fun startBiometricPrompt() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val biometricPrompt = BiometricPrompt.Builder(mActivity)
                .setTitle("指纹验证")
                .setSubtitle("小标题")
                .setDescription("描述一下")
                .setNegativeButton(
                    "取消",
                    mActivity.mainExecutor,
                    DialogInterface.OnClickListener { dialog, which ->
                        MyLogUtils.logI("取消 指纹识别")
                    }
                )
                .build()
            //触发指纹识别的事件回调：错误/帮助/失败/成功
            biometricPrompt.authenticate(
                CancellationSignal(),
                mActivity.mainExecutor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errorCode, errString)
                        MyLogUtils.logI("onAuthenticationError errorCode: $errorCode errString: $errString")
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                        MyLogUtils.logI("onAuthenticationSucceeded")
                        if (result?.cryptoObject != null) {
                            MyLogUtils.logI("result = " + result.cryptoObject.toString())
                        }
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        MyLogUtils.logI("onAuthenticationFailed")
                    }
                })
        } else {
            MyLogUtils.logI("VERSION.SDK_INT < P (安卓系统小于9 另行处理)")

        }
    }

}