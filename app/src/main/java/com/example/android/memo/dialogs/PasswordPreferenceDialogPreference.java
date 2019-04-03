package com.example.android.memo.dialogs;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v14.preference.PreferenceDialogFragment;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.android.memo.R;

/**
 * Created by 21poonkw1 on 2/4/2019.
 */

public class PasswordPreferenceDialogPreference extends PreferenceDialogFragmentCompat {

    private EditText mPassword;

    public static PasswordPreferenceDialogPreference newInstance(String key) {
        final PasswordPreferenceDialogPreference
                fragment = new PasswordPreferenceDialogPreference();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mPassword = (EditText) view.findViewById(R.id.edit);

        // Exception: There is no TimePicker with the id 'edit' in the dialog.
        if (mPassword == null) {
            throw new IllegalStateException("Dialog view must contain a EditText with id 'edit'");
        }


        DialogPreference preference = getPreference();
        if (preference instanceof PasswordPreference) {
        }

    }

    @Override
    public void onDialogClosed(boolean positiveResult) {

        DialogPreference preference = getPreference();
        if(preference instanceof PasswordPreference){
            PasswordPreference passwordPreference = ((PasswordPreference) preference);
        }

    }
}
