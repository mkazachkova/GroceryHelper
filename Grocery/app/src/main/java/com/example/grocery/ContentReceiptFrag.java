package com.example.grocery;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mariyakazachkova on 4/16/17.
 */

public class ContentReceiptFrag extends Fragment {

    //private DriveLogDBAdapter dbAdapt;  // ref to our database
    private ListView receiptListView;
    protected static ArrayList<Receipt> receiptItems;
    protected static ReceiptAdapter aa; //array adapter
    private Context context;
    private View rootView;
    FloatingActionButton fab;
    private FragmentTransaction transaction;
    private int CAMERA_PIC_REQUEST;
    //private SharedPreferences myPrefs;


    public ContentReceiptFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.receipts_main_fragment, container, false);

        //Hide main activity floating button
        FloatingActionButton floatingActionButton = ((MainActivity) getActivity()).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.setVisibility(View.INVISIBLE);
            //floatingActionButton.hide();
        }
        //init floating button (take pic)
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_receipt);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReceiptImage.class);
                startActivity(intent);

            }
        });


        receiptListView = (ListView) rootView.findViewById(R.id.receipt_list_view);
        receiptItems = new ArrayList<Receipt>();
        //make array adapter to bind arrayList to ListView with new custom item layout
        aa = new ReceiptAdapter(getActivity(), R.layout.receipt_item, receiptItems);
        receiptListView.setAdapter(aa);
        getActivity().setTitle("Receipts");
        updateArray();

        receiptListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = receiptListView.getItemAtPosition(position);
                System.out.println(listItem);

                MainActivity.rcurrID = position; //update current receipt ID

                Intent intent = new Intent(getActivity(), EditReceiptImage.class);
                startActivity(intent);

            }
        });

        return rootView;
    }


    public void updateArray() {
        //TODO: need to sort database by Date
        Cursor cursor = MainActivity.rdbAdapt.getAllReceipts();
        receiptItems.clear();
        if (cursor.moveToFirst())
            do {
                Receipt result = new Receipt(cursor.getFloat(1), cursor.getLong(2), cursor.getString(3));
                receiptItems.add(0, result); //puts in reverse order
            } while (cursor.moveToNext());
        aa.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("in on Resume of ContentReceiptFrag");
        // Populating fake data - will need to replace with getting actual data from database
        //receiptItems = populateData();

        aa = new ReceiptAdapter(getActivity(), R.layout.receipt_item, receiptItems);
        receiptListView.setAdapter(aa);
        getActivity().setTitle("Receipts");
        updateArray();

    }

    public ArrayList<Receipt> populateData() {
        ArrayList<Receipt> rList = new ArrayList<Receipt>();

        float[] amount = new float[2];
        amount[0] = (float) 15.265;
        amount[1] = (float) 37.5333;

        long[] date = new long[2];

        try {
            String dateString1 = "30/09/2017";
            String dateString2 = "20/04/2017";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse(dateString1);
            Date date2 = sdf.parse(dateString2);

            long d1 = date1.getTime();
            long d2 = date2.getTime();
            date[0] = d1;
            date[1] = d2;
        } catch (Exception e) {
        }


        return rList;
    }





    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Content Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.
    }

    @Override
    public void onPause(){
        Log.d ("Content Fragment", "onPause");
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        super.onPause();
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d ("Content Fragment", "onSaveInstanceState");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        Log.d ("Content Fragment", "onStop");
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
        super.onStop();
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView() {
        Log.d ("Content Fragment", "onDestroyView");
        // Clean up resources related to the View.
        super.onDestroyView();
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        Log.d ("Content Fragment", "onDestroy");
        // Clean up any resources including ending threads,
        // closing database connections etc.
        super.onDestroy();
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach() {
        Log.d ("Content Fragment", "onDetach");
        super.onDetach();
    }
}
