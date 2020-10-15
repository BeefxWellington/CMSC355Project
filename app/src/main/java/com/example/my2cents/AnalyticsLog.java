package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class AnalyticsLog extends Fragment {

    View v;
    ListView listView;
    String[] month;
    String[] day;
    String[] title;
    String[] type;
    int[] amount;
    int[] balance;


    public AnalyticsLog(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.analytics_log, container, false);

        listView = v.findViewById(R.id.logListView);

        // Inflate the layout for this fragment
        return v;
    }


}