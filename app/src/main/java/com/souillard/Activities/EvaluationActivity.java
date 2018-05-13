package com.souillard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    private LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evaluation);
        micro = findViewById(R.id.TextToSpeech);
        setTriggerButton(micro);
        super.onCreate(savedInstanceState);


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

        listId = getIntent().getIntExtra("listId", 1);

        listeInfo = listesDAO.getListesById(listId);
        listNameView.setText(listeInfo.getNameOfList());


        dataMots = motsDAO.getList(listId);

        Collections.shuffle(dataMots);

        boutonValidation.setOnClickListener(listenerValidation);

        maxScore=0;
        nbBonnesRep=0;
        indiceMot = 1;
        changerMot(indiceMot);




    }


    @Override
    protected void speechTriggered(){
        super.speechTriggered();
        toast.cancel();
        //choses supplémentaires à l'appui du bouton

    }

    View.OnKeyListener textEnterListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == event.KEYCODE_ENTER)){
                valide();
                return true;
            }
            return true;
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
        editText.setText(res);
    }

    private void changerMot(int indice){
        infoSuppView.setText("");
        indiceMotView.setText("Mot " + indice + " sur " + listeInfo.getNbWords());
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
        if(verifierMot(reponse, dataMots.get(indiceMot-1).getMotsEn())){
            onCorrectAnswer();
        }
        else{
            onWrongAnswer();
        }

        if(indiceMot+1>dataMots.size()){
            onTestEnd();
        }
        else {
            maxScore++;
            indiceMot++;
            changerMot(indiceMot);
            Log.i("Reponse: ", dataMots.get(indiceMot - 1).getMotsEn());

            if (indiceMot > listeInfo.getNbWords()) {

                //trigger la fin cad balancer les résultats et mettre en bdd

            }
        }

    }

    private boolean verifierMot(String entree, String attente){
        /*TODO: prendre en entrée le tableau traductions possibles, les chercher toutes dans la réponse

         */
        return Objects.equals(entree, attente);
    }

    private void onCorrectAnswer(){
        nbBonnesRep++;
        Log.i("rep", "bonnerep");
        toast = Toast.makeText(getBaseContext(), "Bonne réponse", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, bodyEval.getBottom()+20);
        toast.show();
    }

    private void onWrongAnswer() {
        dataMots.get(indiceMot-1).incrementNbFaults(1);
        String toastText = "Mauvaise réponse, la(les) réponses\ncorrectes étaient :";
        int i;
        for (i = 0; i < dataMots.get(indiceMot - 1).getTabMotsEn().length; i++){
            toastText = toastText +"\n" + dataMots.get(indiceMot-1).getTabMotsEn()[i];

        }
        toast = Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, bodyEval.getBottom()+20);
        toast.show();
    }

    private void onPartialAnswer(){

    }

    private void onTestEnd(){
        EvaluationsDAO evaluationsDAO = AppDataBase.getAppDatabase(getBaseContext()).EvaluationsDAO();
        MotsDAO motsDAO = AppDataBase.getAppDatabase(getBaseContext()).MotsDao();
        Evaluations resultats = new Evaluations(listId, listeInfo.getNbWords()-nbBonnesRep, (nbBonnesRep*20)/maxScore,   System.currentTimeMillis());
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
