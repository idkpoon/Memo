package com.example.android.memo.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.memo.R;
import com.example.android.memo.database.TodoContract.TodoEntry;
import com.example.android.memo.database.TodoDBHelper;

public class AddTodoActivity extends AppCompatActivity {

    EditText etTaskName;
    private android.support.v7.widget.Toolbar mToolbar;
    TextView mTitle;
    Button btnDatePicker;
    Button btnTimePicker;
    private Typeface futuraFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        futuraFont = Typeface.createFromAsset(this.getAssets(), "Fonts/Futura-Medium.ttf");

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_menu);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(futuraFont);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        etTaskName = (EditText)findViewById(R.id.editTextTodoName);
        btnDatePicker = (Button)findViewById(R.id.btnDatePicker);
        btnTimePicker = (Button)findViewById(R.id.btnTimePicker);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_todo_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }









}
