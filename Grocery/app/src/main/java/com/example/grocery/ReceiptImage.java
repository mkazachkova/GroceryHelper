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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

/**
 * Created by Kiki on 4/21/17.
 */

public class ReceiptImage extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private Calendar cal;
    protected EditText dateText, amountText;
    protected String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_image); //this is not used for fragment

        setTitle("Receipt");

        //MainActivity.rdbAdapt.clear();


        Button cancel = (Button)findViewById(R.id.btn_delete);
        Button save = (Button)findViewById(R.id.btn_save);
        amountText = (EditText)findViewById(R.id.total);

        //TODO: change this from accessing from database??
        imageView = (ImageView) findViewById(R.id.receipt_pic);

        uri = "android.resource://com.example.grocery/drawable/select_image.png";
        //bmap = Uri.parse("android.resource://com.example.grocery/drawable/select_image.png");
        //System.out.println(bmap);

        //imageView.setVisibility(View.INVISIBLE);
        //System.out.println("receipt image should be invisible");

        //set default Date (get date)
        dateText = (EditText) findViewById(R.id.date);
        resetView(); //set amount, date to empty
        cal = Calendar.getInstance();
        dateText.setText(String.format("%02d", cal.get(Calendar.MONTH) + 1) + "/" +
                String.format("%02d",cal.get(Calendar.DAY_OF_MONTH)) + "/" + cal.get(Calendar.YEAR));

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Context context = ReceiptImage.this;
                CharSequence text = "Cancel Pressed!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                finish(); //go back to prev activity
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                Context context = ReceiptImage.this;
                CharSequence text = "Save pressed!";
                int duration = Toast.LENGTH_SHORT;

                Long milliseconds = cal.getTimeInMillis(); //date

                //convert dateText back to Long
                SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date d = f.parse(dateText.getText().toString());
                    milliseconds = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long d = cal.getTimeInMillis(); //date
                if (!amountText.getText().toString().isEmpty()) {
                    System.out.println("amountText.getText() is not null");
                Float amt = Float.parseFloat(amountText.getText().toString()); //amount

                //TODO: add imageURI
                Receipt receipt = new Receipt(amt, milliseconds, uri); //save user's curr time if db not found?
                MainActivity.rdbAdapt.insertReceipt(receipt);
                    System.out.println("after insert receipt");
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent, REQUEST_CODE);
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
                System.out.println("after imageView setImage");
                //TODO: also need to setImageBitmap for the receipt instance.
                System.out.println(data.getData());
                System.out.println("after data.getData()");
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

}
