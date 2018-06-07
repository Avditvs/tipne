package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.abreviations.AbreviationsDAO;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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
    private SeekBar seekBar;
    private TextView textEn;
    private int motActuel = 1;
    private int motActuelBis;
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
    private int nbItems;
    private int avancement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_mots_activity);
        extras = getIntent().getExtras();
        final String choixUser = extras.getString("choixUtilisateur");
        String nameList = extras.getString("nameList");
        textListe = findViewById(R.id.nomListe);
        textMot = findViewById(R.id.numeroMot);
        textEn = findViewById(R.id.motEN);
        textFr = findViewById(R.id.motTRAD);

        seekBar = findViewById(R.id.seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                motActuel = (progress*nbDeMots)/seekBar.getMax();
                if(motActuel == -1||motActuel==0){
                    motActuel =1;
                }

                 //motActuel = motActuelBis;
                if(choixUser.equals("mots")){
                    updateTextViews(motActuel);
                }else{
                    updateAbbrevViews(motActuel);
                }
                avancement = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        clickGauche = findViewById(R.id.clickGauche);
        clickDroite = findViewById(R.id.clickDroite);
        textToSpeech = findViewById(R.id.TextToSpeech);



        if (choixUser.equals("mots")) {
            motsChosen(nameList);
        }
        else {
            abbrevChosen(nameList);
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

    private void abbrevChosen(String nameList){
        idList = getIdListAbbrev(nameList);

        textListe.setText(setProperName(nameList));

        abbrevs = dbAbbrev.getAbrev(idList);
        nbDeMots = dbAbbrev.getAbrev(idList).length;
        significations = dbAbbrev.getSignification(idList);


        textMot.setText("Abreviation 1 sur " + nbDeMots);
        textEn.setText(significations[motActuel]);
        textFr.setText(abbrevs[motActuel]);

        clickGauche.setOnClickListener(clickListenerGaucheA);
        clickDroite.setOnClickListener(clickListenerDroiteA);
        textToSpeech.setOnClickListener(clickTextToSpeechA);
    }

    private void motsChosen(String nameList){
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
            if (motActuel > 1) {
                motActuel--;
                seekBar.setProgress((motActuel+1)*seekBar.getMax()/nbDeMots);

                updateTextViews(motActuel);
            }
        }
    };

    private OnClickListener clickListenerDroiteM = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (motActuel < nbDeMots) {
                motActuel++;
                seekBar.setProgress((motActuel+1)*seekBar.getMax()/nbDeMots);
                updateTextViews(motActuel);
            }
        }
    };

    private OnClickListener clickTextToSpeechM = new OnClickListener() {
        @Override
        public void onClick(View v) {
            voice.speak(wordsEN[motActuel-1], TextToSpeech.QUEUE_FLUSH, null);
        }
    };

    private void updateTextViews(int mot){

        String strEN=new String();
        String[] motEN = wordsEN[mot-1].split(";");
        int i = 0;
        for(String str : motEN){
            i++;
            if(i>1){
                strEN = strEN+",";
            }
            strEN = strEN+" "+str;
        }

        String[] motFr = wordsFR[mot-1].split(";");
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
        textMot.setText("Mot " + (mot) + " sur " + nbDeMots);


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
            if (motActuel > 1) {
                motActuel--;
                seekBar.setProgress((motActuel+1)*seekBar.getMax()/nbDeMots);
                updateAbbrevViews(motActuel);
            }
        }
    };

    private OnClickListener clickListenerDroiteA = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (motActuel < nbDeMots) {
                motActuel++;
                seekBar.setProgress((motActuel+1)*seekBar.getMax()/nbDeMots);
                updateAbbrevViews(motActuel);
            }
        }
    };

    private OnClickListener clickTextToSpeechA = new OnClickListener() {
        @Override
        public void onClick(View v) {
            voice.speak(significations[motActuel-1], TextToSpeech.QUEUE_FLUSH, null);
        }
    };

    private void updateAbbrevViews(int mot){
        String strSign=new String();
        String[] signification = significations[mot-1].split(";");
        int i = 0;
        for(String str : signification){
            i++;
            if(i > 1){
                strSign = strSign + ",";
            }
            strSign = strSign + " " + str;
        }


        String strAbbrev=new String();
        String[] abbrev = abbrevs[mot-1].split(";");
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
        textMot.setText("Abreviation " + (mot) + " sur " + nbDeMots);

    }

}



