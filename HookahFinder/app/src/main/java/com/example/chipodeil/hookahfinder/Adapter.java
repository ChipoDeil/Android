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
    ArrayList<Item> items;

    Adapter(Context ctx, ArrayList<Item> items){
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
        Item currentItem =  (Item) getItem(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView status =(TextView) view.findViewById(R.id.opened);
        TextView rating = (TextView) view.findViewById(R.id.rating);
        TextView averagePrice = (TextView) view.findViewById(R.id.averagePrice);
        title.setText(currentItem.title);
        if(currentItem.opened) {
            status.setText("Открыто");
            status.setTextColor(Color.GREEN);
        }else{
            status.setText("Закрыто");
            status.setTextColor(Color.RED);
        }
        rating.setText("Рейтинг: "+currentItem.rating);
        averagePrice.setText("Средний чек: "+currentItem.averagePrice);
        return view;
    }

}
