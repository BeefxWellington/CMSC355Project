package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;

public class analytics extends Fragment {

    private static analytics analyticsObject;
    View v;
    ViewPager viewPager;
    TabLayout tabLayout;
    AnalyticsAdapter adapter;

    DatabaseReference userRef;
    passingModel passingmodel;
    private DateFormat df;
    private String date;
    Timestamp timeStamp;
    String dbAmount;
    String dbMainCat;
    String dbSubCat;
    final private ArrayList<passingModel> testList = new ArrayList<>();

    public analytics(){
        //Required empty public constructor
        if (analyticsObject != null) {
            try {
                throw new Exception("test home message");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
        String UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";
        String ID = dbRef.push().getKey();

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID).child("AccountEntry");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                testList.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    dbAmount = datasnapshot.child("amount").getValue(String.class);
                    dbMainCat = datasnapshot.child("mainCategories").getValue(String.class);
                    dbSubCat = datasnapshot.child("subCategories").getValue(String.class);
                    passingmodel = new passingModel(dbMainCat, dbSubCat, dbAmount, timeStamp);
                    testList.add(passingmodel);
                }
                //listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        adapter.addFragment(AnalyticsLog.getInstance(), "Activity Log");

        viewPager.setAdapter(adapter);
    }

    public ArrayList<passingModel> getTestList() {
        return testList;
    }

    public static analytics getInstance() {
        if (analyticsObject == null) {
            analyticsObject = new analytics();
        }
        return analyticsObject;
    }
}
