package com.callhh.nn.helper.pickerview;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.callhh.nn.R;

import java.util.List;

/**
 * java 选项选取器辅助类
 * 仿iOS的PickerView控件，有时间选择器和选项选择，支持年月日时分，年月日，时分等格式
 * 封装参考：https://blog.csdn.net/qq_34536167/article/details/81636414
 * 使用：
 * 1. List<String> list1 = new ArrayList<>();
 * 2. SingleOptionsPicker.openOptionsPicker(this, list1, 1, textview);
 */
public class SingleOptionsPicker<T> {

    private Activity activity; //回调接口
    private OnPickerOptionsClickListener listener;
    private OptionsPickerView pvOptions;  //一级联动或条件数据源
    private List<T> options1Items; //二级联动数据源
    private List<List<T>> options2Items; //三级联动数据源

    private List<List<List<T>>> options3Items; //默认选中的位置
    private int options1, options2, options3;

    /**
     * 普通条件选择项方法
     */
    public SingleOptionsPicker(Activity activity, String select, List<T> options1Items, OnPickerOptionsClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.options1Items = options1Items;
        boolean isContinue = true;
        for (int i = 0; i < options1Items.size() && isContinue; i++) {
            //设置选中项
            if (select.equals(options1Items.get(i))) {
                options1 = i;
                isContinue = false;
            }
        }
        getInstance();
    }

    /**
     * 三级联动选择项方法
     */
    public SingleOptionsPicker(Activity activity, int options1, int options2, int options3, List<
            T> options1Items, List<List<T>> options2Items, List<List<List<T>>> options3Items, OnPickerOptionsClickListener
                                       listener) {
        this.activity = activity;
        this.listener = listener;
        this.options1 = options1;
        this.options2 = options2;
        this.options3 = options3;
        this.options1Items = options1Items;
        this.options2Items = options2Items;
        this.options3Items = options3Items;
        getInstance();
    }

    private OptionsPickerView getInstance() {
        pvOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (listener != null) {
                    listener.onOptionsSelect(options1, options2, options3, v);
                }
            }
        }) //分隔线颜色。
                .setDividerColor(Color.parseColor("#BBBBBB")) //滚轮背景颜色
                .setBgColor(Color.parseColor("#F5F5F5")) //设置两横线之间的间隔倍数
                .setLineSpacingMultiplier(1.8f) //设置选中项的颜色
                .setTextColorCenter(Color.parseColor("#333333"))
                // 是否只显示中间选中项的label文字，false则每项item全部都带有label
                .isCenterLabel(true) ////设置选择的三级单位 .setLabels("", "", "") //标题文字
                .setTitleText("标题文字") //默认选中项 .setSelectOptions(options1, options2, options3)
                .setLayoutRes(R.layout.item_picker_options, new CustomListener() { //自定义布局
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(v1 -> {
                                    pvOptions.returnData();
                                    pvOptions.dismiss();
                                }
                        );
                    }
                })
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        return pvOptions;
    }

    public static void openOptionsPicker(Activity activity, List<String> list,
                                         final int type, final TextView textView) {
        String select = textView.getText().toString();
        new SingleOptionsPicker(activity, select, list, (options1, options2, options3, view) -> {
            if (type == 1) {
                textView.setText(options1 + "岁");
            } else if (type == 2) { //注意起始数值
                textView.setText(options1 + 80 + "CM");
            } else if (type == 3) {
                textView.setText(options1 + 30 + "KG");
            }
        }).show();
    }

    /**
     * 显示选择器
     */
    public void show() {
        if (pvOptions != null && !pvOptions.isShowing()) {
            pvOptions.show();
        }
    }

    /**
     * 选择项回调
     */
    public interface OnPickerOptionsClickListener {
        void onOptionsSelect(int options1, int options2, int options3, View view);
    }
}
