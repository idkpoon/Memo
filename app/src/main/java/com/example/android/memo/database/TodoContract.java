package com.example.android.memo.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 21poonkw1 on 19/1/2019.
 */

public class TodoContract {

    private TodoContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.memo";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TODOS = "memo";

    public static final class TodoEntry implements BaseColumns{
        public final static String TABLE_NAME = "Todos";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TODOS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TODOS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TODOS;

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TODO_NAME = "name";
        public final static String COLUMN_TODO_TIME = "time";
        public final static String COLUMN_TODO_DATE = "date";
        public final static String COLUMN_TODO_CATEGORY = "category";
        public final static String COLUMN_TODO_PRIORITY = "priority";

    }
}
