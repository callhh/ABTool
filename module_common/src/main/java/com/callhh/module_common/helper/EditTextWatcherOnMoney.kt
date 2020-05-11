package com.callhh.module_common.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * 金额输入字体监听类，限制小数点后输入位数
 * 功能包含:
 * 默认限制小数点2位
 * 默认第一位输入小数点时，转换为0.
 * 如果起始位置为0,且第二位跟的不是"."，则无法后续输入
 */
class EditTextWatcherOnMoney( private val editText: EditText) : TextWatcher {

    private var digits = 2

    fun setDigits(d: Int): EditTextWatcherOnMoney {
        digits = d
        return this
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence,
        start: Int,
        before: Int,
        count: Int) {
        var textStr = s //获取输入文本内容
        //删除“.”后面超过2位后的数据
        if (textStr.toString().contains(".")) {
            if (textStr.length - 1 - textStr.toString().indexOf(".") > digits) {
                textStr = textStr.toString().subSequence(0, textStr.toString().indexOf(".") + digits + 1)
                editText.setText(textStr)
                editText.setSelection(textStr.length) //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (textStr.toString().trim { it <= ' ' } == ".") {
            textStr = "0$textStr"
            editText.setText(textStr)
            editText.setSelection(2)
        }
        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (textStr.toString().startsWith("0") && textStr.toString().trim { it <= ' ' }.length > 1) {
            if (textStr.toString().substring(1, 2) != ".") {
                editText.setText(textStr.subSequence(0, 1))
                editText.setSelection(1)
                return
            }
        }
    }

    override fun afterTextChanged(s: Editable) {}

}