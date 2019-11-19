package com.callhh.module_common.view.piechart;

/**
 * Created by callhh
 */
public class PieChartEntity implements IPieElement {
    private float mValue;
    private String mColor;
    private String mDescription;

    public PieChartEntity(float value,String color,String description){
        mValue=value;
        mColor=color;
        mDescription =description;
    }
    @Override
    public float getValue() {
        return mValue;
    }

    @Override
    public String getColor() {
        return mColor;
    }

    public String getDescription() {
        return mDescription;
    }
}
