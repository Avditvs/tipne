package com.souillard.BasesDeDonnées.listes;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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




}
