package com.fzuclover.putmedown.networks;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.fzuclover.putmedown.networks.callback.HttpCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lkl on 2016/11/18.
 */

public class OkHttpManager {
    private static OkHttpManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private OkHttpManager(){
        mOkHttpClient = new OkHttpClient();

        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpManager getInstance(){
        if(mInstance == null){
            synchronized (OkHttpManager.class){
                if(mInstance == null){
                    mInstance = new OkHttpManager();
                }
            }
        }
        return mInstance;
    }


    public void okGet(String url, final HttpCallBack httpCallBack){
        final Request request = new Request.Builder().url(url).build();
        if(mOkHttpClient != null){
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            httpCallBack.onError(e.toString());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String responseStr = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            httpCallBack.onFinish(responseStr);
                        }
                    });
                }
            });

        }
    }

    public void okPost(String url, HashMap<String, String> params, final HttpCallBack httpCallBack){
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for(Map.Entry<String, String> entry : params.entrySet()){
            bodyBuilder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = bodyBuilder.build();

        Request request = new Request.Builder().url(url).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseStr = response.body().string();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onFinish(responseStr);
                    }
                });
            }
        });
    }
}
