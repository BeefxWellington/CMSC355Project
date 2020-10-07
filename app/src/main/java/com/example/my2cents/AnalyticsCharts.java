package com.example.my2cents;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class AnalyticsCharts extends Fragment {

    View v;
    PieChart pieChart;
    BarChart barChart;
    LineChart lineChart;
    ScrollView scrollView;

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

        setPieChart();
        setBarChart();
        setLineChart();


        // Inflate the layout for this fragment
        return v;
    }


    public void setPieChart() {
        pieChart = v.findViewById(R.id.pieChart);

        ArrayList<PieEntry> categories = new ArrayList<>();
        categories.add(new PieEntry(15.3f, "Food"));
        categories.add(new PieEntry(8.2f, "Clothing"));
        categories.add(new PieEntry(19.4f, "Grocery"));
        categories.add(new PieEntry(5.8f, "Transportation"));
        categories.add(new PieEntry(17.2f, "Utilities"));
        categories.add(new PieEntry(28.6f, "Rent"));
        categories.add(new PieEntry(5.5f, "Others"));

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
        barDataSet.setValueTextColor(Color.DKGRAY);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animate();
    }

    public void setLineChart(){
        lineChart = v.findViewById(R.id.lineChart);
    }




}