package com.example.daljinski;

import java.util.ArrayList;

public class Channel {
	String objectType;
	long totalCount;
	ArrayList<Program> programs=new ArrayList<>();
	
	public Channel(String o, long t,ArrayList<Program> p) {
		this.objectType=o;
		this.totalCount=t;
		this.programs=p;
	}
	

}