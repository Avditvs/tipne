package com.souillard.BasesDeDonnées.abreviations;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity

public class Abreviations {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "abrev")
    private String Abrev;

    @ColumnInfo(name = "signification")
    private String Signification;

    @ColumnInfo(name = "nbFaults")
    private int NbFaults;

    @ColumnInfo(name = "nbEval")
    private int NbEval;

    @ColumnInfo(name = "idlist")
    private int IdList;

    @ColumnInfo(name = "idInList")
    private int IdInList;

        ///////// Builders /////////

    public Abreviations() {

    }

    public Abreviations(String abrev, String signification, int idList) {
        Abrev = abrev;
        Signification = signification;
        IdList = idList;
    }

    public Abreviations(String abrev, String signification, int nbFaults, int nbEval, int idList, int idInList) {
        Abrev = abrev;
        Signification = signification;
        IdList = idList;
        IdInList = idInList;
        NbFaults = nbFaults;
        NbEval = nbEval;
    }


        ///////// Getters ////////


    public int getUid() {
        return uid;
    }

    public int getIdList() {
        return IdList;
    }

    public int getNbEval() {
        return NbEval;
    }

    public int getNbFaults() {
        return NbFaults;
    }

    public String getAbrev() {
        return Abrev;
    }

    public String getSignification() {
        return Signification;
    }

    public int getIdInList() {
        return IdInList;
    }


    ///////// Setters /////////

    public void setIdList(int idList) {
        IdList = idList;
    }
    public void setAbrev(String abrev) {
        Abrev = abrev;
    }
    public void setNbEval(int nbEval) {
        NbEval = nbEval;
    }
    public void setNbFaults(int nbFaults) {
        NbFaults = nbFaults;
    }
    public void setSignification(String signification) {
        Signification = signification;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setIdInList(int idInList) {
        IdInList = idInList;
    }
}