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
import android.widget.ImageButton;


import com.example.android.memo.Activity.SettingsActivity;
import com.example.android.memo.R;
import com.example.android.memo.colordialog.ColorDialog;
import com.example.android.memo.colordialog.ColorPreference;

import static android.content.Context.MODE_PRIVATE;
import static com.example.android.memo.Activity.SettingsActivity.COLOR_PREFERENCES;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CategoriesPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener  {

    //Categories
    static ColorPreference preferenceCat1, preferenceCat2, preferenceCat3, preferenceCat4;

    //Priority
    static ColorPreference priority1, priority2, priority3;
    private static int currentPreference;
    ColorDialog colorDialog;



    @Override
    @TargetApi(23)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.pref_categories);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = getMySharedPreferences();


        int count = preferenceScreen.getPreferenceCount();

        preferenceCat1 = (ColorPreference) preferenceScreen.findPreference(getString(R.string.key_category_1));
        preferenceCat2 = (ColorPreference) preferenceScreen.findPreference(getString(R.string.key_category_2));
        preferenceCat3 = (ColorPreference) preferenceScreen.findPreference(getString(R.string.key_category_3));
        preferenceCat4 = (ColorPreference) preferenceScreen.findPreference(getString(R.string.key_category_4));

        priority1 = (ColorPreference) preferenceScreen.findPreference(getString(R.string.key_priority_1));
        priority2 = (ColorPreference) preferenceScreen.findPreference(getString(R.string.key_priority_2));
        priority3 = (ColorPreference) preferenceScreen.findPreference(getString(R.string.key_priority_3));


        colorDialog = ColorDialog.getColorDialog();

        String value = sharedPreferences.getString("categoryName1", "School");
        preferenceCat1.setTitle(value);

        value = sharedPreferences.getString("categoryName2", "Errands");
        preferenceCat2.setTitle(value);

        value = sharedPreferences.getString("categoryName3", "Personal");
        preferenceCat3.setTitle(value);

        value = sharedPreferences.getString("categoryName4", "Other");
        preferenceCat4.setTitle(value);

        value = sharedPreferences.getString("priorityName1", "Urgent");
        priority1.setTitle(value);
        value = sharedPreferences.getString("priorityName2", "Do later");
        priority2.setTitle(value);
        value = sharedPreferences.getString("priorityName3", "When you have time");
        priority3.setTitle(value);


        preferenceCat1.setOnPreferenceClickListener(this);
        preferenceCat2.setOnPreferenceClickListener(this);
        preferenceCat3.setOnPreferenceClickListener(this);
        preferenceCat4.setOnPreferenceClickListener(this);

        priority1.setOnPreferenceClickListener(this);
        priority2.setOnPreferenceClickListener(this);
        priority3.setOnPreferenceClickListener(this);


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


    public static ColorPreference getPreferenceCat1(){
        return preferenceCat1;
    }

    public static ColorPreference getPreferenceCat2(){
        return preferenceCat2;
    }

    public static ColorPreference getPreferenceCat3(){
        return preferenceCat3;
    }

    public static ColorPreference getPreferenceCat4(){
        return preferenceCat4;
    }

    public static ColorPreference getPriority1() {
        return priority1;
    }

    public static ColorPreference getPriority2() {
        return priority2;
    }

    public static ColorPreference getPriority3() {
        return priority3;
    }



    public static int getCurrentPreference(){

        return currentPreference;
    }

    @TargetApi(23)
    public SharedPreferences getMySharedPreferences(){
        return getContext().getSharedPreferences(COLOR_PREFERENCES, MODE_PRIVATE);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        switch (preference.getKey()){
            case "category_1":
                currentPreference = 1;
                break;
            case "category_2":
                currentPreference = 2;
                break;
            case "category_3":
                currentPreference = 3;
                break;
            case "category_4":
                currentPreference = 4;
                break;
            case "priority_1":
                currentPreference = 5;
                break;
            case "priority_2":
                currentPreference = 6;
                break;
            case "priority_3":
                currentPreference = 7;
                break;


        }

        Log.v(getClass().getSimpleName(), "Preference Number: " + String.valueOf(currentPreference));
        return false;
    }


}

