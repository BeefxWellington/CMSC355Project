package com.example.my2cents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class AnalyticsLog extends Fragment {

    View v;
    SearchView searchView;
    ListView listView;

    String[] month;
    String[] day;
    String[] title;
    String[] category;
    String[] type;
    String[] amount;
    String[] balance;
    ArrayList<Log> logList;
    ArrayList<Log> filteredLog;

    String singleMonth;
    String singleDay;
    String singleTitle;
    String singleCategory;
    String singleType;
    String singleAmount;
    String singleBalance;

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

        searchView = v.findViewById(R.id.logSearch);
        listView = v.findViewById(R.id.logListView);

        //addLogList();
        //setLogList();
        //searchLog();

        // Inflate the layout for this fragment
        return v;
    }

//    public void setLogListValues(String singleMonth, String singleDay, String singleTitle, String singleCategory, String singleType, String singleAmount, String singleBalance) {
//        this.singleMonth = singleMonth;
//        this.singleDay = singleDay;
//        this.singleTitle = singleTitle;
//        this.singleCategory = singleCategory;
//        this.singleType = singleType;
//        this.singleAmount = singleAmount;
//        this.singleBalance = singleBalance;
//    }

    public void addLogList() {
        logList = new ArrayList<>();
        Log log = new Log(singleMonth, singleDay, singleTitle, singleCategory, singleType, singleAmount, singleBalance);
        logList.add(log);
        LogListAdapter adapter = new LogListAdapter(this.getContext(), R.layout.analytics_log_list, logList);
        listView.setAdapter(adapter);
    }


    public void setLogList(String[] currentMonth, String[] currentDay, String[] title, String[] subCat, String[] mainCat, String[] currentAmount, String[] currentBalance)  {

//        month = currentMonth;
//        day = new String[]{"18", "21", "28", "03", "11", "19", "23", "29", "05", "13"};
//        title = new String[]{"Book", "Shirt", "Paycheck", "Netflix", "Gas", "Eggs", "Electricity", "Water", "Sneakers", "Paycheck"};
//        category = new String[]{"Others", "Clothing", "Income", "Others", "Transportation", "Grocery", "Utilities", "Utilities", "Clothing", "Income"};
//        type = new String[]{"Deduction", "Deduction", "Incoming", "Deduction", "Deduction", "Deduction", "Deduction", "Deduction", "Deduction", "Incoming"};
//        amount = new double[]{15.50, 32.25, 800, 15.99, 32.75, 3.75, 145.50, 75.90, 45.75, 800};
//        balance = new double[]{784.5, 752.25, 1552.25, 1536.26, 1503.51, 1499.76, 1354.26, 1278.36, 1232.61, 2032.61};

        month = currentMonth;
        day = currentDay;
        title = title;
        category = mainCat;
        type = subCat;
        amount = currentAmount;
        balance = currentBalance;

        reverseStringArray(month, month.length);
        reverseStringArray(day, day.length);
        reverseStringArray(title, title.length);
        reverseStringArray(category, category.length);
        reverseStringArray(type, type.length);
        reverseStringArray(amount, amount.length);
        reverseStringArray(balance, balance.length);

        logList = new ArrayList<>();

        for (int i = 0; i < 1; i++){
            @SuppressLint("DefaultLocale") Log log = new Log(month[i], day[i], title[i], category[i], type[i], amount[i], balance[i]);
            logList.add(log);
        }

        LogListAdapter adapter = new LogListAdapter(this.getContext(), R.layout.analytics_log_list, logList);
        listView.setAdapter(adapter);
        searchLog();
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
}