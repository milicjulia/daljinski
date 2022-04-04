package com.example.daljinski.baza;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.Maybe;

@Entity
public class ZanrProgramEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idPrograma;
    private String zanr;

    public ZanrProgramEntity(int idPrograma, String zanr){
        this.idPrograma=idPrograma;
        this.zanr=zanr;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }
}
