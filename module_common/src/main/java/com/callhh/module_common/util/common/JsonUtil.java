package com.callhh.module_common.util.common;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * json 和 实体类之间的相互转换
 */
public class JsonUtil {

    /**
     * beanToJson
     * 将一个实体对象  转换成一个json字符串  提示对象中可包含集合
     * @param t 实体类
     * @return
     */
    public static <T> String beanToJson(T t){
        Gson gson = new Gson();
        String json = gson.toJson(t);
        return json;
    }

    /**
     * jsonToBean
     * 将一个json字符串通过Gson转换成一个实体类对象，可包含list
     * 不能解析JSONArray
     */
    public static <T> T parseJSON(String jsonObj, Class<T> clazz){
        Gson gson = new Gson();
        T bean = gson.fromJson(jsonObj, clazz);
        return bean;
    }

    /**
     * 接口是否返回成功
     */
    public static boolean isRequestSuccess(JSONObject obj) {
        try {
            if (true == obj.getBoolean("success")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 接口是否返回成功
     */
    public static boolean isSuccess(JSONObject obj) {
        try {
            if ("1".equals(obj.getString("error_code"))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 签名错误或TOKEN过期,重新登录
     */
    public static boolean isFailure(JSONObject obj) {
        try {
            if ("-2".equals(obj.getString("code"))) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 将json字符串转换成一个json对象
     * @param str
     * @return
     */
    public static JSONObject stringToJson(String str){
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getString(InputStream is){

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = is.read(buffer)) != -1){
                baos.write(buffer, 0, len);
            }

            byte[] byteArray = baos.toByteArray();
            //String str = new String(byteArray);

            return new String(byteArray,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 从assert文件夹中读取json文件，然后转化为json对象
     * @throws Exception
     */
    public static JSONObject getJsonDataFromAssets(Context context, String jsonFileName) throws Exception{
        JSONObject mJsonObj = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = context.getAssets().open(jsonFileName);
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = is.read(buf)) != -1){
            sb.append(new String(buf, 0, len, "UTF-8"));
        }
        is.close();
        mJsonObj = new JSONObject(sb.toString());
        return mJsonObj;
    }

}
