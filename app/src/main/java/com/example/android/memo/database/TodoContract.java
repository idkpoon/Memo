package com.example.android.memo.database;

import android.provider.BaseColumns;

/**
 * Created by 21poonkw1 on 19/1/2019.
 */

public class TodoContract {

    private TodoContract(){}

    public static final class TodoEntry implements BaseColumns{
        public final static String TABLE_NAME = "Todos";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TODO_NAME = "name";
        public final static String COLUMN_TODO_DUE = "schedule";
        public final static String COLUMN_TODO_CATEGORY = "category";
        public final static String COLUMN_TODO_PRIORITY = "priority";

    }
}
