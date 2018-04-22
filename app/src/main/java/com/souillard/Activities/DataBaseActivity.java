package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.DataBaseBuilder;
import com.souillard.BasesDeDonnées.listes.Listes;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.mots.Mots;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;


public class DataBaseActivity  extends Activity {

    private boolean dbIsLoaded = false;
    private Intent intent = null;
    private TextView text;

    public void onCreate (Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);
        threadDb.start();
        text = findViewById(R.id.affdb);
        intent = new Intent(this, MainActivity.class);

    }

    private Thread threadDb = new Thread(new Runnable() {
        @Override
        public void run() {

            DataBaseBuilder dataBaseBuilder = new DataBaseBuilder(getApplicationContext());
            dataBaseBuilder.buildDataBase();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            dbIsLoaded = true;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("Chargement terminé, appuyez pour continuer");
                }
            });

        }
    });


    public void onScreenClick(View view) {
        if(dbIsLoaded){
            startActivity(intent);
        }
        else{
            Log.i("Base", "Pas encore chargée");
        }
    }
}
