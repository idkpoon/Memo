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
import android.widget.Toast;

import com.example.android.memo.Activity.MainActivity;
import com.example.android.memo.FragmentCommunicator;
import com.example.android.memo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    static FragmentCommunicator fragmentCommunicator;

    private EditText ETEmail, ETPass;
    private Button btnForgotPass, btnLogin, btnGoToSignup;
    private TextView appTitle, loginTitle;

    private FirebaseAuth mAuth;

    private String TAG = "LoginFragment";

    public Typeface helveticaFont;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        helveticaFont = Typeface.createFromAsset(getActivity().getAssets(), "Fonts/HelveticaNeue-Thin.ttf");

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        ETEmail = (EditText) root.findViewById(R.id.editTextEmail);
        ETPass = (EditText) root.findViewById(R.id.editTextPassword);
        btnForgotPass = (Button)root.findViewById(R.id.btnForgotPassword);
        btnLogin = (Button)root.findViewById(R.id.btnLogin);
        btnGoToSignup = (Button)root.findViewById(R.id.btnGoToSignUp);
        appTitle = (TextView)root.findViewById(R.id.app_title);
        loginTitle = (TextView)root.findViewById(R.id.loginTitle);

        appTitle.setTypeface(helveticaFont);
        loginTitle.setTypeface(helveticaFont);



        mAuth = FirebaseAuth.getInstance();

        btnGoToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCommunicator.changeFragment(v, "Login");

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ETEmail.getText().toString().trim();
                String password = ETPass.getText().toString().trim();


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);

                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });

            }
        });


        return root;

    }
    public static void setFragmentCommunicatorListener(FragmentCommunicator listener){
        fragmentCommunicator = listener;
    }



}
