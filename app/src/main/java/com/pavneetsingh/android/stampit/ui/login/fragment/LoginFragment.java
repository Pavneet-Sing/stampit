package com.pavneetsingh.android.stampit.ui.login.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pavneetsingh.android.stampit.ui.posts.AdHostActivity;
import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.di.ActivityScope;
import com.pavneetsingh.android.stampit.util.ActivityHelperClass;
import com.pavneetsingh.android.stampit.util.EspressoTestingIdlingResource;

import javax.inject.Inject;

/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@ActivityScope
public  class LoginFragment extends Fragment implements UserLoginContract.View {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Inject
    LoginAndSignUpPresenter<UserLoginContract.View> presenter;

    @Inject
    SharedPreferences sharedPreferences;


    private static final String ARG_SECTION_NUMBER = "section_number";

    @Inject
    public LoginFragment() {
    }

    public static LoginFragment newInstance(int sectionNumber) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment_user, container, false);
        inputEmail = (EditText) rootView.findViewById(R.id.et_email);
        inputPassword = (EditText) rootView.findViewById(R.id.et_password);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        btnLogin = (Button) rootView.findViewById(R.id.btn_login);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EspressoTestingIdlingResource.increment();
                if (ActivityHelperClass.verifyEmail(inputEmail) & ActivityHelperClass.verifyPassword(inputPassword))
                    presenter.loginUser(inputEmail.getText().toString(),
                            inputPassword.getText().toString());
            }
        });
        return rootView;
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
    public void setProgressBarState(boolean active) {
        if (active) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(getContext(), AdHostActivity.class));
        sharedPreferences.edit().putBoolean("isLoggedIn",true).commit();
        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
        EspressoTestingIdlingResource.decrement();
        getActivity().finish();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), "Un-Authenticated user", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(Throwable throwable) {

    }

    /*To be implwmented for unavailable network*/
    @Override
    public void showNoNetwork() {

    }
}