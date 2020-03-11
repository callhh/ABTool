package com.callhh.nn.ui;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.callhh.module_common.util.AppUtils;
import com.callhh.module_common.util.Constants;
import com.callhh.module_common.util.PermissionUtils;
import com.callhh.module_common.util.common.DialogUtil;
import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.nn.R;
import com.callhh.nn.util.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

/**
 * App启动页
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity mActivity;
    private RxPermissions mRxPermission;
    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mActivity = this;
        initView();

    }

    private void initView() {
        initGlobalData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //系统版本大于23(安卓系统6.0以上，运行时权限申请处理)
            requestPermissions();
        } else {
            if (!mActivity.isFinishing()) {
                new Handler().postDelayed(this::toStartActivity
                        , Constants.DELAY_MILLIS);
            }
        }
    }

    /**
     * 初始化缓存全局数据：字典表、国籍表
     */
    private void initGlobalData() {

    }

    private void toStartActivity() {
//        if (!SPUtils.getValue()) return;
        MyLogUtils.logI("isLogined: " + SPUtils.getIslogin());
        AppUtils.startActivityNoAnimation(mActivity, MainActivity.class);
        mActivity.finish();
    }

    /**
     * Android 6.0+ 运行时权限处理
     * 申请多个权限，获取合并后的详细信息
     * 读存储、定位、拨打电话
     */
    private void requestPermissions() {
        if (null == mRxPermission) {
            mRxPermission = new RxPermissions(this);
        }
        mDisposable = mRxPermission.requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        //All权限同意后调用
                        new Handler().postDelayed(this::toStartActivity
                                , Constants.DELAY_MILLIS);
                        MyLogUtils.logI(" 允许权限 ");
                    } else if (permission.shouldShowRequestPermissionRationale){
                        //只要有一个选择：禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                        MyLogUtils.logI(" 拒绝权限，并没有勾选‘不再询问’ ");
                        showRequestPermissonsDialog();
                    }else {
                        // 小bug：去设置页面手动开启权限后回到app，回调不能自动执行允许权限的代码，
                        // 即没办法自动进入App,需要重启App才正常，是否可以在onResume()里处理？
                        MyLogUtils.logI(" 拒绝权限，并勾选了‘不再询问’ ");
                        PermissionUtils.showToAppSettingDialog(mActivity
                                ,getResources().getString(R.string.permission_apply_go)
                        ,getResources().getString(R.string.permission_to_setting_tips));
                    }
                });
    }

    private void showRequestPermissonsDialog() {
        DialogUtil.showPermissionsTips(mActivity, getResources().getString(R.string.common_tips_text),
                getResources().getString(R.string.request_tips_text),
                SplashActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mDisposable) mDisposable.dispose();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDialogCancle:
                DialogUtil.canclePermissionsTips();
                onBackPressed();
                break;
            case R.id.tvDialogSure:
                DialogUtil.canclePermissionsTips();
                requestPermissions();
                break;
            default:
                break;
        }
    }
}
