package com.example.android.memo.dialogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

import com.example.android.memo.R;

/**
 * Created by 21poonkw1 on 2/4/2019.
 */

public class PasswordPreference extends DialogPreference {

    private int mTime;

    private int mDialogLayoutResId = R.layout.pref_dialog_password;

    public PasswordPreference(Context context) {
        this(context, null);
    }

    public PasswordPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.preferenceStyle);
    }

    public PasswordPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, defStyleAttr);
    }

    public PasswordPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    public int getTime() {
        return mTime;
    }


    public void setTime(int time) {
        mTime = time;

        persistInt(time);
    }


    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }


    @Override
    public int getDialogLayoutResource() {
        return mDialogLayoutResId;
    }


    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setTime(restorePersistedValue ?
                getPersistedInt(mTime) : (int) defaultValue);
    }

}
