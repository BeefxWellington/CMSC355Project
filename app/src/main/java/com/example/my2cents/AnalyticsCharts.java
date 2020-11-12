package com.example.my2cents;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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


public class AnalyticsCharts extends Fragment {

    View v;
    PieChart pieChart;
    BarChart barChart;
    LineChart lineChart;
    ScrollView scrollView;
    DatabaseReference databaseReference;
    DatabaseReference userRef;
    String UserID;
    String type;
    String category;
    ArrayList<String> categoryList;
    ArrayList<Double> categoryAmount;
    ArrayList<PieEntry> categories;
    Double amount;
    String dayNum, month, year;
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

        setCategoryList();
        setMonthList();

        UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID).child("AccountEntry");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    type = snapshot.child("mainCategories").getValue(String.class);
                    category = snapshot.child("subCategories").getValue(String.class);
                    amount = Double.parseDouble(snapshot.child("amount").getValue(String.class));
                    dayNum = snapshot.child("timeStamp").child("dayNum").getValue(String.class);
                    month = snapshot.child("timeStamp").child("month").getValue(String.class);
                    year = snapshot.child("timeStamp").child("year").getValue(String.class);
                    if (type.equals("Expense")){
                        calculateTotalCategoryExpenses(category, amount);
                        calculateTotalMonthExpenses(monthList, month, year, amount);
                    }
                }
                setPieChart();
                setBarChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        setLineChart();

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

    public void calculateTotalMonthExpenses(ArrayList<String> monthList, String month, String year, double amount){
        for (int i = 0; i < monthList.size(); i++){
            if (month.equals(monthList.get(i).substring(0,3))){
                monthAmount.set(i, monthAmount.get(i) + amount);
                break;
            }
        }
    }

    public void setPieChart() {
        pieChart = v.findViewById(R.id.pieChart);
        double totalExpenses = 0;
        for (int i = 0; i < categoryAmount.size(); i++){
            totalExpenses += categoryAmount.get(i);
        }

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
    }

    public void setBarChart(){
        barChart = v.findViewById(R.id.barChart);

        for (int i = 0; i < monthList.size(); i++) {
            monthBar.add(new BarEntry(i + 1, (float) (monthAmount.get(i) * 1)));
        }

        /*
        months.add(new BarEntry(1, 1300));
        months.add(new BarEntry(2, 1200));
        months.add(new BarEntry(3, 1350));
        months.add(new BarEntry(4, 1500));
        months.add(new BarEntry(5, 1400));
        months.add(new BarEntry(6, 1100));
        months.add(new BarEntry(7, 1300));
        months.add(new BarEntry(8, 1250));
        months.add(new BarEntry(9, 1570));
        months.add(new BarEntry(10, 1590));
        months.add(new BarEntry(11, 1310));
        months.add(new BarEntry(12, 1400));
         */

        BarDataSet barDataSet = new BarDataSet(monthBar, "Months");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animate();
    }

    public void setLineChart(){
        lineChart = v.findViewById(R.id.lineChart);

        ArrayList<Entry> weeks = new ArrayList<>();
        weeks.add(new Entry(1, 320));
        weeks.add(new Entry(2, 260));
        weeks.add(new Entry(3, 370));
        weeks.add(new Entry(4, 250));

        LineDataSet lineDataSet = new LineDataSet(weeks, "Weeks");
        lineDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        lineDataSet.setValueTextColor(Color.DKGRAY);
        lineDataSet.setValueTextSize(10f);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.animate();

    }


}