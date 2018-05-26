package com.souillard.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.mots.Mots;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class GameOneActivity extends Activity {

    private TextView nomListe;
    private TextView motFr;
    private TextView motADeviner;
    private TextView chronometer;
    private TextView affichageScore;
    private TextView affichageHighscore;
    private int idList;
    private ListesDAO listesDAO;
    private MotsDAO motsDAO;
    private List<Mots> mots;
    private Button restart;
    private Button confirm;
    private Button[] buttons = new Button[12];
    private int indice;
    private float score;
    private Toast toast;


    ///////////////////////////////////////////activité////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       //lancement de l'activité
        setContentView(R.layout.game_1);          //on choisit l'interface graphique de l'activité

        indice =0;
        score =0;

        initialisationEtMelangeListes();          //récupération des listes de mots et mélange de la liste de mots
        initialisationElementGraphique();
        recupererHighScore();                     //récupération du highscore stocker en preferences


        updateView(mots.get(getNewWord()));       //maj IHM

        for (int j = 0; j < 12; j++){             //mise en fonctionnement des boutons
            buttons[j].setOnClickListener(clickListenerButton);
        }

        restart.setOnClickListener(clickListenerRestart);
        confirm.setOnClickListener(clickListenerValidate);

        new CountDownTimer(101000, 1000) {                      //compte à rebours du jeu

            public void onTick(long millisUntilFinished) {
                chronometer.setText(Long.toString(millisUntilFinished / 1000));         //on affiche le temps restant chaque seconde
            }

            public void onFinish() {
                chronometer.setText("0");                                                 //actions effectuées à l'arrêt du chrono
                String toastTextFin = "Temps écoulé !\nVotre score est de " + score;
                toast = Toast.makeText(getBaseContext(), toastTextFin, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                disableButtons();
                keepHighscore();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 4000);
            }
        }.start();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private String shuffleWord (String word) {
        List<Character> l = new ArrayList<>();
        for (char c : word.toCharArray()) // chaque caractère est mis dans une liste
            l.add(c);
        Collections.shuffle(l); //on mélange la liste avec shuffle

        StringBuilder sb = new StringBuilder(); //on reconstruit le mot
        for (char c : l)
            sb.append(c);

        word = sb.toString();
        return word;
    }

    private void updateView (Mots mots){  //permet de rafraichir ce qu'on voit à l'écran

        motFr.setText(mots.getMotsFr());
        affichageScore.setText(String.valueOf(score));
        String motMelange = shuffleWord(mots.getMotsEn());

        char tableauLettres[] = new char[12];
        Random r = new Random();
        for (byte i =0; i< motMelange.length(); i++){
            tableauLettres[i] = motMelange.charAt(i);
        }
        for (int j = motMelange.length(); (j<12) ; j++){
            tableauLettres[j] = (char)(r.nextInt(26) + 'a');
        }
        Random random = new Random();
        for (byte k =0 ; (k<12) ; k++){
            int nb = random.nextInt(12);
            char tmp = tableauLettres[k];
            tableauLettres[k] = tableauLettres[nb];
            tableauLettres[nb] = tmp;
        }

        for (int x = 0; (x < 12); x++){       //application lettres sur boutons
            buttons[x].setText(Character.toString(tableauLettres[x]));
        }
    };

    private View.OnClickListener clickListenerButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {       //actions au toucher des lettres
            Button boutonCliqué = (Button) v;
            String lettreBouton = boutonCliqué.getText().toString();
            String temp = motADeviner.getText().toString();
            motADeviner.setText(temp + lettreBouton);
            boutonCliqué.setVisibility(View.INVISIBLE);
        }
    };

    private View.OnClickListener clickListenerRestart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {      //actions au toucher de restart
            motADeviner.setText("");
            for (int k = 0; k < 12; k++){
                buttons[k].setVisibility(View.VISIBLE);
            }
        }
    };

    private int getNewWord(){                                        //récupérer nouveau mot anglais
        String test = new String();
        do {
            indice++;
            test = mots.get(indice).getMotsEn();
        }while (test.length() > 11);
        return (indice);
    }

    private View.OnClickListener clickListenerValidate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            valide();
        }
    };
                                                                                    //actions au toucher de restart
    private void valide(){
        String motSolution = mots.get(indice).getMotsEn();
        if (motADeviner.getText().toString().equals(motSolution)) {
            score++;
            onRightAnswer();
        }
        else {
            score = score - (float)0.25;
            onWrongAnswer();
        }
        updateView(mots.get(getNewWord()));
    }

    private void onRightAnswer(){                                                  //actions lors d'une bonne réponse
        toast = Toast.makeText(getBaseContext(), "Bonne réponse", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        reduceToastTimeAppearing();
        motADeviner.setText("");
        for (int k = 0; k < 12; k++){
            buttons[k].setVisibility(View.VISIBLE);}
    }

    private void onWrongAnswer(){                                                 //actions lors d'une mauvaise réponse
        String toastText = "Réponse incorrecte, la(les) réponses\ncorrectes étaient :";
        int i;
        for (i = 0; i < mots.get(indice).getTabMotsEn().length; i++){
            toastText = toastText +"\n" + mots.get(indice).getTabMotsEn()[i];

        }
        toast = Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        reduceToastTimeAppearing();
        motADeviner.setText("");
        for (int k = 0; k < 12; k++){
            buttons[k].setVisibility(View.VISIBLE);}
    }

    private void disableButtons(){                                              //désactivation des boutons à la fin du chrono
        for (int x = 0; x < 12; x++){
            buttons[x].setEnabled(false);}
        restart.setEnabled(false);
        confirm.setEnabled(false);
    }

    private void keepHighscore() {                                              //garder le highscore entre 2 parties et affichage nouveau meilleur score
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        float temp = preferences.getFloat("HighScore", 0);
        if (score > temp) {
            editor.putFloat("HighScore", score);
            editor.apply();
            affichageHighscore.setText(String.valueOf(score));
            String toastTextHighScore = "Nouveau meilleur score ! : \n      " + score;
            toast = Toast.makeText(getBaseContext(), toastTextHighScore, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }

    private void reduceToastTimeAppearing () {          //réduire le temps d'apparition des toasts
        Handler handler_2 = new Handler();
        handler_2.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1100);
    }

    private void initialisationEtMelangeListes() {

        listesDAO = AppDataBase.getAppDatabase(getApplicationContext()).ListesDAO();
        nomListe = findViewById(R.id.ListName);
        Bundle extras = getIntent().getExtras();
        idList = listesDAO.idDeListe((extras.getString("nameList")));
        nomListe.setText(listesDAO.getProperName(idList));
        motsDAO = AppDataBase.getAppDatabase(getApplicationContext()).MotsDao();
        mots = motsDAO.getList(idList);


        Collections.shuffle(mots);
    }

    private void initialisationElementGraphique() {

        motADeviner = findViewById(R.id.motadeviner);
        motADeviner.setText("");

        chronometer = findViewById(R.id.chronometer);
        chronometer.setText("c");

        affichageScore = findViewById(R.id.score);
        affichageHighscore = findViewById(R.id.highscore);
        motFr = findViewById(R.id.motFr);
        restart = findViewById(R.id.restartButton);
        confirm = findViewById(R.id.confirmationButton);
        buttons[0] = findViewById(R.id.char1);
        buttons[1] = findViewById(R.id.char2);
        buttons[2] = findViewById(R.id.char3);
        buttons[3] = findViewById(R.id.char4);
        buttons[4] = findViewById(R.id.char5);
        buttons[5] = findViewById(R.id.char6);
        buttons[6] = findViewById(R.id.char7);
        buttons[7] = findViewById(R.id.char8);
        buttons[8] = findViewById(R.id.char9);
        buttons[9] = findViewById(R.id.char10);
        buttons[10] = findViewById(R.id.char11);
        buttons[11] = findViewById(R.id.char12);
    }

    private void recupererHighScore() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        float temp_1 = preferences.getFloat("HighScore",0);
        editor.apply();
        affichageHighscore.setText(String.valueOf(temp_1));
    }


}
