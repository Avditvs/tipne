package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;
import android.widget.TextView;




public class LearningMotsActivity extends Activity{


    private TextView text;
    AppDataBase db = AppDataBase.getAppDatabase(LearningMotsActivity.this);
    ListesDAO dbListes = db.ListesDAO();
    MotsDAO dbMots = db.MotsDao();
    int idList = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_mots_activity);
        text = findViewById(R.id.test);


        Intent i = getIntent();
        String nomListe = i.getStringExtra(ChooseListActivity.nameList);
        idList = dbListes.idDeListe(nomListe);
        String[] motsEN = dbMots.getEN(idList);
        String[] motsFR = dbMots.getFR(idList);
        text.setText(motsEN[1]);


    }


}
