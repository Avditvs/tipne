package com.souillard.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.mots.Mots;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class GameOneActivity extends Activity {

    private TextView nomListe;
    private TextView motFr;
    private int idList;
    private ListesDAO listesDAO;
    private MotsDAO motsDAO;
    private List<Mots> mots;
    private Button lettre1;
    private Button lettre2;
    private Button lettre3;
    private Button lettre4;
    private Button lettre5;
    private Button lettre6;
    private Button lettre7;
    private Button lettre8;
    private Button lettre9;
    private Button lettre10;
    private Button lettre11;
    private Button lettre12;
    private Button restart;
    private Button confirm;
    private  Button[] buttons = new Button[12];

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


        motFr = findViewById(R.id.motFr);
          //Normalement ça permet de faire un tableau de boutons
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
        //updateNewRandomWord(mots.get(1));
        int i =0;
        String test = new String();
        do {
            test = mots.get(i).getMotsEn();
            i++;
        }while (test.length() > 11);
        updateView(mots.get(i-1));

    }


    private void updateNewRandomWord(Mots mots) {
        motFr.setText(mots.getMotsFr());
    }
    private String shuffleWord (String word) {
        List<Character> l = new ArrayList<>();
        for (char c : word.toCharArray()) //for each char of the word selectionned, put it in a list
            l.add(c);
        Collections.shuffle(l); //shuffle the list

        StringBuilder sb = new StringBuilder(); //now rebuild the word
        for (char c : l)
            sb.append(c);

        word = sb.toString();
        return word;
    }



    private void updateView (Mots mots){

        updateNewRandomWord(mots);
        String[] motMelange = shuffleWord(mots.getMotsEn()).split("");

        int i = 0;

        for (String x : motMelange){
            buttons[i].setText(x);
            i++;
        }
        while(i<11) {
            buttons[i].setText("a");
            i++;
        }
    }

}