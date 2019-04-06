package com.example.android.memo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.memo.FragmentCommunicator;
import com.example.android.memo.fragments.LoginFragment;
import com.example.android.memo.fragments.SignupFragment;
import com.example.android.memo.R;

public class WelcomeActivity extends AppCompatActivity implements FragmentCommunicator {
    Button btnGoToSignUp;

    Button btnGoToLogin;

    LoginFragment loginFragment;
    SignupFragment signupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        LoginFragment.setFragmentCommunicatorListener(this);
        SignupFragment.setFragCom(this);

        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();

        if(savedInstanceState == null) {
            loadFragment(loginFragment);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    private void loadFragment(android.support.v4.app.Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, fragment).commit();

    }

    public void changeFragment(View v, String currentFrag) {
        // you can listen here for btn clicked

        if(currentFrag == "Login") {
            loadFragment(signupFragment);
        }
        else if(currentFrag == "Signup"){
            loadFragment(loginFragment);
        }
    }

}
