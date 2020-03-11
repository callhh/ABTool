package com.callhh.module_common.helper;

/**
 * Created by callhh on 2020/3/9
 */
public class HttpExceptionHelper {
    private int code;
    private String msg;

    public HttpExceptionHelper(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
