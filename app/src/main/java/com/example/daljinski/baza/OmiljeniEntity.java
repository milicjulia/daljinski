package com.example.daljinski.baza;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class OmiljeniEntity {
    @PrimaryKey()
    @NonNull
    private String tip;
    private int kolicina;

    public OmiljeniEntity(String tip){
        this.tip=tip;
        this.kolicina=1;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
