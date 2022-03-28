package com.example.daljinski;

import java.util.ArrayList;

public class Program {
	String objectType;
	long createDate;
	String description;
	long endDate;
	String externalId;
	long id;
	ArrayList<String> images= new ArrayList<>();
	long rating;
	long year;
	String name;
	long startDate;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArrayList<String> getImages() {
		return images;
	}

	public void setImages(ArrayList<String> images) {
		this.images = images;
	}

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

	public ArrayList<String> getCountry() {
		return country;
	}

	public void setCountry(ArrayList<String> country) {
		this.country = country;
	}

	public ArrayList<String> getCategory() {
		return category;
	}

	public void setCategory(ArrayList<String> category) {
		this.category = category;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	ArrayList<String> country= new ArrayList<>();
	ArrayList<String> category= new ArrayList<>();
	ArrayList<String> genres= new ArrayList<>();
	
	public Program(String objectType, long createDate, String description, long endDate, String externalId, long id, ArrayList<String> images, long rating, long year, /*long episode_number, long season_number, long series_id, String series_name,*/ String name, long startDate, ArrayList<String> country, ArrayList<String> category, ArrayList<String> genres) {
		this.objectType=objectType;
		this.createDate=createDate;
		this.description=description;
		this.endDate=endDate;
		this.externalId=externalId;
		this.id=id;
		this.images=images;
		this.rating=rating;
		this.year=year;
		/*this.episode_number=episode_number;
		this.season_number=season_number;
		this.series_id=series_id;
		this.series_name=series_name;*/
		this.name=name;
		this.startDate=startDate;
		this.country=country;
		this.category=category;
		this.genres=genres;
	}
	
	

}
