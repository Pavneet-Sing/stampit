package com.pavneetsingh.android.stampit.di;

import android.app.Application;

import com.pavneetsingh.android.stampit.application.ApplicationModule;
import com.pavneetsingh.android.stampit.application.StampItApplication;
import com.pavneetsingh.android.stampit.di.module.DatabaseModule;
import com.pavneetsingh.android.stampit.ui.login.LoginSignUpModule;
import com.pavneetsingh.android.stampit.ui.login.UserActivityBindingModule;
import com.pavneetsingh.android.stampit.ui.posts.AdActivityBindingModule;
import com.pavneetsingh.android.stampit.ui.posts.AdHostActivityModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


/**
 * Created by Pavneet Singh on 03/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Singleton
@Component(
        modules = {DatabaseModule.class,
                LoginSignUpModule.class,
                UserActivityBindingModule.class,
                ApplicationModule.class,
                AdHostActivityModule.class,
                AdActivityBindingModule.class,
                AndroidSupportInjectionModule.class
        }
)

public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(StampItApplication app);
}

