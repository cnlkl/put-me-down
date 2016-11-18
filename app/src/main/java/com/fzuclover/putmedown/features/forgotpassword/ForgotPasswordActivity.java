package com.fzuclover.putmedown.features.forgotpassword;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.utils.TimeCountUtil;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener, ForgotPasswordContract.View {

    private EditText mPhoneEdt;

    private EditText mCodeEdt;

    private EditText mPasswordEdt;

    private EditText mConfirmPasswordEdt;

    private Button mGetCodeBtn;

    private Button mSubmitBtn;

    private ForgotPasswordContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init(){

        mPresenter = new ForgotPasswordPresenter(this);

        mPhoneEdt = (EditText) findViewById(R.id.forgot_password_phonenum);

        mCodeEdt = (EditText) findViewById(R.id.code_edt);

        mPasswordEdt = (EditText) findViewById(R.id.forgot_password_checksecret);

        mConfirmPasswordEdt = (EditText) findViewById(R.id.forgot_password_newsecret);

        mGetCodeBtn = (Button) findViewById(R.id.get_checknum);
        mGetCodeBtn.setOnClickListener(this);

        mSubmitBtn = (Button) findViewById(R.id.forgot_password_submit);
        mSubmitBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unRegisterSMSHandler();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.get_checknum:
                mPresenter.getCode(mPhoneEdt.getText().toString());
                break;
            case R.id.forgot_password_submit:
                mPresenter.submit(mPhoneEdt.getText().toString(), mCodeEdt.getText().toString());
                break;
        }
    }

    @Override
    public void toLoginActivity() {
        finish();
    }

    @Override
    public String getPhone() {
        return mPhoneEdt.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEdt.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return mConfirmPasswordEdt.getText().toString();
    }

    @Override
    public void setGetCodeBtnTimeCount() {
        TimeCountUtil timer = new TimeCountUtil(60000, 1000, new TimeCountUtil.CountListener() {
            @Override
            public void onTick(long l) {
                mGetCodeBtn.setClickable(false);
                mGetCodeBtn.setText(l /1000+"秒后重发");
            }

            @Override
            public void onFinish() {
                mGetCodeBtn.setText("发送");
                mGetCodeBtn.setClickable(true);
            }
        });
        timer.start();
    }
}
