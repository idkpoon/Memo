package com.example.android.memo.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.memo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesPreferencesFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private void setPreferenceSummary(Preference preference, Object value) {
//        String stringValue = value.toString();
//        String key = preference.getKey();
//
//        if (preference instanceof ListPreference) {
//
//            ListPreference listPreference = (ListPreference) preference;
//            int prefIndex = listPreference.findIndexOfValue(stringValue);
//            if (prefIndex >= 0) {
//                preference.setSummary(listPreference.getEntries()[prefIndex]);
//            }
//        } else {
//            preference.setSummary(stringValue);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        /* Add 'general' preferences, defined in the XML file */
        addPreferencesFromResource(R.xml.pref_categories);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        Preference preference = findPreference(key);
//        if (null != preference) {
//            if (!(preference instanceof CheckBoxPreference)) {
//                setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
//            }
//        }
    }
}