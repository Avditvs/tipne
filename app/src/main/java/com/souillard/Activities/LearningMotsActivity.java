package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.abreviations.AbreviationsDAO;
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
    AbreviationsDAO dbAbbrev = db.AbreviationsDAO();
    private TextView textListe;
    private TextView textMot;
    private TextView textFr;
    private TextView textEn;
    private int motActuel = 0;
    private int nbDeMots = 0;
    private int nbDabbrev = 0;
    private Button clickGauche = null;
    private Button clickDroite = null;
    private Button textToSpeech = null;
    private String[] wordsEN = null;
    private String[] wordsFR  = null;
    private String[] abbrevs = null;
    private String[] significations = null;
    private TextToSpeech voice;
    private int idList;
    private Bundle extras = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_mots_activity);
        textListe = findViewById(R.id.nomListe);
        textMot = findViewById(R.id.numeroMot);
        textEn = findViewById(R.id.motEN);
        textFr = findViewById(R.id.motTRAD);

        clickGauche = findViewById(R.id.clickGauche);
        clickDroite = findViewById(R.id.clickDroite);
        textToSpeech = findViewById(R.id.TextToSpeech);

        extras = getIntent().getExtras();
        String choixUser = extras.getString("choixUtilisateur");
        String nameList = extras.getString("nameList");

        if (choixUser.equals("mots")) {
            motsChoosed(nameList);
        }
        else {
            abbrevChoosed(nameList);
        }

        voice = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    voice.setLanguage(Locale.ENGLISH);
                }
            }
        });

    }

    private void abbrevChoosed(String nameList){
        idList = getIdListAbbrev(nameList);

        textListe.setText(setProperName(nameList));

        abbrevs = dbAbbrev.getAbrev(idList);
        significations = dbAbbrev.getSignification(idList);
        nbDabbrev = significations.length;

        textMot.setText("Abreviation 1 sur " + nbDabbrev);
        textEn.setText(significations[motActuel]);
        textFr.setText(abbrevs[motActuel]);

        clickGauche.setOnClickListener(clickListenerGaucheA);
        clickDroite.setOnClickListener(clickListenerDroiteA);
        textToSpeech.setOnClickListener(clickTextToSpeechA);
    }

    private void motsChoosed(String nameList){
        idList = getIdList(nameList);

        textListe.setText(dbListes.getProperName(idList));
        nbDeMots = extras.getInt("nbMots");

        wordsEN = getENwords(idList);
        wordsFR = getFRwords(idList);

        textMot.setText("Mot 1 sur " + nbDeMots);
        textEn.setText(wordsEN[motActuel]);
        textFr.setText(wordsFR[motActuel]);

        clickGauche.setOnClickListener(clickListenerGaucheM);
        clickDroite.setOnClickListener(clickListenerDroiteM);
        textToSpeech.setOnClickListener(clickTextToSpeechM);


    }

    public int getIdList(String nomListe){
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

    private OnClickListener clickListenerGaucheM = new OnClickListener(){
        @Override
        public void onClick (View v) {
            if (motActuel+1 > 1) {
                motActuel--;
                updateTextViews();
            }
        }
    };

    private OnClickListener clickListenerDroiteM = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (motActuel+1 < nbDeMots) {
                motActuel++;
                updateTextViews();
            }
        }
    };

    private OnClickListener clickTextToSpeechM = new OnClickListener() {
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

    private int getIdListAbbrev(String nameList){
        String[] names = getResources().getStringArray(R.array.ListesAbreviations);
        int id = 0;
        for (int i = 0; i < names.length; i++){
            if (names[i].equals(nameList)){
                id = i + 1;
            }
        }
        return id;
    }

    private String setProperName (String nameList){
        String[] parsedName = nameList.split("_");
        String properName = "";
        for (int i = 1; i < parsedName.length; i++){
            properName = properName + ' ' + parsedName[i];
        }
        return properName;
    }

    private OnClickListener clickListenerGaucheA = new OnClickListener(){
        @Override
        public void onClick (View v) {
            if (motActuel+1 > 1) {
                motActuel--;
                updateAbbrevViews();
            }
        }
    };

    private OnClickListener clickListenerDroiteA = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (motActuel+1 < nbDabbrev) {
                motActuel++;
                updateAbbrevViews();
            }
        }
    };

    private OnClickListener clickTextToSpeechA = new OnClickListener() {
        @Override
        public void onClick(View v) {
            voice.speak(significations[motActuel], TextToSpeech.QUEUE_FLUSH, null);
        }
    };

    private void updateAbbrevViews(){
        String strSign=new String();
        String[] signification = significations[motActuel].split(";");
        int i = 0;
        for(String str : signification){
            i++;
            if(i > 1){
                strSign = strSign + ",";
            }
            strSign = strSign + " " + str;
        }


        String strAbbrev=new String();
        String[] abbrev = abbrevs[motActuel].split(";");
        int j = 0;
        for(String str : abbrev){
            j++;
            if(j > 1){
                strAbbrev = strAbbrev + ",";
            }
            strAbbrev = strAbbrev + " " + str;
        }



        textEn.setText(strSign);
        textFr.setText(strAbbrev);
        textMot.setText("Abreviation " + (motActuel + 1) + " sur " + nbDabbrev);
    }

}



