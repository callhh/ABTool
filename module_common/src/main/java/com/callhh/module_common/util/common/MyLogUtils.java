package com.callhh.module_common.util.common;

import android.text.TextUtils;
import android.util.Log;
import com.callhh.module_common.BuildConfig;
import com.callhh.module_common.util.Constants;

/**
 * 日志操作工具类：打印、弹出提示
 */
public class MyLogUtils {

    //log
    private static final String TAG = Constants.TAG_LOG;
    private static String CLASS_NAME;
    private static String METHOD_NAME;
    private static int LINE_NUMBER;

    /* Protect from instantiations */
    private MyLogUtils() {

    }

    /**
     * 自动判断是否是打包签名的版本，默认返回DEBUG版本
     */
    private static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    /**
     * 输出：[onClick:448] + logMessage
     */
    private static String createLog(String logMessage) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(" [");
        buffer.append(METHOD_NAME);
        buffer.append(":");
        buffer.append(LINE_NUMBER);
        buffer.append("] ");
        buffer.append(logMessage);
        return buffer.toString();
    }

    /**
     * 获取文件+方法+行数
     */
    private static void getMethodNames(StackTraceElement[] sElements) {
        CLASS_NAME = sElements[1].getFileName();
        METHOD_NAME = sElements[1].getMethodName();
        LINE_NUMBER = sElements[1].getLineNumber();
    }

    /**
     * 以级别为 i 的形式输出LOG,一般提示性的消息information
     * 设置TAG=callhh,快速过滤msg
     */
    public static void logI(String message) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            printLogI(TAG, CLASS_NAME + createLog(MyStringUtil.decodeUnicode(message)));
        }
    }


    public static void logI(String tag, Object content) {
        if (isDebuggable()) {
            if (content == null) {
                Log.i(tag, "值为null");
            } else {
                Log.i(tag, content.toString());
            }
        }
    }

    /**
     * 解决日志信息太长,实现分段打印
     */
    private static void printLogI(String tag, String msg) {
        try {
            // 因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            // 把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.i(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.i(tag, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 以级别为 d 的形式输出LOG,输出debug调试信息
     */
    public static void d(String message) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.d(TAG, CLASS_NAME + createLog(message));
        }
    }

    /**
     * 以级别为 v 的形式输出LOG ，verbose啰嗦的意思
     */
    public static void v(String message) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.v(TAG, CLASS_NAME + createLog(message));
        }
    }

    /**
     * 以级别为 w 的形式输出LOG,显示warning警告，一般是需要我们注意优化Android代码
     */
    public static void w(String message) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.w(TAG, CLASS_NAME + createLog(message));
        }
    }

    /**
     * 简单的error日志输出
     *
     * @param tag 标签
     * @param msg 异常信息
     */
    public static void logE(String tag, String msg) {
        if (isDebuggable()) {
            if (TextUtils.isEmpty(tag)) tag = TAG;
            Log.e(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG ，红色的错误信息，查看错误源的关键
     */
    public static void e(String message) {
        if (isDebuggable()) {
            // Throwable instance must be created before any methods
            getMethodNames(new Throwable().getStackTrace());
            StringBuilder buffer = new StringBuilder();
            CLASS_NAME = CLASS_NAME.replaceAll(".java", "");
            buffer.append(CLASS_NAME);
            buffer.append(" [");
            buffer.append(METHOD_NAME);
            buffer.append("{}");
            buffer.append(":");
            buffer.append(LINE_NUMBER);
            buffer.append("] ");
            show(buffer.toString(), message);
            // Log.e(buffer.toString(), message);
        }
    }

    public static void show(String title, String str) {
        str = str.trim();
        int index = 0;
        int maxLength = 4000;
        String sub = null;
        while (index < str.length()) {
            // java的字符不允许指定超过总的长度end
            if (str.length() <= index + maxLength) {
                sub = str.substring(index);
            } else {
                if (maxLength >= index) {
                    sub = str.substring(index, maxLength);
                }
            }
            index += maxLength;
            Log.e(title, sub.trim());
        }
    }

}
