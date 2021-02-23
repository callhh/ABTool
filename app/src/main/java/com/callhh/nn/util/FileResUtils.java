package com.callhh.nn.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件读写相关工具类
 * Created on 2021-02-20 by callhh
 */
public class FileResUtils {

    /**
     * 方式1 从asset路径下读取对应文件转String输出
     */
    public static String getJsonStr(Context context, String fileName) throws IOException {
        InputStream in = context.getResources().getAssets().open(fileName);
        int available = in.available();
        byte[] b = new byte[available];
        in.read(b);
        return new String(b, "UTF-8");
    }

    /**
     * 方式2 从asset路径下读取对应文件转String输出
     */
    public static String getJsonStrA(Context mContext, String fileName) {
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }
}
