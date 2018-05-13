package com.pavneetsingh.android.stampit.ui.posts.fragment;

import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pavneetsingh.android.stampit.model.PostBean;
import com.pavneetsingh.android.stampit.room.PostDB;
import com.pavneetsingh.android.stampit.util.EspressoTestingIdlingResource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;



/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public final class AdListPresenter<V extends AdListContract.View> implements AdListContract.Presenter<V>  {

    @Nullable
    private V view;

    private PostDB postDB;
    private FirebaseDatabase database;
    private FirebaseAuth auth;



    @Inject
    public AdListPresenter(PostDB postDB, FirebaseDatabase database, FirebaseAuth auth) {
        this.postDB = postDB;
        this.database = database;
        this.auth = auth;

    }


    @Override
    public void loadAds() {
        EspressoTestingIdlingResource.increment();
        view.setProgressBarState(true);
        processRequest();
        EspressoTestingIdlingResource.decrement();
    }


    @Override
    public void onItemDelete(PostBean postBean) {
        EspressoTestingIdlingResource.increment();
        view.setProgressBarState(true);
        postDB.getNoteDao().delete(postBean);
        database.getReference()
                .child(auth.getCurrentUser()
                        .getUid())
                .child(postBean.getPushKey()).removeValue();
        view.setProgressBarState(false);
        EspressoTestingIdlingResource.decrement();
    }

    @Override
    public void onItemUpdate(PostBean postBean) {

    }

    private void processRequest(){
        List<PostBean> postBeans = postDB.getNoteDao().gerAll(auth.getCurrentUser().getUid());
        view.onResponseList(postBeans);
        view.setProgressBarState(false);

    }

    @Override
    public void takeView(V view) {
        this.view = view;
        view.setProgressBarState(true);
        processRequest();
    }

    @Override
    public void dropView() {
        view = null;
    }


}
