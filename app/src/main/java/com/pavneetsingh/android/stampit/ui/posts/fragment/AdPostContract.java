package com.pavneetsingh.android.stampit.ui.posts.fragment;

import com.pavneetsingh.android.stampit.base.BasePresenter;
import com.pavneetsingh.android.stampit.base.BaseView;
import com.pavneetsingh.android.stampit.model.PostBean;



/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public interface AdPostContract {

    interface View extends BaseView<Presenter> {
        void setProgressBarState(boolean active);
        void onSuccess(boolean b);
        void updatePost(PostBean postBean);
        void onUpdateSuccess();
        void onLoadingError();
        void onNoDataFound();
        void noNetwork();

    }

    interface Presenter<V extends View> extends BasePresenter<V> {
        void postAd(PostBean bean);
        void updatePost(PostBean bean);
    }
}
