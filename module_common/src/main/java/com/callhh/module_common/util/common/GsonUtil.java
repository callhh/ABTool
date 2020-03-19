package com.callhh.module_common.util.common;

import android.content.Context;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * json 和 实体类之间的相互转换Gson工具类
 * Json2Bean / Json2List / Json2List<T>
 */
public class GsonUtil {

    /**
     * beanToJson
     * 将一个实体对象,转换成一个json字符串(提示对象中可包含list集合)
     * @param t 实体对象
     * @return  返回json字符串
     */
    public static <T> String beanToJson(T t){
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    /**
     * 对象转json字符串
     */
    public static <Object> String objectToJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Json 转为 Bean
     * 将一个json字符串通过Gson转换成一个实体类对象，可包含list
     * 不能解析JSONArray
     */
    public static <T> T parseJSON(String jsonObj, Class<T> clazz){
        Gson gson = new Gson();
        return gson.fromJson(jsonObj, clazz);
    }

    /**
     * 将json字符串转换成一个json对象
     * @param str   字符串
     * @return      返回jsont对象
     */
    public static JSONObject stringToJson(String str){
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数组json --> List<model>
     *     // 针对上述模型 json -> model  有如下操作：
     *     Gson gson = new Gson();
     *     // 错误的操作
     *     List<UserModel> userList = gson.fromJson(jsonArray, List<UserModel>.class)
     *     // 正确的操作
     *     List<UserModel> userList = gson.fromJson(jsonArray, new TypeToken<List<UserModel>>() {}.getType());
     *     // 原因：范型擦除
     *     对于Java来说List<String> 和List<User> 这俩个的字节码文件只一个那就是List.class
     *     参考资源：http://www.jianshu.com/p/d62c2be60617 泛型分析链接
     */


    /**
     * 解析json数组
     * @param json  json字符串列表
     * @param clazz 解析的实体模型
     * @param <T>   Student.class
     *           使用：List<Student> list = parseString2List(jsonString, Student.class);
     * @return  数据模型为clazz的集合对象
     */
    public static <T> List<T> parseStringToList(String json, Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    private  static  class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @NotNull
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};//返回实际类型组成的数据，即new Type[]{String.class,Student.class}
        }

        @NotNull
        @Override
        public Type getRawType() {
            return List.class;//返回原生类型
        }

        @Override
        public Type getOwnerType() {
            return null;//返回 Type 对象，表示此类型是其成员之一的类型
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

            return new String(byteArray, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 从assert文件夹中读取json文件，然后转化为json对象
     * @throws Exception    异常
     */
    public static JSONObject getJsonDataFromAssets(Context context, String jsonFileName) throws Exception{
        JSONObject mJsonObj;
        StringBuilder sb = new StringBuilder();
        InputStream is = context.getAssets().open(jsonFileName);
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = is.read(buf)) != -1){
            sb.append(new String(buf, 0, len, StandardCharsets.UTF_8));
        }
        is.close();
        mJsonObj = new JSONObject(sb.toString());
        return mJsonObj;
    }


    /**
     * 接口是否返回成功
     */
    public static boolean isRequestSuccess(JSONObject obj) {
        try {
            if (obj.getBoolean("success")) {
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

}
