package com.example.chipodeil.authproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by chipodeil on 26.11.2017.
 */

public class RegFragment extends Fragment{

    EditText login;
    EditText pass;
    EditText pin;
    Button submitReg;
    Realm realm;
    View view;
    LoginFragment loginFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_fragment,
                container, false);
        Realm.init(view.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("UsersData.realm")
                .build();
        loginFragment = new LoginFragment();
        realm = Realm.getInstance(config);
        submitReg = view.findViewById(R.id.submit_reg);
        login = (EditText)view.findViewById(R.id.login_Reg);
        pass = (EditText)view.findViewById(R.id.pass_Reg);
        pin = (EditText)view.findViewById(R.id.pin_Reg);
        RxView.clicks(submitReg).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            validateData();
        });

        return view;
    }

    public void validateData(){
        boolean cancel = false;
        String loginText = login.getText().toString();
        String passText = pass.getText().toString();
        String pinText = pin.getText().toString();
        if(passText.length() < 6) {
            pass.setError(getString(R.string.short_error));
            cancel = true;
        }
        if(loginText.length() < 6) {
            login.setError(getString(R.string.short_error));
            cancel = true;
        }
        if(pinText.length() != 4) {
            pin.setError(getString(R.string.pin_incorrect_error));
            cancel = true;
        }
        if(doesUserExists(loginText)){
            login.setError(getString(R.string.user_already_exists_error));
            cancel = true;
        }

        if(!cancel){
            try {
                passText = Holder.encodePass(passText);
                pinText = Holder.encodePass(pinText);
                User newUser = new User();
                newUser.setActive(true);
                newUser.setLogin(loginText);
                newUser.setPassword(passText);
                newUser.setPin(pinText);
                realm.beginTransaction();
                realm.insert(newUser);
                realm.commitTransaction();
                Intent intent = new Intent(view.getContext(), DrawerActivity.class);
                Bundle serializedObj = new Bundle();
                serializedObj.putSerializable("data", newUser);
                intent.putExtras(serializedObj);
                startActivity(intent);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean doesUserExists(String login){
        realm.beginTransaction();
        RealmQuery<User> query = realm.where(User.class);
        RealmResults<User> result = query.findAll();
        ArrayList<User> userList =(ArrayList<User>)realm.copyFromRealm(result);
        realm.commitTransaction();
        for(User u : userList){
            Log.d("lol", u.getLogin());
            if(u.getLogin().equals(login))
                return true;
        }
        return false;
    }
}
