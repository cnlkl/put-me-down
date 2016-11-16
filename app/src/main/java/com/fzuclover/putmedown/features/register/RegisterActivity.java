

package com.fzuclover.putmedown.features.register;


import android.os.Bundle;
import android.view.View;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;

public class RegisterActivity extends BaseActivity implements RegisterContract.View,View.OnFocusChangeListener{

    private MaterialEditText mPhonumEditText;
    private MaterialEditText mSecretEditText;
    private MaterialEditText mCheckSecEditText;
     private String mPhonenum;
     private String mSecret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPhonumEditText=(MaterialEditText)findViewById(R.id.register_phonenum);
        mSecretEditText=(MaterialEditText)findViewById(R.id.register_newsecret);
        mCheckSecEditText=(MaterialEditText)findViewById(R.id.register_checksecret);
        mPhonumEditText.setOnFocusChangeListener(this);
        mPhonumEditText.setTag(1);
        mSecretEditText.setOnFocusChangeListener(this);
        mSecretEditText.setTag(2);
        mCheckSecEditText.setOnFocusChangeListener(this);
        mCheckSecEditText.setTag(3);

    }

    @Override
    public void onFocusChange(View view, boolean b) {
          int tag=(Integer)view.getTag();
          String inputInformation;
          switch(tag){
              case 1:
                  mPhonenum=mPhonumEditText.getText().toString();
                  if(mPhonenum.length()<11)
                  {
                      mPhonumEditText.setError("请输入手机号码");
                  }
                  break;
              case 2:
                  mSecret=mSecretEditText.getText().toString();
                  if(mSecret.length()<5)
                  {
                      mSecretEditText.setError("密码应包含5-20个字符");
                  }
                  break;
              case 3:
                  inputInformation=mCheckSecEditText.getText().toString();
                  if(inputInformation!=mSecret)
                  {
                      mCheckSecEditText.setError("密码不一致");
                      mCheckSecEditText.setText("");
                  }
                  break;a
          }
    }
}