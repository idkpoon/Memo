package com.example.android.memo.preferencefragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.example.android.memo.Activity.SettingsActivity;
import com.example.android.memo.R;
import com.kizitonwose.colorpreference.ColorPreference;
import com.kizitonwose.colorpreference.ColorDialog;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CategoriesPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.pref_categories);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();
        int count = preferenceScreen.getPreferenceCount();


        for(int i = 0; i < count; i++){
            Preference p = preferenceScreen.getPreference(i);
            if(p instanceof ColorPreference) {

                int value = ((ColorPreference) p).getValue();
                Log.d("SettingsActivity", p.getKey() + ": " + Integer.toString(value));

            }
            else if (p.getKey().equals(getString(R.string.priority_urgent))){

            }
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

    public void setSummary(Preference p, String value){
        p.setSummary(value);
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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        int colour = sharedPreferences.getInt(key, 0);
        Log.d("SettingsActivity", key + ": " + Integer.toString(colour) + "from the onSharedPreferenceChanged method");
        String hexColor = String.format("#%06X", (0xFFFFFF & colour));
        Log.d("SettingsActivity", "Hex colour: " + hexColor);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key + "_colour", hexColor);
    }


}

