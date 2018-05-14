package com.pavneetsingh.android.stampit.ui.posts;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.base.BaseActivity;
import com.pavneetsingh.android.stampit.model.PostBean;
import com.pavneetsingh.android.stampit.ui.posts.fragment.AdListContract;
import com.pavneetsingh.android.stampit.ui.posts.fragment.AdListFragment;
import com.pavneetsingh.android.stampit.ui.posts.fragment.PostAdFragment;
import com.pavneetsingh.android.stampit.util.ActivityHelperClass;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public class AdHostActivity extends BaseActivity implements PostAdFragment.OnFragmentInteractionListener , AdListFragment.OnItemUpdatRequestCallback{

    private TextView mTextMessage;
    private FrameLayout frameLayout;
    private AdListContract.OnItemUpdatedCallback updatedCallback ;
    private BottomNavigationView navigation;

    /*Get objects from dagger*/
    @Inject
    PostAdFragment postAdFragment;
    @Inject
    AdListFragment adListFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,adListFragment).commit();
                    return true;
                case R.id.navigation_stamp_add:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,postAdFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        mTextMessage = findViewById(R.id.message);
        navigation = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frame);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ActivityHelperClass.getPermissions(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,adListFragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityHelperClass.PERMISSION_REQUEST == requestCode){
            if (grantResults.length > 0 && ! (grantResults[0] == PackageManager.PERMISSION_GRANTED) && !(grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permissions are required to function this app", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Required permissions to function this app properly", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected int setUpContentView() {
        return R.layout.activity_host;
    }

    @Override
    public void onItemAdded(PostBean postBean) {
        adListFragment.onNewPost(postBean);
    }

    /*
    * Receive updaated object and pass it to list fragment to display
    * */
    @Override
    public void onItemUpdated(PostBean postBean) {
        updatedCallback.onUpdateSuccess(postBean);
        navigation.setSelectedItemId(R.id.navigation_list);
    }

    /*Initiate post update request*/
    @Override
    public void onItemUpdateRequest(PostBean postBean, AdListContract.OnItemUpdatedCallback updatedCallback) {
        this.updatedCallback = updatedCallback;
        navigation.setSelectedItemId(R.id.navigation_stamp_add);
        postAdFragment.updatePost(postBean);
    }

}
