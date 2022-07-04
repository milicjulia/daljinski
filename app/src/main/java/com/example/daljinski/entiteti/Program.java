package com.example.daljinski.entiteti;

import java.util.ArrayList;

public class Program {
	private String objectType;
	private String description;
	private long endDate;
	private String externalId;
	private long id;
	private String name;
	private long startDate;
	private int idKanala;
	private boolean omiljen;

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

	public int getIdKanala() {
		return idKanala;
	}

	public void setIdKanala(int id) {
		this.idKanala = id;
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

	public ArrayList<String> getGenres() {
		return genres;
	}

	ArrayList<String> genres;
	
	public Program(String objectType, String description, long endDate, long id, String name, long startDate, ArrayList<String> genres, boolean omiljen) {
		this.objectType=objectType;
		this.description=description;
		this.endDate=endDate;
		this.id=id;
		this.name=name;
		this.startDate=startDate;
		this.genres=genres;
		this.omiljen=omiljen;
	}
	
	

}
