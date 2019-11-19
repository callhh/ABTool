package com.callhh.module_common.util;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * 文件管理类
 */
public class FilesManager {

    // 获取内部缓存(-1表示获取错误)
    public static long getInternalCache(Context context) {
        return getFileSize(context.getCacheDir());
    }

    // 获取外部缓存(-1表示获取错误)
    public static long getExternalCache(Context context) {
        return getFileSize(context.getExternalCacheDir());
    }

    /**
     * 获取所有的缓存大小
     */
    public static String getAllCache(Context context) {
        return FormetFileSize(getFileSize(context.getCacheDir())
                + getFileSize(context.getExternalCacheDir()));
    }

    /**
     * 清空 内部缓存
     */
    public static boolean cleanInternalCache(Context context) {
        if (deleteFilesByDirectory(context.getCacheDir())) {
            return true;
        }
        return false;

    }

    /**
     * 清空 外部缓存
     */
    public static boolean cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            if (deleteFilesByDirectory(context.getExternalCacheDir())) {
                return true;
            }
        } else {
            Toast.makeText(context, "外部存储卡不可用", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * 清空 所有缓存
     */
    public static boolean cleanAllCache(Context context) {

        if (cleanExternalCache(context)) {

        }
        if (cleanInternalCache(context)) {

        }

        return false;
    }

    /**
     * 删除文件夹
     */
    private static boolean deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if (item.isDirectory()) {
                    deleteFilesByDirectory(item);
                } else {
                    item.delete();
                }
            }
            return true;
        }

        return false;

    }

    // 递归 取得文件夹大小
    public static long getFileSize(File f) {
        long size = 0;
        try {
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSize(flist[i]);
                } else {
                    size = size + flist[i].length();
                }

            }

        } catch (Exception e) {
            size = -1;
            System.out.println("-->>" + e.getMessage());
        }

        return size;
    }

    // 转换文件大小
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 读取本地文本数据
     *
     * @param context  程序上下文
     * @param fileName 文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public static String readLocalAssets(Context context, String fileName) {
        String content = null;
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = context.getAssets().open(fileName);
            if (is != null) {
                byte[] buffer = new byte[1024];
                os = new ByteArrayOutputStream();
                while (true) {
                    int readLength = is.read(buffer);
                    if (readLength == -1) break;
                    os.write(buffer, 0, readLength);
                }
                content = new String(os.toByteArray());
            }
        } catch (Exception e) {
            content = null;
        } finally {
            try {
                if (os != null) os.close();
                if (is != null) is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 读取Json文件
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
