package com.callhh.nn.bean.base;

import java.io.Serializable;

/**
 * 通用的模型类(服务器返回的基本jsonData格式)
 * code:为0时，请求成功；为-1、40000等其他值时，未登录或其他错误;具体咨询后台
 */
public class BaseBody implements Serializable {

	private int code;
	private String msg;

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
