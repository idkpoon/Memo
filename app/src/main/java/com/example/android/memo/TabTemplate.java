package com.example.android.memo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.memo.database.TodoContract;
import com.example.android.memo.database.TodoCursorAdapter;
import com.example.android.memo.database.TodoDBHelper;
import com.example.android.memo.database.TodoContract.TodoEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 21poonkw1 on 30/7/2019.
 */

public class TabTemplate extends Fragment {


    RecyclerView recyclerView;
    private int mIndex;
    TodoCursorAdapter adapter;
    TodoDBHelper mDBHelper;

    public TabTemplate(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndex = getArguments().getInt("index", 1);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab_template, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        refresh();
        return rootView;
    }

    private void refresh(){
        adapter = new TodoCursorAdapter(getContext(), getMyCursor(mIndex));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    public static TabTemplate newInstance(int index) {
        
        Bundle args = new Bundle();
        args.putInt("index", index);
        TabTemplate fragment = new TabTemplate();
        fragment.setArguments(args);
        return fragment;
    }


    private Cursor getMyCursor(int index){


        String[] projection = {
                TodoEntry._ID,
                TodoEntry.COLUMN_TODO_NAME,
                TodoEntry.COLUMN_TODO_DATE,
                TodoEntry.COLUMN_TODO_TIME,
                TodoEntry.COLUMN_TODO_CATEGORY,
                TodoEntry.COLUMN_TODO_STATUS,
                TodoEntry.COLUMN_TODO_PRIORITY};

        String selections = TodoEntry.COLUMN_TODO_CATEGORY + "=? AND " + TodoEntry.COLUMN_TODO_STATUS
                + "=?";

        Log.v(getTag(), "Index: " + Integer.toString(index));

        String[] args = {
                Integer.toString(index), "not done"
        };

        mDBHelper = new TodoDBHelper(getContext());
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = getContext().getContentResolver().query(TodoEntry.CONTENT_URI,
                projection,
                selections,
                args,
                null);

        return cursor;

    }

    public List<Task> getList(){
        List<Task> taskList = new ArrayList<>();
        Cursor mCursor = getMyCursor(mIndex);
        while(mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex(TodoContract.TodoEntry._ID));
            String name = mCursor.getString(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_NAME));
            String date = mCursor.getString(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_DATE));
            String time = mCursor.getString(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_TIME));
            String status = mCursor.getString(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_STATUS));
            int cat = mCursor.getInt(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_CATEGORY));
            int priority = mCursor.getInt(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_PRIORITY));

            Task task = new Task(id, name, date, time, cat, priority, status);
            taskList.add(task);
        }

        return taskList;
    }




}
