package com.example.chipodeil.authproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.io.Serializable;
import java.security.MessageDigest;
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

public class LoginFragment extends Fragment{

    EditText login;
    EditText pass;
    Button signIn;
    Button signUp;
    Fragment regFragment;
    View view;
    Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment,
                container, false);
        Realm.init(view.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("UsersData.realm")
                .build();
        realm = Realm.getInstance(config);
        signIn = (Button)view.findViewById(R.id.sign_in_Login);
        signUp= (Button)view.findViewById(R.id.sign_up_Login);
        login = (EditText)view.findViewById(R.id.login_Login);
        pass = (EditText)view.findViewById(R.id.password_Login);
        final FragmentManager fragmentManager = getFragmentManager();
        regFragment = new RegFragment();
        RxView.clicks(signIn).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            validateData();
        });
        RxView.clicks(signUp).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            fragmentManager.beginTransaction().hide(LoginFragment.this).add(R.id.fragmentPlacement, regFragment).commit();
        });
        return view;
    }

    public void validateData(){
        boolean cancel = false;
        String loginText = login.getText().toString();
        String passText = pass.getText().toString();
        if(passText.length() < 6) {
            pass.setError(getString(R.string.short_error));
            cancel = true;
        }
        if(loginText.length() < 6) {
            login.setError(getString(R.string.short_error));
            cancel = true;
        }
        if(!cancel){
            try {
                passText = Holder.encodePass(passText);
                realm.beginTransaction();
                RealmQuery<User> query = realm.where(User.class);
                RealmResults<User> result = query.findAll();
                ArrayList<User> userList =(ArrayList<User>)realm.copyFromRealm(result);
                realm.commitTransaction();
                for(User u : userList){
                    if(u.getLogin().equals(loginText)){
                        if(!u.getPassword().equals(passText))
                            pass.setError(getString(R.string.wrong_password_error));
                        u.setActive(true);
                        realm.beginTransaction();
                        User user = realm.where(User.class).equalTo("login", u.getLogin()).findFirst();
                        user.setActive(true);
                        realm.commitTransaction();
                        Intent intent = new Intent(view.getContext(), DrawerActivity.class);
                        Bundle serializedObj = new Bundle();
                        serializedObj.putSerializable("data", u);
                        intent.putExtras(serializedObj);
                        startActivity(intent);
                    }
                }
                login.setError(getString(R.string.user_not_exists_error));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }

    }
}
