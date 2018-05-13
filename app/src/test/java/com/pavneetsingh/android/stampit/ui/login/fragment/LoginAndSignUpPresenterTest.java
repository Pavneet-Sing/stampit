package com.pavneetsingh.android.stampit.ui.login.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.Executor;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Pavneet Singh on 04/05/18.
 * Contact : dev.pavneet@gmail.com
 */
public class LoginAndSignUpPresenterTest {


    @Mock
    UserLoginContract.View view;

    @Mock
    FirebaseAuth auth ;

    @Mock
    Task<AuthResult> task;

    LoginAndSignUpPresenter presenter;


    @Captor
    private ArgumentCaptor<OnCompleteListener> callback;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new LoginAndSignUpPresenter(auth);
        presenter.takeView(view);
    }

    @Test
    public void takeView() throws Exception {
        presenter.takeView(view);
    }


    @Test
    public void registerUser() throws Exception {
        when(auth.createUserWithEmailAndPassword(anyString(),anyString())).thenReturn(task);
        presenter.registerUser("", "");
    }

}