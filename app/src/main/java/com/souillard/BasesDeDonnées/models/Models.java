package com.souillard.BasesDeDonnées.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Models {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "nameOfModel")
    private String nameOfModel;

    @ColumnInfo(name = "properNameOfModel")
    private String properNameOfModel;

    @ColumnInfo(name = "audioName")
    private String audioName;


    ///// Builders /////
    public Models(){

    }

    public Models(String modelName, String properModelName, String nameOfAudio){
        this.nameOfModel = modelName;
        this.properNameOfModel = properModelName;
        this.audioName = nameOfAudio;
    }


    ///// Getters /////

    public Integer getUid(){
        return uid;
    }

    public String getNameOfModel() {
        return nameOfModel;
    }

    public String getProperNameOfModel() {
        return properNameOfModel;
    }

    public String getAudioName() {
        return audioName;
    }

    ///// Setters /////


    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setNameOfModel(String nameOfModel) {
        this.nameOfModel = nameOfModel;
    }

    public void setProperNameOfModel(String properNameOfModel) {
        this.properNameOfModel = properNameOfModel;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }
}
