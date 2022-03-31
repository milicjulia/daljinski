package com.example.daljinski.ui;

import java.util.ArrayList;

public class Channel {
	String objectType;
	long totalCount;

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

	public ArrayList<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(ArrayList<Program> programs) {
		this.programs = programs;
	}

	ArrayList<Program> programs=new ArrayList<>();
	
	public Channel(String o, long t,ArrayList<Program> p) {
		this.objectType=o;
		this.totalCount=t;
		this.programs=p;
	}
	

}