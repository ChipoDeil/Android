package com.example.chipodeil.hookahfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CoffeeShops> items;
    Adapter adapter;
    ListView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getApi().getCoffeeShops().enqueue(new Callback<List<CoffeeShops>>() {
            @Override
            public void onResponse(Call<List<CoffeeShops>> call, Response<List<CoffeeShops>> response) {
                items = new ArrayList<>();
                items.addAll(response.body());
                adapter = new Adapter(MainActivity.this, items);
                mainView = (ListView)findViewById(R.id.mainLV);
                mainView.setAdapter(adapter);
                mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, FullViewActivity.class);
                        CoffeeShops selectedItem = items.get(position);
                        Bundle serializedObj = new Bundle();
                        serializedObj.putSerializable("data", selectedItem);
                        intent.putExtras(serializedObj);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<CoffeeShops>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Проверьте ваше подключение к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
