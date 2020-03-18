package com.callhh.module_common.util.common;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author callhh
 */

public class MyStringUtil {

    /**
     * 把字符串中的指定位置的内容，变为指定的颜色
     *
     * @param textView textView控件
     * @param color    显示标注的颜色
     * @param start    字符串的起始位置
     * @param end      字符串的结束位置
     */
    public static void setSpannableString(TextView textView, int color, int start, int end) {
        if (null == textView && TextUtils.isEmpty(textView.getText().toString())) return;
        SpannableString spannableString = new SpannableString(textView.getText().toString());
        spannableString.setSpan(new ForegroundColorSpan(color),
                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    /**
     * 把字符串中的数字变为指定的颜色
     *
     * @param textView 文本控件
     * @param strColor 颜色值
     */
    public static void setSpannableStringForNum(TextView textView, String strColor) {
        if (null == textView && TextUtils.isEmpty(textView.getText().toString())) return;
        String string = textView.getText().toString();
        SpannableStringBuilder spannableString = new SpannableStringBuilder(string);
        for (int i = 0; i < string.length(); i++) {
            char a = string.charAt(i);
            if (a >= '0' && a <= '9') {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(strColor)),
                        i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(spannableString);
    }

    /**
     * 设置部分文字内容加租
     *
     * @param textView tv控件获取文本
     * @param start    字符串的起始位置
     * @param end      字符串的结束位置
     */
    public static void setSpannableStringBold(TextView textView, int start, int end) {
        if (null == textView && TextUtils.isEmpty(textView.getText().toString())) return;
        SpannableString spannableString = new SpannableString(textView.getText().toString());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),
                start, start + end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    /**
     * String转换为int类型
     *
     * @param string 字符串
     */
    public static int stringToInt(String string) {
        int j = 0;
        String str = string.substring(0, string.indexOf(".")) + string.substring(string.indexOf(".") + 1);
        int intgeo = Integer.parseInt(str);
        return intgeo;
    }

    /**
     * 字符串转换为整数,判断除了小数、负数、非数字等情况
     *
     * @param str 字符串
     * @return int数字
     */
    public static int strToInt(String str) {
        int result = 0;
        if (isNum(str)) {
            if (!TextUtils.isEmpty(str)) {
                if (str.contains(".")) {
                    double dStr = Double.parseDouble(str);
                    result = Double.valueOf(dStr).intValue();
                } else {
                    result = Integer.parseInt(str);
                }
            }
            MyLogUtils.logI("parseInt: " + result);
        }
        return result;
    }

    /**
     * 字符串转换为double
     */
    public static double strToDoubleValue(String str) {
        double result = 0.0;
        if (isNum(str)) result = Double.parseDouble(str);
        return result;
    }

    /**
     * 判断字符串是否为正数,校验包含正数，小数，为负数的就是非整数
     *
     * @param str 字符串
     * @return 是纯数字则返回true
     */
    public static boolean isNum(String str) {
        boolean result = false;
        try {
            if (str != null) {
//                Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*");
                Pattern pattern = Pattern.compile("[0-9]+.*[0-9]*");
                Matcher isNum = pattern.matcher(str);
                result = isNum.matches();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * double类型的数字保留两位小数
     */
    public static String getDoubleValue(double d) {
        DecimalFormat df = new DecimalFormat("##0.00");
        String str = "0.00";
        if (d > 0) {
            str = df.format(d);
        }
        return str;
    }

    /**
     * 校验密码的完整性，设置密码必须符合由数字,大写字母,小写字母,特殊符,至少其中三种组成密码并校验
     * [A-Za-z]+$ 表示字符串是由大写字母和小写字母组成
     * ![A-Za-z]+$ 表示字符串不全是大写字母和小写字母组成
     * (?![A-Za-z]+$) 表示如果从当前匹配位置开始到结尾是一个不全是大写字母和小写字母组成的字符串，就匹配，否则匹配位置保持不变，执行接下来的表达式
     * \\S{8,18}表示字符串是8-20位
     * @param pwd   需要校验的密码串
     * @return      校验密码完整符合规则返回true，不符合返回false
     */
    public static boolean checkPasswordIntegrity(String pwd) {
        Pattern p = Pattern.compile(
                "^(?![A-Za-z]+$)(?![A-Z\\d]+$)(?![A-Z\\W]+$)(?![a-z\\d]+$)(?![a-z\\W]+$)(?![\\d\\W]+$)\\S{8,18}$");
        Matcher m = p.matcher(pwd);
        boolean isOk = m.find();
        return isOk;
    }

    /**
     * 正则表达式，截取字符串中的所有数字
     *
     * @param content 字符串
     */
    public static String getNumbers(String content) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        return m.replaceAll("").trim();
    }

    /**
     * 生成随机的4位数字验证码
     */
    public static String getCode() {
        Random random = new Random();
        return random.nextInt(10) + ""
                + random.nextInt(10) + ""
                + random.nextInt(10) + "" + random.nextInt(10);
    }

    /**
     * 将手机号码中间四位数隐藏
     */
    public static String hidePhoneNum(String hide) {
        try {
            return hide.substring(0, 3) + "****" + hide.substring(7, hide.length());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 正则判断，验证手机号格式
     *
     * @param mobileNumber 手机号
     */
    public static boolean checkMobileNumber(String mobileNumber) {
         /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3\4\5\7\8\9，其他位置的可以为0-9
    */
        // "[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位
        String num = "[1][3456789]\\d{9}";
        if (TextUtils.isEmpty(mobileNumber)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return mobileNumber.matches(num);
        }
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIDCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) return false;
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return idCard.matches(regx) || idCard.matches(reg1) || idCard.matches(regex);
        //方案二：
//        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
//        return Pattern.matches(regex, idCard);
    }

    /**
     * 银行卡号，保留最后4位，其他星号替换
     *
     * @param cardId 卡号
     * @return 星号替换的银行卡号
     */
    public static String cardIdHide(String cardId) {
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }

    /**
     * 身份证号，中间10位星号替换
     *
     * @param id 身份证号
     * @return 星号替换的身份证号
     */
    public static String idHide(String id) {
        return id.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1** **** ****$2");
    }

    /**
     * 将字符串中的数字替换为星号的方法
     */
    public static String ReplaceNumber(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isANumber(c + "")) {
                sb.append("*");
            } else {
                sb.append(c);
            }

        }
        return sb.toString();
    }

    public static boolean isANumber(String str) {
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 使用String的split方法,将一段逗号分割的字符串转换成一个数组
     */
    public static String[] convertStringToArray(String str) {
        String[] strArray;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    /**
     * 获取指定字符串内容：截取该字符串中"symbolString"【符号前】的所有内容
     *
     * @param string    原字符串
     * @param strSymbol 指定标志，符号等
     * @return 截取后的字符串
     */
    public static String getSpecifiedStringBefore(String string, String strSymbol) {
        if (TextUtils.isEmpty(string)) {
            return null;
        } else {
            return string.substring(0, string.indexOf(strSymbol));
        }
    }

    /**
     * 获取指定字符串内容：截取该字符串中"symbolString"【符号后】的所有内容
     *
     * @param string    原字符串
     * @param strSymbol 指定标志，符号等
     * @return 截取后的字符串
     */
    public static String getSpecifiedStringAfter(String string, String strSymbol) {
        if (TextUtils.isEmpty(string)) {
            return null;
        } else {
            return string.substring(string.indexOf(strSymbol) + 1);
        }
    }

    /**
     * 使用逗号将集合拼接成字符串
     *
     * @param stringList 字符串集合
     */
    public static String getArrayToString(List<String> stringList, String strSymbol) {
        if (null == stringList || stringList.size() <= 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringList.size(); i++) {
            //该步即不会第一位有逗号，也防止最后一位拼接逗号！
            if (sb.length() > 0) {
                sb.append(strSymbol);
            }
            sb.append(stringList.get(i));
        }
        return sb.toString();
    }

    /**
     * 替换所有字符串中的指定关键词
     *
     * @param content        字符串内容
     * @param keyword        关键词
     * @param replaceKeyword 替换的关键词
     * @return 新的字符串
     */
    public static String replaceString(String content, String keyword, String
            replaceKeyword) {
        String newStr = "";
        if (!TextUtils.isEmpty(content))
            if (content.contains(keyword)) {
                newStr = content.replaceAll(keyword, replaceKeyword);
            }
        return newStr;
    }

    /**
     * 计算两地的距离
     *
     * @param slocx 发送地经度
     * @param slocy 发送地纬度
     * @param rlocx 接收地
     * @param rlocy 接收地
     */
    public double getDistance(String slocx, String slocy, String rlocx, String rlocy) {

        double jl_jd = 102834.74258026089786013677476285;
        double jl_wd = 111712.69150641055729984301412873;
        double b = Math.abs((Double.parseDouble(slocx) - Double.parseDouble(rlocx)) * jl_jd);
        double a = Math.abs((Double.parseDouble(slocy) - Double.parseDouble(rlocy)) * jl_wd);
        return Math.sqrt(b * b + a * a) / 1000;
    }

    public static void getONEKEY() {
//        OkHttpUtils.get()
//                .url(IOUtils.S_API + UriUtil.MKEY)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onResponse(String response, int id) {
//                        try {
//                            JSONObject jsonData = new JSONObject(response);
//                            String value = jsonData.getJSONObject("result").getString("v");
//                            if (value.equals("1")){
//                                SPUtils.saveValue(true);
//                            }else {
//                                SPUtils.saveValue(false);
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                    }
//                });
    }

    /**
     * HTML统一的加载样式，资讯详情页面
     */
    public static String getHtmlContentStyle(String bodyHTML) {
        String htmlStyle = "<html lang=\"zh-cn\">" + "<head>" + "<meta charset=\"utf-8\">"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\">"
                + "<style>body {margin: 0;padding: 0;font-size:62.5%;}"
                + "p {margin: 0px;padding: 0px;}"
                + ".layout {max-width: 768px;min-width: 320px;padding: 6px 15px 15px;margin: 0 auto;box-sizing: border-box;}"
                + ".layout article header h1 {font-size: 2.2em;line-height: 1.2em;}"
                + ".layout article header h4 {color: #999;font-size: 1.2em;font-weight: normal;line-height: 0.5em;margin-bottom: 30px;}"
                + ".layout article p {font-size: 1.8em;line-height: 1.6em;text-align: justify;color: #555;}"
                + "img {max-width: 100%;height: auto;margin: 10px auto;}"
                + "a{color: #555;text-decoration: none;}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='layout'>"
                + "<article>"
                + bodyHTML
                + "</article>"
                + "</div>"
                + "</body></html>";
        return htmlStyle;
    }

    /**
     * 内容添加头部,防止scrollview webView嵌套导致的底部大量空白
     */
    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 去除字符串中小数点后面的字符（'7.00'=='7'）
     */
    public static String getPiceSubstring(String price) {
        String newStr;
        if (price.contains(".")) {
            int i = price.indexOf(".");//首先获取字符的位置
            newStr = price.substring(0, i);
        } else {
            newStr = price;
        }
        return newStr;
    }

    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param theString 要转码的字符串
     * @return 转化后的结果.
     */
    public static String decodeUnicode(String theString) {
        if (TextUtils.isEmpty(theString))return "";
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * Json 转成 Map<>
     *
     * @param jsonObject 需要转成map的json对象
     * @return Map<String   ,       Object>对象
     */
    public static Map<String, Object> getMapForJson(JsonObject jsonObject) {
        Map<String, Object> resultMap = new HashMap<>();
        String key;
        Object value;
        try {
            Set<String> keySet = jsonObject.keySet();
            //循环添加到Map集合
            for (String keyString : keySet) {
                key = keyString;
                value = jsonObject.get(key);
                resultMap.put(key, value);
            }
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * Json 转成 Map<>
     *
     * @param jsonStr 需要转成map对象的json字符串
     * @return Map<String   ,       Object>对象
     */
    public static Map<String, Object> getMapForJson(String jsonStr) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonStr);

            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> resultMap = new HashMap<>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                resultMap.put(key, value);
            }
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json 转成 List<Map<>>
     *
     * @param jsonStr 需要转成map对象的json字符串
     * @return Map<String   ,       Object>
     */
    public static List<Map<String, Object>> getlistForJson(String jsonStr) {
        List<Map<String, Object>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObj;
            list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = (JSONObject) jsonArray.get(i);
                list.add(getMapForJson(jsonObj.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
