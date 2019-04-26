package com.example.android.memo.dialogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.android.memo.Activity.SettingsActivity;
import com.example.android.memo.R;
import com.kizitonwose.colorpreference.ColorDialog;
import com.kizitonwose.colorpreference.ColorShape;

/**
 * Created by 21poonkw1 on 6/4/2019.
 */

public class PriorityPreference extends DialogPreference implements ColorDialog.OnColorSelectedListener{

    private String DIALOG_TAG = "dialog";

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

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        ImageButton imageButton = view.findViewById(R.id.icon);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SettingsActivity", "it's clicked");
            }
        });
    }

    private void openDialog(){
        new ColorDialog.Builder(SettingsActivity.class)
    }


    @Override
    public void onColorSelected(int i, String s) {

    }
}
