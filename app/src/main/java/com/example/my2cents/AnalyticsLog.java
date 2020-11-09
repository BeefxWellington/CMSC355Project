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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnalyticsLog extends Fragment {

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

    DatabaseReference userRef;
    passingModel passingmodel;
    private DateFormat df;
    private String date;
    Timestamp timeStamp;
    String dbAmount;
    String dbMainCat;
    String dbSubCat;
    final private ArrayList<passingModel> testList = new ArrayList<>();

    public AnalyticsLog(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.v = inflater.inflate(R.layout.analytics_log, container, false);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
        searchView = v.findViewById(R.id.logSearch);
        listView = v.findViewById(R.id.logListView);

        df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        date = df.format(Calendar.getInstance().getTime());
        String currentDay = date.substring(0, 3);
        String dayNum = date.substring(5, 6);
        String currentMonth = date.substring(7, 10);
        String year = date.substring(11, 15);
        String hour = date.substring(16, 18);
        String min = date.substring(19, 21);
        String sec = date.substring(22, 24);
        this.timeStamp.setDay(date.substring(0, 3));
        this.timeStamp.setMonth(date.substring(7, 10));

        timeStamp = new Timestamp(currentDay, currentMonth, year, dayNum, hour, min, sec);
        dbAmount = "100.00";
        dbMainCat = "Test";
        dbSubCat = "Test";
        passingmodel = new passingModel(dbMainCat, dbSubCat, dbAmount, this.timeStamp);
        String UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";
        String ID = dbRef.push().getKey();
        userRef.child(UserID).child("AccountEntry").child(ID).setValue(passingmodel);

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID).child("AccountEntry");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                testList.clear();
                for (DataSnapshot datasnapshot1 : snapshot.getChildren()) {
                    dbAmount = datasnapshot1.child("amount").getValue(String.class);
                    dbMainCat = datasnapshot1.child("mainCategories").getValue(String.class);
                    dbSubCat = datasnapshot1.child("subCategories").getValue(String.class);
                    passingmodel = new passingModel(dbMainCat, dbSubCat, dbAmount, timeStamp);
                    testList.add(passingmodel);
                }
                //listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setLogList();
        searchLog();

        // Inflate the layout for this fragment
        return v;
    }

    public void setLogListValues(String[] currentMonth, String[] currentDay, String[] currenttitle, String[] subCat, String[] mainCat, double[] currentAmount, double[] currentBalance) {
        month = currentMonth;
        day = currentDay;
        title = currenttitle;
        category = mainCat;
        type = subCat;
        amount = currentAmount;
        balance = currentBalance;

//        setLogList();
//        searchLog();
    }

    private void setLogList()  {

//        month = new String[]{"AUG", "AUG", "AUG", "SEP", "SEP", "SEP", "SEP", "SEP", "OCT", "OCT"};
//        day = new String[]{"18", "21", "28", "03", "11", "19", "23", "29", "05", "13"};
//        title = new String[]{"Book", "Shirt", "Paycheck", "Netflix", "Gas", "Eggs", "Electricity", "Water", "Sneakers", "Paycheck"};
//        category = new String[]{"Others", "Clothing", "Income", "Others", "Transportation", "Grocery", "Utilities", "Utilities", "Clothing", "Income"};
//        type = new String[]{"Deduction", "Deduction", "Incoming", "Deduction", "Deduction", "Deduction", "Deduction", "Deduction", "Deduction", "Incoming"};
//        amount = new double[]{15.50, 32.25, 800, 15.99, 32.75, 3.75, 145.50, 75.90, 45.75, 800};
//        balance = new double[]{784.5, 752.25, 1552.25, 1536.26, 1503.51, 1499.76, 1354.26, 1278.36, 1232.61, 2032.61};

        passingmodel = testList.get(testList.size()-1);
        timeStamp = passingmodel.getTimeStamp();
        month = new String[]{timeStamp.getMonth()};
        day = new String[]{timeStamp.getDay()};
        title = new String[]{"test"};
        category = new String[]{passingmodel.getMainCategories()};
        type = new String[]{passingmodel.getSubCategories()};
        amount = new double[]{Double.parseDouble(passingmodel.getAmount())};
        amount = new double[]{100.00};

        reverseStringArray(month, month.length);
        reverseStringArray(day, day.length);
        reverseStringArray(title, title.length);
        reverseStringArray(category, category.length);
        reverseStringArray(type, type.length);
        reverseDoubleArray(amount, amount.length);
        reverseDoubleArray(balance, balance.length);

        this.logList = new ArrayList<>();
        for (int i = 0; i < 1; i++){
            Log log = new Log(this.month[i], this.day[i], this.title[i], this.category[i], this.type[i], String.format("%.2f", this.amount[i]), String.format("%.2f", this.balance[i]));
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

}