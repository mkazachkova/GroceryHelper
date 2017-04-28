package com.example.grocery;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mariyakazachkova on 4/16/17.
 */

public class ContentExpirationFrag extends Fragment {

    private ListView expirationListView;
    protected static ArrayList<ShoppingItem> myItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list,container, false);
        getActivity().setTitle("My Expiration Reminders");

        final FragmentActivity act = this.getActivity();

        expirationListView = (ListView) view.findViewById(R.id.itemsList);

       // populateItems();

        ExpirationListAdapter listViewAdapter = new ExpirationListAdapter(
                getActivity(),
                R.layout.single_expiration_item,
                myItems
        );


        expirationListView.setAdapter(listViewAdapter);

        expirationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =  getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                dialogBuilder.setView(dialogView);

                // final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

                Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                delete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = getActivity();
                        CharSequence text = "Delete Pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

                TextView quantity = (TextView) dialogView.findViewById(R.id.textView);
                quantity.setVisibility(View.GONE);


                Button save = (Button) dialogView.findViewById(R.id.btn_save);
                save.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = getActivity();
                        CharSequence text = "Save pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });


                Spinner quantitySpinner = (Spinner) dialogView.findViewById(R.id.quantity_spinner);
                quantitySpinner.setVisibility(View.GONE);

                /*final ArrayAdapter<CharSequence> quanAdapter = ArrayAdapter.createFromResource(act,
                        R.array.quantityTypes,android.R.layout.simple_spinner_item);
                quanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                quantitySpinner.setAdapter(quanAdapter);
                quantitySpinner.setSelection(0);*/



                Spinner reminderSpinner = (Spinner) dialogView.findViewById(R.id.reminder_spinner);

                final ArrayAdapter<CharSequence> remindAdapter = ArrayAdapter.createFromResource(act,
                        R.array.reminderTypes,android.R.layout.simple_spinner_item);
                remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                reminderSpinner.setAdapter(remindAdapter);
                reminderSpinner.setSelection(3);


                AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });

        return view;
    }
    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Content Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.
    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        Log.d ("Content Fragment", "onResume");
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.
    }

    // Called at the end of the active lifetime.
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
/*
    public void populateItems() {
        ShoppingItem one = new ShoppingItem("Chicken breast", 1, 3);
        ShoppingItem two = new ShoppingItem("Salmon", 1, 2);
        ShoppingItem three = new ShoppingItem("Peppers", 1, 7);
        ShoppingItem four = new ShoppingItem("Shrimp", 1, 7);


        myItems = new ArrayList<ShoppingItem>();
        myItems.add(one);
        myItems.add(three);
        myItems.add(two);
        myItems.add(four);

    }*/
}
