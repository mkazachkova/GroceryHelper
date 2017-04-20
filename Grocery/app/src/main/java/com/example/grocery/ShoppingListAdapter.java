package com.example.grocery;

/**
 * Created by mariyakazachkova on 4/17/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem>{

    int res;

    public ShoppingListAdapter(Context ctx, int res, List<ShoppingItem> items)  {
        super(ctx, res, items);
        this.res = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout shoppingListView;
        ShoppingItem item = getItem(position);

        if (convertView == null) {
            shoppingListView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(res, shoppingListView, true);
        } else {
            shoppingListView = (LinearLayout) convertView;
        }

        TextView itemName = (TextView) shoppingListView.findViewById(R.id.item_name);
        ImageView foodType = (ImageView) shoppingListView.findViewById(R.id.food_image);

        itemName.setText(item.getName());

       // String date = dLog.getDate();
        // float hours = dLog.getHours();
        //String road = dLog.getRoadType();
        //String weather = dLog.getWeather();

   //     dateHours.setText(date + " - " + String.format("%.2f", hours) + " hours");

        switch (item.getName()) {
            case "Apples":
                foodType.setImageResource(R.drawable.apple_red_icon);
                break;
            case "Watermelon":
                foodType.setImageResource(R.drawable.watermelon_icon);
                break;
            case "Lemon":
                foodType.setImageResource(R.drawable.lemon_icon);
                break;
            case "Milk":
                foodType.setImageResource(R.drawable.milk_icon);
                break;
            case "Cheese":
                foodType.setImageResource(R.drawable.cheese_2_icon);
                break;
            default:
                break;
        }

      /*  switch (weather) {
            case "Clear":
                weatherImage.setImageResource(R.drawable.sun);
                break;
            case "Raining":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "Snow/Ice":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            default:
                break;
        }*/

        return shoppingListView;
    }

}
