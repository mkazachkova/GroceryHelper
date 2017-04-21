package com.example.grocery;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Kiki on 4/21/17.
 */

public class ReceiptImage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_image_fragment); //this is not used for fragment

        setTitle("Receipt");

        Button delete = (Button)findViewById(R.id.btn_delete);
        Button save = (Button)findViewById(R.id.btn_save);


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

}
