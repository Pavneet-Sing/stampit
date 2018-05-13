package com.pavneetsingh.android.stampit.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.ui.login.fragment.LoginFragment;
import com.pavneetsingh.android.stampit.ui.login.fragment.SignUPFragment;
import com.pavneetsingh.android.stampit.ui.posts.AdHostActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public class UserActivity extends AppCompatActivity implements SignUPFragment.onSignUpCallback{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Inject
    LoginFragment loginFragment;

    @Inject
    SignUPFragment signUPFragment;

    @Inject
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        if (preferences.getBoolean("isLoggedIn",false)){
            startActivity(new Intent(UserActivity.this, AdHostActivity.class));
            finish();
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public void onSignUp() {
        mViewPager.setCurrentItem(0,true);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return loginFragment;
                case 1:
                    return signUPFragment;
            }
            throw new RuntimeException("Invalid Index");
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
