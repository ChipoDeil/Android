package com.example.chipodeil.hookahfinder;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FullView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);
        Intent intent = this.getIntent();
        Bundle serializedObj = intent.getExtras();
        Item item = (Item)serializedObj.getSerializable("data");
        TextView title = (TextView)findViewById(R.id.title);
        TextView desc = (TextView)findViewById(R.id.desc);
        title.setText("Название: " + item.title);
        desc.setText("Описание: " + item.desc);
    }
}
