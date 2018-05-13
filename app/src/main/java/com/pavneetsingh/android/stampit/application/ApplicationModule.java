package com.pavneetsingh.android.stampit.application;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Pavneet Singh on 04/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Module
public abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);

}
