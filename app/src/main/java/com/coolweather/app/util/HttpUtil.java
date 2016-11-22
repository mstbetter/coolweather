package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tyj on 2016/11/22.
 */
public class HttpUtil {
    public  static   void  sendHttpRequest(final String address, final HttpCallbackListener listener){

        new  Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection openConnection=null;
                try {
                    URL url = new URL(address);
                    openConnection = (HttpURLConnection) url.openConnection();
                    openConnection.setRequestMethod("GET");
                    openConnection.setConnectTimeout(8000);
                    openConnection.setReadTimeout(8000);
                    InputStream in = openConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }

                    if (listener!=null){
                        //回调onFinish()方法
                        listener.onFinish(response.toString());
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                    if (listener!=null){
                        //回调onError()方法
                        listener.onError(e);
                    }
                }finally {
                    if (openConnection!=null){
                        openConnection.disconnect();
                    }
                }
            }
        }).start();
    }
}
