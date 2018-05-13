package com.pavneetsingh.android.stampit.ui.login;

import com.pavneetsingh.android.stampit.di.ActivityScope;
import com.pavneetsingh.android.stampit.di.FragmentScoped;
import com.pavneetsingh.android.stampit.ui.login.fragment.LoginAndSignUpPresenter;
import com.pavneetsingh.android.stampit.ui.login.fragment.LoginFragment;
import com.pavneetsingh.android.stampit.ui.login.fragment.SignUPFragment;
import com.pavneetsingh.android.stampit.ui.login.fragment.UserLoginContract;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Module
public abstract class LoginSignUpModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginFragment getLoginFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SignUPFragment getSignUpFragment();

    @ActivityScope
    @Binds
    abstract UserLoginContract.Presenter getPresenter(LoginAndSignUpPresenter presenter);
}
