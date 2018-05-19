package com.souillard.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.DataBaseBuilder;
import com.souillard.BasesDeDonnées.evaluations.EvaluationsDAO;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.models.ModelsDAO;
import com.souillard.BasesDeDonnées.models.ModelsDAO_Impl;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.Fragments.ChangeClassFragment;
import com.souillard.R;

public class ParamsActivity extends Activity {

    SharedPreferences sharedPreferences;
    Switch yearSwitch;
    View aboutView;
    ChangeClassFragment changeClassFragment;
    AlertDialog.Builder aDBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);
        sharedPreferences = getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE);
        yearSwitch = findViewById(R.id.ychooseswitch);
        aboutView = findViewById(R.id.about);
        if (sharedPreferences.getInt("user_year", 1) == 2) {
            yearSwitch.setChecked(true);
        } else {
            yearSwitch.setChecked(false);
        }

        yearSwitch.setOnCheckedChangeListener(yearListener);


    }

    CompoundButton.OnCheckedChangeListener yearListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //changeClassFragment = new ChangeClassFragment();
            //changeClassFragment.show(getFragmentManager().beginTransaction(), "change_class");
            aDBuilder = new AlertDialog.Builder(ParamsActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            aDBuilder.setTitle("Attention");
            aDBuilder.setMessage("Voulez vraiment changer de classe ?");
            aDBuilder.setCancelable(true);
            yearSwitch.setChecked(!yearSwitch.isChecked());
            aDBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor spEditor = getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE).edit();
                    int year;
                    if (!yearSwitch.isChecked()) {
                        year = 2;
                    } else {
                        year = 1;
                    }
                    spEditor.putInt("user_year", year);
                    AppDataBase.getAppDatabase(getBaseContext()).ListesDAO().nukeTableListes();
                    AppDataBase.getAppDatabase(getBaseContext()).MotsDao().nukeTableMots();
                    AppDataBase.getAppDatabase(getBaseContext()).ModelsDAO().nukeTableModels();
                    AppDataBase.getAppDatabase(getBaseContext()).EvaluationsDAO().nukeTableEvaluations();
                    Intent intent = new Intent(getBaseContext(), DataBaseActivity.class);
                    spEditor.apply();
                    dialog.cancel();
                    dialog.dismiss();
                    startActivity(intent);
                }
            });

            aDBuilder.show();


        }
    };




}


