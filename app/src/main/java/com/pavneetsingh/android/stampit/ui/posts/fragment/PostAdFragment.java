package com.pavneetsingh.android.stampit.ui.posts.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.model.PostBean;
import com.pavneetsingh.android.stampit.util.ActivityHelperClass;
import com.pavneetsingh.android.stampit.util.EspressoTestingIdlingResource;

import javax.inject.Inject;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */
public class PostAdFragment extends Fragment implements AdPostContract.View{

    private ImageView imageView;
    private Button button;
    private TextInputEditText et_name,et_cost,et_desc,et_loc;
    private ProgressBar progressBar;
    private String imgURL;
    private PostBean postBean;
    private boolean updateFlag;


    @Inject
    AdPostPresenter<AdPostContract.View> presenter;


    private OnFragmentInteractionListener mListener;

    @Inject
    public PostAdFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_ad, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.img_item);
        et_cost = view.findViewById(R.id.et_ad_cost);
        et_desc = view.findViewById(R.id.et_ad_desc);
        et_loc = view.findViewById(R.id.et_ad_location);
        et_name= view.findViewById(R.id.et_ad_name);
        button = view.findViewById(R.id.btn_post);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                popupMenu.getMenu().addSubMenu(0,0,0,"Click Image");
                popupMenu.getMenu().addSubMenu(0,1,0,"Pick Image From Gallary");
                popupMenu.setOnMenuItemClickListener(listener);
                popupMenu.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EspressoTestingIdlingResource.increment();
                if (ActivityHelperClass.verfifyPostInput(et_desc,et_loc,et_name) & ActivityHelperClass.verifyCostInput(et_cost)){
                    if (imgURL != null && imgURL.contains("stampit")){
                        if (updateFlag){
                            postBean.setName(ActivityHelperClass.getStringFromTextView(et_name));
                            postBean.setDesc(ActivityHelperClass.getStringFromTextView(et_desc));
                            postBean.setLoc(ActivityHelperClass.getStringFromTextView(et_loc));
                            postBean.setCost(Double.parseDouble(ActivityHelperClass.getStringFromTextView(et_cost)));
                            postBean.setUrl(imgURL);
                            presenter.updatePost(postBean);
                        }else {

                            postBean = new PostBean( ActivityHelperClass.getStringFromTextView(et_name),
                                    ActivityHelperClass.getStringFromTextView(et_desc),
                                    imgURL,
                                    ActivityHelperClass.getStringFromTextView(et_loc),
                                    Double.parseDouble(ActivityHelperClass.getStringFromTextView(et_cost))
                            );
                            presenter.postAd(postBean);
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Image cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



    PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == 0 ){
                Intent i = ActivityHelperClass.clickCamera(getActivity());
                imgURL = i.getStringExtra("path");
                startActivityForResult(i,ActivityHelperClass.PERMISSION_REQUEST);
            }else {
                startActivityForResult(Intent.createChooser(ActivityHelperClass.pickImage(), "Select Picture"), ActivityHelperClass.PICK_IMAGE_FROM_GALLARY);
            }
            return false;
        }
    };

    public void onItemPosted(PostBean postBean) {
        if (mListener != null) {
            mListener.onItemAdded(postBean);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (updateFlag){
            et_name.setText(postBean.getName());
            et_loc.setText(postBean.getLoc());
            et_desc.setText(postBean.getDesc());
            et_cost.setText(String.valueOf(postBean.getCost()));
            imgURL = postBean.getUrl();
            Glide.with(this).load(imgURL).into(imageView);
            button.setText("Update Post");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null)
            if (requestCode == ActivityHelperClass.PERMISSION_REQUEST) {
                Log.e(ActivityHelperClass.TAG, "onActivityResult: "+imgURL );
                //don't compare the data to null, it will always come as  null because we are providing a file URI, so load with the imageFilePath we obtained before opening the cameraIntent
                // If you are using Glide.
            }else {
    /*
                Uri selectedImage = data.getData();
                try {
                    imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
    */
                imgURL = ActivityHelperClass.getImagePathFromURI(data.getData(),getContext());
            }
        Glide.with(this).load(imgURL).into(imageView);
        Log.e(ActivityHelperClass.TAG, "onActivityResult: 1 "+imgURL );

    }

    @Override
    public void setProgressBarState(boolean active) {
        if (active) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onSuccess(boolean b) {
        if (b) {
            Toast.makeText(getActivity(), "Ad posted successfully", Toast.LENGTH_SHORT).show();
            cleanViews();
            onItemPosted(postBean);
        }
        else Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        EspressoTestingIdlingResource.decrement();
        Log.e(ActivityHelperClass.TAG, "onSuccess: "+postBean.toString()+"\n"+this.postBean.toString() );
    }

    @Override
    public void updatePost(PostBean postBean) {
        updateFlag = true;
        this.postBean = postBean;
    }

    @Override
    public void onUpdateSuccess() {
        updateFlag = false;
        postBean = null;
        button.setText("POST");
        cleanViews();
        mListener.onItemUpdated(postBean);
        EspressoTestingIdlingResource.decrement();
    }

    @Override
    public void onLoadingError() {

    }

    @Override
    public void onNoDataFound() {

    }

    @Override
    public void noNetwork() {

    }

    public interface OnFragmentInteractionListener {
        void onItemAdded(PostBean postBean);
        void onItemUpdated(PostBean postBean);
    }

    private void cleanViews(){
        ActivityHelperClass.setEmptyText(et_cost);
        ActivityHelperClass.setEmptyText(et_desc);
        ActivityHelperClass.setEmptyText(et_loc);
        ActivityHelperClass.setEmptyText(et_name);
        imgURL = null;
        imageView.setImageResource(R.mipmap.ic_launcher);
    }
}
