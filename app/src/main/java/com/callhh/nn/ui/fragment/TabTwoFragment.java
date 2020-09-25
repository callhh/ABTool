package com.callhh.nn.ui.fragment;


import android.annotation.SuppressLint;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.callhh.abtool.util.common.MyLogUtils;
import com.callhh.nn.R;
import com.callhh.nn.base.BaseFragment;

/**
 * 首页2 second fragment
 */
public class TabTwoFragment extends BaseFragment {


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_tab_two;
    }

    @Override
    protected void initView() {
        MyLogUtils.logI("start TabTwoFragment- 首页2 ");

    }

    @SuppressLint("NewApi")
    @Override
    protected void addListener() {
        findView(mView, R.id.tvTestBtn1).setOnClickListener(view -> {
            BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(mActivity)
                    .setTitle("指纹验证")
                    .setSubtitle("小标题")
                    .setDescription("描述")
                    .setNegativeButton("取消", mActivity.getMainExecutor(), (dialog, which) -> {
                        MyLogUtils.logI("cancel fingerprint authenticate");
                    })
                    .build();
            biometricPrompt.authenticate(new CancellationSignal(), mActivity.getMainExecutor(), authenticationCallback);
        });
        RecyclerView recyclerView = findView(mView, R.id.recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    /**
     * 指纹识别回调
     */
    BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            MyLogUtils.logI( "onAuthenticationError errorCode: " + errorCode + " errString: " + errString);
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            MyLogUtils.logI( "onAuthenticationHelp helpCode:" + helpCode + "helpString: " + helpString);
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            MyLogUtils.logI("onAuthenticationSucceeded");
            if (result !=null && result.getCryptoObject() != null ){
                MyLogUtils.logI("result = "  + result.getCryptoObject().toString());
            }
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            MyLogUtils.logI("onAuthenticationFailed");
        }
    };



}
