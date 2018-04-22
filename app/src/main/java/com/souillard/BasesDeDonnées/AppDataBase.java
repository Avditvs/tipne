package com.souillard.BasesDeDonnées;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.souillard.BasesDeDonnées.evaluations.Evaluations;
import com.souillard.BasesDeDonnées.evaluations.EvaluationsDAO;
import com.souillard.BasesDeDonnées.mots.Mots;
import com.souillard.BasesDeDonnées.mots.MotsDAO;
import com.souillard.BasesDeDonnées.listes.Listes;
import com.souillard.BasesDeDonnées.listes.ListesDAO;
import com.souillard.BasesDeDonnées.verbes.Verbes;
import com.souillard.BasesDeDonnées.verbes.VerbesDAO;

@Database(entities = {Mots.class, Listes.class, Evaluations.class, Verbes.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract MotsDAO MotsDao();

    public abstract ListesDAO ListesDAO();

    public abstract VerbesDAO VerbesDAO();

    public abstract EvaluationsDAO EvaluationsDAO();

    private static AppDataBase INSTANCE;

    public static AppDataBase getAppDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"list2").allowMainThreadQueries().build();

        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }


}
