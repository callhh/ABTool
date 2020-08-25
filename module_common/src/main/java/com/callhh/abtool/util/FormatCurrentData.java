package com.callhh.abtool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatCurrentData {
    /**
     * 设置每个阶段时间
     */
    private static final int seconds_of_1minute = 60;

    private static final int seconds_of_30minutes = 30 * 60;

    private static final int seconds_of_1hour = 60 * 60;

    private static final int seconds_of_1day = 24 * 60 * 60;

    private static final int seconds_of_15days = seconds_of_1day * 15;

    private static final int seconds_of_30days = seconds_of_1day * 30;

    private static final int seconds_of_6months = seconds_of_30days * 6;

    private static final int seconds_of_1year = seconds_of_30days * 12;

    /**
     * 时间标签：默认为0指的是"大于60分钟"，1="小于60分钟"
     */
    private static int timeTag = 0;

    /**
     * 格式化时间
     *
     * @param mTime     传入的时间
     * @return
     */
    public static String getTimeRange(String mTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /**获取当前时间*/
        Date curDate = new Date(System.currentTimeMillis());
        String dataStrNew = sdf.format(curDate);
        Date startTime = null;
        try {
            /**将时间转化成Date*/
            curDate = sdf.parse(dataStrNew);
            startTime = sdf.parse(mTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**除以1000是为了转换成秒*/
        long between = (curDate.getTime() - startTime.getTime()) / 1000;
        int elapsedTime = (int) (between);
        if (elapsedTime < seconds_of_1minute) {
            //当前时间减去创建时间，小于60s，返回日期提示"刚刚"
            timeTag = 1;
            return "刚刚";
        }
        if (elapsedTime < seconds_of_30minutes) {
            timeTag = 1;
            return elapsedTime / seconds_of_1minute + "分钟前";
        }
        if (elapsedTime < seconds_of_1hour) {
            timeTag = 1;
            return "半小时前";
        }
        if (elapsedTime < seconds_of_1day) {
            timeTag = 1;
            return elapsedTime / seconds_of_1hour + "小时前";
        }
//        if (elapsedTime < seconds_of_15days) {
//
//            return elapsedTime / seconds_of_1day + "天前";
//        }
//        if (elapsedTime < seconds_of_30days) {
//
//            return "半个月前";
//        }
//        if (elapsedTime < seconds_of_6months) {
//
//            return elapsedTime / seconds_of_30days + "月前";
//        }
//        if (elapsedTime < seconds_of_1year) {
//
//            return "半年前";
//        }
//        if (elapsedTime >= seconds_of_1year) {
//
//            return elapsedTime / seconds_of_1year + "年前";
//        }
        if (elapsedTime > seconds_of_1day) {
            //大于24小时，直接显示创建日期，如：2018-01-18
            String strDate = TimeOrDateUtils.getDateString(mTime);
            return strDate;
        }
        return "";
    }

    /**
     * 为0，大于60分钟，短信内容字体颜色为灰色
     * 为1，小于60分钟，短信内容字体颜色为#333黑色
     * @return  时间标签
     */
    public static int getTimeTag(){
        return timeTag;
    }

}
