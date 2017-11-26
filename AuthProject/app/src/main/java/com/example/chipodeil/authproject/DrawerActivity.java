package com.example.chipodeil.authproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView username;
    User current;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LayoutInflater factory = getLayoutInflater();
        final View headerView = factory.inflate(R.layout.nav_header_drawer, null);
        username = (TextView)headerView.findViewById(R.id.userName);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("UsersData.realm")
                .build();
        realm = Realm.getInstance(config);
        Intent intent = this.getIntent();
        Bundle serializedObj = intent.getExtras();
        current = (User) serializedObj.getSerializable("data");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.notes) {
            Toast.makeText(this, "notes", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.setting) {
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.logout){
            setUnActive(current.getLogin());
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<User> getAllUsers(){
        realm.beginTransaction();
        RealmQuery<User> query = realm.where(User.class);
        RealmResults<User> result = query.findAll();
        ArrayList<User> userList =(ArrayList<User>)realm.copyFromRealm(result);
        realm.commitTransaction();
        return userList;
    }
    public void setUnActive(String login){
        realm.beginTransaction();
        User user = realm.where(User.class).equalTo("login", login).findFirst();
        user.setActive(false);
        realm.commitTransaction();
    }
}
