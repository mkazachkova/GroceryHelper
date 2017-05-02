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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        // Inflate the layout for this fragment
//        View view =  inflater.inflate(R.layout.receipts_main_fragment, container, false);
//        getActivity().setTitle("My Receipts");
//        return view;
//    }

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
                //System.out.println("should open phone camera");
                //TODO: this was working earlier (open default android phone camera)
                //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(intent);

//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivity(cameraIntent);
//                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            }
        });

        //dbAdapt = DriveLogDBAdapter.getInstance(getActivity().getApplicationContext());
        //dbAdapt.open();

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

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.out.println("inside onActivityResult");
//
//        if (requestCode == CAMERA_PIC_REQUEST) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//
//            //get uri from the bitmap
//            Uri tempURI = data.getData();
//            System.out.println(tempURI);
//            //Uri tempURI = getImageUri(context, photo);
//
//            //get actual path
//            //File finalFile = new File(getRealPathFromURI(tempURI));
//            //System.out.println(finalFile);
//            System.out.println("hello world");
//            //ImageView imageview = (ImageView) rootView.findViewById(R.id.ImageView01);
//            //imageview.setImageBitmap(image);
//        }
//    }

    //TODO: this method is not used.
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }

//    public String getRealPathFromURI(Uri uri) {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//        return cursor.getString(idx);
//    }


//    public String getRealPathFromURI(Uri contentUri) {
//        try {
//            String[] proj = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } catch (Exception e) {
//            return contentUri.getPath();
//        }
//    }


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


        //for (int i = 0; i < amount.length; i++) {
            //Receipt temp = new Receipt(amount[i], date[i]);
            //rList.add(temp);
        //}
        return rList;
    }





    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Content Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.
    }

//    // Called at the start of the active lifetime.
//    @Override
//    public void onResume(){
//        super.onResume();
//        Log.d ("Content Fragment", "onResume");
//        // Resume any paused UI updates, threads, or processes required
//        // by the Fragment but suspended when it became inactive.
//    }

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
}
