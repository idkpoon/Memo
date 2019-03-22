package com.example.android.memo.Fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.Preference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android.memo.R;


public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener{

    private ViewGroup container;

    public SettingsFragment(){

    }


    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        this.container = container;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_headers);
        Preference generalPref = getPreferenceManager().findPreference(getString(R.string.key_pref_general));
        Preference categoriesPref = getPreferenceManager().findPreference(getString(R.string.key_pref_categories));
        generalPref.setOnPreferenceClickListener(this);
        categoriesPref.setOnPreferenceClickListener(this);

    }



    @Override
    public boolean onPreferenceClick(Preference preference) {

        String key = preference.getKey();
        if(key.equals(getString(R.string.key_pref_general))){
            GeneralPreferencesFragment generalPreferencesFragment = new GeneralPreferencesFragment();
            getChildFragmentManager().beginTransaction()
                    .replace(android.R.id.list_container, generalPreferencesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
        else if(key.equals(getString(R.string.key_pref_categories))){
            CategoriesPreferencesFragment categoriesPreferencesFragment = new CategoriesPreferencesFragment();
            getChildFragmentManager().beginTransaction()
                    .replace(android.R.id.list_container, categoriesPreferencesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
//
//        PreferenceScreen settings = getPreferenceScreen();
//        settings.removeAll();
        return true;

    }


}
