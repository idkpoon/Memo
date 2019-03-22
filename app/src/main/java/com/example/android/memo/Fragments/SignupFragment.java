package com.example.android.memo.Fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.memo.Activity.MainActivity;
import com.example.android.memo.Activity.WelcomeActivity;
import com.example.android.memo.FragmentCommunicator;
import com.example.android.memo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private Button btnSignup, btnGoToLogin;
    private EditText ETemail, ETpassword;
    private TextView appTitle, loginTitle;
    private FirebaseAuth mAuth;
    static FragmentCommunicator fragmentCommunicator;
    private String TAG = "SignupFragment";
    public Typeface helveticaFont;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        helveticaFont = Typeface.createFromAsset(getActivity().getAssets(), "Fonts/HelveticaNeue-Thin.ttf");
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        btnGoToLogin = (Button) root.findViewById(R.id.btnGotoLogin);
        ETemail = (EditText) root.findViewById(R.id.editTextEmail);
        ETpassword = (EditText) root.findViewById(R.id.editTextPassword);
        btnSignup = (Button)root.findViewById(R.id.btnSignup);
        appTitle = (TextView)root.findViewById(R.id.app_title);
        loginTitle = (TextView)root.findViewById(R.id.loginTitle);

        appTitle.setTypeface(helveticaFont);
        loginTitle.setTypeface(helveticaFont);


        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCommunicator.changeFragment(v, "Signup");

            }
        });

        mAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ETemail.getText().toString().trim();
                String password = ETpassword.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "SignUpWithEmail:success");



                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "SignUpwithEmail:failure", task.getException());
                        }
                    }
                });
            }
        });

        return root;



    }

    public static void setFragCom(FragmentCommunicator listener){
        fragmentCommunicator =listener;
    }

}
