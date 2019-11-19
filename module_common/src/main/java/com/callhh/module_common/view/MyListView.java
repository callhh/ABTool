package com.callhh.module_common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * >解决ScrollView嵌套冲突问题
 * 
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 重写该方法，重新计算ListView高度
	 * 累计所有行，计算出ListView的最大高度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//			return true;
//		}
//		return super.dispatchTouchEvent(ev);
//	}

}
