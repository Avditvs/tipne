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
import android.widget.LinearLayout;
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
    private  Button[] buttons = new Button[12];
    private int indice;
    private float score;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_1);

        listesDAO = AppDataBase.getAppDatabase(getApplicationContext()).ListesDAO();
        motsDAO = AppDataBase.getAppDatabase(getApplicationContext()).MotsDao();

        Bundle extras = getIntent().getExtras();


        idList = listesDAO.idDeListe((extras.getString("nameList")));

        nomListe = findViewById(R.id.ListName);
        nomListe.setText(listesDAO.getProperName(idList));
        mots = motsDAO.getList(idList);

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


        Collections.shuffle(mots);

        indice =0;
        score =0;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        float temp_1 = preferences.getFloat("HighScore",0);
        editor.apply();
        affichageHighscore.setText(String.valueOf(temp_1));
        updateView(mots.get(getNewWord()));

        for (int j = 0; j < 12; j++){
            buttons[j].setOnClickListener(clickListenerButton);
        }

        restart.setOnClickListener(clickListenerRestart);
        confirm.setOnClickListener(clickListenerValidate);


        new CountDownTimer(101000, 1000) {

            public void onTick(long millisUntilFinished) {
                chronometer.setText(Long.toString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                chronometer.setText("0");
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



    private void updateNewRandomWord(Mots mots) {
        motFr.setText(mots.getMotsFr());
    }

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


    private void updateView (Mots mots){

        updateNewRandomWord(mots);
        affichageScore.setText(String.valueOf(score));
        String motMelange = shuffleWord(mots.getMotsEn());

        int i = 0;

        for (int x = 0; x < motMelange.length(); x++){
            buttons[x].setText(Character.toString(motMelange.charAt(x)));
            i++;
        }
        while(i<12) {
            Random r = new Random();
            char c = (char)(r.nextInt(26) + 'a');
            buttons[i].setText(Character.toString(c));
            i++;
        }
    }

    private View.OnClickListener clickListenerButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button boutonCliqué = (Button) v;
            String lettreBouton = boutonCliqué.getText().toString();
            String temp = motADeviner.getText().toString();
            motADeviner.setText(temp + lettreBouton);
            boutonCliqué.setVisibility(View.INVISIBLE);
        }
    };

    private View.OnClickListener clickListenerRestart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            motADeviner.setText("");
            for (int k = 0; k < 12; k++){
                buttons[k].setVisibility(View.VISIBLE);
            }
        }
    };

    private int getNewWord(){
        String test = new String();
        do {
            indice++;
            test = mots.get(indice).getMotsEn();
        }while (test.length() > 11);
        return (indice);
    };

    private View.OnClickListener clickListenerValidate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            valide();
        }
    };

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
    };

    private void onRightAnswer(){
        toast = Toast.makeText(getBaseContext(), "Bonne réponse", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        reduceToastTimeAppearing();
        motADeviner.setText("");
        for (int k = 0; k < 12; k++){
            buttons[k].setVisibility(View.VISIBLE);}
    }

    private void onWrongAnswer(){
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

    private void disableButtons(){
        for (int x = 0; x < 12; x++){
            buttons[x].setEnabled(false);}
        restart.setEnabled(false);
        confirm.setEnabled(false);
    }

    private void keepHighscore() {
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

    private void reduceToastTimeAppearing () {
        Handler handler_2 = new Handler();
        handler_2.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1100);
    }
    }
