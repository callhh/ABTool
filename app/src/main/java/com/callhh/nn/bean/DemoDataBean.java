package com.callhh.nn.bean;

import com.callhh.module_common.bean.BaseResponseBean;

/**
 * Created by admin on 2020/3/18
 */
public class DemoDataBean extends BaseResponseBean {

    private DemoBean data;

    public DemoBean getData() {
        return data;
    }

    public void setData(DemoBean data) {
        this.data = data;
    }
}
