package com.callhh.module_common.util.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.callhh.module_common.listener.HttpCallBackListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 处理图片工具类
 */
public class ImageTools {

    private static String FILE_PATH = "MyApp";

    /**
     * 图片压缩
     *
     * @param bmp         bitmap图片
     * @param needRecycle 是否需要回收
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                e.printStackTrace();
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }

    /**
     * 网络url转换成bitmap对象(开启子线程处理)
     *
     * @param path       图片url
     * @param isCompress 是否需要再次压缩,true==压缩到34k以下;false==压缩到100k左右
     * @param listener   图片处理结果回调的监听器
     */
    public static void getImage(final String path, final boolean isCompress
            , final HttpCallBackListener listener) {
        if (TextUtils.isEmpty(path)) {
            if (listener != null) {
                listener.onFinish(null);
                return;
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap newBitmap;
                URL imageUrl;
                try {
                    imageUrl = new URL(path);
                    if (null != imageUrl) {
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        if (isCompress) {
                            //处理图片，压缩到32k以下
                            newBitmap = createBitmapThumbnail(bitmap, false);
                        } else {
                            //处理图片，压缩到100k以下
//                        newBitmap = compressImage(bitmap);
                            newBitmap = bitmap;
                        }
                        if (listener != null) {
                            listener.onFinish(newBitmap);
                        }
                        is.close();
                    }else {
                        if (listener != null) {
                            listener.onFinish(null);
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }

    /**
     * 把获取的Bitmap图片变小，压缩到32K以下
     */
    public static Bitmap createBitmapThumbnail(Bitmap bitmap, boolean needRecycler) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 80;
        int newHeight = 80;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitMap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (needRecycler) bitmap.recycle();
        return newBitMap;
    }

    /**
     * view转换为bitmap
     *
     * @param view 视图
     * @return bitmap   生成的图片
     */
    public static Bitmap convertViewToBitmap(View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }


    /**
     * Transfer drawable to bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap to drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }


    /**
     * Resize the bitmap
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * Resize the drawable
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        matrix.postScale(sx, sy);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(newbmp);
    }

    /**
     * Check the SD card
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 保存图片到手机SD卡
     *
     * @param photoBitmap 图片
     * @param photoName   图片名
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap, String photoName) {
        if (checkSDCardAvailable()) {
            File sdcardDir = Environment.getExternalStorageDirectory();

            String path = sdcardDir.getPath() + FILE_PATH;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName + ".jpg");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
//                      fileOutputStream.close();
                    }
                }
            }catch (Exception e) {
                photoFile.delete();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存图片到手机
     *
     * @param bmp 图片
     */
    public static String saveImageToGallery(Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + FILE_PATH;
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storePath + "/" + fileName;
    }

    /**
     * Delete the image from SD card
     *
     * @param path file:///sdcard/temp.jpg
     */
    public static void deleteAllPhoto(String path) {
        if (checkSDCardAvailable()) {
            File folder = new File(path);
            File[] files = folder.listFiles();
            if (null == files || files.length <= 0) return;
            for (File file : files) {
                file.delete();
            }
        }
    }

    public static void deletePhotoAtPathAndName(String path, String fileName) {
        if (checkSDCardAvailable()) {
            File folder = new File(path);
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().split("\\.")[0].equals(fileName)) {
                    files[i].delete();
                }
            }
        }
    }

    /**
     * 获取压缩完的图片
     *
     * @param path       图片路径
     * @param destWidth  缩放宽度
     * @param destHeight 缩放高度
     */
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 压缩文件
     */
    public static File compress2File(File imgFile, int destWidth, int destHeight) {
        Bitmap scaledBitmap = getScaledBitmap(imgFile.getAbsolutePath(), destWidth, destHeight);
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/app");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdir();
        }

        String fileName = imgFile.getName();
        String postfix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //效果："/storage/emulated/0/Pictures/xmsh/1505141720452_cropped.jpg"
        String newFilePath = mediaStorageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + "_cropped." + postfix;

        File newFile = new File(newFilePath);
        try {
            newFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(newFile);
            if (scaledBitmap != null && postfix.equalsIgnoreCase("jpg") || postfix.equalsIgnoreCase("jpeg")) {
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } else {
                scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 图片按比例大小压缩方法
     *
     * @param image （根据Bitmap图片压缩）
     */
    public static Bitmap compressScale(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是1920*1080分辨率，所以高和宽我们设置为
        // float hh = 800f;// 这里设置高度为1920f
        // float ww = 480f;// 这里设置宽度为1080f
        float hh = 1920f;
        float ww = 1080f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressBitmap(bitmap, 100);// 压缩好比例大小后再进行质量压缩
        //return bitmap;
    }

    /**
     * 图片质量压缩，大小压缩到xxK左右
     *
     * @param bitmapSize 图片最终的大小
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int bitmapSize) {
        Bitmap newBitmap = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            int options = 90;
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            while (baos.toByteArray().length / 1024 > bitmapSize) {
                baos.reset(); // 重置baos即清空baos
                // 这里压缩options%，把压缩后的数据存放到baos中
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;// 每次都减少10
            }
            // 把压缩后的数据baos存放到ByteArrayInputStream中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            // 把ByteArrayInputStream数据生成图片
            newBitmap = BitmapFactory.decodeStream(isBm, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBitmap;
    }

}