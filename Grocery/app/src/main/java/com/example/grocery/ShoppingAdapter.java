package com.example.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mariyakazachkova on 4/27/17.
 */

public class ShoppingAdapter extends ArrayAdapter<ShoppingItem> {
    int resource;

    public ShoppingAdapter(Context ctx, int res, List<ShoppingItem> items)
    {
        super(ctx, res, items);
        resource = res;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        ShoppingItem it = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView nameView = (TextView) itemView.findViewById(R.id.item_name);
       // TextView quantityView = (TextView) itemView.findViewById(R.id.detail);
       // TextView reminderDaysView = (TextView) itemView.findViewById(R.id.detail);

        nameView.setText(it.getName());


        return itemView;
    }
}
