package com.example.grocery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kiki on 4/21/17.
 */

public class ReceiptImage extends AppCompatActivity {

    protected static final int REQUEST_CODE = 1;
    protected Bitmap bitmap;
    protected ImageView imageView;
    private Calendar cal;
    protected EditText dateText, amountText;
    protected String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_image); //this is not used for fragment

        setTitle("Receipt");


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Button cancel = (Button)findViewById(R.id.btn_delete);
        Button save = (Button)findViewById(R.id.btn_save);
        amountText = (EditText)findViewById(R.id.total);

        //TODO: change this from accessing from database??
        imageView = (ImageView) findViewById(R.id.receipt_pic);
        imageView.getLayoutParams().height = 700;
        imageView.getLayoutParams().width =  600;

        uri = "android.resource://com.example.grocery/drawable/select_image2";
        dateText = (EditText) findViewById(R.id.date);
        resetView(); //set amount, date to empty
        cal = Calendar.getInstance();
        dateText.setText(String.format("%02d", cal.get(Calendar.MONTH) + 1) + "/" +
                String.format("%02d",cal.get(Calendar.DAY_OF_MONTH)) + "/" + cal.get(Calendar.YEAR));

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                finish(); //go back to prev activity
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                Context context = ReceiptImage.this;
                CharSequence text = "Save Pressed!";
                int duration = Toast.LENGTH_SHORT;

                Long milliseconds = cal.getTimeInMillis(); //date


                //convert dateText back to Long
                SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date d = f.parse(dateText.getText().toString());
                    milliseconds = d.getTime(); //user date
                    if (!checkUniqueDate(milliseconds)) { //date already exists
                        CharSequence text4 = "Date already exists!";
                        Toast toast = Toast.makeText(context, text4, duration);
                        toast.show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!amountText.getText().toString().isEmpty()) {
                 //   System.out.println("amountText.getText() is not null");
                    Float amt = Float.parseFloat(amountText.getText().toString()); //amount

                    //TODO: add imageURI
                    Receipt receipt = new Receipt(amt, milliseconds, uri); //save user's curr time if db not found?
                    MainActivity.rdbAdapt.insertReceipt(receipt);
                    System.out.println("after insert receipt");
                    Toast toast = Toast.makeText(context, text, duration);
               //     toast.show();
                    finish(); //go back to prev activity
                } else {
                    System.out.println("amountText.getText() is null");
                    CharSequence text2 = "Please type receipt amount!";
                    Toast toast = Toast.makeText(context, text2, duration);
                    toast.show();
                }

            }
        });

    }

    public void resetView(){
        amountText.setText("");
        dateText.setText("");
        //TODO: remove image
    }

    /* select image from the phone gallery */
    public void onClick(View View) {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                // recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);

                imageView.setImageBitmap(bitmap);
                uri = data.getData().toString(); //uri

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    /* return true if the userDate is not already in the database; false otherwise */
    public boolean checkUniqueDate(Long userDate) {
        Long d = null;
        Calendar c = Calendar.getInstance();
        Cursor cursor = MainActivity.rdbAdapt.getAllReceipts();

        Calendar userC = Calendar.getInstance();
        userC.setTimeInMillis(userDate);

        int uYear = userC.get(Calendar.YEAR);
        int uMonth = userC.get(Calendar.MONTH);
        int uDay = userC.get(Calendar.DAY_OF_MONTH);

            while (cursor.moveToNext()) {
                d = cursor.getLong(2);
                c.setTimeInMillis(d);
                int dYear = c.get(Calendar.YEAR);
                int dMonth = c.get(Calendar.MONTH);
                int dDay = c.get(Calendar.DAY_OF_MONTH);

                if (uYear == dYear && uMonth == dMonth && uDay == dDay) {
                    return false;
                }
            }
            return true;
    }

}
