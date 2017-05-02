package com.example.grocery;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by JSK on 4/18/17.
 */

public class GraphFrag extends Fragment {

    protected static ArrayList<Receipt> receiptItems;
    private int count;
    private String temp;

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
        count = 0;
        View view = inflater.inflate(R.layout.statistics_graph_fragment, container, false);

        receiptItems = new ArrayList<Receipt>();
        Cursor cursor = MainActivity.rdbAdapt.getAllReceipts();
        if (cursor.moveToFirst())
            do {
                Receipt result = new Receipt(cursor.getFloat(1), cursor.getLong(2));
                receiptItems.add(result); //puts in reverse order
                System.out.println("count: "+count);
                count ++;
            } while (cursor.moveToNext() && count <4);
        cursor.close();


        BarChart chart = (BarChart) view.findViewById(R.id.bar_chart);
        BarDataSet set1 = new BarDataSet(getDataSet(), "Amount in $");
        set1.setColor(R.color.colorNav);
        BarData data = new BarData(getXAxisValues(), set1);
        chart.setData(data);
        chart.setNoDataText("No shopping history!");
        chart.setDescription("Recent Shopping History");
        chart.animateXY(2000, 2000);

        return view;
    }

    private ArrayList<BarEntry> getDataSet() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        System.out.println("in dataset");

        ArrayList<BarEntry> set = new ArrayList<>();
        for (int i = 0; i < receiptItems.size(); i++) {
            set.add(new BarEntry(receiptItems.get(i).getAmount(), 3 -i));
            System.out.println("amount: "+receiptItems.get(i).getAmount()+ " , date: "+ dateFormat.format(receiptItems.get(i).getDate()));
        }

        BarDataSet barDataSet1 = new BarDataSet(set, "Amount in $");
        barDataSet1.setColor(R.color.colorNav);

        return set;
    }

    private ArrayList<String> getXAxisValues() {
        System.out.println("in xAxis");
        ArrayList<String> xAxis = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

        for (int i = receiptItems.size(); i > 0; i--) {
            temp = dateFormat.format(receiptItems.get(i-1).getDate());
            System.out.println("amount: "+receiptItems.get(i-1).getAmount()+ " , date: "+ temp);
            xAxis.add(temp);
        }
        return xAxis;
    }
}