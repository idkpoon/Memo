package com.example.android.memo.dialogs;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.memo.Activity.MainActivity;
import com.example.android.memo.Activity.SettingsActivity;
import com.example.android.memo.R;
import com.example.android.memo.database.TodoCursorAdapter;
import com.example.android.memo.database.TodoDBHelper;
import com.example.android.memo.database.TodoContract.TodoEntry;
import com.example.android.memo.fragments.InboxFragment;

import java.util.ArrayList;

/**
 * Created by 21poonkw1 on 28/4/2019.
 */

public class FullScreenDialog extends DialogFragment implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    public static Button btnOpenTimePicker, btnOpenDatePicker;
    ImageView save;
    EditText etTaskName;
    Spinner prioritySpinner, categorySpinner;
    TodoCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        setHasOptionsMenu(true);
        adapter = InboxFragment.adapter;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.full_screen_dialog, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        toolbar.setTitle("My Dialog");
        toolbar.inflateMenu(R.menu.add_todo_menu);
        toolbar.setOnMenuItemClickListener(this);

        btnOpenTimePicker = view.findViewById(R.id.btnTimePicker);
        btnOpenDatePicker = view.findViewById(R.id.btnDatePicker);
        etTaskName = view.findViewById(R.id.editTextTodoName);

        btnOpenTimePicker.setOnClickListener(this);
        btnOpenDatePicker.setOnClickListener(this);

        categorySpinner = view.findViewById(R.id.spinnerCategory);
        prioritySpinner = view.findViewById(R.id.spinnerPriority);

        initSpinners(prioritySpinner, categorySpinner);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    @TargetApi(17)
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnTimePicker:
                DialogFragment time = new TimePicker();
                time.show(getChildFragmentManager(), "Time picker");

                break;
            case R.id.btnDatePicker:
                DialogFragment date = new DatePicker();
                date.show(getChildFragmentManager(), "Date picker");
                break;
        }
    }



    @TargetApi(23)
    private void initSpinners(Spinner prioritySpinner, Spinner catSpinner){

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SettingsActivity.COLOR_PREFERENCES, Context.MODE_PRIVATE);

        ArrayList<String> priorityOptions = new ArrayList<>();
        priorityOptions.add(sharedPreferences.getString("priorityName1", "Urgent"));
        priorityOptions.add(sharedPreferences.getString("priorityName2", "Do later"));
        priorityOptions.add(sharedPreferences.getString("priorityName3", "When you have time"));

        ArrayList<String> categoryOptions = new ArrayList<>();
        categoryOptions.add(sharedPreferences.getString("categoryName1", "School"));
        categoryOptions.add(sharedPreferences.getString("categoryName2", "Errands"));
        categoryOptions.add(sharedPreferences.getString("categoryName3", "Personal"));
        categoryOptions.add(sharedPreferences.getString("categoryName4", "Other"));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,priorityOptions);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setSelection(0);

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,categoryOptions);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setSelection(0);
    }


    @TargetApi(23)
    private void insertTask(){

        TodoDBHelper mDBHelper = new TodoDBHelper(getContext());
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        String  name = etTaskName.getText().toString().trim();
        String dueDate = btnOpenDatePicker.getText().toString();
        String time = btnOpenTimePicker.getText().toString();
        String priority = String.valueOf(prioritySpinner.getSelectedItemPosition()+1);
        String category = String.valueOf(categorySpinner.getSelectedItemPosition()+1);

        Log.v(getTag(), "Category is " + category);
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoEntry.COLUMN_TODO_NAME, name);
        contentValues.put(TodoEntry.COLUMN_TODO_DATE, dueDate);
        contentValues.put(TodoEntry.COLUMN_TODO_TIME, time);
        contentValues.put(TodoEntry.COLUMN_TODO_CATEGORY, category);
        contentValues.put(TodoEntry.COLUMN_TODO_PRIORITY, priority);
        contentValues.put(TodoEntry.COLUMN_TODO_STATUS, "not done");

        Uri newUri = getContext().getContentResolver().insert(TodoEntry.CONTENT_URI, contentValues);

        if (newUri == null) {
            Toast.makeText(getContext(), "Error, try again", Toast.LENGTH_SHORT).show();

        } else {
            dismiss();
            Toast.makeText(getContext(), "Task Added!", Toast.LENGTH_SHORT).show();
        }
        adapter.swapCursor(InboxFragment.getAllItems());


    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        insertTask();
        return false;
    }
}
