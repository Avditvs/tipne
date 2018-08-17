package com.souillard.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.souillard.R;


public class GrammarExerciceActivity extends Activity {

    private TextView phrase;
    private int indice = 0;
    private String[] listeExercices;
    private Spinner choix_1;
    private Spinner choix_2;
    private Button validate;
    private Bundle extras;
    private int exercice;
    private String param;
    private String[] params;
    private String nomExo;
    private int nombreExos;
    private int choixExo;
    private int indiceParam;
    private String[] texteExo;
    private int nbChoix;
    private String[] texte;
    private String[] propositions_1;
    private String[] propositions_2;
    private String[] solutions;
    private PopupWindow popup;
    private LinearLayout layout;
    private int nombrePhrases;
    private int scoreTot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gramar_exercice_activity);
        layout = findViewById(R.id.grammar_exercice_activity);


        /////////////////////Chargement paramètres ///////////////////////////////////

        listeExercices = getResources().getStringArray(R.array.Liste_Exos_Grammar);  //récupère tous les paramètres de tous les exos
        extras = getIntent().getExtras();
        exercice = extras.getInt("exoChoisi"); //Récupère le numéro de l'exo choisi dans l'Intent précédent
        choixExo = extras.getInt("partieExo"); //Récupère le sous-exo choisi
        param = listeExercices[exercice]; //Récupère les paramètres de l'exo choisi
        params = param.split("/");
        nomExo = params[0];
        nombreExos = Integer.parseInt(params[1]);


        /////////////////Chargement des données de l'exercice///////////////

        choixExo--;
        indiceParam = 2;
        while (choixExo > 0){ //on se décale au bon indice pour charger les paramètres de l'exercice choisi
            indiceParam = indiceParam + Integer.parseInt(params[indiceParam]) + 3;
            choixExo--;
        }

        nbChoix = Integer.parseInt(params[indiceParam]);   //Récupère le nombre de choix que doit faire l'utilisateur
        indiceParam++;
        texte = getResources().getStringArray(getResources().getIdentifier(params[indiceParam], "array", getPackageName())); //récupère le texte de l'exercice
        nombrePhrases = texte.length;
        indiceParam++;
        propositions_1 = getResources().getStringArray(getResources().getIdentifier(params[indiceParam], "array", getPackageName())); //récupère les propositions du choix 1
        indiceParam++;
        if (nbChoix>1){
            propositions_2 = getResources().getStringArray(getResources().getIdentifier(params[indiceParam], "array", getPackageName())); //récupère si besoin celle du choix 2
            indiceParam++;
        }
        solutions = getResources().getStringArray(getResources().getIdentifier(params[indiceParam], "array", getPackageName())); //récupère les solutions de l'exercice


        //////////////////////////Initialisation/////////////////////////

        indice = 0;
        scoreTot = 0;

        choix_1 = findViewById(R.id.premier);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, propositions_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choix_1.setAdapter(adapter);

        choix_2 = findViewById(R.id.deuxieme);
        if (nbChoix>1) {
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, propositions_2);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            choix_2.setAdapter(adapter2);
        }
        else {
            choix_2.setVisibility(View.INVISIBLE);
        }


        validate = findViewById(R.id.valider);
        validate.setOnClickListener(clickListenerValidate);

        phrase = findViewById(R.id.requete);
        phrase.setText(texte[indice]);

    }



    private View.OnClickListener clickListenerValidate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (indice <= nombrePhrases - 1){
                String reponseChoisie = choix_1.getSelectedItem().toString();
                if (nbChoix>1){
                    reponseChoisie = reponseChoisie + "/" + choix_2.getSelectedItem().toString();
                }
                scoreTot = scoreTot + calculScore(reponseChoisie);
                if (indice + 1 < nombrePhrases){
                    indice++;
                    phrase.setText(texte[indice]);
                }
                else {
                    Toast.makeText(GrammarExerciceActivity.this, "Exercice fini", Toast.LENGTH_SHORT).show();
                    showResults();
                }
            }
        }
    };



    private int calculScore(String reponseChoisie){
        int score = 0;
        if (reponseChoisie.equals(solutions[indice])){
            score++;
            Toast.makeText(GrammarExerciceActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(GrammarExerciceActivity.this, "Mauvaise réponse", Toast.LENGTH_SHORT).show();
        }
        return score;
    }







    private void showResults() {
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

        float note = (scoreTot*20)/nombrePhrases;


        viewNomListe.setText("Résultat pour l'exercice " + nomExo );
        viewScore.setText("Score : " + note);
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }



}
