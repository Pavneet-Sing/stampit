package com.pavneetsingh.android.stampit.application;

import android.app.Activity;
import android.app.Application;


import com.pavneetsingh.android.stampit.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;



/**
 * Created by Pavneet Singh on 02/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public class StampItApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(this).build().inject(this);
   }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
