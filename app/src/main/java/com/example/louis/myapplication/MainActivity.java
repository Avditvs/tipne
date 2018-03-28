package com.example.louis.myapplication;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.mots.*;



public class MainActivity extends Activity {

    TextView text = null;
    Mots motBd = null;
    TextToSpeech tts = null;
    Button bouton = null;






    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////Récupération des views/////////////////
        text = findViewById(R.id.text);
        bouton = findViewById(R.id.test);

        AppDataBase db = AppDataBase.getAppDatabase(getApplicationContext());
        MotsDAO bdDao = db.MotsDao();


        if (!verifiedDb(bdDao)){
            bdDao.nukeTableMots();
            System.out.println("Nuke mots");
            buildDb(bdDao);
        }

        text.setText(bdDao.getUnique(1,1).getTabMotsEn()[0]);


    }

    private boolean verifiedDb(MotsDAO dbDAO){
        System.out.println("Verifying the database");

        //coder qqchose qui vérifie si la bdd est bonne


        return !dbDAO.getAll().isEmpty();
    }


    private void buildDb(MotsDAO dbDAO){
        System.out.println("Importing database");
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

    private String[] getList(String nameList){
        int listID = getResources().getIdentifier(nameList, "array", getPackageName());
        String[] WordsList = getResources().getStringArray(listID);
        return WordsList;
    }

    private String[] separateWords (String linkedWords) {
        String[] separatedWords = linkedWords.split("/");
        return separatedWords;
    }

    private Mots setWord (String[] separatedWords, int idList, int idInList) {
        Mots aWord = new Mots(idList, idInList, separatedWords[0], separatedWords[1], 0, 0);
        return aWord;
    }
}
