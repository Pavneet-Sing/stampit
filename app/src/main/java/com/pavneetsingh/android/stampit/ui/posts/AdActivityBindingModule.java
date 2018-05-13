package com.pavneetsingh.android.stampit.ui.posts;

import com.pavneetsingh.android.stampit.di.ActivityScope;
import com.pavneetsingh.android.stampit.ui.login.LoginSignUpModule;
import com.pavneetsingh.android.stampit.ui.login.UserActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Module
public abstract class AdActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = AdHostActivityModule.class)
    abstract AdHostActivity userAdActivity();
}
