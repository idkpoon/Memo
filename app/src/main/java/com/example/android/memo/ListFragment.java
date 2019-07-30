package com.example.android.memo;

import android.annotation.TargetApi;
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

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int currentPosition;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

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

        String[] oneList = {"Catherine of Aragon", "Anne Boleyn", "Jane Seymour", "Anne of Cleves", "Katherine Howard", "Catherine Howard"};
        String[] twoList = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
        String[] threeList = {"Cookies", "Muffins", "Cupcakes", "Cake", "Banana Bread", "Tiramisu", "Ice cream"};

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(TabTemplate.newInstance("One", oneList), "ONE");
        adapter.addFragment(TabTemplate.newInstance("Two", twoList), "TWO");
        adapter.addFragment(TabTemplate.newInstance("Three", threeList), "THREE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.currentPosition = position;
        Log.v(getClass().getSimpleName(), "Current Position from onPageSelected() : " + currentPosition);
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
