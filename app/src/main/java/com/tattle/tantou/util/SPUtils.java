package com.tattle.tantou.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.callhh.module_common.util.ActivityManager;
import com.callhh.module_common.util.common.MyLogUtils;
import com.tattle.tantou.base.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SharedPreferences轻量级的存储工具类
 * 使用键值对的形式存储数据，其背后是用xml文件存放数据，文件存放在/data/data/<package name>/shared_prefs目录下
 */
public class SPUtils {

    private static String FILE_NAME = "tattle";
    /**
     * 第一个参数用于指定该文件的名称; 第二个参数指定文件的操作模式，共有四种操作模式
     * mode为操作模式，默认的模式为0或MODE_PRIVATE，还可以使用MODE_WORLD_READABLE和MODE_WORLD_WRITEABLE
     * mode指定为MODE_PRIVATE，则该配置文件只能被自己的应用程序访问。
     * mode指定为MODE_WORLD_READABLE，则该配置文件除了自己访问外还可以被其它应该程序读取。
     * mode指定为MODE_WORLD_WRITEABLE，则该配置文件除了自己访问外还可以被其它应该程序读取和写入
     */
    private static SharedPreferences sp = BaseApplication.getInstance()
            .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    /**
     * put Object preferences
     * 保存对象，场景：保存list\map,收藏、足迹、评论数
     */
    @SuppressWarnings("unchecked")
    public static boolean putObject(String key, Object object) {
        boolean flag = false;
        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //创建对象输出流，并封装字节流
            out = new ObjectOutputStream(baos);
            //将对象写入字节流
            out.writeObject(object);
            //将字节流编码成base64的字符窜
            String objectVal = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
            //将数据存入本地缓存
            flag = sp.edit().putString(key, objectVal).commit();
        } catch (Exception e) {
            e.printStackTrace();
            MyLogUtils.logI(e.toString());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * get Object preferences
     */
    @SuppressWarnings("unchecked")
    public static Object getObject(String key) {
        Object mObject = new Object();
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 获取已保存的字符串
            String objectVal = sp.getString(key, "");
            byte[] buffer = Base64.decode(objectVal, Base64.NO_WRAP);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            bais = new ByteArrayInputStream(buffer);
            if (bais.available() != 0)
                //再次封装
                ois = new ObjectInputStream(bais);
            //读取对象
            mObject = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mObject;
    }

    /**
     * 设置是否第一次进入App
     */
    public static void setIsFirstEnterApp(boolean isFirstEnterApp) {
        sp.edit().putBoolean("is_first_enter_app", isFirstEnterApp).commit();
    }

    public static boolean getIsFirstEnterApp() {
        return sp.getBoolean("is_first_enter_app", true);
    }

    /**
     * 保存地址来源的选中状态
     */
    public static void setSourceState(int state) {
        sp.edit().putInt("source_state", state).commit();
    }

    /**
     * 获取地址来源的选中状态
     */
    public static int getSourceState() {
        return sp.getInt("source_state", -1);
    }

    /**
     * 保存用户最新的logo图片
     */
    public static void setLogo(String logo) {
        sp.edit().putString("logo", logo).commit();
    }

    public static String getLogo() {
        return sp.getString("logo", "");
    }

    /**
     * 保存设备ID
     */
    public static void setDeviceID(String device_id) {
        sp.edit().putString("device_id", device_id).commit();
    }

    public static String getDeviceID() {
        return sp.getString("device_id", "");
    }

    /**
     * 保存城市ID
     */
    public static void setCityID(String city_id) {
        sp.edit().putString("city_id", city_id).commit();
    }

    public static String getCityID() {
        return sp.getString("city_id", "");
    }

    /**
     * 保存用户登录状态
     */
    public static void saveIsLogin(boolean loginState) {
        sp.edit().putBoolean(ConstUtils.KEY_IS_LOGIN, loginState).commit();
    }

    /**
     * 获取用户登录状态
     */
    public static boolean getIslogin() {
        return sp.getBoolean(ConstUtils.KEY_IS_LOGIN, false);
    }

    /**
     * 保存用户登录kaiguan
     */
    public static void saveValue(boolean value) {
        sp.edit().putBoolean(ConstUtils.KEY_OPENVALUE, value).commit();
    }

    public static boolean getValue() {
        return sp.getBoolean(ConstUtils.KEY_OPENVALUE, false);
    }

    /**
     * 保存用户Token
     */
    public static void saveUserToken(String token) {
        if (!TextUtils.isEmpty(token))
            sp.edit().putString(ConstUtils.KEY_USER_TOKEN, token).commit();
    }

    /**
     * 获取用户token
     */
    public static String getUserToken() {
        return sp.getString(ConstUtils.KEY_USER_TOKEN, "");
    }

    /**
     * 保存用户id
     */
    public static void saveUserID(int userID) {
        sp.edit().putInt(ConstUtils.KEY_USER_ID, userID).apply();
    }

    /**
     * 获取用户id
     */
    public static int getUserID() {
        return sp.getInt(ConstUtils.KEY_USER_ID, -1);
    }

    /**
     * 保存用户手机号码
     */
    public static void saveUserPhoneNum(String phone_num) {
        if (!TextUtils.isEmpty(phone_num))
            sp.edit().putString(ConstUtils.KEY_USER_PHONE, phone_num).commit();
    }

    /**
     * 获取用户手机号
     */
    public static String getUserPhoneNum() {
        return sp.getString(ConstUtils.KEY_USER_PHONE, "");
    }

    /**
     * 保存用户头像url
     */
    public static void saveUserAvatar(String avatar) {
        sp.edit().putString(ConstUtils.KEY_USER_AVATAR, avatar).commit();
    }

    /**
     * 获取用户头像url
     */
    public static String getUserAvatar() {
        return sp.getString(ConstUtils.KEY_USER_AVATAR, "");
    }

    /**
     * 保存用户昵称
     */
    public static void saveUserName(String userName) {
        if (!TextUtils.isEmpty(userName))
            sp.edit().putString(ConstUtils.KEY_USER_NAME, userName).commit();
    }
    public static String getUserName() {
        return sp.getString(ConstUtils.KEY_USER_NAME, "");
    }

    /**
     * 保存用户账号
     */
    public static void saveUserAccount(String userAccount) {
        if (!TextUtils.isEmpty(userAccount))
            sp.edit().putString(ConstUtils.KEY_USER_ACCOUNT, userAccount).commit();
    }
    public static String getUserAccount() {
        return sp.getString(ConstUtils.KEY_USER_ACCOUNT, "");
    }

    /**
     * 缓存用户登录账号
     */
    public static void saveUserLoginAccount(String userLoginAccount) {
        if (!TextUtils.isEmpty(userLoginAccount))
            sp.edit().putString(ConstUtils.KEY_USER_LOGIN_ACCOUNT, userLoginAccount).commit();
    }
    public static String getUserLoginAccount() {
        return sp.getString(ConstUtils.KEY_USER_LOGIN_ACCOUNT, "");
    }

    /**
     * 清除用户信息(场景：退出登录或token被改变)
     */
    public static void clearUserInfo() {
        saveIsLogin(false);//保存用户登录状态
        saveUserToken("");
        saveUserID(-1);
        saveUserAccount("");
        saveUserName("");
        saveUserAvatar("");
        ActivityManager.getInstance().finishAll();
    }


}
