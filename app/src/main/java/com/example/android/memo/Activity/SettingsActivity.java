package com.example.android.memo.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.android.memo.DialogClosed;
import com.example.android.memo.R;
import com.example.android.memo.colordialog.ColorPreference;
import com.example.android.memo.colordialog.ColorShape;
import com.example.android.memo.colordialog.ColorUtils;
import com.example.android.memo.preferencefragments.CategoriesPreferenceFragment;
import com.example.android.memo.preferencefragments.GeneralPreferenceFragment;
import com.example.android.memo.preferencefragments.ProductivityPreferenceFragment;
import com.example.android.memo.colordialog.ColorDialog;

import java.util.List;
public class SettingsActivity extends AppCompatPreferenceActivity implements ColorDialog.OnColorSelectedListener, DialogClosed{

    public static final String COLOR_PREFERENCES = "ColorPreferences";
    private static Context context;
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Preferences);
        super.onCreate(savedInstanceState);
        setupActionBar();
        SettingsActivity.context = this;
        ColorDialog.setOnDialogClosedListener(this);

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }





    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }


    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }


    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || ProductivityPreferenceFragment.class.getName().equals(fragmentName)
                || CategoriesPreferenceFragment.class.getName().equals(fragmentName);
    }


    @Override
    public void onColorSelected(int newColor, String tag) {

        SharedPreferences sharedPreferences = getSharedPreferences(COLOR_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String hexColor = String.format("#%06X", (0xFFFFFF & newColor));
        hexColor = hexColor.replace("#", "");
        Log.v(TAG, "Hex Colour: " + hexColor);
        int regularColor = Integer.valueOf(hexColor, 16);

        int preference = CategoriesPreferenceFragment.getCurrentPreference();

        if(preference == 1){
           editor.putInt("selectedColor1", regularColor);

        }
        else if(preference == 2){
            editor.putInt("selectedColor2", regularColor);

        }
        else if(preference == 3){
            editor.putInt("selectedColor3", regularColor);

        }
        else if(preference == 4){
            editor.putInt("selectedColor4", regularColor);

        }
        editor.commit();

        ColorDialog dialog = ColorDialog.getColorDialog();

        Log.v(TAG, "Color Int: " + String.valueOf(regularColor));
        dialog.repopulateItems();


    }


    @Override
    public void onDialogClosed(ColorDialog colorDialog, String TAG) {
        String value = colorDialog.getName().trim();
        int color = 0;
        ColorPreference colorPreference = null;
        if(value.isEmpty() == false){

            SharedPreferences.Editor editor = getMySharedPreferences().edit();
            int preference = CategoriesPreferenceFragment.getCurrentPreference();

            switch (preference) {
                case 1:
                    editor.putString("categoryName1", value);
                    color = getMySharedPreferences().getInt("selectedColor1", 12597547);
                    colorPreference = CategoriesPreferenceFragment.getPreferenceCat1();
                    break;
                case 2:
                    editor.putString("categoryName2", value);
                    color = getMySharedPreferences().getInt("selectedColor2", 12597547);
                    colorPreference = CategoriesPreferenceFragment.getPreferenceCat2();
                    break;
                case 3:
                    editor.putString("categoryName3", value);
                    color = getMySharedPreferences().getInt("selectedColor3", 12597547);
                    colorPreference = CategoriesPreferenceFragment.getPreferenceCat3();
                    break;
                case 4:
                    editor.putString("categoryName4", value);
                    color = getMySharedPreferences().getInt("selectedColor4", 12597547);
                    colorPreference = CategoriesPreferenceFragment.getPreferenceCat4();
                    break;
            }
            editor.commit();
            ImageView colorView = ColorPreference.getImageView();
            ColorUtils.setColorViewValue(colorView, color, false, ColorShape.CIRCLE, this);
        }
        else{
            int preference = CategoriesPreferenceFragment.getCurrentPreference();
            switch (preference) {
                case 1:
                    color = getMySharedPreferences().getInt("selectedColor1", 12597547);
                    value = getMySharedPreferences().getString("categoryName1", "");
                    colorPreference = CategoriesPreferenceFragment.getPreferenceCat1();
                    break;
                case 2:
                    color = getMySharedPreferences().getInt("selectedColor2", 12597547);
                    colorPreference = CategoriesPreferenceFragment.getPreferenceCat2();
                    value = getMySharedPreferences().getString("categoryName2", "");
                    break;
                case 3:
                    color = getMySharedPreferences().getInt("selectedColor3", 12597547);
                    colorPreference = CategoriesPreferenceFragment.getPreferenceCat3();
                    value = getMySharedPreferences().getString("categoryName3", "");
                    break;
                case 4:
                    color = getMySharedPreferences().getInt("selectedColor4", 12597547);
                    colorPreference = CategoriesPreferenceFragment.getPreferenceCat4();
                    value = getMySharedPreferences().getString("categoryName4", "");
                    break;
            }


        }
        ImageView colorView = ColorPreference.getImageView();
        Drawable drawable = setColorViewValue(colorView, color, ColorShape.CIRCLE, this);
        colorView.setImageDrawable(drawable);
        Log.v(getClass().getSimpleName(), "My Colour: " + String.valueOf(color));
        colorPreference.setTitle(value);



    }

    public static Drawable setColorViewValue(ImageView imageView, int color, ColorShape shape, Context context) {
        Resources res = imageView.getContext().getResources();
        Drawable currentDrawable = imageView.getDrawable();

        GradientDrawable colorChoiceDrawable = new GradientDrawable();
        colorChoiceDrawable.setShape(GradientDrawable.OVAL);

        int darkenedColor = Color.rgb(
                Color.red(color) * 192 / 256,
                Color.green(color) * 192 / 256,
                Color.blue(color) * 192 / 256);

        String hexColor = String.format("#%06X", (0xFFFFFF & color));

        colorChoiceDrawable.setColor(Color.parseColor(hexColor));
        colorChoiceDrawable.setStroke((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, res.getDisplayMetrics()), darkenedColor);

        return colorChoiceDrawable;
    }

    public SharedPreferences getMySharedPreferences(){
        return getSharedPreferences(COLOR_PREFERENCES, MODE_PRIVATE);
    }




    public static Context getMyContext(){
        return context;
    }
}
