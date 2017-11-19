package com.example.chipodeil.hookahfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<CoffeeShops> items;
    Adapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("CoffeeShops.realm")
                .build();
        final Realm realm = Realm.getInstance(config);
        App.getApi().getCoffeeShops().enqueue(new Callback<List<CoffeeShops>>() {
            @Override
            public void onResponse(Call<List<CoffeeShops>> call, Response<List<CoffeeShops>> response) {
                items = new ArrayList<>();
                items.addAll(response.body());
                adapter = new Adapter(items, MainActivity.this, new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, FullViewActivity.class);
                        CoffeeShops selectedItem = items.get(position);
                        Bundle serializedObj = new Bundle();
                        serializedObj.putSerializable("data", selectedItem);
                        intent.putExtras(serializedObj);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(items);
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<List<CoffeeShops>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Проверьте ваше подключение к интернету", Toast.LENGTH_SHORT).show();
                realm.beginTransaction();
                RealmQuery<CoffeeShops> query = realm.where(CoffeeShops.class);
                RealmResults<CoffeeShops> result = query.findAll();
                items = (ArrayList<CoffeeShops>)realm.copyFromRealm(result);
                realm.commitTransaction();
                adapter = new Adapter(items, MainActivity.this, new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, FullViewActivity.class);
                        CoffeeShops selectedItem = items.get(position);
                        Bundle serializedObj = new Bundle();
                        serializedObj.putSerializable("data", selectedItem);
                        intent.putExtras(serializedObj);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });
    }


}
