package com.souillard.BasesDeDonnées.listes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class Listes {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "idList")
    private int idList;

    @ColumnInfo(name = "nameOfList")
    private String nameOfList;

    @ColumnInfo(name = "nbWords")
    private int nbWords;

    @ColumnInfo (name = "nbWordsHalf")
    private int nbWordsHalf;


    ///// Builders //////
    public Listes () {
        }


    public Listes ()
}
