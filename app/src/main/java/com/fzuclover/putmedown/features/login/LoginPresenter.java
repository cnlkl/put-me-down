package com.fzuclover.putmedown.features.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.fzuclover.putmedown.networks.OkHttpManager;
import com.fzuclover.putmedown.networks.callback.HttpCallBack;
import com.fzuclover.putmedown.networks.data.HttpUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by lkl on 2016/11/4.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
    }


    @Override
    public void login(final String username, String password) {
        OkHttpManager okHttpManager = OkHttpManager.getInstance();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        okHttpManager.okPost(HttpUrl.LOGIN_URL, params, new HttpCallBack() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        saveLoginStatu(username);
                        mView.toTotalTimingTodayActivity();
                    } else {
                        Toast.makeText((Context) mView, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String err) {
                Toast.makeText((Context) mView, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void saveLoginStatu(String username) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)mView);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putBoolean("is_login", true);
        editor.commit();
    }

    @Override
    public boolean getLoginStatu() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)mView);
        return sharedPreferences.getBoolean("is_login",false);
    }
}
