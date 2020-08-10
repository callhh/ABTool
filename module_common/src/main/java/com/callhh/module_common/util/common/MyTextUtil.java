package com.callhh.module_common.util.common;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * TextView、EditText等文本控件空安全处理工具类
 */
public class MyTextUtil {

    private static String NULL = "null";

    /**
     * 设置textview，内容为空提示空串
     *
     * @param textView view控件
     * @param content  更新内容
     */
    public static void setText(TextView textView, String content) {
        try {
            if (null == textView || TextUtils.isEmpty(content)) return;
            //赋值
            if (content.contains(NULL)) content = content.replace(NULL, "");
            textView.setText(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置textview，内容为空可自定义提示内容
     *
     * @param textView     view控件
     * @param content      更新内容
     * @param defaultHints 默认文本提示
     */
    public static void setText(TextView textView, String content, String defaultHints) {
        try {
            if (null == textView) return;
            if (TextUtils.isEmpty(content)) {
                if (defaultHints != null) textView.setText(defaultHints);
            } else {
                if (content.contains(NULL)) content = content.replace(NULL, "");
                textView.setText(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置editext内容
     *
     * @param editText     控件
     * @param etContent    提示内容
     * @param defaultValue 默认值
     */
    public static void setEditText(EditText editText, String etContent, String defaultValue) {
        try {
            if (null == editText) return;
            if (TextUtils.isEmpty(etContent) || NULL.equals(etContent)) {
                if (defaultValue != null) editText.setText(defaultValue);
            } else {
                if (etContent.contains(NULL)) etContent = etContent.replace(NULL, "");
                editText.setText(etContent);
                editText.setSelection(etContent.length());//光标设置在文字后面
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置editext提示语
     *
     * @param editText     控件
     * @param etContent    提示内容
     * @param defaultHints 默认值
     */
    public static void setEditTextHints(EditText editText, String etContent, String defaultHints) {
        try {
            if (null == editText) return;
            if (TextUtils.isEmpty(etContent) || NULL.equals(etContent)) {
                if (defaultHints != null) {
                    editText.setText("");
                    editText.setHint(defaultHints);
                }
            } else {
                if (etContent.contains(NULL)) etContent = etContent.replace(NULL, "");
                editText.setText(etContent);
                editText.setSelection(etContent.length());//光标设置在文字后面
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置editext内容，并限制edittext是否可再次编辑
     *
     * @param editText     控件
     * @param etContent    提示内容
     * @param defaultValue 默认值
     * @param isEnabled    是否可编辑
     */
    public static void setEditTextAndIsEnabled(EditText editText, String etContent, String defaultValue
            , boolean isEnabled) {
        try {
            if (null == editText) return;
            if (TextUtils.isEmpty(etContent) || NULL.equals(etContent)) {
                if (defaultValue != null) editText.setText(defaultValue);
            } else {
                if (etContent.contains(NULL)) etContent = etContent.replace(NULL, "");
                editText.setText(etContent);
                editText.setSelection(etContent.length());//光标设置在文字后面
            }
            editText.setEnabled(isEnabled);//是否可编辑
            editText.setFocusableInTouchMode(isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
