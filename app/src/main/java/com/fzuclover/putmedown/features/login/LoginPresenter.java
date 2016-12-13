package com.fzuclover.putmedown.features.login;

import android.content.Context;
import android.widget.Toast;

import com.fzuclover.putmedown.networks.OkHttpManager;
import com.fzuclover.putmedown.networks.callback.HttpCallBack;
import com.fzuclover.putmedown.networks.data.HttpUrl;
import com.fzuclover.putmedown.utils.SharePreferenceUtil;

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
                        SharePreferenceUtil.getInstance((Context)mView).close();
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
        SharePreferenceUtil.getInstance((Context)mView).saveUserName(username);
        SharePreferenceUtil.getInstance((Context)mView).saveLoginStatu(true);
    }

    @Override
    public boolean getLoginStatu() {
        return SharePreferenceUtil.getInstance((Context)mView).getLoginStatu();
    }
}
