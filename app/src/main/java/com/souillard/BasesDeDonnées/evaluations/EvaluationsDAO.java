package com.souillard.BasesDeDonnées.evaluations;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.content.Intent;


import java.util.List;

@Dao
public interface EvaluationsDAO {

    @Query("SELECT * FROM evaluations")
    List<Evaluations> getAll();

    @Insert
    void insertEvaluation (Evaluations evaluation);

    @Query("DELETE FROM evaluations")
    void nukeTableEvaluations();

    @Query("SELECT count(*) from evaluations WHERE idListe LIKE :id")
    int countEvals(int id);

    @Query("SELECT note from evaluations where idListe LIKE :id")
    List<Float>  getListNotes(int id);

    @Query("SELECT note from evaluations where idListe LIKE :id AND epoch like (SELECT MAX(epoch) from evaluations where idListe like :id)" )
    float getLastNote(int id);

    @Query("SELECT idListe from evaluations")
    List<Integer> getEvalsIdListe();

    @Query("SELECT epoch from evaluations where idListe like :id")
    List<Long> getEpochs(int id);
}

