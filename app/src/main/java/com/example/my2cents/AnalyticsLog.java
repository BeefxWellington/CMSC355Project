package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class AnalyticsLog extends Fragment {

    View v;
    ListView listView;


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
        setLogList();

        // Inflate the layout for this fragment
        return v;
    }

    private void setLogList() {
        int logCount = 20;
        ArrayList<Log> logList = new ArrayList<>();

        for (int i = 1; i <= logCount; i++){
            Log log = new Log("OCT", "19", "Shirt", "Clothing", "Deduction", "34.00", "154.00");
            logList.add(log);
        }

        LogListAdapter adapter = new LogListAdapter(this.getContext(), R.layout.analytics_log_list, logList);
        listView.setAdapter(adapter);
    }


}