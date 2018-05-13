package com.pavneetsingh.android.stampit.ui.posts.fragment;

import com.pavneetsingh.android.stampit.base.BasePresenter;
import com.pavneetsingh.android.stampit.base.BaseView;
import com.pavneetsingh.android.stampit.model.PostBean;

import java.util.List;



/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public interface AdListContract {

    interface View extends BaseView<Presenter> {
        void setProgressBarState(boolean active);
        void onResponseList(List<PostBean> news);
        void onLoadingError();
        void onNoDataFound();
        void noNetwork();

    }

    interface Presenter<V extends View> extends BasePresenter<V> {
        void loadAds();
        void onItemDelete(PostBean postBean);
        void onItemUpdate(PostBean postBean);
    }

    interface OnItemUpdatedCallback{
        void onUpdateSuccess(PostBean postBean);
    }
}
