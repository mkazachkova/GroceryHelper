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
}
