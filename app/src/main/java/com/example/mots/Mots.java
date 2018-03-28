package com.example.mots;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class Mots {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "idList")
    private int idList;

    @ColumnInfo(name = "idInList")
    private int idInList;

    @ColumnInfo(name = "wordEn")
    private String motsEn;

    @ColumnInfo(name = "wordFr")
    private String motsFr;

    @ColumnInfo(name = "nbFaults")
    private int nbFaults;

    @ColumnInfo(name = "nbEval")
    private int nbEval;


    ///////// Builders /////////

    public Mots() {

    }

    public Mots(String fr, String en){
        motsEn = en;
        motsFr = fr;

    }

    public Mots(int IdList, int IdInList, String WordEn, String WordFr, int NbFaults, int NbEval) {
        idList = IdList;
        idInList = IdInList;
        motsEn = WordEn;
        motsFr = WordFr;
        nbFaults = NbFaults;
        nbEval = NbEval;
    }


    ///////// Getters ////////


    public String getMotsEn() {
        return motsEn;
    }

    public String getMotsFr() {
        return motsFr;
    }

    public Integer getUid() {
        return uid;
    }

    public Integer getIdInList() {
        return idInList;
    }

    public Integer getIdList() {
        return idList;
    }

    public Integer getNbEval() {
        return nbEval;
    }

    public Integer getNbFaults() {
        return nbFaults;
    }


    ///////// Setters /////////

    public void setIdList(int id) {
        this.idList = id;
    }

    public void setIdInList(int id) {
        this.idInList = id;
    }

    public void setMotsEn(String motsEn) {
        this.motsEn = motsEn;
    }

    public void setMotsFr(String motsFr) {
        this.motsFr = motsFr;
    }

    public void setUid(int id) {
        this.uid = id;
    }

    public void setNbEval(int nbEval) {
        this.nbEval = nbEval;
    }

    public void setNbFaults(int nbFaults) {
        this.nbFaults = nbFaults;
    }


}