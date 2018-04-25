package com.souillard.BasesDeDonnées.verbes;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface VerbesDAO {

    @Query("SELECT * FROM verbes")
    List<Verbes> getAll();

    @Query("DELETE FROM verbes")
    void nukeTableVerbes();

    @Insert
    void insertVerb(Verbes aVerb);

    @Query("SELECT tradFr FROM verbes")
    String[] getFr();

    @Query ("SELECT baseVerbale FROM verbes")
    String[] getBv();

    @Query("SELECT preterit FROM verbes")
    String[] getPret();

    @Query("SELECT participe_passe FROM verbes")
    String[] getPart();

}
