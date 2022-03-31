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

    public OmiljeniEntity(String tip){
        this.tip=tip;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
