package com.example.android.memo.preferencefragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

import com.example.android.memo.R;
import com.example.android.memo.Activity.SettingsActivity;
import com.example.android.memo.dialogs.NumberPickerPreference;
import com.example.android.memo.dialogs.PasswordPreference;

public class ProductivityPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.pref_productivity);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();


        for(int i = 0; i < count; i++){
            Preference p = preferenceScreen.getPreference(i);
            if(p instanceof NumberPickerPreference){
                int value = sharedPreferences.getInt(p.getKey(), 0);
                String message = Integer.toString(value) + " tasks";
                setPreferenceSummary(p, message);
            }
            else if(p instanceof CheckBoxPreference){
                setUpCheckbox((CheckBoxPreference) p);
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

    private void setUpCheckbox(CheckBoxPreference p){
        String key = p.getKey();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        boolean checked = sharedPreferences.getBoolean(key, false);
        p.setChecked(checked);
    }


    private void setPreferenceSummary(Preference preference, String value){
        if(preference instanceof EditTextPreference){
            preference.setSummary(value);
        }
        else if(preference instanceof NumberPickerPreference){
            SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
            int numTasks = sharedPreferences.getInt(preference.getKey(), 0);

            String message = "";
            if(numTasks == 1){
                message = Integer.toString(numTasks) + " task";
            }
            else{
                message = Integer.toString(numTasks) + " tasks";
            }
            preference.setSummary(message);

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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        int numTasks = sharedPreferences.getInt(key, 0);
        setPreferenceSummary(findPreference(key), Integer.toString(numTasks));
        setUpCheckbox((CheckBoxPreference) findPreference(key));
    }
}
