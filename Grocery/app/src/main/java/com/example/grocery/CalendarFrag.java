package com.example.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Date;

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

        View view = inflater.inflate(R.layout.statistics_calendar_fragment, container, false);

        CaldroidFragment cfragment = new CaldroidFragment();
        Bundle args = new Bundle();
        args.putInt( CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY );
        args.putString(CaldroidFragment.DIALOG_TITLE, "Shopping History");
        cfragment.setArguments( args );
        //dummy vars
        Date first = new Date(117,3,20);
        Date second = new Date(117,3,16);
        Date third = new Date(117,3,8);
        Date fourth = new Date(117,2,28);

        cfragment.setTextColorForDate(R.color.colorPrimary, first);
        cfragment.setTextColorForDate(R.color.colorPrimary, second);
        cfragment.setTextColorForDate(R.color.colorPrimary, third);
        cfragment.setTextColorForDate(R.color.colorPrimary, fourth);

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                //dummy date
                if(date.equals(new Date(117,3,20)) || date.equals(new Date(117,3,16)) ||
                        date.equals(new Date(117,3,8)) || date.equals(new Date(117,2,28))) {
                    Intent intent = new Intent(getActivity(), ReceiptImage.class);
                    startActivity(intent);
                }
            }

        };


        cfragment.setCaldroidListener(listener);
        Toast toast = Toast.makeText(getContext(), "Press Highlighted dates to see shopping activities for the day!", Toast.LENGTH_LONG);
        toast.show();

        getActivity().getSupportFragmentManager().beginTransaction().replace( R.id.container_caldroid , cfragment ).commit();

        return view;
    }
}
