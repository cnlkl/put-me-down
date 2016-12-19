package com.fzuclover.putmedown;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;

import com.fzuclover.putmedown.features.forgotpassword.ForgotPasswordActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Laotou on 2016/12/13.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ForgotPasswordActivityTest extends ActivityInstrumentationTestCase2<ForgotPasswordActivity>{

    private Instrumentation mInstrumentation;
    private ForgotPasswordActivity mForgotPasswordActivity;
    private MaterialEditText mPhonumEditTest;
    private MaterialEditText mNewSecretEditTest;
    private MaterialEditText mCheckSecretEditTest;
    private MaterialEditText mCodeEdt;
    private Button mGetCodeBtn;
    private Button mSubmitBtn;
    public ForgotPasswordActivityTest() {
        super(ForgotPasswordActivity.class);
    }
    @Before
    public void setUp() throws Exception {
        super.setUp();
        // @Before注解表示在执行所有的testCase之前要做的事情
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mInstrumentation = getInstrumentation();
        // getActivity()方法会在开始所有的testCase之前启动相应的Activity
        mForgotPasswordActivity = getActivity();

        mPhonumEditTest=(MaterialEditText)mForgotPasswordActivity.findViewById(R.id.forgot_password_phonenum);
        mNewSecretEditTest=(MaterialEditText)mForgotPasswordActivity.findViewById(R.id.forgot_password_newsecret);
        mCheckSecretEditTest=(MaterialEditText)mForgotPasswordActivity.findViewById(R.id.forgot_password_checksecret);
        mCodeEdt=(MaterialEditText)mForgotPasswordActivity.findViewById(R.id.code_edt);

        mGetCodeBtn=(Button)mForgotPasswordActivity.findViewById(R.id.get_checknum);
        mSubmitBtn=(Button)mForgotPasswordActivity.findViewById(R.id.forgot_password_submit);

}
    public void input() {
        mForgotPasswordActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mPhonumEditTest.requestFocus();
                mPhonumEditTest.performClick();
            }
        });
        /*由于测试用例在单独的线程上执行，所以此处需要同步application
         * 调用waitForIdleSync等待测试线程和UI线程同步，才能进行输入操作
         * waitForIdleSync和sendKeys不允许在UI线程里运行
         */
        mInstrumentation.waitForIdleSync();
        //调用sendKeys方法，输入账号名
        sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_8, KeyEvent.KEYCODE_2,
                KeyEvent.KEYCODE_5, KeyEvent.KEYCODE_9, KeyEvent.KEYCODE_0,
                KeyEvent.KEYCODE_5, KeyEvent.KEYCODE_8, KeyEvent.KEYCODE_8,
                KeyEvent.KEYCODE_7, KeyEvent.KEYCODE_8);

        mForgotPasswordActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mNewSecretEditTest.requestFocus();
                mNewSecretEditTest.performClick();
            }
        });
        //调用sendKeys方法，输入密码
        sendKeys(KeyEvent.KEYCODE_1,KeyEvent.KEYCODE_2,KeyEvent.KEYCODE_3,
                KeyEvent.KEYCODE_4,KeyEvent.KEYCODE_5,KeyEvent.KEYCODE_6);

        mForgotPasswordActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mCheckSecretEditTest.requestFocus();
                mCheckSecretEditTest.performClick();
            }
        });

        //调用sendKeys方法，输入和密码一致的验证码
        //调用sendKeys方法，输入和密码不一致的验证码
        sendKeys(KeyEvent.KEYCODE_1,KeyEvent.KEYCODE_2,KeyEvent.KEYCODE_3,
                KeyEvent.KEYCODE_4,KeyEvent.KEYCODE_5);
    }
    @Test
    public void mGetCodeTest(){
        //3.输入错误格式的帐号
        //2.输入帐号的情况下获取验证码
        //4.输入6位数的密码
        input();
        //1.未输入帐号的情况下点击获取验证码
        //5.输入不一致密码提交数据
        onView(withId(R.id.get_checknum)).perform(click());
        onView(withId(R.id.forgot_password_submit)).perform(click());
    }
    @Test
    public void mSubmitBtnTest(){
        //为输入帐号的情况下点击重置按钮
        onView(withId(R.id.forgot_password_submit)).perform(click());
    }

}



