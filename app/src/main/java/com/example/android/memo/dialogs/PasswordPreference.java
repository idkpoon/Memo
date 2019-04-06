package com.example.android.memo.dialogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.memo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordPreference extends DialogPreference {
    private EditText editTextEmail, editTextPassword;
    private String mUsername;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private void setUpAuth(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @TargetApi(21)
    public PasswordPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUpAuth();


    }

    public PasswordPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpAuth();

    }

    public PasswordPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpAuth();
    }

    @TargetApi(21)
    public PasswordPreference(Context context) {
        super(context);
        setUpAuth();
    }

    private LayoutInflater getMyInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected View onCreateDialogView() {
        setDialogLayoutResource(R.layout.password_dialog);
        LayoutInflater inflater = getMyInflater();
        View view = inflater.inflate(R.layout.password_dialog, null);

        editTextEmail = (EditText) view.findViewById(R.id.edit);
        editTextPassword = (EditText)view.findViewById(R.id.ETpassword);

        return view;
    }




    @Override
    protected void onDialogClosed(boolean positiveResult) {
        String TAG = "SettingsActivity";
        Log.d(TAG, "onDialogClosed is called");

        if(positiveResult){
            Log.d(TAG, "positive result");

            String value = editTextEmail.getText().toString();

            Log.d(TAG, "the value = " + value);

            SharedPreferences sharedPreferences = getSharedPreferences();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String key = getKey();
            editor.putString(key, value.toString());
            editor.apply();
            getEmail();

            if(mUser != null){
                mUser.updatePassword(editTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Password updated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        }




    }

    public String getKey(){

        return getContext().getString(R.string.key_pref_password);
    }



//
//    public String getUsername(){
//        return mUsername;
//    }

    public void getEmail(){
        String mUsername = editTextEmail.getText().toString();
    }



    @Override
    public Context getContext() {

        return super.getContext();
    }

}