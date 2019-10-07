package com.example.android.memo.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.memo.Activity.MainActivity;
import com.example.android.memo.R;
import com.example.android.memo.TabTemplate;
import com.example.android.memo.Task;
import com.example.android.memo.database.TodoContract.TodoEntry;
import com.example.android.memo.database.TodoDBHelper;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int currentPosition;
    private String COLOUR_PREFERENCES;
    TodoDBHelper mDBHelper;
    ViewPagerAdapter adapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        COLOUR_PREFERENCES = "ColorPreferences";

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

        return rootView;
    }

    @Override
    @TargetApi(21)
    public void onResume() {
        super.onResume();
        MainActivity.getmToolbar().setElevation(0.0f);
    }

    @Override
    @TargetApi(21)
    public void onStart() {
        super.onStart();
        MainActivity.getmToolbar().setElevation(0.0f);
    }

    private void setupViewPager(ViewPager viewPager) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(COLOUR_PREFERENCES, Context.MODE_PRIVATE);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        String[] defaultNames = {"School", "Errands", "Personal", "Other"};

        for(int i = 1; i <=4; i++){
            String catName = sharedPreferences.getString("categoryName" + Integer.toString(i), defaultNames[i-1]);
            adapter.addFragment(TabTemplate.newInstance(i), catName);
        }

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.currentPosition = position+1;
        TabTemplate tabTemplate = (TabTemplate) adapter.mFragmentList.get(position);

        List<Task> listItems = tabTemplate.getList();
        Log.v(getClass().getSimpleName(), "Current Position from onPageSelected() : "
                + Integer.toString(currentPosition));
        Log.v(getTag(), "Cursor count: " + listItems.size());


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private String TAG = getClass().getSimpleName();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.v(TAG, "Index from getItem(): " + Integer.toString(position));
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
