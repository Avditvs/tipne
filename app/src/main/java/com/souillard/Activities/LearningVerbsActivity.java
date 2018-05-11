package com.souillard.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.verbes.VerbesDAO;
import com.souillard.R;

import java.util.Locale;

public class LearningVerbsActivity extends Activity {

    AppDataBase db = AppDataBase.getAppDatabase(LearningVerbsActivity.this);
    VerbesDAO dbVerbs = db.VerbesDAO();

    private int verbeActuel = 0;

    private TextView textFr;
    private TextView textBv;
    private TextView textPret;
    private TextView textPart;

    private Button clickGauche = null;
    private Button clickDroite = null;
    private Button textToSpeech = null;

    private String[] verbFr;
    private String[] verbBv;
    private String[] verbPret;
    private String[] verbPart;

    private TextToSpeech voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_verbs_activity);

        textFr = findViewById(R.id.trad);
        textBv = findViewById(R.id.base_verbale);
        textPret = findViewById(R.id.preterit);
        textPart = findViewById(R.id.participe_passe);

        clickGauche = findViewById(R.id.clickGauche);
        clickDroite = findViewById(R.id.clickDroite);
        textToSpeech = findViewById(R.id.TextToSpeech);

        verbFr = dbVerbs.getFr();
        verbBv = dbVerbs.getBv();
        verbPret = dbVerbs.getPret();
        verbPart = dbVerbs.getPart();

        textFr.setText(verbFr[verbeActuel]);
        textBv.setText(verbBv[verbeActuel]);
        textPret.setText(verbPret[verbeActuel]);
        textPart.setText(verbPart[verbeActuel]);

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

    private View.OnClickListener clickListenerGauche = new View.OnClickListener(){
        @Override
        public void onClick (View v) {
            if (verbeActuel+1 > 1) {
                verbeActuel--;
                textFr.setText(verbFr[verbeActuel]);
                textBv.setText(verbBv[verbeActuel]);
                textPret.setText(verbPret[verbeActuel]);
                textPart.setText(verbPart[verbeActuel]);;
            }
        }
    };

    private View.OnClickListener clickListenerDroite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (verbeActuel+1 < 158) {
                verbeActuel++;
                textFr.setText(verbFr[verbeActuel]);
                textBv.setText(verbBv[verbeActuel]);
                textPret.setText(verbPret[verbeActuel]);
                textPart.setText(verbPart[verbeActuel]);
            }
        }
    };

    private View.OnClickListener clickTextToSpeech = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            voice.speak(verbBv[verbeActuel] + ' ' + verbPret[verbeActuel] + ' ' + verbPart[verbeActuel], TextToSpeech.QUEUE_FLUSH, null);
        }
    };



}
