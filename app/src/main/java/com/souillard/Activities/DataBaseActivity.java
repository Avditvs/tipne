package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.mots.Mots;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.R;


public class DataBaseActivity  extends Activity {

    private boolean dbIsLoaded = false;
    private Intent intent = null;
    private TextView text;

    public void onCreate (Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);
        threadDb.start();
        text = findViewById(R.id.affdb);
        intent = new Intent(this, MainActivity.class);

    }

    private Thread threadDb = new Thread(new Runnable() {
        @Override
        public void run() {
            AppDataBase db = AppDataBase.getAppDatabase(getApplicationContext());
            MotsDAO dbDAO = db.MotsDao();

            if (!verifiedDb(dbDAO)){
                dbDAO.nukeTableMots();
                Log.i("Tables", "Table mots détruite");
                buildDb(dbDAO);
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            dbIsLoaded = true;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("Chargement terminé, appuyez pour continuer");
                }
            });

        }
    });


    public void onScreenClick(View view) {
        if(dbIsLoaded){
            startActivity(intent);
        }
        else{
            Log.i("Base", "Pas encore chargée");
        }
    }


    private boolean verifiedDb(MotsDAO dbDAO){
        System.out.println("Verifying the database");

        //coder qqchose qui vérifie si la bdd est bonne


        return !dbDAO.getAll().isEmpty();
    }



    private void buildDb(MotsDAO dbDAO){  //créé la DB
        Log.i("Base", "Import de la base de données");
        String[] namesList = getNamesOfTheLists();
        int idList = 1;
        for (String nameOfList : namesList){
            int idInList = 1;
            String[] aList = getList(nameOfList);
            for (String linkedWords : aList){
                String[] separatedWords = separateWords(linkedWords);
                Mots aWord = setWord(separatedWords, idList, idInList);
                insertWordInDB(aWord, dbDAO);
                idInList ++;
            }
            idList ++;
        }
    }

    private void insertWordInDB (Mots aWord, MotsDAO dbDAO) {
        dbDAO.insertMot(aWord);
    }

    private String[] getNamesOfTheLists(){      //Récupère les noms de toutes nos listes
        String[] namesList = getResources().getStringArray(R.array.Names);
        return namesList;
    }

    private String[] getList(String nameList){ //Récupère la liste associé au nom fourni
        int listID = getResources().getIdentifier(nameList, "array", getPackageName());
        String[] WordsList = getResources().getStringArray(listID);
        return WordsList;
    }

    private String[] separateWords (String linkedWords) { //sépare les mots français collé aux mots anglais
        String[] separatedWords = linkedWords.split("/");
        return separatedWords;
    }

    private Mots setWord (String[] separatedWords, int idList, int idInList) { //Permet de définir le mot dans la DB avec ses ID
        Mots aWord = new Mots(idList, idInList, separatedWords[0], separatedWords[1], 0, 0);
        return aWord;
    }



}
