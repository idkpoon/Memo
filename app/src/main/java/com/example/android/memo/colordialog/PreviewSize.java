package com.example.android.memo.colordialog;


public enum PreviewSize {
    NORMAL, LARGE;

    public static PreviewSize getSize(int num) {
        switch (num) {
            case 1:
                return NORMAL;
            case 2:
                return LARGE;
            default:
                return NORMAL;
        }
    }
}
