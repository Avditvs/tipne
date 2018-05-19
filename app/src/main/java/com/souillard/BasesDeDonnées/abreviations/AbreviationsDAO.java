package com.souillard.BasesDeDonnées.abreviations;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface AbreviationsDAO {

    @Query("SELECT * FROM abreviations")
    List<Abreviations> getAll();

    @Query("SELECT * FROM abreviations WHERE idInList like :idList")
    Abreviations getUnique(int idList);

    @Insert
    void insertAbreviations(Abreviations anAbreviations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListe(List<Abreviations> list);

    @Query("SELECT * FROM abreviations WHERE idlist like :list")
    List<Abreviations> getList(Integer list);

    @Query("SELECT Abrev FROM abreviations WHERE idlist like :idList")
    String[] getAbrev(int idList);

    @Query("SELECT signification FROM abreviations WHERE idlist like :idList")
    String[] getSignification(int idList);

    @Query("DELETE FROM abreviations")
    void nukeTableAbreviations();

    @Query("SELECT COUNT(*) FROM abreviations WHERE idlist like :idList")
    int countNbAbbrevInlist(int idList);

}
