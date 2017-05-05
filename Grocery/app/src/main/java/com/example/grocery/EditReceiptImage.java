package com.example.grocery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kiki on 5/1/17.
 */

public class EditReceiptImage extends ReceiptImage {

    private ImageView img;
    private int currPos;
    private Cursor cursor;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hello");
        super.onCreate(savedInstanceState);
        setTitle("Edit Receipt");

        //update text buttons
        Button deleteButton = (Button)findViewById(R.id.btn_delete);
        Button updateButton = (Button)findViewById(R.id.btn_save);
        deleteButton.setText("DELETE");
        updateButton.setText("UPDATE"); //same function as save button in ReceiptImage

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delete();
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                update();
            }
        });

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
        picUri = Uri.parse(cursor.getString(3));
        img = (ImageView)findViewById(R.id.receipt_pic);
        img.setImageURI(picUri);
        img.setVisibility(View.VISIBLE);
        uri = picUri.toString();
    }

    public void delete() {
        //toast message
        Context context = EditReceiptImage.this;
        CharSequence text = "Delete Pressed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
       // toast.show();

        MainActivity.rdbAdapt.removeReceipt(cursor.getLong(0)); //delete from database
        finish(); //go back to prev activity
    }


    public void update() {
        //toast message
        Context context = EditReceiptImage.this;
        CharSequence text = "Save Pressed!!!!";
        int duration = Toast.LENGTH_SHORT;

        Calendar cal = Calendar.getInstance();
        Long milliseconds = cal.getTimeInMillis();


        Date d = null;
        try {
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            d = f.parse(dateText.getText().toString());
            if (!dateText.getText().toString().equals(f.format(d))) {
                d = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d == null) { //invalid date format
            CharSequence text3 = "Please format date as MM/dd/yyyy";
            Toast toast = Toast.makeText(context, text3, duration);
            toast.show();
        } else { //valid date format
            milliseconds = d.getTime();
        }

        if (!amountText.getText().toString().isEmpty()) {
            Float amt = Float.parseFloat(amountText.getText().toString()); //amount

            //String uriString = picUri.toString();
            System.out.println(uri);
            System.out.println("uri should be printed");
            MainActivity.rdbAdapt.updateReceipt(cursor.getLong(0), amt, milliseconds, uri);
            System.out.println("after insert receipt");
            Toast toast = Toast.makeText(context, text, duration);
         //   toast.show();
            finish(); //go back to prev activity
        } else {
            CharSequence text2 = "Please type receipt amount!";
            Toast toast = Toast.makeText(context, text2, duration);
            toast.show();
        }
    }

}
