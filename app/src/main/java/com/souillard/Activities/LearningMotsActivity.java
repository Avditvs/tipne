package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.speech.tts.TextToSpeech;

import java.util.Locale;


public class LearningMotsActivity extends Activity{


    AppDataBase db = AppDataBase.getAppDatabase(LearningMotsActivity.this);
    ListesDAO dbListes = db.ListesDAO();
    MotsDAO dbMots = db.MotsDao();
    private TextView textListe;
    private TextView textMot;
    private TextView textFr;
    private TextView textEn;
    private int motActuel = 0;
    private int nbDeMots = 0;
    private Button clickGauche = null;
    private Button clickDroite = null;
    private Button textToSpeech = null;
    private String[] wordsEN = null;
    private String[] wordsFR  = null;
    private TextToSpeech voice;
    private int idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_mots_activity);
        textListe = findViewById(R.id.nomListe);
        textMot = findViewById(R.id.numeroMot);
        textEn = findViewById(R.id.motEN);
        textFr = findViewById(R.id.motFR);

        clickGauche = findViewById(R.id.clickGauche);
        clickDroite = findViewById(R.id.clickDroite);
        textToSpeech = findViewById(R.id.TextToSpeech);

        idList = getIdList();

        textListe.setText(dbListes.getNameOfList(idList));
        nbDeMots = dbListes.getNbWords(idList);

        wordsEN = getENwords(idList);
        wordsFR = getFRwords(idList);

        textMot.setText("Mot 1 sur " + nbDeMots);
        textEn.setText(wordsEN[motActuel]);
        textFr.setText(wordsFR[motActuel]);

        clickGauche.setOnClickListener(clickListenerGauche);
        clickDroite.setOnClickListener(clickListenerDroite);
        textToSpeech.setOnClickListener(clickTextToSpeech);

        voice = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    voice.setLanguage(Locale.ENGLISH);
                }
            }
        });

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

    private OnClickListener clickListenerGauche = new OnClickListener(){
        @Override
        public void onClick (View v) {
            if (motActuel+1 > 1) {
                motActuel--;
                updateTextViews();
            }
        }
    };

    private OnClickListener clickListenerDroite = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (motActuel+1 < nbDeMots) {
                motActuel++;
                updateTextViews();
            }
        }
    };

    private OnClickListener clickTextToSpeech = new OnClickListener() {
        @Override
        public void onClick(View v) {
            voice.speak(wordsEN[motActuel], TextToSpeech.QUEUE_FLUSH, null);
        }
    };

    private void updateTextViews(){

        String strEN=new String();
        String[] motEN = wordsEN[motActuel].split(";");
        int i = 0;
        for(String str : motEN){
            i++;
            if(i>1){
                strEN = strEN+",";
            }
            strEN = strEN+" "+str;
        }

        String[] motFr = wordsFR[motActuel].split(";");
        String strFR=new String();
        int j = 0;
        for(String str : motFr){
            j++;
            if(j>1){
                strFR = strFR+",";
            }
            strFR = strFR+" "+str;
        }



        textEn.setText(strEN);
        textFr.setText(strFR);
        textMot.setText("Mot " + (motActuel+1) + " sur " + nbDeMots);

    }

}


