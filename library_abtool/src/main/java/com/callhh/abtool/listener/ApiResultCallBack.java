package com.callhh.abtool.listener;

import com.callhh.abtool.helper.HttpExceptionHelper;

/**
 * Created by callhh on 2020/3/9
 */
public abstract class ApiResultCallBack<T> {

    public void onResponse( T response) {

    }

    public void onError(HttpExceptionHelper helper){

    }

}
