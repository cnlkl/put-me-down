package com.fzuclover.putmedown;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;

import com.fzuclover.putmedown.features.register.RegisterActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Administrator on 2016/12/11.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    private Instrumentation mInstrumentation;
    private  RegisterActivity mRegisterActivity;
    private  MaterialEditText mPhonumEditText;
    private  MaterialEditText mSecretEditText;
    private  MaterialEditText mCheckSecEditText;
    private  MaterialEditText mCodeEdt;
    private  Button mGetCodeBtn;
    private  Button mSubmitBtn;
    public RegisterActivityTest()
    {
        super(RegisterActivity.class);
    }
    @Before
    public void setUp() throws Exception {

        super.setUp();
        // @Before注解表示在执行所有的testCase之前要做的事情
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mInstrumentation = getInstrumentation();
        // getActivity()方法会在开始所有的testCase之前启动相应的Activity
        mRegisterActivity= getActivity();

        mPhonumEditText=(MaterialEditText)mRegisterActivity.findViewById(R.id.register_phonenum);
        mSecretEditText=(MaterialEditText)mRegisterActivity.findViewById(R.id.register_newsecret);
        mCheckSecEditText=(MaterialEditText)mRegisterActivity.findViewById(R.id.register_checksecret);
        mCodeEdt = (MaterialEditText) mRegisterActivity.findViewById(R.id.register_checknum);

        mGetCodeBtn = (Button)mRegisterActivity.findViewById(R.id.register_get_checknum);
        mSubmitBtn = (Button) mRegisterActivity.findViewById(R.id.register_submit);

    }

    public void input()
    {
        mRegisterActivity.runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                mPhonumEditText.requestFocus();
                mPhonumEditText.performClick();
            }
        });
        /*由于测试用例在单独的线程上执行，所以此处需要同步application，
         * 调用waitForIdleSync等待测试线程和UI线程同步，才能进行输入操作。
         * waitForIdleSync和sendKeys不允许在UI线程里运行
         */
        mInstrumentation.waitForIdleSync();

        //调用sendKeys方法，输入用户名
        sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_8,
                KeyEvent.KEYCODE_2, KeyEvent.KEYCODE_5,
                KeyEvent.KEYCODE_9, KeyEvent.KEYCODE_0,
                KeyEvent.KEYCODE_5,KeyEvent.KEYCODE_8,
                KeyEvent.KEYCODE_7,KeyEvent.KEYCODE_3,
                KeyEvent.KEYCODE_3
                );

        mRegisterActivity.runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                mSecretEditText.requestFocus();
                mSecretEditText.performClick();
            }
        });

        //调用sendKeys方法，输入密码
        sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_2,
                KeyEvent.KEYCODE_3, KeyEvent.KEYCODE_4,
                KeyEvent.KEYCODE_5,KeyEvent.KEYCODE_6);

        mRegisterActivity.runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                mCheckSecEditText.requestFocus();
                mCheckSecEditText.performClick();
            }
        });

        //调用sendKeys方法，输入和密码一致的验证码
        //调用sendKeys方法，输入和密码不一致的验证码
        sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_2,
                KeyEvent.KEYCODE_3, KeyEvent.KEYCODE_4,
                KeyEvent.KEYCODE_5);

    }


    @Test
    public void mGetCodeBtnTest()
  {
      //3.输入错误格式的帐号
      //2.输入帐号的情况下获取验证码
      //4.输入6位数的密码
      input();
      //1.未输入帐号的情况下点击获取验证码
      //5.输入不一致密码提交数据
      onView(withId(R.id.register_get_checknum)).perform(click());
      onView(withId(R.id.register_submit)).perform(click());
  }

  @Test
  public void mSubmitBtnTest()
  {
      //为输入帐号的情况下点击注册按钮
      onView(withId(R.id.register_submit)).perform(click());
}
}
