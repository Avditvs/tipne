package com.souillard.Activities;

import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.TextView;
import com.souillard.R;
import com.souillard.SpeechToText.VoiceRecognitionActivity;

public class testSTT extends VoiceRecognitionActivity {

    TextView retour = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* on récupère le layout avant d'assigner les views */
        setContentView(R.layout.test_stt);


        /* assignation du bouton, ce bouton est le
        * déclencheur de la reconaissance vocale
        * */
        setTriggerButton((Button)findViewById(R.id.bouton));
        retour = findViewById(R.id.retour);

        /*on lance les méthodes de VoiceRecognitionActivity pour le setup de
        * tout le bordel
        */
        super.onCreate(savedInstanceState);

        /* ici tout le reste de l'activité */
    }
    /* on override la méthode onResults juste pour lui dire quoi faire de
    * l'output, ici on se contente de la mettre dans une textview mais on
    * pourrait aussi se contenter de stocker ça dans une variable par exemple
    * */
    @Override
    public void onResults(Bundle results) {
        super.onResults(results);
        retour.setText(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0)+"\n"
                + results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)[0]*100 +"%");
    }
}
