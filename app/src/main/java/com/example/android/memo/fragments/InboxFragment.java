package com.example.android.memo.fragments;


import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.memo.Activity.MainActivity;
import com.example.android.memo.R;
import com.example.android.memo.database.TodoContract;
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
        displayDatabaseInfo(displayView);
        return root;


    }


    @Override
    public void onStart() {
        displayDatabaseInfo(displayView);
        super.onStart();
    }

    public void displayDatabaseInfo(TextView displayView) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                TodoEntry._ID,
                TodoEntry.COLUMN_TODO_NAME,
                TodoEntry.COLUMN_TODO_DATE,
                TodoEntry.COLUMN_TODO_TIME,
                TodoEntry.COLUMN_TODO_CATEGORY,
                TodoEntry.COLUMN_TODO_PRIORITY};

        mDBHelper = new TodoDBHelper(getActivity());
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor cursor = getActivity().getContentResolver().query(TodoEntry.CONTENT_URI, projection, null, null, null);

        if(cursor != null) {
            try {
                displayView.setText("There are " + cursor.getCount() + " tasks.\n\n");
                displayView.append(TodoEntry._ID + " - " +
                        TodoEntry.COLUMN_TODO_NAME + " - " +
                        TodoEntry.COLUMN_TODO_DATE + " - " +
                        TodoEntry.COLUMN_TODO_TIME + " - " +
                        TodoEntry.COLUMN_TODO_CATEGORY + " - " +
                        TodoEntry.COLUMN_TODO_PRIORITY + "\n");

                // Figure out the index of each column
                int idIndex = cursor.getColumnIndex(TodoEntry._ID);
                int nameIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_NAME);
                int dateIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_DATE);
                int timeIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_TIME);
                int catIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_CATEGORY);
                int priorityIndex = cursor.getColumnIndex(TodoEntry.COLUMN_TODO_PRIORITY);

                // Iterate through all the returned rows in the cursor
                while (cursor.moveToNext()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    int currentID = cursor.getInt(idIndex);
                    String currentName = cursor.getString(nameIndex);
                    String currentDate = cursor.getString(dateIndex);
                    String currentTime = cursor.getString(timeIndex);
                    String currentCat = cursor.getString(catIndex);
                    String currentPriority = cursor.getString(priorityIndex);

                    // Display the values from each column of the current row in the cursor in the TextView
                    displayView.append(("\n" + currentID + " - "
                            + currentName + " - "
                            + currentDate + " - "
                            + currentTime + " - "
                            + currentCat  + " - "
                            + currentPriority + " - "));
                }
            } finally {
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }
}
