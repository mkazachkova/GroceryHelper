package com.example.grocery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by JSK on 4/18/17.
 */

public class CalendarFrag extends Fragment {


    protected static ArrayList<Date> dateArrayList;
    private int temp;
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

        temp = -1;
        View view = inflater.inflate(R.layout.statistics_calendar_fragment, container, false);

        CaldroidFragment cfragment = new CaldroidFragment();
        Bundle args = new Bundle();
        args.putInt( CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY );
        args.putString(CaldroidFragment.DIALOG_TITLE, "Shopping History");
        cfragment.setArguments( args );

        dateArrayList = new ArrayList<Date>();
        Cursor cursor = MainActivity.rdbAdapt.getAllReceipts();
        if (cursor.moveToFirst())
            do {
                Receipt result = new Receipt(cursor.getFloat(1), cursor.getLong(2), cursor.getString(3));
                dateArrayList.add(new Date(result.getDate())); //puts in reverse order
            } while (cursor.moveToNext());
        cursor.close();

        for(int i = 0; i < dateArrayList.size(); i ++ ) {
            cfragment.setTextColorForDate(R.color.colorPrimary, dateArrayList.get(i));
        }



        Toast toast = Toast.makeText(getContext(), "Press Highlighted dates to see shopping activities for the day!", Toast.LENGTH_LONG);
        toast.show();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {

                System.out.println("date pressed: "+date.getDate()+ " , "+dateArrayList.contains(date));
                for(int i = 0; i <dateArrayList.size(); i++) {
                    if(dateArrayList.get(i).getYear() == date.getYear() &&
                            dateArrayList.get(i).getMonth() == date.getMonth() &&
                            dateArrayList.get(i).getDate() == date.getDate()) {
                        temp = dateArrayList.size() - (i  + 1);
                    }
                }

                if(temp >= 0) { //if there exists shopping
                    MainActivity.rcurrID = temp;
                    Intent intent = new Intent(getActivity(), EditReceiptImage.class);
                    startActivity(intent);
                    temp = -1;
                } else {
                    Toast t = Toast.makeText(getContext(), "No shopping event that day!", Toast.LENGTH_LONG);
                }
            }

        };
        cfragment.setCaldroidListener(listener);

        getActivity().getSupportFragmentManager().beginTransaction().replace( R.id.container_caldroid , cfragment ).commit();

        return view;
    }
}
