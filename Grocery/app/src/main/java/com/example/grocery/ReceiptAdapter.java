package com.example.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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


//        TextView dateHours = (TextView) logView.findViewById(R.i);
//        ImageView roadTypeImage = (ImageView) logView.findViewById(R.id.road_image);
//        ImageView weatherImage = (ImageView) logView.findViewById(R.id.weather_image);

        //convert date (long) back to string
        String date = new Date(receipt.getDate()).toString();
        receiptDate.setText(date);
        amount.setText(String.format("%.2f", receipt.getAmount()));

        //TODO: setReceiptImage (replace the receipt placeholder)
        //int resID=getResources().getIdentifier(picID,"id",getPackageName()); //find image file
        //receiptImage.setImageResource();

        return logView;
    }

}
