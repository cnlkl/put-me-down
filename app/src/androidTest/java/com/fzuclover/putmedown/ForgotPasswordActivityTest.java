package com.fzuclover.putmedown;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fzuclover.putmedown.features.forgotpassword.ForgotPasswordActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Administrator on 2016/12/10.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ForgotPasswordActivityTest {

    @Rule
    public ActivityTestRule<ForgotPasswordActivity> mActivityRule = new ActivityTestRule<ForgotPasswordActivity>(ForgotPasswordActivity.class);

    @Test
    public void forgotPasswordTest(){

        onView(withId(R.id.forgot_password_submit)).perform(click());
    }
}
