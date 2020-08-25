// Generated code from Butter Knife. Do not modify!
package com.callhh.nn.ui.common;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.callhh.abtool.widget.MyLoadingLayout;
import com.callhh.nn.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WebActivity_ViewBinding implements Unbinder {
  private WebActivity target;

  private View view7f0800b6;

  @UiThread
  public WebActivity_ViewBinding(WebActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WebActivity_ViewBinding(final WebActivity target, View source) {
    this.target = target;

    View view;
    target.mTvTitlebarTitle = Utils.findRequiredViewAsType(source, R.id.tvTitlebarTitle, "field 'mTvTitlebarTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ivRightButton, "field 'mIvRightButton' and method 'onClick'");
    target.mIvRightButton = Utils.castView(view, R.id.ivRightButton, "field 'mIvRightButton'", ImageView.class);
    view7f0800b6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'mProgressBar'", ProgressBar.class);
    target.mWebView = Utils.findRequiredViewAsType(source, R.id.webView, "field 'mWebView'", WebView.class);
    target.mMyLoading = Utils.findRequiredViewAsType(source, R.id.myLoading, "field 'mMyLoading'", MyLoadingLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WebActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvTitlebarTitle = null;
    target.mIvRightButton = null;
    target.mProgressBar = null;
    target.mWebView = null;
    target.mMyLoading = null;

    view7f0800b6.setOnClickListener(null);
    view7f0800b6 = null;
  }
}
