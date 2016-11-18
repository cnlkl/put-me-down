package com.fzuclover.putmedown.features.forgotpassword;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.fzuclover.putmedown.networks.OkHttpManager;
import com.fzuclover.putmedown.networks.callback.HttpCallBack;
import com.fzuclover.putmedown.networks.data.HttpUrl;
import com.fzuclover.putmedown.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by lkl on 2016/11/4.
 */

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {

    private ForgotPasswordContract.View mView;
    private EventHandler mEventHandler;
    private Handler mHandler;

    public ForgotPasswordPresenter(ForgotPasswordContract.View view){
        mView = view;

        mHandler = new Handler(Looper.getMainLooper());

        //手机短信验证
        SMSSDK.initSDK((Context)mView, "19219815dbf5a", "87fcdb78d2a73bb58b9791b2918d5f16");
        mEventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        updatePassword(mView.getPhone(), mView.getPassword());
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        //TODO 倒计时60秒
                        if(result == SMSSDK.RESULT_COMPLETE){
                            boolean smart = (boolean) data;
                            if(smart){
                                LogUtil.d("获取验证码","这个手机号不用验证");
                            }else{
                            }
                        }
                    }
                }else{
                    final Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    ((Throwable)data).printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject object = null;
                                    try {
                                        object = new JSONObject(throwable.getMessage());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    String des = object.optString("detail");//错误描述
                                    int status = object.optInt("status");//错误代码
                                    if (status > 0 && !TextUtils.isEmpty(des)) {
                                        Toast.makeText((Context) mView, des, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        };
        SMSSDK.registerEventHandler(mEventHandler);
    }


    @Override
    public void getCode(String phone) {
        mView.setGetCodeBtnTimeCount();
        SMSSDK.getVerificationCode("86", phone);
    }

    @Override
    public void submit(String phone, String code) {
        if(mView.getPassword().equals(mView.getConfirmPassword())){
            SMSSDK.submitVerificationCode("86", phone, code);
        }else{
            Toast.makeText((Context) mView,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void unRegisterSMSHandler() {
        SMSSDK.initSDK((Context)mView, "19219815dbf5a", "87fcdb78d2a73bb58b9791b2918d5f16");
        SMSSDK.unregisterEventHandler(mEventHandler);
    }

    private void updatePassword(String phone, String password){
        OkHttpManager okHttpManager = OkHttpManager.getInstance();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", phone);
        params.put("password", password);
        okHttpManager.okPost(HttpUrl.FORGET_PASSWORD_URL, params, new HttpCallBack() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("success")){
                        mView.toLoginActivity();
                        Toast.makeText((Context)mView, "修改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText((Context)mView, "修改密码失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText((Context)mView, "请输入正确信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String err) {
                Toast.makeText((Context)mView, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
