package com.pavneetsingh.android.stampit.ui.posts.fragment;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pavneetsingh.android.stampit.model.PostBean;
import com.pavneetsingh.android.stampit.room.PostDB;
import com.pavneetsingh.android.stampit.util.ActivityHelperClass;

import javax.annotation.Nullable;
import javax.inject.Inject;



/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public final class AdPostPresenter<V extends AdPostContract.View> implements AdPostContract.Presenter<V>  {

    @Nullable
    private V view;
    private PostDB postDB;
    private FirebaseDatabase database;
    private FirebaseAuth auth;



    @Inject
    public AdPostPresenter(PostDB postDB,  FirebaseDatabase database, FirebaseAuth auth) {
        this.postDB = postDB;
        this.database = database;
        this.auth = auth;
    }




    @Override
    public void takeView(V view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }


    @Override
    public void postAd(final PostBean bean) {
        bean.setUserId(auth.getCurrentUser().getUid());
        String pushKey = database.getReference().push().getKey();
        bean.setPushKey(pushKey);
        long id = postDB.getNoteDao().insert(bean);
        bean.setId(id);
        if (id > 0){
            database.getReference()
                    .child(auth.getCurrentUser().getUid())
                    .child(pushKey)
                    .setValue(bean)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            postDB.getNoteDao().update(bean);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }
        view.onSuccess(id > 0 );
        Log.e(ActivityHelperClass.TAG, "postAd: "+id );
    }

    @Override
    public void updatePost(PostBean bean) {
        postDB.getNoteDao().update(bean);
        database
                .getReference()
                .child(auth.getCurrentUser().getUid())
                .child(bean.getPushKey())
                .setValue(bean);
        view.onUpdateSuccess();
    }
}
