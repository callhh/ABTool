package com.onesports.sx.reception.helper

import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

/**
 * EditText简单封装类
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
    if (hide) {
        transformationMethod = HideReturnsTransformationMethod.getInstance()
    } else {
        transformationMethod = PasswordTransformationMethod.getInstance()
    }
}