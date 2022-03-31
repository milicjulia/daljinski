package com.example.daljinski.baza;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.daljinski.ui.Program;

@Entity
public class ProgramEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String objectType;
    private long createDate;
    private String description;
    private long endDate;
    private String externalId;
    private String url;
    private long rating;
    private long year;
    private String name;
    private long startDate;
    private int idKanala;

    public ProgramEntity(Program p) {
        this.objectType = p.getObjectType();
        this.createDate = p.getCreateDate();
        this.description = p.getDescription();
        this.endDate = p.getEndDate();
        this.externalId = p.getExternalId();
        this.url = p.getImage();
        this.rating = p.getRating();
        this.year = p.getYear();
        this.name = p.getName();
        this.startDate = p.getStartDate();
        this.idKanala = p.getIdKanala();
    }
    public ProgramEntity() {}

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
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

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
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


}
