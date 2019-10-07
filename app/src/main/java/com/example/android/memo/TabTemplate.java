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
    public static List<Task>  mCurrentList;

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
        mCurrentList = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fragment_tab_template, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TodoCursorAdapter(getContext(), getMyCursor(mIndex));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        mCurrentList = adapter.getCurrentList();


        return rootView;
    }

    public static TabTemplate newInstance(int index) {
        
        Bundle args = new Bundle();
        args.putInt("index", index);
        TabTemplate fragment = new TabTemplate();
        fragment.setArguments(args);
        return fragment;
    }


    private Cursor getMyCursor(int index){
        String[] args = {
                Integer.toString(index), "not done"
        };

        String[] projection = {
                TodoEntry._ID,
                TodoEntry.COLUMN_TODO_NAME,
                TodoEntry.COLUMN_TODO_DATE,
                TodoEntry.COLUMN_TODO_TIME,
                TodoEntry.COLUMN_TODO_CATEGORY,
                TodoEntry.COLUMN_TODO_STATUS,
                TodoEntry.COLUMN_TODO_PRIORITY};

        mDBHelper = new TodoDBHelper(getContext());
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = getContext().getContentResolver().query(TodoEntry.CONTENT_URI,
                projection,
                "category=? AND status=?",
                args,
                null);

        return cursor;
    }






}
