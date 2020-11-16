package com.example.my2cents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.*;

import java.sql.Ref;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnalyticsLog extends Fragment {

    private static AnalyticsLog analyticsLog;

    View v;
    SearchView searchView;
    ListView listView;

    String[] month;
    String[] day;
    String[] title;
    String[] category;
    String[] type;
    double[] amount;
    double[] balance;
    ArrayList<Log> logList;
    ArrayList<Log> filteredLog;
    LogListAdapter adapter;

    passingModel passingmodel;
    Timestamp timeStamp;
    private ArrayList<passingModel> testList = new ArrayList<>();

    private Home home;
    private ArrayList<passingModel> useList;
    private analytics analyticsObject;

    public AnalyticsLog(){
        if (analyticsLog != null) {
            try {
                throw new Exception("test message");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            analyticsLog = this;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.v = inflater.inflate(R.layout.analytics_log, container, false);

        searchView = v.findViewById(R.id.logSearch);
        listView = v.findViewById(R.id.logListView);

        setLogList();
        searchLog();

        // Inflate the layout for this fragment
        return v;
    }

    public void setTestList(ArrayList<passingModel> newTestList) {
        testList = newTestList;
    }

    public void setBalance(double[] newSetBalance) {
        balance = newSetBalance;
    }

    public void setLogListValues(String[] currentMonth, String[] currentDay, String[] currenttitle, String[] subCat, String[] mainCat, double[] currentAmount, double[] currentBalance) {
        month = currentMonth;
        day = currentDay;
        title = currenttitle;
        category = mainCat;
        type = subCat;
        amount = currentAmount;
        balance = currentBalance;
    }

    private void setLogList()  {

        home = Home.getInstance();
        analyticsObject = analytics.getInstance();
        useList = testList;

        this.logList = new ArrayList<>();
        for (int i = 0; i < (useList.size()-1); i++) {

            passingmodel = useList.get(i);
            timeStamp = passingmodel.getTimeStamp();

            month = new String[]{timeStamp.getMonth()};
            day = new String[]{timeStamp.getDay()};
            title = new String[]{"test"};
            category = new String[]{passingmodel.getMainCategories()};
            type = new String[]{passingmodel.getSubCategories()};
            amount = new double[]{Double.parseDouble(passingmodel.getAmount())};
            //balance = new double[]{100.00};

            reverseStringArray(month, month.length);
            reverseStringArray(day, day.length);
            reverseStringArray(title, title.length);
            reverseStringArray(category, category.length);
            reverseStringArray(type, type.length);
            reverseDoubleArray(amount, amount.length);
            reverseDoubleArray(balance, balance.length);


            Log log = new Log(this.month[0], this.day[0], this.title[0], this.category[0], this.type[0], String.format("%.2f", this.amount[0]), String.format("%.2f", this.balance[0]));
            logList.add(log);
        }
        adapter = new LogListAdapter(this.getContext(), R.layout.analytics_log_list, logList);
        listView.setAdapter(adapter);
    }


    private void searchLog(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                filteredLog = new ArrayList<>();
                for (int i = 0; i < logList.size(); i++){
                    Log log = logList.get(i);
                    if(log.getMonth().toLowerCase().startsWith(s.toLowerCase()) ||
                            log.getTitle().toLowerCase().startsWith(s.toLowerCase()) ||
                            log.getCategory().toLowerCase().startsWith(s.toLowerCase()) ||
                            log.getType().toLowerCase().startsWith(s.toLowerCase())){
                        filteredLog.add(log);
                    }

                }
                LogListAdapter adapter = new LogListAdapter(getContext(), R.layout.analytics_log_list, filteredLog);
                listView.setAdapter(adapter);

                return false;
            }
        });
    }


    private void reverseDoubleArray(double[] array, int length) {
        double temp;
        for (int i = 0; i < length / 2; i++){
            temp = array[i];
            array[i] = array[length - i - 1];
            array[length - i - 1] = temp;
        }
    }


    private void reverseStringArray(String[] array, int length) {
        String temp;
        for (int i = 0; i < length / 2; i++){
            temp = array[i];
            array[i] = array[length - i - 1];
            array[length - i - 1] = temp;
        }
    }

    // Create method to get new balance for log list
    double getBalance(double balance, double amount, String type){
        double newBalance;
        if (type.equals("Incoming")){
            newBalance = balance + amount;
        }
        else {
            newBalance = balance - amount;
        }
        return newBalance;
    }

    public static AnalyticsLog getInstance() {
        if (analyticsLog == null) {
            analyticsLog = new AnalyticsLog();
        }
        return analyticsLog;
    }

}