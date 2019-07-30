package com.example.android.memo.dialogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.android.memo.R;

/**
 * Created by 21poonkw1 on 6/4/2019.
 */

public class NumberPickerPreference extends DialogPreference {

    NumberPicker numberPicker;
    @TargetApi(21)
    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setDialogTitle(context.getString(R.string.daily_tasks_dialog_title));
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDialogTitle(context.getString(R.string.daily_tasks_dialog_title));

    }

    public NumberPickerPreference(Context context, AttributeSet attrs) {

        super(context, attrs);
        setDialogTitle(context.getString(R.string.daily_tasks_dialog_title));

    }

    @TargetApi(21)
    public NumberPickerPreference(Context context) {

        super(context);
        setDialogTitle(context.getString(R.string.daily_tasks_dialog_title));

    }


    private LayoutInflater getMyInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void setUpNumberPicker(View view){
        numberPicker = view.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
    }

    @Override
    protected View onCreateDialogView() {
        setDialogLayoutResource(R.layout.number_dialog);
        LayoutInflater inflater = getMyInflater();
        View view = inflater.inflate(R.layout.number_dialog, null);
        setUpNumberPicker(view);

        return view;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        String TAG = "SettingsActivity";
        super.onDialogClosed(positiveResult);
        if(positiveResult){
            int value = numberPicker.getValue();
            SharedPreferences sharedPreferences = getSharedPreferences();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("daily_goal", value).apply();
        }
    }
}
