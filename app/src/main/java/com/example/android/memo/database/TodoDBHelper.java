package com.example.android.memo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.memo.database.TodoContract.TodoEntry;

/**
 * Created by 21poonkw1 on 24/1/2019.
 */

public class TodoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;
    public TodoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_STATEMENT = "CREATE TABLE " + TodoEntry.TABLE_NAME + "("
                + TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TodoEntry.COLUMN_TODO_NAME + " TEXT NOT NULL, "
                + TodoEntry.COLUMN_TODO_TIME + " TEXT, "
                + TodoEntry.COLUMN_TODO_DATE + " TEXT, "
                + TodoEntry.COLUMN_TODO_CATEGORY + " TEXT, "
                + TodoEntry.COLUMN_TODO_PRIORITY + " TEXT);";

        db.execSQL(SQL_CREATE_STATEMENT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
