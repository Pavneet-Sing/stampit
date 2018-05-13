package com.pavneetsingh.android.stampit.ui.login.fragment;

import android.app.Activity;

import com.pavneetsingh.android.stampit.base.BasePresenter;
import com.pavneetsingh.android.stampit.base.BaseView;



/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public interface UserLoginContract {

    interface View extends BaseView<Presenter> {
        void setProgressBarState(boolean active);
        void onSuccess();
        void onFailure();
        void onError(Throwable throwable);
        void showNoNetwork();

    }

    interface Presenter<V extends View> extends BasePresenter<V> {
        void loginUser(String email, String password);
        void registerUser(String email,String password);
    }
}
