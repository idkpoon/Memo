package com.example.android.memo.Fragments;


import android.annotation.TargetApi;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.memo.R;
import com.example.android.memo.database.TodoDBHelper;
import com.example.android.memo.database.TodoContract.TodoEntry;



public class InboxFragment extends Fragment {

    TextView displayView;
    TodoDBHelper mDBHelper;


    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    @TargetApi(23)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_inbox, container, false);
        displayView = (TextView) root.findViewById(R.id.displayNumViews);
        mDBHelper = new TodoDBHelper(getContext());
        displayDatabaseInfo();
        return root;


    }

    private void displayDatabaseInfo(){

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] projection = {TodoEntry._ID, TodoEntry.COLUMN_TODO_NAME, TodoEntry.COLUMN_TODO_DUE,
                TodoEntry.COLUMN_TODO_CATEGORY,
                TodoEntry.COLUMN_TODO_PRIORITY};

        Cursor cursor = db.query(TodoEntry.TABLE_NAME, projection, null, null,null,null,null);


        try {

            displayView.setText("Number of rows = " + cursor.getCount());

            displayView.append(TodoEntry._ID
                            + " - " + TodoEntry.COLUMN_TODO_NAME
                    + " - " + TodoEntry.COLUMN_TODO_DUE
                    + " - " + TodoEntry.COLUMN_TODO_PRIORITY
                    + " - " + TodoEntry.COLUMN_TODO_CATEGORY
                    + "\n");

            int idColumnIndex = cursor.getColumnIndex(TodoEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_NAME);
            int priorityColumnIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_PRIORITY);
            int dueDateColumnIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_DUE);
            int categoryColumnIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_CATEGORY);

            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDueDate = cursor.getString(dueDateColumnIndex);
                String currentPriority = cursor.getString(priorityColumnIndex);
                String currentCategory = cursor.getString(categoryColumnIndex);

                displayView.append("\n" + currentID
                        + " - " + currentName
                        + " - " + priorityColumnIndex
                        + " - " + dueDateColumnIndex
                        + " - " + categoryColumnIndex);
            }
        } finally{
            cursor.close();

        }



    }

    @Override
    public void onStart() {
        displayDatabaseInfo();
        super.onStart();
    }
}
