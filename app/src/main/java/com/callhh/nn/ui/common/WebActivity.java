package com.callhh.nn.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.callhh.abtool.util.AppUtils;
import com.callhh.abtool.util.MyWebViewUtils;
import com.callhh.abtool.util.NetWorkUtils;
import com.callhh.abtool.util.common.MyLogUtils;
import com.callhh.abtool.util.common.MyTextUtil;
import com.callhh.abtool.util.common.TitleBarUtils;
import com.callhh.nn.R;
import com.callhh.nn.base.BaseActivity;
import com.callhh.nn.util.ConstUtils;
import com.callhh.abtool.widget.MyLoadingLayout;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 通用的h5页面
 */
public class WebActivity extends BaseActivity {

    @BindView(R.id.tvTitlebarTitle)
    TextView mTvTitlebarTitle;
    @BindView(R.id.ivRightButton)
    ImageView mIvRightButton;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.myLoading)
    MyLoadingLayout mMyLoading;
    private String mWebUrl;
    private String mWebTitle;

    /**
     * 开启H5页面
     *
     * @param activity 上下文
     * @param url      网址
     * @param title    标题
     */
    public static void openWebActivity(Activity activity, String url, String title) {
        Intent intent = new Intent();
        intent.putExtra(ConstUtils.WEB_URL, url);
        intent.putExtra(ConstUtils.WEB_TITLE, title);
        AppUtils.startActivityDataWithIntent(activity, WebActivity.class, intent);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        if (null != getIntent()) {
            mWebUrl = getIntent().getStringExtra(ConstUtils.WEB_URL);
            mWebTitle = getIntent().getStringExtra(ConstUtils.WEB_TITLE);
            TitleBarUtils.setCommonIconTitle(mActivity, mWebTitle
                    , -1, this);
        }
        mIvRightButton.setVisibility(View.GONE);
        MyLogUtils.logI("WebActivity  webUrl: " + mWebUrl);
        setWebListener();
    }

    /**
     * 设置webView监听事件
     */
    private void setWebListener() {
        MyWebViewUtils.intWebSettings(mWebView);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
            }
        });
        mWebView.addJavascriptInterface(new H5CallBackAndroid(), "android");// 向网页暴露本地接口
        mWebView.setWebViewClient(new MyWebViewClient());
        mMyLoading.setOnRetryListenner(() ->
                loadWebView()
        );
        loadWebView();
    }

    /**
     * 判断网络，加载链接
     */
    private void loadWebView() {
        if (NetWorkUtils.isNetworkAvailable(mActivity)) {
            mMyLoading.setLoadingSuccess();
            mWebView.loadUrl(mWebUrl);
        } else {
            mMyLoading.setLoadedFailHints(getResources().getString(com.callhh.abtool.R.string.request_error_no_network));
        }
    }

    /**
     * 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
     */
    class MyWebViewClient extends WebViewClient {

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
            mWebTitle = view.getTitle();// 取webUrl里面的标题
            if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            }
            //提取网页源码url
            String url_AppTitle = "javascript:window.android.getAppTitle(document.getElementsByName('AppTitle')[0].content)";
            if (Build.VERSION.SDK_INT > 19) {//sdk系统版本大于4.4才可用
                mWebView.evaluateJavascript(url_AppTitle, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        MyLogUtils.logI("value：" + value);
                        if (!StringUtils.equals("null", value) && StringUtils.isNotBlank(value)) {
                            mWebTitle = StringUtils.replace(value, "\"", "");
                        }
//                        MyTextUtil.setText(mTvTitlebarTitle, mWebTitle);
                    }
                });
            } else {
                mWebView.loadUrl(url_AppTitle);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            // 由于HTTPS协议通过SSL进行通信，当使用HTTPS通信得url再Webview出现错误时，一般会通过onReceivedSslError()回调通知
            // 常见的做法是直接忽略，让webview继续加载

//            handler.proceed(); // 忽略错误(SSL错误)，继续加载
        }

    }

    /**
     * Android对象类编写
     * 固定用法：H5调用Android使用得方法，需要在方法上用@JavascriptInterface注解来定义该方法，且必须是函数权限必须声明为public
     */
    public class H5CallBackAndroid {

        @JavascriptInterface
        public void getAppTitle(String htmlData) {
            // htmlData为网页源码（H5传过来得数据）
            if (StringUtils.isNotBlank(htmlData)) {
                mWebTitle = htmlData;
                MyTextUtil.setText(mTvTitlebarTitle, mWebTitle);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();// 返回键退回
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyWebViewUtils.destroyWebView(mWebView);
    }

    @OnClick({R.id.ivRightButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRightButton:
                //分享
//                DialogUtil.shareDialog(mActivity,WebActivity.this);
                break;
        }
    }
}
