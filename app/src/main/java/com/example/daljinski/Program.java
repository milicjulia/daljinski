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
