package com.example.android.memo.dialogs;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by 21poonkw1 on 28/4/2019.
 */

public class TimePicker extends DialogFragment {

    @Override
    @TargetApi(23)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getContext(), (TimePickerDialog.OnTimeSetListener) getContext(), hour, minute, DateFormat.is24HourFormat(getContext()));

    }
}
