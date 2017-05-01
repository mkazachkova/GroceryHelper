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
import java.util.Calendar;

/**
 * Created by Kiki on 4/21/17.
 */

public class ReceiptImage extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private Calendar cal;
    protected EditText dateText, amountText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_image); //this is not used for fragment

        setTitle("Receipt");

        Button cancel = (Button)findViewById(R.id.btn_delete);
        Button save = (Button)findViewById(R.id.btn_save);
        amountText = (EditText)findViewById(R.id.total);

        //TODO: change this from accessing from database??
        imageView = (ImageView) findViewById(R.id.receipt_pic);
        imageView.setVisibility(View.INVISIBLE);
        System.out.println("receipt image should be invisible");

        //set Date (get date)
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

            }
        });

        //TODO: save changes to database
        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                Context context = ReceiptImage.this;
                CharSequence text = "Save pressed!";
                int duration = Toast.LENGTH_SHORT;

                Long d = cal.getTimeInMillis(); //date
                Float f = Float.parseFloat(amountText.getText().toString()); //amount
                System.out.println("F: "+f);
                System.out.println("D: "+d);

                //TODO: imageURI
                Receipt receipt = new Receipt(f, d);
                MainActivity.rdbAdapt.insertReceipt(receipt);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });

    }

    public void resetView(){
        amountText.setText("");
        dateText.setText("");
        //TODO: remove image
    }

    public void onClick(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
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

                //TODO: also need to setImageBitmap for the receipt instance.
                System.out.println(data.getData());

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
