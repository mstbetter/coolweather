package com.coolweather.app.util;

/**
 * Created by tyj on 2016/11/22.
 */
public interface HttpCallbackListener {
    void  onFinish(String response);
    void  onError(Exception e);
}
