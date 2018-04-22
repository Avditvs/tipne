package com.souillard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.souillard.BasesDeDonnées.AppDataBase;
import com.souillard.BasesDeDonnées.listes.Listes;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
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
            MotsDAO dbMots = db.MotsDao();
            ListesDAO dbListe = db.ListesDAO();

            if (!verifiedDbMots(dbMots)){
                dbMots.nukeTableMots();
                Log.i("Tables", "Table mots détruite");
                buildDbListes(dbListe);
                buildDbMots(dbMots, dbListe);
            }

            try {
                Thread.sleep(1000);
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


    private boolean verifiedDbMots(MotsDAO dbMots){
        System.out.println("Verifying the database");

        //coder qqchose qui vérifie si la bdd est bonne


        return !dbMots.getAll().isEmpty();
    }



    private void buildDbMots(MotsDAO dbMots, ListesDAO dbListes){  //créé la DB
        Log.i("Base", "Import de la base de données");
        String[] namesList = getNamesOfTheLists(dbListes);
        int idList = 1;
        for (String nameOfList : namesList){
            int idInList = 1;
            String[] aList = getList(nameOfList);
            for (String linkedWords : aList){
                String[] separatedWords = separateWords(linkedWords);
                Mots aWord = setWord(separatedWords, idList, idInList);
                insertWordInDB(aWord, dbMots);
                idInList ++;
            }
            idList ++;
        }
    }


    private void buildDbListes (ListesDAO dbListes){
        String[] listesParameters = getListsParameters();
        int idList = 1;
        for (String linkedListe : listesParameters){
            String[] separatedListe = separateList(linkedListe);
            Listes aListe = setListe(separatedListe, idList);
            insertListeInDB(aListe, dbListes);
            idList ++;
        }
    }


    private String[] getListsParameters(){
        String[] listsParameters = getResources().getStringArray(R.array.Listes);
        return listsParameters;
    }

    private String[] separateList(String linkedList){
        String[] separatedList = linkedList.split("/");
        return separatedList;
    }

    private Listes setListe (String[] separatedList, int idList){
        Listes aList = new Listes(idList, separatedList[0], Integer.parseInt(separatedList[1]), Integer.parseInt(separatedList[2]));
        return  aList;
    }

    private void insertListeInDB (Listes aListe, ListesDAO dbListes){dbListes.insertListe(aListe);

    }


    private String[] getNamesOfTheLists(ListesDAO dbListes){      //Récupère les noms de toutes nos listes
        String[] namesList = dbListes.getNames();
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

    private void insertWordInDB (Mots aWord, MotsDAO dbMots) {
        dbMots.insertMot(aWord);
    }

}
