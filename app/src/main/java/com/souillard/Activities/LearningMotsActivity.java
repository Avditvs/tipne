package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import android.view.View.OnClickListener;




public class LearningMotsActivity extends Activity{


    AppDataBase db = AppDataBase.getAppDatabase(LearningMotsActivity.this);
    ListesDAO dbListes = db.ListesDAO();
    MotsDAO dbMots = db.MotsDao();
    public TextView textListe;
    public TextView textMot;
    public TextView textFr;
    public TextView textEn;
    public int motActuel = 0;
    public int nbDeMots = 0;
    Button clickGauche = null;
    Button clickDroite = null;
    public String[] wordsEN = null;
    public String[] wordsFR = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_mots_activity);

        textListe = findViewById(R.id.nomListe);
        textMot = findViewById(R.id.numeroMot);
        textEn = findViewById(R.id.motEN);
        textFr = findViewById(R.id.motFR);
        clickGauche = (Button) findViewById(R.id.clickGauche);
        clickDroite = (Button) findViewById(R.id.clickDroite);

        int idList = getIdList();

        textListe.setText(dbListes.getNameOfList(idList));
        nbDeMots = dbListes.getNbWords(idList);

        wordsEN = getENwords(idList);
        wordsFR = getFRwords(idList);

        textMot.setText("Mot 1 sur " + nbDeMots);
        textEn.setText(wordsEN[motActuel]);
        textFr.setText(wordsFR[motActuel]);

        clickGauche.setOnClickListener(clickListenerGauche);
        clickDroite.setOnClickListener(clickListenerDroite);

    }


    public int getIdList(){
        Intent i = getIntent();
        String nomListe = i.getStringExtra(ChooseListActivity.nameList);
        int idDeList = dbListes.idDeListe(nomListe);
        return idDeList;
    }

    public String[] getENwords(int idList){
        String[] motsEN = dbMots.getEN(idList);
        return motsEN;
    }

    public String[] getFRwords(int idList){
        String[] motsFR = dbMots.getFR(idList);
        return motsFR;
    }

    public void onClickGauche (String[] wordsEN, String[] wordsFR, int nbDeMots, int motActuel){
        if (motActuel > 0) {
            motActuel--;
            textEn.setText(wordsEN[motActuel]);
            textFr.setText(wordsFR[motActuel]);
            textMot.setText("Mot " + (motActuel+1) + " sur " + nbDeMots);

        }

    }

    private OnClickListener clickListenerGauche = new OnClickListener(){
        @Override
        public void onClick (View v) {
            if (motActuel > 0) {
                motActuel--;
                textEn.setText(wordsEN[motActuel]);
                textFr.setText(wordsFR[motActuel]);
                textMot.setText("Mot " + (motActuel+1) + " sur " + nbDeMots);
            }
        }
    };

    private OnClickListener clickListenerDroite = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (motActuel < nbDeMots) {
                motActuel++;
                textEn.setText(wordsEN[motActuel]);
                textFr.setText(wordsFR[motActuel]);
                textMot.setText("Mot " + (motActuel+1) + " sur " + nbDeMots);
            }
        }
    };
}


