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
        textToSpeech = (Button) findViewById(R.id.TextToSpeech);

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

    private OnClickListener clickTextToSpeech = new OnClickListener() {
        @Override
        public void onClick(View v) {
            voice.speak(wordsEN[motActuel], TextToSpeech.QUEUE_FLUSH, null);
        }
    };
}


