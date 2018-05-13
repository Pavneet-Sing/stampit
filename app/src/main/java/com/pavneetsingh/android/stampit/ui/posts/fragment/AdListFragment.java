/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pavneetsingh.android.stampit.ui.posts.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.di.ActivityScope;
import com.pavneetsingh.android.stampit.model.PostBean;
import com.pavneetsingh.android.stampit.ui.adapter.AdsRecycleAdapter;
import com.pavneetsingh.android.stampit.util.ActivityHelperClass;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@ActivityScope
public class AdListFragment extends Fragment implements AdListContract.View {

    @Inject
    AdListPresenter<AdListContract.View> presenter;

    @Inject
    SharedPreferences sharedPreferences;

    private List<PostBean> postItemList;
    private RecyclerView listView;
    private SwipeRefreshLayout swipeLayout;
    private AdsRecycleAdapter adapter;
    private int pos;
    private OnItemUpdatRequestCallback mListener;
    private PopupMenu popupMenu;



    @Override
    public void setProgressBarState(boolean active) {
        swipeLayout.setRefreshing(active);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void onResponseList(List<PostBean> news) {
        postItemList = news;
        UpdateAdapter();
        setProgressBarState(false);
    }

    private void UpdateAdapter(){
     adapter.updateData(postItemList);
    }

    @Override
    public void onLoadingError() {
        setProgressBarState(false);
        Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoDataFound() {

    }

    @Override
    public void noNetwork() {
        setProgressBarState(false);
        Toast.makeText(getActivity(), "No Network", Toast.LENGTH_SHORT).show();
    }

    @Inject
    public AdListFragment() {
    }

    AdsRecycleAdapter.RecyclerViewCallbacks callbacks = new AdsRecycleAdapter.RecyclerViewCallbacks() {
        @Override
        public void itemClick(int position, View view) {
            pos = position;
            popupMenu = new PopupMenu(getContext(),view);
            popupMenu.getMenu().addSubMenu(0,0,0,"View");
            popupMenu.getMenu().addSubMenu(0,1,0,"Update");
            popupMenu.getMenu().addSubMenu(0,2,0,"Delete");
            popupMenu.setOnMenuItemClickListener(popUpListener);
            popupMenu.show();

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnItemUpdatRequestCallback) {
            mListener = (OnItemUpdatRequestCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    PopupMenu.OnMenuItemClickListener popUpListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case 0:
                    ActivityHelperClass.showDetailDialog(getActivity(),postItemList.get(pos));
                    break;
                case 1:
                    mListener.onItemUpdateRequest(postItemList.get(pos), new AdListContract.OnItemUpdatedCallback() {
                        @Override
                        public void onUpdateSuccess(PostBean postBean) {
                            postItemList.set(pos,postBean);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    break;
                case 2:
                    presenter.onItemDelete(postItemList.remove(pos));
                    adapter.notifyDataSetChanged();
                    break;
            }
            popupMenu.dismiss();
            return true;
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ad_list_frag, container, false);
        swipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(listener);
        swipeLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), android.R.color.holo_green_light)
        );
        listView = (RecyclerView)root.findViewById(R.id.list);
        RecyclerView.ItemAnimator itemAnimator=new DefaultItemAnimator();
        listView.setItemAnimator(itemAnimator);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdsRecycleAdapter(getContext(),callbacks, R.layout.list_item_photos,null);
        listView.setAdapter(adapter);
        if (postItemList!=null && postItemList.size()>0){
            UpdateAdapter();
        }
        return root;
    }

    SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.loadAds();
        }
    };

    public void onNewPost(PostBean postBean){
        postItemList.add(postBean);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnItemUpdatRequestCallback {
        void onItemUpdateRequest(PostBean postBean, AdListContract.OnItemUpdatedCallback updatedCallback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        sharedPreferences.edit().putBoolean("isLoggedIn",false).commit();
        getActivity().finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0,0,0,"logout");
    }

}
