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
 * Created by mariyakazachkova on 4/20/17.
 */

public class ExpirationListAdapter extends ArrayAdapter<ShoppingItem> {

    int res;

    public ExpirationListAdapter(Context ctx, int res, List<ShoppingItem> items)  {
        super(ctx, res, items);
        this.res = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout expirationListView;
        ShoppingItem item = getItem(position);

        if (convertView == null) {
            expirationListView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(res, expirationListView, true);
        } else {
            expirationListView = (LinearLayout) convertView;
        }

        TextView itemName = (TextView) expirationListView.findViewById(R.id.item_name);
        TextView numDays = (TextView) expirationListView.findViewById(R.id.days);

        itemName.setText(item.getName());
        numDays.setText(item.getDays() + "");

        return expirationListView;
    }

}
