package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    ViewPager viewPager;
    HomeAdapter adapter;
    List<HomeModel> models;
    TabLayout tabLayout;
    View v;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Set info to display for card view
        setModels();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container,false);

        //Create and set adapter for pager
        adapter = new HomeAdapter(models, this.getContext());
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        //Connect dots indicator to pager
        tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);

        return v;
    }

    public void setModels(){

        models = new ArrayList<>();

        //Add temporary strings
        models.add(new HomeModel("Month Summary",
                "Starting Balance", "Total Expenses", "Upcoming Deductions",
                "$000.00", "$000.00", "3",
                null, null, null));

        models.add(new HomeModel("Recent Expenses",
                "Title - Category", "Title - Category", "Title - Category",
                "$000.00","$000.00","$000.00",
                "MM/DD/YYYY","MM/DD/YYYY","MM/DD/YYYY"));

        models.add(new HomeModel("Upcoming Deductions",
                "Title - Category", "Title - Category", "Title - Category",
                "$000.00","$000.00","$000.00",
                "MM/DD/YYYY","MM/DD/YYYY","MM/DD/YYYY"));
    }
}