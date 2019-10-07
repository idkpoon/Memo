package com.example.android.memo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.android.memo.database.TodoCursorAdapter;

/**
 * Created by 21poonkw1 on 7/10/2019.
 */

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private TodoCursorAdapter mAdapter;

    public SwipeToDeleteCallback(TodoCursorAdapter adapter) {
        super(0, ItemTouchHelper.RIGHT );
        mAdapter = adapter;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }
}
