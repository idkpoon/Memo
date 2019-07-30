package com.example.android.memo.dialogs;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by 21poonkw1 on 28/4/2019.
 */

public class DatePicker extends DialogFragment{

    @Override
    @TargetApi(23)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener) getContext(), year, month, day);
    }
}
