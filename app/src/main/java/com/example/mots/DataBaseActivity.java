package com.example.mots;

import android.app.Activity;

public class DataBaseActivity  extends Activity {

    public MotsDAO buildDb(){
        AppDataBase db = AppDataBase.getAppDatabase(getApplicationContext());
        MotsDAO dbDao = db.MotsDao();
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
        return dbDAO;
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
