package com.example.chipodeil.authproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class AuthActivity extends AppCompatActivity{

    LoginFragment loginFragment;
    RegFragment regFragment;
    PinFragment pinFragment;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("UsersData.realm")
                .build();
        realm = Realm.getInstance(config);
        loginFragment = new LoginFragment();
        pinFragment = new PinFragment();
        FragmentManager fragmentManager = getFragmentManager();
        User current = getActiveUser();
        if(current == null)
        {
            fragmentManager.beginTransaction().add(R.id.fragmentPlacement, loginFragment).commit();
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", current);
            pinFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.fragmentPlacement, pinFragment).commit();
        }
    }

    public User getActiveUser(){
        realm.beginTransaction();
        RealmQuery<User> query = realm.where(User.class);
        RealmResults<User> result = query.findAll();
        ArrayList<User> userList =(ArrayList<User>)realm.copyFromRealm(result);
        realm.commitTransaction();
        int i = 0;
        User returning = null;
        for(User u : userList){
            if(u.isActive())
                returning = u;
            i++;
        }
        Log.d("123", i+"");
        return returning;
    }



}

