package com.example.grocery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by JSK on 4/18/17.
 */

public class GraphFrag extends Fragment {
    public static GraphFrag newInstance() {
        GraphFrag fragment = new GraphFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_graph_fragment, container, false);

        BarChart chart = (BarChart) view.findViewById(R.id.bar_chart);
        BarDataSet set1 = new BarDataSet(getDataSet(), "Amount in $");
        set1.setColor(R.color.colorNav);
        BarData data = new BarData(getXAxisValues(), set1);
        chart.setData(data);
        chart.setDescription("4 Recent Shopping History");
        chart.animateXY(2000, 2000);

        return view;
    }

    private ArrayList<BarEntry> getDataSet() {

        ArrayList<BarEntry> set = new ArrayList<>();
        set.add(new BarEntry((45.50f), 0));
        set.add(new BarEntry((53.50f), 1));
        set.add(new BarEntry((28.32f), 2));
        set.add(new BarEntry((33.18f), 3));

        BarDataSet barDataSet1 = new BarDataSet(set, "Amount in $");
        barDataSet1.setColor(R.color.colorNav);

        return set;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Mar 28th 2017");
        xAxis.add("Apr 8th 2017");
        xAxis.add("Apr 16th 2017");
        xAxis.add("Apr 20th 2017");
        return xAxis;
    }
}