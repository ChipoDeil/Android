package com.example.chipodeil.hookahfinder;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chipodeil on 29.10.2017.
 */

public class Adapter extends RecyclerView.Adapter<ViewHolder>{
    Context context;
    ArrayList<CoffeeShops> items;
    final ItemClickListener itemClickListener;

    Adapter(ArrayList<CoffeeShops> items, Context ctx, ItemClickListener itemClickL){
        this.items = items;
        context = ctx;
        itemClickListener = itemClickL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CoffeeShops current = items.get(position);
        holder.defineHolder(current);
        holder.bindListener(itemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public TextView descr;
    public TextView dateView;
    CoffeeShops current;
    public ViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.title);
        descr = (TextView)itemView.findViewById(R.id.description);
        dateView = (TextView)itemView.findViewById(R.id.date);
    }
    public void defineHolder(CoffeeShops coffeeShops){
        current = coffeeShops;
        title.setText(current.getName());
        descr.setText(current.getDescription());
        String unformattedDate = current.getUpdated_at();
        String date = unformattedDate.substring(0, unformattedDate.indexOf('T'));
        Date dateCurrent = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCurrent);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.split("-")[2]));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1])-1);
        calendar.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(dateCurrent);
        currentCalendar.set(Calendar.HOUR, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 0);
        long diff = currentCalendar.getTimeInMillis() - calendar.getTimeInMillis();
        long days = diff / 1000 / 60 / 60 / 24;
        dateView.setText(days+" days ago");
    }
    public void bindListener(final ItemClickListener listener, final int position){
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                listener.onClick(v, position);
            }
        });
    }
}