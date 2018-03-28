package com.example.mots;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
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

    @Query("SELECT * FROM mots WHERE idList like :list")
    List<Mots> getList(Integer list);

    @Query("DELETE FROM mots")
    void nukeTableMots();

}
