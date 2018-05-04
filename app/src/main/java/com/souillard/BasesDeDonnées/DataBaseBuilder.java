package com.souillard.BasesDeDonnées;

import android.content.Context;
import android.util.Log;

import com.souillard.BasesDeDonnées.evaluations.EvaluationsDAO;
import com.souillard.BasesDeDonnées.listes.Listes;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.models.Models;
import com.souillard.BasesDeDonnées.models.ModelsDAO;
import com.souillard.BasesDeDonnées.mots.Mots;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.BasesDeDonnées.verbes.Verbes;
import com.souillard.BasesDeDonnées.verbes.VerbesDAO;
import com.souillard.R;

public class DataBaseBuilder {

    //////Vars////////////////

    private Context context;
    private MotsDAO motsDAO;
    private ListesDAO listesDAO;
    private AppDataBase appDataBase;
    private VerbesDAO verbesDAO;
    private ModelsDAO modelsDAO;
    private EvaluationsDAO evaluationsDAO;


    //////////////Builder///////////////

    public DataBaseBuilder(Context context){
        this.context = context;
        this.appDataBase = AppDataBase.getAppDatabase(context);
        this.listesDAO = appDataBase.ListesDAO();
        this.motsDAO = appDataBase.MotsDao();
        this.verbesDAO = appDataBase.VerbesDAO();
        this.modelsDAO = appDataBase.ModelsDAO();
        this.evaluationsDAO = appDataBase.EvaluationsDAO();
    }

////////////Fonction de build de la db////////////////////

    public void buildDataBase(int annee){
       DataBaseChecker dataBaseChecker = new DataBaseChecker(context);

       if(!dataBaseChecker.dbListesCorrect()){
           listesDAO.nukeTableListes();
           buildDbListes(listesDAO, annee);
       }

       if(!dataBaseChecker.dbMotsCorrect()){
           motsDAO.nukeTableMots();
           buildDbMots(motsDAO, listesDAO);
       }

       if(!dataBaseChecker.dbVerbesCorrect()){
           verbesDAO.nukeTableVerbes();
           buildDbVerbes(verbesDAO);
       }

       if(!dataBaseChecker.dbModelsCorrect()){
           modelsDAO.nukeTableModels();
           buildDbModels(modelsDAO, annee);
       }
    }

////////////Fonctions de build de la dbMots////////////////////////

    private void buildDbMots(MotsDAO dbMots, ListesDAO dbListes){  //créé la DB
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
        Log.i("Base", "Import des mots dans la base de données");
    }

///////////Fonction de build de la dbListes//////////////////////

    private void buildDbListes (ListesDAO dbListes, int annee){
        String[] listesParameters = getListsParameters(annee);
        int idList = 1;
        for (String linkedListe : listesParameters){
            String[] separatedListe = separateList(linkedListe);
            Listes aListe = setListe(separatedListe, idList);
            insertListeInDB(aListe, dbListes);
            idList ++;
        }
        Log.i("Base", "Import des listes dans la bsae de donnée");
    }

///////////Fonction de build de la dbVerbes//////////////////////

    private void buildDbVerbes (VerbesDAO dbVerbes){
        String[] verbsList = getVerbsList();
        for (String linkedVerb : verbsList){
            String[] separatedVerb = separateVerb(linkedVerb);
            Verbes aVerb = setVerb(separatedVerb);
            insertVerbInDB(aVerb, dbVerbes);
        }
    }

///////////Fonction de build de la dbModels /////////////////////////

    private void buildDbModels(ModelsDAO dbModels, int annee){
        String[] modelsList = getModelsList(annee);
        for (String linkedModel : modelsList){
            String[] separatedModel = separateModel(linkedModel);
            Models aModel = setModel(separatedModel);
            insertModelInDB(dbModels, aModel);
        }
    }



/////////////////////Fonctions utiles au build de dbListes////////////////


    private String[] getListsParameters(int annee){
        String[] listsParameters;
        if (annee == 1) {
            listsParameters = context.getResources().getStringArray(R.array.ListesSTPI1);
        }
        else {
            listsParameters = context.getResources().getStringArray(R.array.ListesSTPI2);
        }
        return listsParameters;
    }

    private String[] separateList(String linkedList){
        String[] separatedList = linkedList.split("/");
        return separatedList;
    }

    private Listes setListe (String[] separatedList, int idList){
        Listes aList = new Listes(idList, separatedList[0], Integer.parseInt(separatedList[1]), Integer.parseInt(separatedList[2]), separatedList[3]);
        return  aList;
    }

    private void insertListeInDB (Listes aListe, ListesDAO dbListes){dbListes.insertListe(aListe);

    }


///////////////////Fonctions utiles au build de dbMots//////////////////////

    private String[] getNamesOfTheLists(ListesDAO dbListes){      //Récupère les noms de toutes nos listes
        String[] namesList = dbListes.getNames();
        return namesList;
    }

    private String[] getList(String nameList){ //Récupère la liste associé au nom fourni
        int listID = context.getResources().getIdentifier(nameList, "array", context.getPackageName());
        String[] WordsList = context.getResources().getStringArray(listID);
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

    ///////////////////Fonctions utiles au build de dbVerbes//////////////////////

    private String[] getVerbsList (){
        String[] verbsList = context.getResources().getStringArray(R.array.Verbes);
        return verbsList;
    }

    private String[] separateVerb (String linkedVerb){
        String[] separatedVerb = linkedVerb.split("/");
        return separatedVerb;
    }

    private Verbes setVerb (String[] separatedVerb){
        Verbes aVerb = new Verbes(separatedVerb[0], separatedVerb[1], separatedVerb[2], separatedVerb[3]);
        return aVerb;
    }

    private void insertVerbInDB (Verbes aVerb, VerbesDAO dbVerbes) {dbVerbes.insertVerb(aVerb);}


    /////////////////Fonctions utiles au build de dbModels ///////////////////////////////

    private String[] getModelsList(int annee) {
        String[] modelsList;
        if (annee == 1) {
            modelsList = context.getResources().getStringArray(R.array.Models1);
        } else {
            modelsList = context.getResources().getStringArray(R.array.Models2);
        }
        return modelsList;
    }

    private String[] separateModel(String linkedModel){
        String[] separatedModel = linkedModel.split("/");
        return separatedModel;
    }

    private Models setModel(String[] separatedModel){
        Models aModel = new Models(separatedModel[0], separatedModel[1]);
        return aModel;
    }

    private void insertModelInDB (ModelsDAO dbModels, Models aModel){dbModels.insertModel(aModel);}
}
