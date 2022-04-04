package com.example.daljinski.baza;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ZanroviEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String tip;

    public ZanroviEntity(String s) {this.tip=s; }
    public ZanroviEntity() {}

    public String getTip() {
        return tip;
    }

    public void setTip(String s) {
        this.tip = s;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
