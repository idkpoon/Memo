package com.example.android.memo.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.memo.database.TodoContract.TodoEntry;

/**
 * Created by 21poonkw1 on 29/4/2019.
 */

public class TodoProvider extends ContentProvider {

    private TodoDBHelper mDBHelper;
    public static final String LOG_TAG = TodoProvider.class.getSimpleName();

    private static final int TODOS = 100;
    private static final int TODOS_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.PATH_TODOS, TODOS);
        sUriMatcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.PATH_TODOS + "/#", TODOS_ID);


    }

    @Override
    public boolean onCreate() {
        mDBHelper = new TodoDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch(match){

            case TODOS:
                cursor = db.query(TodoContract.TodoEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case TODOS_ID:
                selection = TodoContract.TodoEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TodoContract.TodoEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TODOS:
                return TodoEntry.CONTENT_LIST_TYPE;
            case TODOS_ID:
                return TodoEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case TODOS:
                return insertTodo(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDBHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TODOS:
                // Delete all rows that match the selection and selection args
                return database.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
            case TODOS_ID:
                // Delete a single row given by the ID in the URI
                selection = TodoEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TODOS:
                return updateTodo(uri, values, selection, selectionArgs);
            case TODOS_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = TodoEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateTodo(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

    }

    private Uri insertTodo(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDBHelper.getWritableDatabase();

        String name = values.getAsString(TodoEntry.COLUMN_TODO_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Todo requires a name");
        }
        long id = database.insert(TodoEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    private int updateTodo(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(TodoEntry.COLUMN_TODO_NAME)) {
            String name = values.getAsString(TodoEntry.COLUMN_TODO_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Todo requires a name");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        return database.update(TodoEntry.TABLE_NAME, values, selection, selectionArgs);

    }
}
