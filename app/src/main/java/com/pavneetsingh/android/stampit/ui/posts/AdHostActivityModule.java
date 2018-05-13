package com.pavneetsingh.android.stampit.ui.posts;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pavneetsingh.android.stampit.di.ActivityScope;
import com.pavneetsingh.android.stampit.di.FragmentScoped;
import com.pavneetsingh.android.stampit.room.PostDB;
import com.pavneetsingh.android.stampit.ui.posts.fragment.AdListContract;
import com.pavneetsingh.android.stampit.ui.posts.fragment.AdListFragment;
import com.pavneetsingh.android.stampit.ui.posts.fragment.AdListPresenter;
import com.pavneetsingh.android.stampit.ui.posts.fragment.AdPostPresenter;
import com.pavneetsingh.android.stampit.ui.posts.fragment.PostAdFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Module
public abstract class AdHostActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AdListFragment getAdListFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PostAdFragment getPostAdsFragment();

    @ActivityScope
    @Provides
    static AdListPresenter getPostListPresenter(PostDB postDB, FirebaseDatabase database, FirebaseAuth auth){
        return new AdListPresenter(postDB,database,auth);
    }

    @ActivityScope
    @Provides
    static AdPostPresenter getAdPostPresenter(PostDB postDB, FirebaseDatabase database, FirebaseAuth auth){
        return new AdPostPresenter(postDB,database,auth);
    }
}
