package com.callhh.module_common.bean;

import java.io.Serializable;

/**
 * 基础http响应实体
 */
public class BaseResponseBean implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public int code;
    public String msg;

    public ResponseBean toResponseBean() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.code = code;
        responseBean.msg = msg;
        return responseBean;
    }
}
