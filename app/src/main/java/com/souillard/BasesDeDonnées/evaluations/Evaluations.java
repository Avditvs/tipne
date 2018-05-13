package com.souillard.BasesDeDonnées.evaluations;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity
public class Evaluations {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private long epoch;

    @ColumnInfo
    private int nbFautes;

    @ColumnInfo
    private int idListe;

    @ColumnInfo
    private float note;

    @Ignore
    private Date date;


    ////////Constructeur//////////

    public Evaluations(){

    }

    public Evaluations(int idListe, int nbFautes, float note, long epoch){
        this.epoch = epoch;
        this.idListe = idListe;
        this.nbFautes = nbFautes;
        this.note = note;
    }


    //////////Getters///////////


    public int getIdListe() {
        return idListe;
    }


    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getNbFautes() {
        return nbFautes;
    }

    public long getEpoch() {
        return epoch;
    }

    public float getNote(){
        return note;
    }




    ///////


    public void setDate(Date date) {
        this.date = date;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    public void setNbFautes(int nbFautes) {
        this.nbFautes = nbFautes;
    }

    public void setNote(float note){
        this.note = note;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
        this.date = new Date(epoch);
    }

}

