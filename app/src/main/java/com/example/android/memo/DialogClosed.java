package com.example.android.memo;

import com.example.android.memo.colordialog.ColorDialog;

/**
 * Created by 21poonkw1 on 26/4/2019.
 */

public interface DialogClosed {
    void onPositiveButton(ColorDialog colorDialog, String TAG);
    void onNegativeButton(ColorDialog colorDialog, String TAG);

}
