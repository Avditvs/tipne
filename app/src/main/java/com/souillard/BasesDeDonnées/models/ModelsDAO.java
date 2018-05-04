package com.souillard.BasesDeDonnées.models;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ModelsDAO {

    @Insert
    void insertModel(Models aModel);

    @Query("SELECT * FROM models")
    List<Models> getAll();

    @Query("DELETE FROM models")
    void nukeTableModels();

    @Query("SELECT properNameOfModel FROM models")
    String[] getProperNames();

  //  @Query("SELECT nameOfModel FROM models WHERE properNameOfModel Like :properName")
  //  String getNameOfModel(String properName);

    @Query("SELECT audioName FROM models WHERE properNameOfModel Like :properName")
    String getAudioName(String properName);


}
