package com.pavneetsingh.android.stampit.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pavneetsingh.android.stampit.room.PostDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Pavneet Singh on 03/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Module
public abstract class DatabaseModule {

    @Singleton
    @Provides
    static FirebaseAuth getAuth(){
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    static FirebaseDatabase getFirebaseDB(){
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    static PostDB getDB(Context context){
        return Room.databaseBuilder(context,
                PostDB.class,
                "Post.db")
                .allowMainThreadQueries().build();
    }


}
