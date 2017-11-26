package com.example.chipodeil.authproject;

import android.app.Fragment;
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
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by chipodeil on 26.11.2017.
 */

public class PinFragment extends Fragment{

    Realm realm;
    Button pinButton;
    EditText pin;
    User current;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pin_fragment,
                container, false);
        Realm.init(view.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("UsersData.realm")
                .build();
        realm = Realm.getInstance(config);
        pin = view.findViewById(R.id.pin_Pin);
        pinButton = view.findViewById(R.id.submit_Pin);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            current = (User)bundle.getSerializable("user");
        }
        RxView.clicks(pinButton).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            validateData();
        });
        return view;
    }

    public void validateData(){
        boolean cancel = false;
        String pinText = pin.getText().toString();
        if(pinText.length() != 4){
            pin.setError(getString(R.string.pin_incorrect_error));
            cancel = true;
        }
        if(current == null){
            Toast.makeText(view.getContext(), "Это странно", Toast.LENGTH_SHORT).show();
            cancel = true;
        }
        if(!cancel){
            try {
                pinText = Holder.encodePass(pinText);
                if(pinText.equals(current.getPin())){
                    goToAttract(view);
                }else{
                    pin.setError(getString(R.string.pin_wrong_error));
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
    public void goToAttract(View v)
    {
        Intent intent = new Intent(getActivity().getApplicationContext(), DrawerActivity.class);
        Bundle serializedObj = new Bundle();
        serializedObj.putSerializable("data", current);
        intent.putExtras(serializedObj);
        getActivity().startActivity(intent);
    }


}
