package com.example.android.memo.fragments;


import android.annotation.TargetApi;
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
import com.example.android.memo.R;
import com.example.android.memo.Task;
import com.example.android.memo.database.TodoContract;
import com.example.android.memo.database.TodoCursorAdapter;
import com.example.android.memo.database.TodoDBHelper;
import com.example.android.memo.database.TodoContract.TodoEntry;

import java.util.ArrayList;
import java.util.List;


public class InboxFragment extends Fragment {

    static TodoDBHelper mDBHelper;
    RecyclerView recyclerView;
    public static TodoCursorAdapter adapter;
    static Context context;


    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    @TargetApi(23)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_inbox, container, false);
        mDBHelper = new TodoDBHelper(getContext());

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecor);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        adapter = new TodoCursorAdapter(getContext(), getAllItems());

        recyclerView.setAdapter(adapter);

        return root;



    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//            viewHolder.getAdapterPosition();
//            adapter.notifyDataSetChanged();
        }
    };


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

        mDBHelper = new TodoDBHelper(getMyContext());
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = getMyContext().getContentResolver().query(TodoEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        return cursor;
    }

    public static Context getMyContext(){
        return context;
    }


}
