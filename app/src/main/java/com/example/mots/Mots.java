package com.example.mots;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
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

    @Ignore
    private String[] tabMotsFr = null;
    @Ignore
    private String[] tabMotsEn = null;




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

    public String[] getTabMotsEn() {
        return tabMotsEn;
    }

    public String[] getTabMotsFr() {
        return tabMotsFr;
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
        this.tabMotsEn = parsemot(motsEn);
    }

    public void setMotsFr(String motsFr) {
        this.motsFr = motsFr;
        this.tabMotsFr = parsemot(motsFr);
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

    private  String[] parsemot(String nonParsed){
        return nonParsed.split(";");
    }


}