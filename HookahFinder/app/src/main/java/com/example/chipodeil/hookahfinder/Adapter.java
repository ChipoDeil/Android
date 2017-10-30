package com.example.chipodeil.hookahfinder;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chipodeil on 29.10.2017.
 */

public class Adapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<CoffeeShops> items;

    Adapter(Context ctx, ArrayList<CoffeeShops> items){
        context = ctx;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
            view = inflater.inflate(R.layout.listitem, parent, false);
        CoffeeShops currentItem =  (CoffeeShops) getItem(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView updated = (TextView) view.findViewById(R.id.updated);
        TextView averagePrice = (TextView) view.findViewById(R.id.averagePrice);
        title.setText(currentItem.getName());
        updated.setText("Обновлено: " + currentItem.getUpdated_at());
        averagePrice.setText("Средний чек: "+currentItem.getAverage_bill());
        return view;
    }

}
