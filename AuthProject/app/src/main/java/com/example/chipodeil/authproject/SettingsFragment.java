package com.example.chipodeil.authproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

/**
 * Created by chipodeil on 27.11.2017.
 */

public class SettingsFragment extends Fragment{

    View view;
    RadioButton randomize;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_fragment,
                container, false);
        randomize = (RadioButton)view.findViewById(R.id.randomize);
        if(Holder.shouldRandomize)
            randomize.setChecked(true);
        randomize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Holder.shouldRandomize = true;
                    randomize.setChecked(true);
                }else{
                    Holder.shouldRandomize = false;
                    randomize.setChecked(false);
                }
            }
        });
        return view;
    }


}
