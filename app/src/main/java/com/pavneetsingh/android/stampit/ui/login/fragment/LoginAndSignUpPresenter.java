package com.pavneetsingh.android.stampit.ui.login.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */



public final class LoginAndSignUpPresenter<V extends UserLoginContract.View> implements UserLoginContract.Presenter<V> {

    @Nullable
    private V view;

    private FirebaseAuth auth;

    @Inject
    public LoginAndSignUpPresenter(FirebaseAuth auth) {
        this.auth = auth;
    }


    private void processLoginRequest(String email, String password) {
        view.setProgressBarState(true);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.setProgressBarState(false);
                        if (task.isSuccessful()) {
                            view.onSuccess();
                        } else {
                            view.onFailure();
                        }
                        view.setProgressBarState(false);
                    }
                });
    }

    private void processSignInRequest(String email, String password) {
        view.setProgressBarState(true);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.setProgressBarState(false);
                        if (task.isSuccessful()) {
                            view.onSuccess();
                        } else {
                            view.onFailure();
                        }
                    }

                });
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
    public void loginUser(String email, String password) {
        processLoginRequest(email,password);

    }

    @Override
    public void registerUser(String email, String password) {
        processSignInRequest(email,password);
    }
}
