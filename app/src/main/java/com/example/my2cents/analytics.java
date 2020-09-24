package com.example.my2cents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link analytics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class analytics extends Fragment {

    private AnyChartView anyChartView;
    private String[] months = {"Jan", "Feb", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private int[] expenditures = {20, 50, 100, 500, 1000, 1200, 1500, 50, 180, 200, 70, 350};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public analytics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment analytics.
     */
    // TODO: Rename and change types and number of parameters
    public static analytics newInstance(String param1, String param2) {
        analytics fragment = new analytics();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_analytics, container, false);

        anyChartView = v.findViewById(R.id.piechart);
        setupPieChart();

        return v;
    }

    public void setupPieChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i=0;i < months.length; i++) {
            dataEntries.add(new ValueDataEntry(months[i], expenditures[i]));
        }
        pie.data(dataEntries);
        anyChartView.setBackgroundColor("#ffffff"); //sets LOADING background color
        pie.background().fill("#ffffff");
        pie.background("#ffffff");

        pie.normal().outline().enabled(true);
        pie.normal().outline().width("5%");
        pie.hovered().outline().width("10%");
        pie.selected().outline().width("3");
        pie.selected().outline().fill("#455a64");
        pie.selected().outline().stroke(null);
        pie.selected().outline().offset(2);

        anyChartView.setChart(pie);
    }
}