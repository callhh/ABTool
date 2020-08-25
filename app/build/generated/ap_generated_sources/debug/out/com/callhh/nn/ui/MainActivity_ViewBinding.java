// Generated code from Butter Knife. Do not modify!
package com.callhh.nn.ui;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.callhh.nn.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.mTvTabOne = Utils.findRequiredViewAsType(source, R.id.tvTabOne, "field 'mTvTabOne'", TextView.class);
    target.mTvTabTwo = Utils.findRequiredViewAsType(source, R.id.tvTabTwo, "field 'mTvTabTwo'", TextView.class);
    target.mTvTabThree = Utils.findRequiredViewAsType(source, R.id.tvTabThree, "field 'mTvTabThree'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvTabOne = null;
    target.mTvTabTwo = null;
    target.mTvTabThree = null;
  }
}
