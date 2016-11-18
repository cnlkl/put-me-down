package com.fzuclover.putmedown.features.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;

public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, SettingContract.View {

    private Switch mIsSendWxMsgSwc;

    private Switch mIsNotifySwc;

    private Switch mIsShockSwc;

    private Switch mIsLightSwc;

    private EditText mWxMsgEdt;

    private LinearLayout mNotifyLayout;

    private boolean mIsSendWxMsg;
    private boolean mIsNotify;
    private boolean mIsShock;
    private boolean mIsLight;
    private String mWxMsg;

    private SettingContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init(){

        mPresenter = new SettingPresenter(this);

        mIsSendWxMsg = mPresenter.getIsSendWxMsg();
        mWxMsg = mPresenter.getWxMsgContent();
        mIsNotify = mPresenter.getIsNotify();
        mIsShock = mPresenter.getIsShock();
        mIsLight = mPresenter.getIsLight();

        mNotifyLayout = (LinearLayout) findViewById(R.id.notify_type_layout);

        mIsSendWxMsgSwc = (Switch) findViewById(R.id.auto_share_swc);
        mIsSendWxMsgSwc.setChecked(mIsSendWxMsg);
        mIsSendWxMsgSwc.setOnCheckedChangeListener(this);

        mIsNotifySwc = (Switch) findViewById(R.id.notify_no);
        mIsNotifySwc.setChecked(mIsNotify);
        mIsNotifySwc.setOnCheckedChangeListener(this);

        mIsLightSwc = (Switch) findViewById(R.id.notify_led);
        mIsLightSwc.setChecked(mIsLight);
        mIsLightSwc.setOnCheckedChangeListener(this);

        mIsShockSwc = (Switch) findViewById(R.id.notify_shake);
        mIsShockSwc.setChecked(mIsShock);
        mIsShockSwc.setOnCheckedChangeListener(this);

        mWxMsgEdt = (EditText) findViewById(R.id.wx_content_edt);
        mWxMsgEdt.setText(mWxMsg);


        if(mIsSendWxMsg){
            mWxMsgEdt.setVisibility(View.VISIBLE);
        }else{
            mWxMsgEdt.setVisibility(View.INVISIBLE);
        }

        if (mIsNotify){
            mNotifyLayout.setVisibility(View.VISIBLE);
        }else{
            mNotifyLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.auto_share_swc:
                if(b){
                    mWxMsgEdt.setVisibility(View.VISIBLE);
                }else {
                    mWxMsgEdt.setVisibility(View.INVISIBLE);
                }
                mPresenter.setIsSendWxMsg(b);
                break;
            case R.id.notify_no:
                if(b){
                   mNotifyLayout.setVisibility(View.VISIBLE);
                }else{
                    mNotifyLayout.setVisibility(View.INVISIBLE);
                }
                mPresenter.setIsNotify(b);
                break;
            case R.id.notify_shake:
                mPresenter.setIsShock(b);
                break;
            case R.id.notify_led:
                mPresenter.setIsLight(b);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mWxMsg.equals(mWxMsgEdt.getText().toString())){
                finish();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true)
                        .setTitle("提示")
                        .setMessage("是否保存自动发送的朋友圈内容");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.setWxMsgContent(mWxMsgEdt.getText().toString());
                        finish();
                    }
                });
                builder.setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.show();
            }
        }
        return false;
    }
}
