package com.callhh.module_common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


/**
 * 自定义控件，使用drawableRight与text水平居中显示
 */
public class DrawableRightCenterTextView extends AppCompatTextView {

    public DrawableRightCenterTextView(Context context) {
        super(context);
    }

    public DrawableRightCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableRightCenterTextView(Context context, AttributeSet attrs,
                                       int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();//left,top,right,bottom
        if(drawables != null){
            Drawable drawableRight = drawables[2];
            if(drawableRight != null){
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableRight.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                setPadding(0, 0, (int)(getWidth() - bodyWidth), 0);
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }

}
