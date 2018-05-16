package com.souillard.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.souillard.R;

public class ParamsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Switch yearSwitch;
    View aboutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);
        sharedPreferences = getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE);
        yearSwitch = findViewById(R.id.ychooseswitch);
        aboutView = findViewById(R.id.about);
        if(sharedPreferences.getInt("user_year", 1)==2){
            yearSwitch.setChecked(true);
        }
        else{
            yearSwitch.setChecked(false);
        }

        yearSwitch.setOnCheckedChangeListener(yearListener);


    }

    CompoundButton.OnCheckedChangeListener yearListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //TODO Ajouter l'intent de demander si'il veut vraiment changer
        }
    };

}
