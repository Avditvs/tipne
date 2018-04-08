package com.souillard.BasesDeDonnées.evaluations;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface EvaluationsDAO {

    @Query("SELECT * FROM evaluations")
    List<Evaluations> getAll();

    @Insert
    void insertEvaluation (Evaluations evaluation);

    @Query("DELETE FROM evaluations")
    void nukeTableEveluations();
}
