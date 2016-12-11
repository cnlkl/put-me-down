package com.fzuclover.putmedown;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fzuclover.putmedown.features.achievement.AchievementActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Administrator on 2016/12/11.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TotalTimingTodayActivityTest {

    @Rule
    public ActivityTestRule<TotalTimeTodayActivity> mActivityRule = new ActivityTestRule<TotalTimeTodayActivity>(TotalTimeTodayActivity.class);


    @Test
    public void testPreconditions() {
        // @Test注解表示一个测试用例方法
        onView(withText("累计时间")).check(matches(isDisplayed()));

    }
}
