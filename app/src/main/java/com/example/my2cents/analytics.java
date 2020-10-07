package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class analytics extends Fragment {

    View v;
    ViewPager viewPager;
    TabLayout tabLayout;
    AnalyticsAdapter adapter;

    public analytics(){
        //Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_analytics, container, false);

        viewPager = v.findViewById(R.id.analyticsPager);
        tabLayout = v.findViewById(R.id.analyticsTab);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        adapter = new AnalyticsAdapter(getChildFragmentManager());

        adapter.addFragment(new AnalyticsCharts(), "Charts");
        adapter.addFragment(new AnalyticsRecent(), "Recent");
        adapter.addFragment(new AnalyticsUpcoming(), "Upcoming");

        viewPager.setAdapter(adapter);
    }
}
