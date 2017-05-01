package com.example.grocery;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kiki on 5/1/17.
 */

public class EditReceiptImage extends ReceiptImage {

    private ImageView img;
    private int currPos;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hello");
        super.onCreate(savedInstanceState);
        setTitle("Edit Receipt");

        currPos = MainActivity.rcurrID;
        cursor = MainActivity.rdbAdapt.getAllReceipts();
        cursor.move(cursor.getCount() - currPos);
        populateData(cursor.getLong(0));

    }
    public void populateData(long id) {
        Cursor cursor = MainActivity.rdbAdapt.getReceiptCursor(id);
        Long l = cursor.getLong(2); //get date from database (long form)
        //String date = new Date(l).toString();
        String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date(l)); //String date = new Date(l).toString(); //convert date (long) back to string
        dateText.setText(date);
        amountText.setText(String.format("%.2f", cursor.getFloat(1)));
        //TODO:finish populating (image)
        img = (ImageView)findViewById(R.id.receipt_pic);
        img.setVisibility(View.VISIBLE);
    }

}
