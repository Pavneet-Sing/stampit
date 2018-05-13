package com.pavneetsingh.android.stampit.ui.posts;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.ui.login.UserActivity;
import com.pavneetsingh.android.stampit.util.EspressoTestingIdlingResource;

import org.hamcrest.Description;
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
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
/*
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AdHostActivityTest {

    @Rule
    public ActivityTestRule<AdHostActivity> activityTestRule =
            new ActivityTestRule<>(AdHostActivity.class);
    private boolean loginState;

    @Before
    public void setUP(){
        IdlingRegistry.getInstance().register(EspressoTestingIdlingResource.getIdlingResource());
    }

    @After
    public void restore(){
        IdlingRegistry.getInstance().unregister(EspressoTestingIdlingResource.getIdlingResource());
    }


    @Test
    public void addPost() throws Exception {
        onView(withId(R.id.navigation_stamp_add)).perform(click());
        onView(withId(R.id.et_ad_name)).perform(typeText("1"),closeSoftKeyboard());
        onView(withId(R.id.et_ad_location)).perform(typeText("1"),closeSoftKeyboard());
        onView(withId(R.id.et_ad_cost)).perform(typeText("1"),closeSoftKeyboard());
        onView(withId(R.id.et_ad_desc)).perform(typeText("1"),closeSoftKeyboard());
        onView(withId(R.id.btn_post)).perform(click());
    }

    @Test
    public void displyRecyclerViewDeleteItem() throws Exception {

        onView(withId(R.id.list)).check(matches(isDisplayed()));
        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText("Delete"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
    }

    @Test
    public void displyRecyclerViewUpdateClick() throws Exception {

        onView(withId(R.id.list)).check(matches(isDisplayed()));
        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText("Update"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
        onView(withId(R.id.et_ad_name)).perform(typeText("1CUP"),closeSoftKeyboard());
    }

}