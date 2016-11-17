

package com.fzuclover.putmedown.features.register;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fzuclover.putmedown.features.login.LoginActivity;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;


public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterContract.View,View.OnFocusChangeListener{

    private MaterialEditText mPhonumEditText;
    private MaterialEditText mSecretEditText;
    private MaterialEditText mCheckSecEditText;
    private MaterialEditText mCodeEdt;
    private Button mSendCodeBtn;
    private Button mSubmitBtn;
    private String mPhonenum;
    private String mSecret;
    private RegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        mPresenter = new RegisterPresenter(this);

        mPhonumEditText=(MaterialEditText)findViewById(R.id.register_phonenum);
        mSecretEditText=(MaterialEditText)findViewById(R.id.register_newsecret);
        mCheckSecEditText=(MaterialEditText)findViewById(R.id.register_checksecret);
        mCodeEdt = (MaterialEditText) findViewById(R.id.register_checknum);
        mPhonumEditText.setOnFocusChangeListener(this);
        mPhonumEditText.setTag(1);
        mSecretEditText.setOnFocusChangeListener(this);
        mSecretEditText.setTag(2);
        mCheckSecEditText.setOnFocusChangeListener(this);
        mCheckSecEditText.setTag(3);

        mSendCodeBtn = (Button) findViewById(R.id.register_get_checknum);
        mSendCodeBtn.setOnClickListener(this);
        mSendCodeBtn = (Button) findViewById(R.id.register_submit);
        mSendCodeBtn.setOnClickListener(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       mPresenter.unRegisterHandler();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
          int tag=(Integer)view.getTag();
          String inputInformation;
          switch(tag){
              case 1:
                  mPhonenum=mPhonumEditText.getText().toString();
                  if((mPhonenum.length()<11&&mPhonenum.length()>0) || mPhonenum.length()>11)
                  {
                      mPhonumEditText.setError("手机号码格式错误");
                  }
                  break;
              case 2:
                  mSecret=mSecretEditText.getText().toString();
                  if(mSecret.length()<6)
                  {
                      mSecretEditText.setError("密码应包含6-20个字符");
                  }
                  break;
              case 3:
                  inputInformation=mCheckSecEditText.getText().toString();
                  if(!inputInformation.equals(mSecret))
                  {
                      mCheckSecEditText.setError("密码不一致");
                      mCheckSecEditText.setText("");
                  }
                  break;
          }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_get_checknum:
                mPresenter.getCode(mPhonumEditText.getText().toString());
                break;
            case R.id.register_submit:
                mPresenter.submit(mPhonumEditText.getText().toString(), mCodeEdt.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public String getPhone() {
        return mPhonumEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mSecretEditText.getText().toString();
    }
}