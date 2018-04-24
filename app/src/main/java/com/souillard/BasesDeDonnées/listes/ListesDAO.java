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




}
