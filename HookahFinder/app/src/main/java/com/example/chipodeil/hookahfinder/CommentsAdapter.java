package com.example.chipodeil.hookahfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chipodeil on 19.11.2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<ViewCommentHolder>{
    Context context;
    ArrayList<String> items;
    final ItemClickListener itemClickListener;

    CommentsAdapter(ArrayList<String> items, Context ctx, ItemClickListener itemClickL){
        this.items = items;
        context = ctx;
        itemClickListener = itemClickL;
    }

    @Override
    public ViewCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comments_item, parent, false);
        return new ViewCommentHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewCommentHolder holder, int position) {
        String current = items.get(position);
        holder.defineHolder(current);
        holder.bindListener(itemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class ViewCommentHolder extends RecyclerView.ViewHolder{
    public TextView title;
    String current;
    public ViewCommentHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.commentText);
    }
    public void defineHolder(String comment){
        current = comment;
        title.setText(current);
    }
    public void bindListener(final ItemClickListener listener, final int position){
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                listener.onClick(v, position);
            }
        });
    }
}
