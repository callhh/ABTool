package com.callhh.nn.util.http;

import android.text.TextUtils;


import com.callhh.abtool.util.common.MyLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * http请求的日志拦截器
 */
public class HttpLoggingInterceptor implements Interceptor {

    private static final String TAG = "OkHttp";
    private String tag;
    private boolean showResponse;

    public HttpLoggingInterceptor(String tag, boolean showResponse) {
        if (StringUtils.isBlank(tag)) tag = TAG;
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public HttpLoggingInterceptor(String tag) {
        this(tag, false);
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Request request = chain.request();
        logForRequest(request);
//        Request newRequest = chain.request().newBuilder()
//                .removeHeader("User-Agent")
//                .addHeader( "User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
//                .build() ;
        Response response; // 请求响应体
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long reqTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        //响应日志拦截
        return logForResponse(response, reqTime);
    }

    /**
     * 打印请求体相关参数
     *
     * @param request 请求体
     */
    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            log("======== Request Log======= Start");
            log("method : " + request.method());
            log("url : " + url);
            if (headers.size() > 0) {
                log("headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    log("requestBody contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        log("requestBody content : " + bodyToString(request));
                    } else {
                        log("requestBody content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
//            log("========request'log======= End");
        } catch (Exception e) {
            log(e.toString());
        }
    }

    /**
     * 打印响应体相关参数
     *
     * @param response 响应体
     * @param reqTime  请求响应的时间
     */
    private Response logForResponse(Response response, long reqTime) {
        try {
            //===>response log
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
//            log("url : " + clone.request().url());
            log("code : " + clone.code());
            log("protocol : " + clone.protocol());

            if (!TextUtils.isEmpty(clone.message()))
                log("message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        log("responseBody contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            log("responseBody content : " + resp);
                            body = ResponseBody.create(mediaType, resp);

                            return response.newBuilder().body(body).build();
                        } else {
                            log("responseBody content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
            log("======== Response Log======= End" + " (reqTime = " + reqTime + "ms)");
        } catch (Exception e) {
            log(e.toString());
        }

        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        subtype = subtype.toLowerCase();
        return subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml")
                || subtype.contains("html") || subtype.contains("webviewhtml");
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            Objects.requireNonNull(copy.body()).writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

    /**
     * 打印日志信息
     */
    private void log(String msg) {
        try {
            MyLogUtils.logE(tag, URLDecoder.decode(msg, "utf-8"));
        } catch (Exception e) {
            MyLogUtils.logI(e.toString());
        }
    }

}
