package com.pavneetsingh.android.stampit.ui.login;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.util.EspressoTestingIdlingResource;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserActivityTest {

    @Rule
    public ActivityTestRule<UserActivity> activityTestRule =
            new ActivityTestRule<>(UserActivity.class);
    private boolean loginState;
    private SharedPreferences preferences;

    @Before
    public void setUP(){
        IdlingRegistry.getInstance().register(EspressoTestingIdlingResource.getIdlingResource());
        preferences = PreferenceManager.getDefaultSharedPreferences(activityTestRule.getActivity());
        loginState = preferences.getBoolean("isLoggedIn",false);
        preferences.edit().putBoolean("isLoggedIn",false).commit();
    }

    @After
    public void restore(){
        preferences.edit().putBoolean("isLoggedIn",loginState).commit();
        IdlingRegistry.getInstance().unregister(EspressoTestingIdlingResource.getIdlingResource());
    }


    @Test
    public void onCreate() throws Exception {
        onView(withId(R.id.container)).check(matches(isDisplayed()));
        onView(withId(R.id.et_email)).check(matches(isDisplayed()));
        onView(withId(R.id.et_password)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));

    }

    @Test
    public void onSwipe() throws Exception {
        onView(withId(R.id.container)).perform(withCustomConstraints(swipeLeft(),isDisplayingAtLeast(80)));
        onView(withId(R.id.et_sign_email)).check(matches(isDisplayed()));
        onView(withId(R.id.et_sign_password)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_up_button)).check(matches(isDisplayed()));
    }

    @Test
    public void onPerformLogin(){
        onView(withId(R.id.container)).perform(withCustomConstraints(swipeLeft(),isDisplayingAtLeast(80)));
        onView(withId(R.id.et_sign_email)).check(matches(isDisplayed()));
        onView(withId(R.id.et_sign_password)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_up_button)).check(matches(isDisplayed()));
        onView(withId(R.id.et_sign_email)).perform(typeText("pavneet@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.et_sign_password)).perform(typeText("pavneet!0"),closeSoftKeyboard());
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withId(R.id.et_email)).perform(typeText("pavneet@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("pavneet!0"),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        // Will not work in case of permission dialog
//        onView(withText("Login successful")).
//                inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).
//                check(matches(isDisplayed()));
    }


    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }
}