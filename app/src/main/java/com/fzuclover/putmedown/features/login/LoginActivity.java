package com.fzuclover.putmedown.features.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.features.forgotpassword.ForgotPasswordActivity;
import com.fzuclover.putmedown.features.register.RegisterActivity;
import com.fzuclover.putmedown.features.totaltimingtoday.TotalTimingTodayActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import at.markushi.ui.CircleButton;

public class LoginActivity extends BaseActivity implements LoginContract.View,View.OnClickListener {

    private TextView mForgotPassword;
    private TextView mRegister;
    private CircleButton mLogin;
    private LoginContract.Presenter mPresenter;
    private MaterialEditText mAccountEdt;
    private MaterialEditText mPasswordEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){

        mPresenter = new LoginPresenter(this);

        mLogin = (CircleButton) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mForgotPassword = (TextView) findViewById(R.id.forget_password);
        mForgotPassword.setOnClickListener(this);
        mRegister = (TextView) findViewById(R.id.sign_in);
        mRegister.setOnClickListener(this);

        mAccountEdt = (MaterialEditText) findViewById(R.id.account_edt);
        mPasswordEdt = (MaterialEditText) findViewById(R.id.password_edt);
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
            case R.id.login:
                mPresenter.login(mAccountEdt.getText().toString(), mPasswordEdt.getText().toString());
                break;
        }
    }

    @Override
    public void toTotalTimingTodayActivity() {
        Intent intent = new Intent(LoginActivity.this,TotalTimingTodayActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void toForgotPasswordActivity() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void toRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}

