package com.example.daljinski.baza;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ChannelEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int brojKanala;
    private String objectType;
    private long totalCount;

    public ChannelEntity(int brojKanala, String objectType, long totalCount){
        this.brojKanala=brojKanala;
        this.objectType=objectType;
        this.totalCount=totalCount;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrojKanala() {
        return brojKanala;
    }

    public void setBrojKanala(int id) {
        this.brojKanala = id;
    }


}
