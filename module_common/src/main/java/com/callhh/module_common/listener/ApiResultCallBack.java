package com.callhh.module_common.listener;

import com.callhh.module_common.bean.ResponseBean;
import com.callhh.module_common.helper.HttpExceptionHelper;

/**
 * Created by callhh on 2020/3/9
 */
public abstract class ApiResultCallBack<T> {

    public void onResponse( T response) {

    }

    public void onError(HttpExceptionHelper helper){

    }

}
