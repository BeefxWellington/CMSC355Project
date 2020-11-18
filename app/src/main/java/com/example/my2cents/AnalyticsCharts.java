package com.example.my2cents;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;


public class AnalyticsCharts extends Fragment {

    View v;
    PieChart pieChart;
    BarChart barChart;
    ScrollView scrollView;
    TextView barGraphTitle;
    Calendar calendar;
    String currentMonth;
    int currentYear;
    DatabaseReference databaseReference;
    DatabaseReference userRef;
    String UserID;
    String type;
    String category;
    double amount;
    double totalExpenses;
    String dayNum, month, year;
    ArrayList<String> categoryList;
    ArrayList<Double> categoryAmount;
    ArrayList<PieEntry> categories;
    DateFormatSymbols dateFormatSymbols;
    ArrayList<String> monthList;
    ArrayList<Double> monthAmount;
    ArrayList<BarEntry> monthBar;
    String[] months;

    public AnalyticsCharts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.analytics_charts, container, false);
        scrollView = v.findViewById(R.id.chartScroll);
        barGraphTitle = v.findViewById(R.id.barGraphTitle);

        calendar = Calendar.getInstance();
        currentMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        currentYear = calendar.get(Calendar.YEAR);
        barGraphTitle.setText("Monthly Expenses in " + currentYear);

        setCategoryList();
        setMonthList();

        UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID).child("AccountEntry");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearCharts();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    type = snapshot.child("mainCategories").getValue(String.class);
                    category = snapshot.child("subCategories").getValue(String.class);
                    amount = Double.parseDouble(snapshot.child("amount").getValue(String.class));
                    dayNum = snapshot.child("timeStamp").child("dayNum").getValue(String.class);
                    month = snapshot.child("timeStamp").child("month").getValue(String.class);
                    year = snapshot.child("timeStamp").child("year").getValue(String.class);
                    if (type.equals("Expense")){
                        if (year.equals(Integer.toString(currentYear))) {
                            calculateTotalCategoryExpenses(category, amount);
                            calculateTotalMonthExpenses(monthList, month, amount);
                        }
                        else {
                            calculateTotalCategoryExpenses(category, amount);
                        }
                    }
                }
                setPieChart();
                setBarChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public void setCategoryList() {
        categoryList = new ArrayList<>();
        categoryList.add("Bills");
        categoryList.add("Food");
        categoryList.add("Gas");
        categoryList.add("Entertainment");
        categoryList.add("Pay Check");
        categoryList.add("Savings");
        categoryList.add("Miscellaneous");
        categoryList.add("Lottery");

        categories = new ArrayList<>();
        categoryAmount = new ArrayList<>(categoryList.size());
        for (int i = 0; i < categoryList.size(); i++){
            categoryAmount.add((double) 0);
        }
    }

    public void calculateTotalCategoryExpenses(String category, double amount){
        for (int i = 0; i < categoryList.size(); i++){
            if (category.equals(categoryList.get(i))){
                categoryAmount.set(i, categoryAmount.get(i) + amount);
                break;
            }
        }

        totalExpenses = 0;
        for (int i = 0; i < categoryAmount.size(); i++){
            totalExpenses += categoryAmount.get(i);
        }
    }

    public void setMonthList(){
        dateFormatSymbols = new DateFormatSymbols();
        months = dateFormatSymbols.getMonths();
        monthList = new ArrayList<>();
        monthList.addAll(Arrays.asList(months));

        monthBar = new ArrayList<>();
        monthAmount = new ArrayList<>(monthList.size());
        for (int i = 0; i < monthList.size(); i++){
            monthAmount.add((double) 0);
        }
    }

    public void calculateTotalMonthExpenses(ArrayList<String> monthList, String month, double amount){
        for (int i = 0; i < monthList.size(); i++) {
            if (month.equals(monthList.get(i).substring(0, 3))) {
                    monthAmount.set(i, monthAmount.get(i) + amount);
                    break;
            }
        }

    }

    public void setPieChart() {
        pieChart = v.findViewById(R.id.pieChart);

        if (totalExpenses != 0){
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryAmount.get(i) != 0) {
                    categories.add(new PieEntry((float) (categoryAmount.get(i) / totalExpenses) * 100, categoryList.get(i)));
                }
            }
        }

        PieDataSet pieDataSet = new PieDataSet(categories, "Categories");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieDataSet.setValueTextColor(Color.DKGRAY);
        pieDataSet.setValueTextSize(15f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Categories");
        pieChart.animate();
        pieChart.invalidate();
    }

    public void setBarChart(){
        barChart = v.findViewById(R.id.barChart);

        for (int i = 0; i < monthList.size(); i++) {
            monthBar.add(new BarEntry(i + 1, (float) (monthAmount.get(i) * 1)));
        }

        BarDataSet barDataSet = new BarDataSet(monthBar, "Months");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animate();
        barChart.invalidate();
    }

    public void clearCharts(){
        categoryList.clear();
        categoryAmount.clear();
        categories.clear();
        monthList.clear();
        monthAmount.clear();
        monthBar.clear();

        setCategoryList();
        setMonthList();
    }

}