package com.souillard.BasesDeDonnées.verbes;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface VerbesDAO {

    @Query("SELECT * FROM verbes")
    List<Verbes> getAll();



}
