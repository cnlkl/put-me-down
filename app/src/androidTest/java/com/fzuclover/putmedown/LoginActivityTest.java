package com.fzuclover.putmedown;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import com.fzuclover.putmedown.features.login.LoginActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by lkl on 2016/12/9.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Instrumentation mInstrumentation;
    private LoginActivity mloginActivity;
    private MaterialEditText usernameTv;
    private MaterialEditText passwordTv;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Before
    public void setUp() throws Exception {

        super.setUp();
        // @Before注解表示在执行所有的testCase之前要做的事情
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mInstrumentation = getInstrumentation();
        // getActivity()方法会在开始所有的testCase之前启动相应的Activity
        mloginActivity = getActivity();

        usernameTv = (MaterialEditText) mloginActivity.findViewById(R.id.account_edt);
        passwordTv=(MaterialEditText) mloginActivity.findViewById(R.id.password_edt);

    }

    //该测试用例实现在测试其他用例之前，测试确保获取的组件不为空
    public void testPreConditions()
    {
        assertNotNull( usernameTv);
        assertNotNull( passwordTv);

    }

    /**该方法实现在登录界面上输入相关的登录信息。由于UI组件的
     * 相关处理（如此处的请求聚焦）需要在UI线程上实现，
     * 所以需调用Activity的runOnUiThread方法实现。
     */
    public void input()
    {
        mloginActivity.runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                usernameTv.requestFocus();
                usernameTv.performClick();
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
                KeyEvent.KEYCODE_3);

        mloginActivity.runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                passwordTv.requestFocus();
                passwordTv.performClick();
            }
        });

        //调用sendKeys方法，输入密码
        sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_2,
                KeyEvent.KEYCODE_3, KeyEvent.KEYCODE_4,
                KeyEvent.KEYCODE_5,KeyEvent.KEYCODE_6);
    }


    //测试输入的用户信息
   @Test
   public void testInput()
    {
        //调用测试类的input方法，实现输入用户信息(sendKeys实现输入)
        input();
        //测试验证用户信息的预期值是否等于实际值
        assertEquals("18259058733", usernameTv.getText().toString());
        //密码的预期值123与实际值1234不符，Failure;
        assertEquals("123456", passwordTv.getText().toString());
    }

    //登录按钮测试
    @Test
    public void loginTest(){

      input();

        onView(withId(R.id.login)).perform(click()
        );
    }
    //忘记密码按钮测试
    @Test
    public void forgotpasswordTest(){
        onView(withId( R.id.forget_password)).perform(click());
    }

    //注册按钮测试
    @Test
    public void sign_inTest(){
        onView(withId( R.id.sign_in)).perform(click());
    }



}
