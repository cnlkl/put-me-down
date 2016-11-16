package com.fzuclover.putmedown.features.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.features.forgotpassword.ForgotPasswordActivity;
import com.fzuclover.putmedown.features.forgotpassword.ForgotPasswordContract;
import com.fzuclover.putmedown.features.register.RegisterActivity;
import com.fzuclover.putmedown.features.totaltimingtoday.TotalTimingTodayActivity;

public class LoginActivity extends BaseActivity implements LoginContract.View,View.OnClickListener {

    private TextView mForgotPassword;
    private TextView mRegister;

    void toForgotPasswordActivity() {
        Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void toRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mForgotPassword = (TextView) findViewById(R.id.forget_password);
        mForgotPassword.setOnClickListener(this);
        mRegister = (TextView) findViewById(R.id.sign_in);
        mRegister.setOnClickListener(this);
    }

    @Override

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_password:
                toForgotPasswordActivity();
                break;
            case R.id.sign_in:
                toRegisterActivity();
                break;
        }
    }
}

