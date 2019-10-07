package com.example.android.memo.fragments;


import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.android.memo.Activity.MainActivity;
import com.example.android.memo.ItemDeleted;
import com.example.android.memo.R;
import com.example.android.memo.SwipeToDeleteCallback;
import com.example.android.memo.Task;
import com.example.android.memo.database.TodoContract;
import com.example.android.memo.database.TodoCursorAdapter;
import com.example.android.memo.database.TodoDBHelper;
import com.example.android.memo.database.TodoContract.TodoEntry;

import java.util.ArrayList;
import java.util.List;


public class InboxFragment extends Fragment implements ItemDeleted{

    static TodoDBHelper mDBHelper;
    RecyclerView recyclerView;
    public static TodoCursorAdapter adapter;
    static Context context;
    List<Task> taskList;
    List<Task> completedItemsList;


    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    @TargetApi(23)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        completedItemsList = new ArrayList<>();
        context = getContext();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_inbox, container, false);
        mDBHelper = new TodoDBHelper(getContext());

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecor);
        adapter = new TodoCursorAdapter(getContext(), getAllItems());

        adapter.setOnItemDeleted(this);
        recyclerView.setAdapter(adapter);
        taskList = adapter.getList();
        Log.v(getTag(), "Cursor count (Inbox): " + taskList.size());

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;



    }


    @Override
    public void onStart() {
        adapter = new TodoCursorAdapter(getContext(), getAllItems());
        recyclerView.setAdapter(adapter);
        super.onStart();
    }

    public static Cursor getAllItems(){
        // you will actually use after this query.
        String[] projection = {
                TodoEntry._ID,
                TodoEntry.COLUMN_TODO_NAME,
                TodoEntry.COLUMN_TODO_DATE,
                TodoEntry.COLUMN_TODO_TIME,
                TodoEntry.COLUMN_TODO_CATEGORY,
                TodoEntry.COLUMN_TODO_STATUS,
                TodoEntry.COLUMN_TODO_PRIORITY};

        String[] args = {"not done"};
        mDBHelper = new TodoDBHelper(getMyContext());
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = getMyContext().getContentResolver().query(TodoEntry.CONTENT_URI,
                projection,
                "status=?",
                args,
                null);

        return cursor;
    }

    public static Context getMyContext(){

        return context;
    }

    @Override
    public void onPause() {
        super.onPause();
        updateInfo();

    }

    @Override
    public void onStop() {
        super.onStop();
        updateInfo();

    }

    private void updateInfo(){
        TodoDBHelper mDBHelper = new TodoDBHelper(getContext());
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        int count = completedItemsList.size();


        for(int i = 0; i < completedItemsList.size(); i++) {
            Task task = completedItemsList.get(i);

            Log.v(getTag(), task.getName() + " " + task.getDate() + " " + task.getTime()
                    + " " + task.getCategory() + " " + task.getPriority() +  " " + task.getStatus());

            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoEntry.COLUMN_TODO_NAME, task.getName());
            contentValues.put(TodoEntry.COLUMN_TODO_DATE, task.getDate());
            contentValues.put(TodoEntry.COLUMN_TODO_TIME, task.getTime());
            contentValues.put(TodoEntry.COLUMN_TODO_CATEGORY, task.getCategory());
            contentValues.put(TodoEntry.COLUMN_TODO_PRIORITY, task.getPriority());
            contentValues.put(TodoEntry.COLUMN_TODO_STATUS, "done");


            db.update(TodoEntry.TABLE_NAME, contentValues,
                    "_id="+ task.getId(), null);
        }
        Log.v(getTag(), "Info has been updated");

    }

    @Override
    public void onItemDeleted(int position) {
        Log.v(getTag(), "Item " + position + " is deleted");
        Task deletedTask = taskList.get(position);


        Log.v(getTag(), "Task Name: " + deletedTask.getName());
        completedItemsList.add(deletedTask);
    }
}
