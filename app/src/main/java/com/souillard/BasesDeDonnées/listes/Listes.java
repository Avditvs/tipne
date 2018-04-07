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


    public Listes (int IdList, String NameOfList, int NbWords, int NbWordsHalf){
        idList = IdList;
        nameOfList = NameOfList;
        nbWords = NbWords;
        nbWordsHalf = NbWordsHalf;
    }


    //// Getters ////

    public Integer getUid() {
        return uid;
    }

    public int getIdList() {
        return idList;
    }

    public String getNameOfList() {
        return nameOfList;
    }

    public int getNbWords() {
        return nbWords;
    }

    public int getNbWordsHalf() {
        return nbWordsHalf;
    }


    //// Setters ////

    public void setIdList(int idList) {
        this.idList = idList;
    }

    public void setNameOfList(String nameOfList) {
        this.nameOfList = nameOfList;
    }

    public void setNbWords(int nbWords) {
        this.nbWords = nbWords;
    }

    public void setNbWordsHalf(int nbWordsHalf) {
        this.nbWordsHalf = nbWordsHalf;
    }

    public void setUid(int id) {
        this.uid = id;
    }
}
