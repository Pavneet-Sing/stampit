package com.pavneetsingh.android.stampit.ui.login.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.di.ActivityScope;
import com.pavneetsingh.android.stampit.ui.login.UserActivity;
import com.pavneetsingh.android.stampit.util.ActivityHelperClass;
import com.pavneetsingh.android.stampit.util.EspressoTestingIdlingResource;

import javax.inject.Inject;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@ActivityScope
public class SignUPFragment extends Fragment implements UserLoginContract.View {
    onSignUpCallback  callback;

    public interface onSignUpCallback{
        void onSignUp();
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;

    @Inject
    LoginAndSignUpPresenter<UserLoginContract.View> presenter;

    @Inject
    public SignUPFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signup_fragment_user, container, false);

        btnSignUp = (Button)rootView.findViewById(R.id.sign_up_button);
        inputEmail = (EditText)rootView.findViewById(R.id.et_sign_email);
        inputPassword = (EditText)rootView.findViewById(R.id.et_sign_password);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EspressoTestingIdlingResource.increment();
                if (ActivityHelperClass.verifyEmail(inputEmail) & ActivityHelperClass.verifyPassword(inputPassword))
                    presenter.registerUser(inputEmail.getText().toString().trim(),
                        inputPassword.getText().toString().trim());
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserActivity){
            callback = (onSignUpCallback) context;
        }
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
        callback.onSignUp();
        EspressoTestingIdlingResource.decrement();
        Toast.makeText(getActivity(), "Sign UP successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), "Sign UP failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable!=null && throwable.getMessage()!=null)
            Toast.makeText(getActivity(), "Error "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNetwork() {

    }


}
