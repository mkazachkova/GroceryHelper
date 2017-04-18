package com.example.grocery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JSK on 4/18/17.
 */

public class CalendarFrag extends Fragment {
    public static CalendarFrag newInstance() {
        CalendarFrag fragment = new CalendarFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_calendar_fragment, container, false);
    }
}
