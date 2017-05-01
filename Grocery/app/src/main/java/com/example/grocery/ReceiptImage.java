package com.example.grocery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Kiki on 4/21/17.
 */

public class ReceiptImage extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_image); //this is not used for fragment

        setTitle("Receipt");

        Button delete = (Button)findViewById(R.id.btn_delete);
        Button save = (Button)findViewById(R.id.btn_save);


        //TODO: change this from accessing from database
        imageView = (ImageView) findViewById(R.id.receipt_pic);

        //TODO: delete from database
        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
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

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
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


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

}
