package com.example.android.memo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 21poonkw1 on 30/7/2019.
 */

public class TabTemplate extends Fragment implements RecyclerAdapter.ItemClickListener{


    TextView tvTitle;
    RecyclerView recyclerView;
    private String mTitle;
    private ArrayList<String> mArrayList;
    RecyclerAdapter adapter;

    public TabTemplate(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString("title", "No value");

        mArrayList = new ArrayList<>();

        String[] strings = getArguments().getStringArray("array");
        for (int i = 0; i < strings.length; i++){
            mArrayList.add(strings[i]);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_template, container, false);
        tvTitle = (TextView) rootView.findViewById(R.id.titleText);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        tvTitle.setText(mTitle);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter(getContext(), mArrayList);
        adapter.setClickListener(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public static TabTemplate newInstance(String title, String[] array) {
        
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArray("array", array);
        TabTemplate fragment = new TabTemplate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


}
