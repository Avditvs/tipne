package com.souillard.BasesDeDonnées.mots;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface MotsDAO {

    @Query("SELECT * FROM mots")
    List<Mots> getAll();

    @Query("SELECT * FROM mots WHERE idInList like :id AND idList LIKE :idList")
    Mots getUnique(Integer idList, Integer id);

    @Insert
    void insertMot(Mots aWord);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListe(List<Mots> list);

    @Query("SELECT * FROM mots WHERE idList like :list")
    List<Mots> getList(Integer list);

    @Query("SELECT wordEn FROM mots WHERE idList like :idList")
    String[] getEN(int idList);

    @Query("SELECT wordFr FROM mots WHERE idList like :idList")
    String[] getFR(int idList);

    @Query("DELETE FROM mots")
    void nukeTableMots();

    @Query("SELECT COUNT(*) FROM mots WHERE idList like :list")
    int countNbWordInlist(int list);

}
