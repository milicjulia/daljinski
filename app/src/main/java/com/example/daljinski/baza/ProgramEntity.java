package com.example.daljinski.baza;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.daljinski.entiteti.Program;

@Entity
public class ProgramEntity {
    @PrimaryKey
    private long id;
    private String objectType;
    private String description;
    private long endDate;
    private String externalId;
    private String name;
    private long startDate;
    private int idKanala;
    private boolean omiljen = false;

    public ProgramEntity(Program p) {
        this.id=p.getId();
        this.objectType = p.getObjectType();
        this.description = p.getDescription();
        this.endDate = p.getEndDate();
        this.externalId = p.getExternalId();
        this.name = p.getName();
        this.startDate = p.getStartDate();
        this.idKanala = p.getIdKanala();
        this.omiljen=p.getOmiljen();
    }
    public ProgramEntity() {}

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public int getIdKanala() {
        return idKanala;
    }

    public void setIdKanala(int idKanala) {
        this.idKanala = idKanala;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getOmiljen() {
        return omiljen;
    }

    public void setOmiljen(boolean omiljen) {
        this.omiljen = omiljen;
    }


}
