package com.callhh.module_common.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.callhh.module_common.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/**
 * Glide加载图片的單例模式封装，圆形、圆角，模糊等处理操作用到了jp.wasabeef:glide-transformations:2.0.1
 * Glide默认使用httpurlconnection协议，可以配置为OkHttp
 */
public class GlideUtil {

    private static GlideUtil mInstance;

    private GlideUtil() {
    }

    public static GlideUtil getInstance() {
        if (mInstance == null) {
            synchronized (GlideUtil.class) {
                if (mInstance == null) {
                    mInstance = new GlideUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 显示加载网络图片，无占位图或动画效果
     * 场景：圆形头像、简单的图标
     */
    public void showImage(Context context, ImageView imageView, String urlPath) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (!TextUtils.isEmpty(urlPath)) {
            Glide.with(context)
                    .load(urlPath)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 常用的加载图片
     * isAnimationAndPlaceholder = false不设置任何占位图,防止和CircleImageView产生冲突
     * 注意：不适合加载圆形图片控件
     * @param context    上下文
     * @param imageView  图片容器
     * @param imgUrl     图片地址
     * @param isOpenAnim 是否需要淡入淡出动画
     */
    public void showImage(Context context, ImageView imageView,
                          String imgUrl, int errorImages, boolean isOpenAnim) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(errorImages)//占位图
                .error(errorImages)//异常占位图
//                .override(200, 100)//指定图片大小,图片像素200*100
//                .skipMemoryCache(true)//禁用掉Glide的内存缓存功能
//                .transform(new BlurTransformation(23, 4))//多种变换，如高斯模糊
                .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存策略，ALL=表示既缓存原始图片，也缓存转换过后的图片
        if (isOpenAnim) {
            Glide.with(context)
                    .load(imgUrl)
                    .apply(requestOptions)//p配置相关信息
                    .transition(withCrossFade())//淡入淡出的过渡效果
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(imgUrl)
                    .into(imageView);
        }
    }

    /**
     * 显示加载本地图片资源
     * @param context       上下文
     * @param imageView     图片View
     * @param resourceId    图片资源路径
     */
    public void showLocalImage(Context context, ImageView imageView, int resourceId) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(resourceId)
                .apply(requestOptions)
                .transition(withCrossFade())//淡入淡出的过渡效果
                .into(imageView);
    }

    /**
     * 显示加载本地资源图片，伴有占位符(默认或失败图)
     *
     * @param context    上下文
     * @param imageView  图片控件
     * @param resourceId 本地图片资源
     * @param errorImage 异常占位图片
     */
    public void showLocalImage(Context context, ImageView imageView, int resourceId, int errorImage) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(errorImage)
                .error(errorImage)//异常占位图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(resourceId)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 显示加载图片并设置图片宽高大小
     *
     * @param context     上下文
     * @param imageView   View控件
     * @param imgUrl      图片url
     * @param errorImages 占位符-加载失败后显示的图片资源
     * @param withSize    宽
     * @param heightSize  高
     */
    public void showImageAndSetSize(Context context, ImageView imageView,
                                    String imgUrl, int errorImages, int withSize, int heightSize) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(errorImages)
                .error(errorImages)
                .override(withSize, heightSize)//指定图片大小,如图片像素200*100
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 显示加载图片并含有动画
     * api也提供了几个常用的动画：比如crossFade()
     * 使用自定义的动画,可以使用GenericTransitionOptions.with(int viewAnimationId)
     * 或者BitmapTransitionOptions.withCrossFade(int animationId, int duration)
     * 或者DrawableTransitionOptions.withCrossFade(int animationId, int duration)
     */
    public void showImageWithAnimation(Context context, ImageView imageView, String urlPath) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(urlPath)
                .apply(requestOptions)
//                .transition(GenericTransitionOptions.with(viewAnimationId))//自定义动画，但是展示较生硬
                .transition(withCrossFade())//自定义动画
                .into(imageView);
    }

    /**
     * 先显示缩略图，再显示原图：用原图的5/10作为缩略图
     */
    public void showImageWithThumbnail(Context context, ImageView imageView, String urlPath) {
        Glide.with(context)
                .load(urlPath)
                .thumbnail(0.5f)
                .into(imageView);
    }

    /**
     * 先显示缩略图，再显示原图。用本地资源图片作为缩略图
     */
    public void showImageWithLocalThumbnail(Context context
            , ImageView imageView, String urlPath,int imgResId) {
        RequestBuilder<Drawable> requestBuilder = Glide
                .with(context)
                .load(imgResId);
        Glide.with(context).load(urlPath)
                .thumbnail(requestBuilder)
                .into(imageView);
    }

    /**
     * 显示加载圆形图片，Glide工具原生处理
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 图片控件
     */
    public void showCircleImage(final Context context, String url, final ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.ic_avatar_def);
        Glide
                .with(context)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 显示加载圆形图片，Glide工具原生处理
     * 可配置圆形半径、圆角大小
     *
     * @param context   上下文
     * @param imgUrl    图片路径
     * @param imageView 图片控件
     * @param radius_dp 圆形半径
     */
    public void showCircleImage(final Context context, String imgUrl
            , final ImageView imageView, final int radius_dp) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.ic_avatar_def);
        Glide
                .with(context)
                .asBitmap()
                .load(imgUrl)
                .apply(requestOptions)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(radius_dp);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * Glide4.7.1版本的使用和实现圆角方案,可自定义指定某个角为圆角
     * 注意：图片样式不要设置centerGrop，否者圆角无效
     *
     * @param context   上下文
     * @param imageView 图片控件
     * @param imgUrl    图片Url
     * @param radius_dp 圆角半径
     * @param type      圆角类型
     */
    public void showRoundCornerImage(Context context, ImageView imageView
            , String imgUrl, int radius_dp, GlideRoundCornersTransUtils.CornerType type) {
        RequestOptions requestOptions = new RequestOptions()
                //设置等待时的图片【这个时候需要注释，否则这个会作为背景图】
//                .placeholder(R.mipmap.img_error)
                .error(R.drawable.ic_loading_def)
                .centerCrop()
                //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                .skipMemoryCache(false)
                //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //设置图片加载的优先级
                .priority(Priority.HIGH)
//                .override(imageWidthSize,imageHeightSize)
                .transform(new GlideRoundCornersTransUtils(dip2px(context, radius_dp)
                        , 0, type));
        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                //默认淡入淡出动画
                .transition(withCrossFade())
                .into(imageView);
    }

    /**
     * 显示加载本地的圆角图片
     */
    public void showLocalRoundCornerImage(Context context, ImageView imageView
            ,  int resourceId, int radius_dp, GlideRoundCornersTransUtils.CornerType type) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_loading_def)
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
//                .override(imageWidthSize,imageHeightSize)
                .transform(new GlideRoundCornersTransUtils(dip2px(context, radius_dp)
                        , 0, type));
        Glide.with(context)
                .load(resourceId)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(imageView);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 加载图片变换：模糊效果
     * @param context       上下文
     * @param imageView     图片控件
     * @param imgUrl        图片地址
     * @param errorImage    异常占位图片
     */
//    public void loadGaussBlurredImages(Context context, ImageView imageView
//            , String imgUrl, int errorImage) {
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(errorImage)
//                .error(errorImage)//异常占位图
//                // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”
//                .transform(new BlurTransformation(22, 4))
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.with(context)
//                .load(imgUrl)
//                .apply(requestOptions)
//                .into(imageView);
//    }

    /**
     * 恢复请求，一般在停止滚动的时候
     */
    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     */
    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache(final Context context) {
        new Thread(() -> {
            //清理磁盘缓存 需要在子线程中执行
            Glide.get(context).clearDiskCache();
        }).start();
    }

    /**
     * 清除内存缓存
     */
    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();//清理内存缓存  可以在UI主线程中进行
    }

}