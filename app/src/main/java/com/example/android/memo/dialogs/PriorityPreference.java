package com.example.android.memo.dialogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * Created by 21poonkw1 on 6/4/2019.
 */

public class PriorityPreference extends DialogPreference {
    @TargetApi(21)
    public PriorityPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PriorityPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PriorityPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(21)
    public PriorityPreference(Context context) {
        super(context);
    }


}
