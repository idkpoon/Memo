package com.example.android.memo.preferencefragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

import com.example.android.memo.Activity.SettingsActivity;
import com.example.android.memo.R;
import com.example.android.memo.dialogs.PasswordPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by 21poonkw1 on 6/4/2019.
 */

public class GeneralPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
        SharedPreferences.OnSharedPreferenceChangeListener{

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.pref_general);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();


        for(int i = 0; i < count; i++){
            Preference p = preferenceScreen.getPreference(i);
            if(p instanceof EditTextPreference){
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
                p.setOnPreferenceChangeListener(this);
            }
            else if (p instanceof PasswordPreference){
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }



    }



    private void setPreferenceSummary(Preference preference, String value){
        if(preference instanceof EditTextPreference){
            preference.setSummary(value);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    @TargetApi(23)
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference instanceof EditTextPreference){
            setPreferenceSummary(preference, newValue.toString());
            SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(preference.getKey(), newValue.toString());
            editor.apply();

            if(preference.getKey().equals(getString(R.string.key_pref_email))){
                String email = preference.getKey().toString();
                mUser.updateEmail(email);


            }

            return true;


        }
        else if(preference instanceof PasswordPreference){
            SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
            String email = sharedPreferences.getString(getString(R.string.key_pref_password), "default");
            preference.setSummary(email);
            return true;
        }
        else{
            return false;
        }
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (null != preference) {
            if (preference instanceof PasswordPreference) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


}