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

import java.util.ArrayList;


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
    ArrayList<Float> categoryAmount;
    float amount;

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

        UserID = "pgnjJooFMAdnARk2LqV8pOFxGjs2";
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID).child("Account Entry");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    type = dataSnapshot.child("mainCategories").getValue(String.class);
                    category = dataSnapshot.child("subCategories").getValue(String.class);
                    amount = (float) (dataSnapshot.child("amount").getValue());
                    if (type.equals("Expense")){
                        calculateTotalCategoryExpenses(category, amount);
                        setPieChart();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        setBarChart();
        setLineChart();

        // Inflate the layout for this fragment
        return v;
    }

    public void calculateTotalCategoryExpenses(String category, float amount){
        categoryAmount = new ArrayList<>(categoryList.size());
        for (int i = 0; i < categoryList.size(); i++){
            if (category.equals(categoryList.get(i))){
                categoryAmount.set(i, categoryAmount.get(i) + amount);
                break;
            }
            else {
                i++;
            }
        }
    }

    public void setCategoryList() {
        categoryList = new ArrayList<>();
        categoryList.add("Bills");
        categoryList.add("Food");
        categoryList.add("Gas");
        categoryList.add("Entertainment");
        categoryList.add("Paycheck");
        categoryList.add("Savings");
        categoryList.add("Miscellaneous");
        categoryList.add("Lottery");
    }


    public void setPieChart() {
        pieChart = v.findViewById(R.id.pieChart);
        float totalExpenses = 0;
        for (int i = 0; i < categoryAmount.size(); i++){
            totalExpenses += categoryAmount.get(i);
        }



        ArrayList<PieEntry> categories = new ArrayList<>();
        if (totalExpenses != 0){
            for (int i = 0; i < categoryList.size(); i++) {
                categories.add(new PieEntry((categoryAmount.get(i) / totalExpenses), categoryList.get(i)));
            }
        }
        /*
        categories.add(new PieEntry(15.3f, "Food"));
        categories.add(new PieEntry(8.2f, "Clothing"));
        categories.add(new PieEntry(19.4f, "Grocery"));
        categories.add(new PieEntry(5.8f, "Transportation"));
        categories.add(new PieEntry(17.2f, "Utilities"));
        categories.add(new PieEntry(28.6f, "Rent"));
        categories.add(new PieEntry(5.5f, "Others"));

         */

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

        ArrayList<BarEntry> months = new ArrayList<>();
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

        BarDataSet barDataSet = new BarDataSet(months, "Months");
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