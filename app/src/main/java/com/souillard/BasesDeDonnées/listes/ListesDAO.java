package com.souillard.BasesDeDonnées.listes;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ListesDAO {

    @Query("SELECT * FROM listes")
    List<Listes> getAll();

    @Query ("SELECT nameOfList FROM listes")
    String[] getNames();

    @Insert
    void insertListe (Listes aList);

    @Query("DELETE FROM listes")
    void nukeTableListes();

    @Query("SELECT nbWords FROM listes")
    List<Integer> getNbWordsList();

    @Query("SELECT nbWords FROM listes WHERE idList LIKE :id")
    int getNbWords(int id);

    @Query("SELECT idList FROM listes WHERE nameOfList LIKE :nameList")
    int idDeListe(String nameList);

    @Query("SELECT * FROM listes WHERE idList LIKE :id")
    Listes getListesById(int id);

    @Query("SELECT nameOfList FROM listes WHERE idList Like :id")
    String getNameOfList(int id);

    @Query("SELECT properName FROM listes")
    String[] getProperNames();

    @Query("SELECT properName FROM listes WHERE idList Like :id")
    String getProperName(int id);

    @Query("SELECT idList FROM listes WHERE properName LIKE :name")
    Integer getIdFromProperName(String name);

    @Query("SELECT nbWords FROM listes WHERE nameOfList LIKE :nameList")
    int getNbWords(String nameList);

    @Query("SELECT nbWordsHalf FROM listes WHERE nameOfList LIKE :nameList")
    int getNbWordsHalf(String nameList);


}
