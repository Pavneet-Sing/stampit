package com.pavneetsingh.android.stampit.ui.login;

import com.pavneetsingh.android.stampit.di.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Module
public abstract class UserActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginSignUpModule.class)
    abstract UserActivity userActivity();
}
