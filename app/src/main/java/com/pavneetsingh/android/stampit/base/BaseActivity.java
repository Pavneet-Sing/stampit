package com.pavneetsingh.android.stampit.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Pavneet Singh on 02/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setUpContentView());
        onViewReady(savedInstanceState,getIntent());
    }

    /*
    * Called when view hierarchy is attached with the layout
    * Initialize views here*/
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
    }


    protected abstract int setUpContentView();
}
