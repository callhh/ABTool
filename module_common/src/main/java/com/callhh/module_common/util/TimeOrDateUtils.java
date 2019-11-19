package com.callhh.module_common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间或日期处理工具类
 */
public class TimeOrDateUtils {

    public static String FORMAT_D = "yyyy-MM-dd";

    /**
     * 获取当前时间精确到毫秒
     *
     * @return 2018-01-18 00:00:00
     */
    public static String getCurrentTime() {
        // 2016-04-28 09:09:22
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间精确到微秒
     *
     * @return
     */
    public static String getCurrentTime2() {
        // 2016-04-28 09:09:22
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyyMMddHHmmssSSS");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前的时间 hh:mm:ss
     *
     * @return
     */
    public static String getCurrentHour() {
        // 2016-04-28 09:09:22
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取日期
     *
     * @param time 传入的完整时间
     * @return 返回："2018-01-18"
     */
    public static String getDateString(String time) {
        String substring;
        if (TextUtils.isEmpty(time)) {
            substring = "";
        } else {
            substring = time.substring(0, 10);
        }
        return substring;
    }

    /**
     * 获取某个月有多少天
     */
    public static int getMonthDayNum(int year, int month) {
        int dayCount;
        Calendar cl = Calendar.getInstance();// 实例化一个日历对象
        cl.set(Calendar.YEAR, 2015);// 年设置为2015年
        cl.set(Calendar.MONTH, 6);// 7月的id是6
        dayCount = cl.getActualMaximum(Calendar.DATE);
        return dayCount;
    }

    /**
     * 当前时间内转换为毫秒数
     *
     * @return
     */
    public static long nowTimeToMile() {
        try {
            String dateTime = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date());
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(dateTime));
            return c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 毫秒数转换为时间字符串
     *
     * @param dateTime 毫秒
     */
    public static String mileToString(long dateTime) {
        Date date = new Date(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 毫秒数转换为时间字符串-转到小时
     */
    public static String mileToString_hour(long dateTime) {
        Date date = new Date(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 毫秒转换当前时间，显示格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String convertTime(long dateTime) {
        Date date = new Date(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 毫秒转日期
     *
     * @param dateTime long类型时间
     * @return 如：2018-01-18
     */
    public static String convertLongTime(long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"
                , Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dateTime);
        Date date = c.getTime();
        return sdf.format(date);
    }

    /**
     * 将时间戳转成日期字符串
     *
     * @param timeStamp 时间戳的值,类型为：Long
     * @param format    转成字符串的格式
     * @return 返回pattern模式的日期字符串
     */
    public static String getDateStringByTimeStamp(Long timeStamp, String format) {
        String result = null;
        Date date = new Date(timeStamp * 1000);
        SimpleDateFormat sd = new SimpleDateFormat(format);
        result = sd.format(date);
        return result;
    }

    /**
     * date到时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String createStringDate(Date dt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(dt);
        String y = date.substring(0, 4);
        String m = date.substring(4, 6);
        String d = date.substring(6, 8);
        return y + "-" + m + "-" + d + " 00:00:00";
    }

    public static long getSystime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取时间差 返回毫秒
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTimeDifference(String nowTime, String targetTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now;
        try {
            now = df.parse(nowTime);
            Date date = df.parse(targetTime);
            long l = date.getTime() - now.getTime();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取时间差返回小时
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTimeDifferenceHour(String nowTime, String targetTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now;
        try {
            now = df.parse(nowTime);
            Date date = df.parse(targetTime);
            long l = date.getTime() - now.getTime();
            return l / 3600000;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 时间转换时间戳
     * 2019-04-18 18:42:44 转换Long类型的时间戳 1555584164
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTimeStampToLong(String timeStamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(timeStamp);
            long longDate = date.getTime();
            return longDate * 1000L;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 秒转时分秒
     */
    public static String secToTime(int time) {
        String timeStr;
        int hour;
        int minute;
        int second;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 字符串转换日期="yyyy-MM-dd"
     *
     * @param dateStr 字符串日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parseStringToDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    @SuppressLint("SimpleDateFormat")
    public static Date parseStringToDate(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转换日期="yyyy/MM/dd"
     *
     * @param dateStr 字符串日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parseStringToDate2(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 时分转时间戳
     *
     * @param timeStr 字符串时分,03:12
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parseStringToDate3(String timeStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = simpleDateFormat.parse(timeStr);
            long dateTime = date.getTime();
            return new Date(dateTime * 1000L);
//            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将时间戳转成日期字符串
     *
     * @param timeStamp 时间戳的值,类型为：Long
     * @return
     */
    public static String getDateStringByTimeSTamp(Long timeStamp) {
        String result = null;
        Date date = new Date(timeStamp * 1000);
        long time = date.getTime();

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        result = sd.format(date);
        return result;
    }

    /**
     * 日期转换字符串="yyyy-MM-dd"
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * 日期转换字符串="yyyy/MM/dd"
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateToString2(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDateToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 月份的处理 类似 12  02
     */
    public static String handleMonth(String month) {
        return month.length() == 2 ? month : "0" + month;
    }

    /**
     * 时间戳转换日期和时间,样式1
     * 例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     */
    public static String getDateAndTimes1(String time) {
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            long lt = new Long(time);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * 时间戳转换日期和时间,样式2
     * 例如（1402733340）输出（"2014-06-14  16:09:00"）
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateAndTimes2(String time) {
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(time);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * 时间戳转换日期和时间,样式3
     * 例如（1402733340）输出（"2014年06月14日16:09"）
     */
    public static String getDataAndTimes3(String time) {
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
            long lt = new Long(time);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * string类型转换为date类型
     * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
     * HH时mm分ss秒，
     * strTime的时间格式必须要与formatType的时间格式相同
     *
     * @throws ParseException
     */
    public static Date stringToDate(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * string类型转换为long类型
     * strTime要转换的String类型的时间
     * formatType时间格式
     * strTime的时间格式和formatType的时间格式必须相同
     *
     * @throws ParseException
     */
    public static long stringToLong(String strTime)
            throws ParseException {
        Date date = stringToDate(strTime); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }


	/*public static double formatDoubleBy2(String x){
        DecimalFormat df = new DecimalFormat("0.##");
		String y = df.format(x);
		if(".".equals(y.subSequence(0, 1))){
			y = "0"+y;
		}
		return Double.parseDouble(y);
	}*/

    /**
     * Double类型转字符串
     */
    public static String formatDouble2String(Double z) {
        DecimalFormat df = new DecimalFormat("0.##");
        String y = df.format(z);
        //会出现小于1的情况==>0.50==> .50
        if (".".equals(y.subSequence(0, 1))) {
            y = "0" + y;
        }
        return y;
    }

    /**
     * 计算时间差
     *
     * @param fromDate 创建时间
     * @param toDate   使用时间
     * @return 返回时间差：xx小时
     */
    public static int computeTimeDifference(String fromDate, String toDate) {
        //初始化时间格式，如2016-08-10 20:40
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            long from = simpleFormat.parse(fromDate).getTime();
            long to = simpleFormat.parse(toDate).getTime();
//            int days = (int) ((to - from)/(1000 * 60 * 60 * 24)); //计算天数差
//            int minutes = (int) ((to - from)/(1000 * 60)); //计算分钟差
//            return minutes;
            int hours = (int) ((to - from) / (1000 * 60 * 60)); //计算小时差
            return hours;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
