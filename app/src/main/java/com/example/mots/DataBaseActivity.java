package com.example.mots;

import android.app.Activity;
import android.os.Bundle;


public class DataBaseActivity  extends Activity {

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        AppDataBase db = AppDataBase.getAppDatabase(getApplicationContext());
        MotsDAO dbDAO = db.MotsDao();
        buildDb(dbDAO);
    }


    private void buildDb(MotsDAO dbDAO){  //créé la DB
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
