package com.callhh.module_common.extension

import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

/**
 * EditText添加事件监听封装，返回onChange()
 * 使用：EditTextExtentions.onChange(editText,edittextString ->{  return Unit})
 */
fun EditText.onChange(onchange: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            onchange(s?.trim()?.toString() ?: "")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
    })
}

fun EditText.hidePassword(hide: Boolean) {
    transformationMethod = if (hide) {
        HideReturnsTransformationMethod.getInstance()
    } else {
        PasswordTransformationMethod.getInstance()
    }
}