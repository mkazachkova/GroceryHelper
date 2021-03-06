package com.example.grocery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kiki on 4/20/17. Array Adapter receipt
 */

public class ReceiptAdapter extends ArrayAdapter<Receipt> {

    int res; //what is this for??
    //TextView textTargetUri;
    //ImageView targetImage;

    public ReceiptAdapter(Context ctx, int res, List<Receipt> items)  {
        super(ctx, res, items);
        this.res = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout logView;
        Receipt receipt = getItem(position); //dLog

        if (convertView == null) {
            logView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(res, logView, true);
        } else {
            logView = (LinearLayout) convertView;
        }

        TextView amount = (TextView) logView.findViewById(R.id.receipt_amt);
        TextView receiptDate = (TextView) logView.findViewById(R.id.receipt_date);
        ImageView receiptImage = (ImageView) logView.findViewById(R.id.receipt_image);
        receiptImage.setVisibility(View.INVISIBLE);

        ImageView edit = (ImageView)logView.findViewById(R.id.receipt_edit);
        ImageView delete = (ImageView)logView.findViewById(R.id.receipt_delete);


        //convert date (long) back to string
        Long d = receipt.getDate();

        String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date(d));

        //String date = new Date(d).toString();
        receiptDate.setText(date);
        amount.setText(String.format("%.2f", receipt.getAmount()));

        //TODO: setReceiptImage (replace the receipt placeholder)
        //int resID=getResources().getIdentifier(picID,"id",getPackageName()); //find image file
        //receiptImage.setImageResource();

        //TODO: get uri

//        InputStream stream = getContentResolver().openInputStream(receipt.getUri());
//        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        //receiptImage.setImageBitmap(bitmap);

        if (receipt.getUri() != null) {
            System.out.println("receipt uri is not nulll");
            receiptImage.setImageURI(Uri.parse(receipt.getUri()));
            receiptImage.setVisibility(View.VISIBLE);
        }
        return logView;
    }


}
