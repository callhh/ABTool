package com.callhh.nn.helper.pickerview

import android.app.Activity
import android.content.Context
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.callhh.abtool.util.MyAppUtils
import com.callhh.abtool.util.common.WidgetUtils
import com.callhh.nn.R
import java.util.*

/**
 * 选择器辅助类
 * kotlin写法：单选/多选
 */
object PickerViewHelpers {

    /**
     * 单级选项
     *
     * @param items 选项
     * @param listener 选择监听，回调中 option 为选中选项值
     */
    fun showTwoOptionsPicker(context: Context, items1: List<String>, items2: List<List<String>>, activity: Activity? = null, listener: (option1: String, option2: String) -> Unit) {
        hideSoftInput(activity, context)
        val options: OptionsPickerView<String> = OptionsPickerBuilder(context) { options1, options2, _, _ ->
            listener(items1[options1], items2[options1][options2])
        }
                .setTextColorCenter(WidgetUtils.getColor(context, R.color.black_bb111111))
                .setTextColorOut(WidgetUtils.getColor(context, R.color.gray_ccc))
                .setContentTextSize(WidgetUtils.dp2px(context, 7f).toInt())
                .build()
        options.setPicker(items1, items2)
        options.show()
    }

    /**
     * 单级选项
     *
     * @param items 选项
     * @param listener 选择监听，回调中 option 为选中选项值
     */
    fun showOptionsPicker(context: Context, items: List<String>
                          , activity: Activity? = null, listener: (option: String) -> Unit) {
        hideSoftInput(activity, context)
        val options: OptionsPickerView<String> = OptionsPickerBuilder(context) { options1, _, _, _ ->
            listener(items[options1])
        }
                .setTextColorCenter(WidgetUtils.getColor(context, R.color.black_bb111111))
                .setTextColorOut(WidgetUtils.getColor(context, R.color.gray_ccc))
                .setContentTextSize(WidgetUtils.dp2px(context, 7f).toInt())
                .build()
        options.setNPicker(items, null, null)
        options.show()
    }

    /**
     * 选择年月日
     * @param listener 选择日期后回调，获得 date: Date
     */
    fun showDatePicker(context: Context, activity: Activity? = null, listener: (date: Date?, v: View?) -> Unit) {
        hideSoftInput(activity, context)
        val options: TimePickerView = TimePickerBuilder(context, listener)
                .setTextColorCenter(WidgetUtils.getColor(context, R.color.black_bb111111))
                .setTextColorOut(WidgetUtils.getColor(context, R.color.gray_ccc))
                .setContentTextSize(WidgetUtils.dp2px(context, 7f).toInt())
                .build()
        options.show()
    }

    /**
     * 选择年月日小时分钟
     * @param listener 选择时间后回调，获得 date: Date
     */
    fun showTimePicker(context: Context, activity: Activity? = null, listener: (date: Date?, v: View?) -> Unit) {
        hideSoftInput(activity, context)
        val options: TimePickerView = TimePickerBuilder(context, listener)
                .setType(booleanArrayOf(true, true, true, true, true, false))
                .setTextColorCenter(WidgetUtils.getColor(context, R.color.black_bb111111))
                .setTextColorOut(WidgetUtils.getColor(context, R.color.gray_ccc))
                .setContentTextSize(WidgetUtils.dp2px(context, 7f).toInt())
                .build()
        options.show()
    }

    private fun hideSoftInput(activity: Activity?, context: Context) {
        if (activity == null) {
            if (context is Activity) {
                MyAppUtils.hideSoftKeyboard(context)
            }
        } else {
            MyAppUtils.hideSoftKeyboard(activity)
        }
    }
}