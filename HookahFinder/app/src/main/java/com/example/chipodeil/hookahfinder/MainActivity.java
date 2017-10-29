package com.example.chipodeil.hookahfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> items;
    String[] titles;
    Adapter adapter;
    ListView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createItems();
        adapter = new Adapter(this, items);
        mainView = (ListView)findViewById(R.id.mainLV);
        mainView.setAdapter(adapter);
        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FullView.class);
                Item selectedItem = items.get(position);
                Bundle serializedObj = new Bundle();
                serializedObj.putSerializable("data", selectedItem);
                intent.putExtras(serializedObj);
                startActivity(intent);
            }
        });
    }

    private void createItems(){
        items = new ArrayList<>();
        titles = new String[]{"Голубая лагуна", "Очень крутое заведение", "Лига разработчиков",
                                "У Макинтоша(халимоша)", "У Бори", "фыдлвофлдоывр"};
        for (int i = 0; i < titles.length; i++){
            boolean opened = (i % 2 ==  0);
            items.add(new Item(titles[i], i*100, "some description lol 3333333333333333333333333", opened, 5.0f));
        }
    }
}
