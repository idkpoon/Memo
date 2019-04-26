package com.example.android.memo.dialogs;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.app.DialogFragment;
import android.view.View;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.android.memo.Activity.AppCompatPreferenceActivity;
import com.kizitonwose.colorpreference.ColorShape;
import com.kizitonwose.colorpreference.ColorUtils;
import com.kizitonwose.colorpreference.R.array;
import com.kizitonwose.colorpreference.R.dimen;
import com.kizitonwose.colorpreference.R.id;
import com.kizitonwose.colorpreference.R.layout;
import com.kizitonwose.colorpreference.Utils;

public class ColorDialog extends DialogFragment {
    private GridLayout colorGrid;
    private com.example.android.memo.dialogs.ColorDialog.OnColorSelectedListener colorSelectedListener;
    private int numColumns;
    private int[] colorChoices;
    private ColorShape colorShape;
    private int selectedColorValue;
    private static final String NUM_COLUMNS_KEY = "num_columns";
    private static final String COLOR_SHAPE_KEY = "color_shape";
    private static final String COLOR_CHOICES_KEY = "color_choices";
    private static final String SELECTED_COLOR_KEY = "selected_color";

    public ColorDialog() {
    }

    public static com.example.android.memo.dialogs.ColorDialog newInstance(int numColumns, ColorShape colorShape, int[] colorChoices, int selectedColorValue) {
        Bundle args = new Bundle();
        args.putInt("num_columns", numColumns);
        args.putSerializable("color_shape", colorShape);
        args.putIntArray("color_choices", colorChoices);
        args.putInt("selected_color", selectedColorValue);
        com.example.android.memo.dialogs.ColorDialog dialog = new com.example.android.memo.dialogs.ColorDialog();
        dialog.setArguments(args);
        return dialog;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        this.numColumns = args.getInt("num_columns");
        this.colorShape = (ColorShape)args.getSerializable("color_shape");
        this.colorChoices = args.getIntArray("color_choices");
        this.selectedColorValue = args.getInt("selected_color");
    }

    public void setOnColorSelectedListener(com.example.android.memo.dialogs.ColorDialog.OnColorSelectedListener colorSelectedListener) {
        this.colorSelectedListener = colorSelectedListener;
        this.repopulateItems();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof com.example.android.memo.dialogs.ColorDialog.OnColorSelectedListener) {
            this.setOnColorSelectedListener((com.example.android.memo.dialogs.ColorDialog.OnColorSelectedListener)context);
        } else {
            this.repopulateItems();
        }

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.getActivity());
        View rootView = layoutInflater.inflate(com.kizitonwose.colorpreference.R.layout.dialog_colors, (ViewGroup)null);
        this.colorGrid = (GridLayout)rootView.findViewById(com.kizitonwose.colorpreference.R.id.color_grid);
        this.colorGrid.setColumnCount(this.numColumns);
        this.repopulateItems();
        return (new android.app.AlertDialog.Builder(this.getActivity())).setView(rootView).create();
    }

    private void repopulateItems() {
        if(this.colorSelectedListener != null && this.colorGrid != null) {
            Context context = this.colorGrid.getContext();
            this.colorGrid.removeAllViews();
            int[] var2 = this.colorChoices;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                final int color = var2[var4];
                View itemView = LayoutInflater.from(context).inflate(com.kizitonwose.colorpreference.R.layout.grid_item_color, this.colorGrid, false);
                ColorUtils.setColorViewValue((ImageView)itemView.findViewById(com.kizitonwose.colorpreference.R.id.color_view), color, color == this.selectedColorValue, this.colorShape);
                itemView.setClickable(true);
                itemView.setFocusable(true);
                itemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if(ColorDialog.this.colorSelectedListener != null) {
                            ColorDialog.this.colorSelectedListener.onColorSelected(color, com.example.android.memo.dialogs.ColorDialog.this.getTag());
                        }

                        ColorDialog.this.dismiss();
                    }
                });
                this.colorGrid.addView(itemView);
            }

            this.sizeDialog();
        }
    }

    public void onStart() {
        super.onStart();
        this.sizeDialog();
    }

    private void sizeDialog() {
        if(this.colorSelectedListener != null && this.colorGrid != null) {
            Dialog dialog = this.getDialog();
            if(dialog != null) {
                Resources res = this.colorGrid.getContext().getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                this.colorGrid.measure(MeasureSpec.makeMeasureSpec(dm.widthPixels, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(dm.heightPixels, MeasureSpec.EXACTLY));
                int width = this.colorGrid.getMeasuredWidth();
                int height = this.colorGrid.getMeasuredHeight();
                int extraPadding = res.getDimensionPixelSize(com.kizitonwose.colorpreference.R.dimen.color_grid_extra_padding);
                width += extraPadding;
                height += extraPadding;
                dialog.getWindow().setLayout(width, height);
            }
        }
    }

    public static class Builder {
        private int numColumns = 5;
        private int[] colorChoices;
        private ColorShape colorShape;
        private Context context;
        private int selectedColor;
        private String tag;

        public <ColorActivityType extends AppCompatPreferenceActivity & com.kizitonwose.colorpreference.ColorDialog.OnColorSelectedListener> Builder(@NonNull ColorActivityType context) {
            this.colorShape = ColorShape.CIRCLE;
            this.context = context;
            this.setColorChoices(com.kizitonwose.colorpreference.R.array.default_color_choice_values);
        }

        public com.example.android.memo.dialogs.ColorDialog.Builder setNumColumns(int numColumns) {
            this.numColumns = numColumns;
            return this;
        }

        public com.example.android.memo.dialogs.ColorDialog.Builder setColorChoices(@ArrayRes int colorChoicesRes) {
            this.colorChoices = ColorUtils.extractColorArray(colorChoicesRes, this.context);
            return this;
        }

        public com.example.android.memo.dialogs.ColorDialog.Builder setColorShape(ColorShape colorShape) {
            this.colorShape = colorShape;
            return this;
        }

        public com.example.android.memo.dialogs.ColorDialog.Builder setSelectedColor(@ColorInt int selectedColor) {
            this.selectedColor = selectedColor;
            return this;
        }

        public com.example.android.memo.dialogs.ColorDialog.Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        protected com.kizitonwose.colorpreference.ColorDialog build() {
            com.kizitonwose.colorpreference.ColorDialog dialog = com.kizitonwose.colorpreference.ColorDialog.newInstance(this.numColumns, this.colorShape, this.colorChoices, this.selectedColor);
            dialog.setOnColorSelectedListener((com.kizitonwose.colorpreference.ColorDialog.OnColorSelectedListener)this.context);
            return dialog;
        }

        public com.kizitonwose.colorpreference.ColorDialog show() {
            com.kizitonwose.colorpreference.ColorDialog dialog = this.build();
            dialog.show(Utils.resolveContext(this.context).getFragmentManager(), this.tag == null?String.valueOf(System.currentTimeMillis()):this.tag);
            return dialog;
        }
    }

    public interface OnColorSelectedListener {
        void onColorSelected(int var1, String var2);
    }
}
