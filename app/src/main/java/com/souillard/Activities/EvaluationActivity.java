package com.souillard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.evaluations.Evaluations;
import com.souillard.BasesDeDonnées.evaluations.EvaluationsDAO;
import com.souillard.BasesDeDonnées.listes.Listes;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.mots.Mots;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;
import com.souillard.SpeechToText.VoiceRecognitionActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.souillard.Activities.ChooseListActivity.nameList;

public class EvaluationActivity extends VoiceRecognitionActivity {

    private Button micro;
    private List<Mots> dataMots;
    private int listId;
    private Listes listeInfo;
    private TextView listNameView;
    private TextView indiceMotView;
    private TextView requeteView;
    private TextView infoSuppView;
    private Button boutonValidation;
    private EditText editText;
    private int indiceMot;
    private int nbBonnesRep;
    private LinearLayout bodyEval;
    private Toast toast;
    private int maxScore;
    private int nbWords;
    private LinearLayout layout;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evaluation);
        micro = findViewById(R.id.TextToSpeech);
        setTriggerButton(micro);
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();


        MotsDAO motsDAO = AppDataBase.getAppDatabase(getApplicationContext()).MotsDao();
        ListesDAO listesDAO = AppDataBase.getAppDatabase(getApplicationContext()).ListesDAO();


        listNameView = findViewById(R.id.nomListe);
        boutonValidation = findViewById(R.id.validerEval);
        indiceMotView = findViewById(R.id.numeroMot);
        editText = findViewById(R.id.reponse);
        requeteView = findViewById(R.id.requete);
        infoSuppView = findViewById(R.id.infoSupp);
        bodyEval = findViewById(R.id.bodyEval);
        layout = findViewById(R.id.header);

        editText.setOnKeyListener(textEnterListener);

        listId = listesDAO.idDeListe(extras.getString("nameList"));

        sharedPreferences = getSharedPreferences("APP_SHARED_PREFERENCES", Context.MODE_PRIVATE);
        int annee = sharedPreferences.getInt("user_year", 1);
        listeInfo = listesDAO.getListesById(listId);


        if ((annee == 1) && (extras.getInt("nbMots") < 126)) {
            listNameView.setText("Moitié Liste " + listesDAO.getProperName(listId));
            dataMots = motsDAO.getHalfList(extras.getInt("nbMots"), listId);
        }
        else {
            listNameView.setText(listesDAO.getProperName(listId));
            dataMots = motsDAO.getList(listId);
        }

        dataMots = motsDAO.getList(listId);
        nbWords = extras.getInt("nbMots");

        for (int i =0; i < nbWords; i++){    //test suppression ce qu'il y a entre parenthèses
            String newEn = dataMots.get(i).getMotsEn().replaceAll("\\(" + ".*" + "?" + "\\)", "");
            Mots newWord = dataMots.get(i);
            newWord.setMotsEn(newEn);
            dataMots.set(i, newWord);
        }

        Collections.shuffle(dataMots);

        boutonValidation.setOnClickListener(listenerValidation);

        maxScore=0;
        nbBonnesRep=0;
        indiceMot = 1;
        changerMot(indiceMot);

    }


    @Override
    protected void onPause(){
        super.onPause();
        Collections.shuffle(dataMots);
        indiceMot = 1;
        maxScore=0;
        nbBonnesRep=0;

    }


    @Override
    protected void speechTriggered(){
        super.speechTriggered();

        //choses supplémentaires à l'appui du bouton

    }

    View.OnKeyListener textEnterListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == event.KEYCODE_ENTER)){
                valide();
                return true;
            }

            return false;
        }
    };

    View.OnClickListener listenerValidation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           valide();
        }
    };

    @Override
    public void onResults(Bundle bundle){
        super.onResults(bundle);
        String res = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
        editText.setText(editText.getText()+" "+res);
    }

    private void changerMot(int indice){
        infoSuppView.setText("");
        indiceMotView.setText("Mot " + indice + " sur " + 20);
        Mots motActuel  = dataMots.get(indice-1);
        dataMots.get(indice-1).incrementNbEval(1);
        String request = "";
        for(int i =0; motActuel.getTabMotsFr().length>i; i++){
            request += motActuel.getTabMotsFr()[i];
            if(i<motActuel.getTabMotsFr().length-1){
                request += ", ";
            }
        }
        if(motActuel.getTabMotsEn().length >1){
            infoSuppView.setText("(" +motActuel.getTabMotsEn().length + " traductions possibles)");
        }
        requeteView.setText(request);

    }

    private void valide(){
        String reponse = editText.getText().toString().toLowerCase();
        editText.setText("");
        ArrayList<String> corrections = new ArrayList<>();
        int partielrep = 0;
        for(String mot : dataMots.get(indiceMot-1).getTabMotsEn()){
            mot = mot.split("\\(")[0];
            if (mot.split("\\)").length>1){
                mot = mot.split("\\)")[1];
            }
            mot = mot.trim();
            maxScore++;
            if(verifierMot(reponse,mot)){
                nbBonnesRep++;
                partielrep++;
            }

        }

        if(partielrep==dataMots.get(indiceMot-1).getTabMotsEn().length){
            onCorrectAnswer();
        }
        else{
            if(partielrep==0){
                onWrongAnswer();
            }
            else{
                onPartialAnswer();
            }
        }



        if(indiceMot+1>20){
            onTestEnd();
        }
        else {
            indiceMot++;
            changerMot(indiceMot);
            Log.i("Reponse: ", dataMots.get(indiceMot - 1).getMotsEn());

        }

    }

    private boolean verifierMot(String entree, String attente){
        /*TODO: prendre en entrée le tableau traductions possibles, les chercher toutes dans la réponse

         */


        return entree.contains(attente);
    }

    private void onCorrectAnswer(){
        Log.i("rep", "bonnerep");
        toast = Toast.makeText(getBaseContext(), "Bonne réponse", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, bodyEval.getBottom()+20);
        toast.show();
    }

    private void onWrongAnswer() {
        dataMots.get(indiceMot-1).incrementNbFaults(1);
        String toastText = "Réponse incorrecte, la(les) réponses\ncorrectes étaient :";
        int i;
        for (i = 0; i < dataMots.get(indiceMot - 1).getTabMotsEn().length; i++){
            toastText = toastText +"\n" + dataMots.get(indiceMot-1).getTabMotsEn()[i];

        }
        toast = Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, bodyEval.getBottom()+20);
        toast.show();
    }

    private void onPartialAnswer(){
        dataMots.get(indiceMot-1).incrementNbFaults(1);
        String toastText = "Réponse partiellle, la(les) réponses\ncorrectes étaient :";
        int i;
        for (i = 0; i < dataMots.get(indiceMot - 1).getTabMotsEn().length; i++){
            toastText = toastText +"\n" + dataMots.get(indiceMot-1).getTabMotsEn()[i];

        }
        toast = Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, bodyEval.getBottom()+20);
        toast.show();
    }

    private void onTestEnd(){
        EvaluationsDAO evaluationsDAO = AppDataBase.getAppDatabase(getBaseContext()).EvaluationsDAO();
        MotsDAO motsDAO = AppDataBase.getAppDatabase(getBaseContext()).MotsDao();
        Evaluations resultats = new Evaluations(listId, listeInfo.getNbWords()-nbBonnesRep, (float)(nbBonnesRep*20)/(float)(maxScore),   System.currentTimeMillis());
        evaluationsDAO.insertEvaluation(resultats);
        motsDAO.insertListe(dataMots);
        showResults();



    }

    private void showResults(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.resultats_mots, null);
        PopupWindow popup = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView viewNomListe = view.findViewById(R.id.nomListe);
        TextView viewScore = view.findViewById(R.id.score);
        Button recommencer = view.findViewById(R.id.recommencer);
        Button toMenu = view.findViewById(R.id.toMenuPrincipal);

        recommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getIntent());
            }
        });

        toMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        float score = (nbBonnesRep*20)/maxScore;

        viewNomListe.setText("Résultat pour la liste \n" + listeInfo.getNameOfList());
        viewScore.setText("Score : " + score);
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }
}
