package com.callhh.abtool.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * webView工具
 */

public class MyWebViewUtils {

    /**
     * 设置webView基础属性配置,实现Android与H5交互
     */
    @SuppressLint("JavascriptInterface")
    public static void intWebSettings(WebView webView) {
        if (webView.getSettings() == null) return;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 设置与Js交互的权限，允许网页执行js脚本
        webSettings.setDomStorageEnabled(true);// 用于持久化本地存储，除非主动删除数据否则数据是永远不会过期。
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 支持通过JS打开新窗口
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        // webSettings.setLoadsImagesAutomatically(false);// 先不加载图片
        // webSettings.setUseWideViewPort(true);// 内容适应屏幕
        // webSettings.setLoadWithOverviewMode(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //解决Android 5.0以上使用webView 加载网页不显示图片问题
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 初始化WebView
     */
    public static void initWebView(final Context context, final WebView webView) {
        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//加载缓存否则网络
        }

        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);//图片自动缩放 打开
        } else {
            webSettings.setLoadsImagesAutomatically(false);//图片自动缩放 关闭
        }

        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//软件解码
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//硬件解码

//        webSettings.setAllowContentAccess(true);
//        webSettings.setAllowFileAccessFromFileURLs(true);
//        webSettings.setAppCacheEnabled(true);
   /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }*/


        // setMediaPlaybackRequiresUserGesture(boolean require) //是否需要用户手势来播放Media，默认true

        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportZoom(true);// 设置可以支持缩放
        webSettings.setBuiltInZoomControls(true);// 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false

        webSettings.setDisplayZoomControls(false);//隐藏缩放工具
        webSettings.setUseWideViewPort(true);// 扩大比例的缩放

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDatabaseEnabled(true);//
        if (Build.VERSION.SDK_INT <= 18)
            webSettings.setSavePassword(true);//保存密码
        webSettings.setDomStorageEnabled(true);//是否开启本地DOM存储  鉴于它的安全特性（任何人都能读取到它，尽管有相应的限制，将敏感数据存储在这里依然不是明智之举），Android 默认是关闭该功能的。
        webView.setSaveEnabled(true);
        webView.setKeepScreenOn(true);


        //设置此方法可在WebView中打开链接，反之用浏览器打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }

                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setComponent(null);//恶意页面可以通过Intent scheme URL执行基于Intent的攻击,修复建议：将Intent的component/selector设置为null
                context.startActivity(intent);
                return true;
            }
        });
        webView.setDownloadListener((paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousString4, paramAnonymousLong) -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(paramAnonymousString1));
            context.startActivity(intent);
        });
    }

    /**
     * 加载h5
     *
     * @param webView webView控件
     * @param content html内容
     */
    public static void loadData(WebView webView, String content) {
        //这种写法可以正确解码
        webView.loadDataWithBaseURL(null, content
                , "text/html", "UTF-8", null);
    }

    public static void destroyWebView(WebView webView) {
        if (null != webView) {
            webView.destroy();
            webView.removeAllViews();

            webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl("about:blank");// clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now mWebView.freeMemory();
            webView.pauseTimers();
        }
    }

}
