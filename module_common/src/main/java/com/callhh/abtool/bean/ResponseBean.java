package com.callhh.abtool.bean;

import java.io.Serializable;

/**
 * Created by ccb on 2017/10/11.
 *
 */
public class ResponseBean<T> implements Serializable {

    public int code;
    public String msg;
    public T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}