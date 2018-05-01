package com.souillard.BasesDeDonnées;

import android.content.Context;

import com.souillard.BasesDeDonnées.evaluations.EvaluationsDAO;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.models.ModelsDAO;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.BasesDeDonnées.verbes.VerbesDAO;

import java.util.ArrayList;
import java.util.List;

public class DataBaseChecker {

    private Context context;
    private MotsDAO motsDAO;
    private ListesDAO listesDAO;
    private AppDataBase appDataBase;
    private VerbesDAO verbesDAO;
    private ModelsDAO modelsDAO;
    private EvaluationsDAO evaluationsDAO;


    public DataBaseChecker(Context context){
        this.context = context;
        this.appDataBase = AppDataBase.getAppDatabase(context);
        this.listesDAO = appDataBase.ListesDAO();
        this.motsDAO = appDataBase.MotsDao();
        this.verbesDAO = appDataBase.VerbesDAO();
        this.evaluationsDAO = appDataBase.EvaluationsDAO();
        this.modelsDAO = appDataBase.ModelsDAO();
    };


    public boolean dbListesCorrect(){
        if(listesDAO.getAll().isEmpty()){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean dbMotsCorrect(){
        if(motsDAO.getAll().isEmpty()||!hasRightNumber()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean dbVerbesCorrect(){
        if(verbesDAO.getAll().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean dbModelsCorrect(){
        if(modelsDAO.getAll().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean dbEvaluationsCorrect(){

        //pas encore implémenté

        return true;
    }

    private boolean hasRightNumber(){
        List<Integer> nbWordsList = listesDAO.getNbWordsList();
        boolean ok = true;
        int i = 1;
        for(int nb : nbWordsList){
            ok = (nb==motsDAO.countNbWordInlist(i))&&ok;
            i++;
        }
        return ok;
    }


}
