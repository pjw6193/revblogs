package com.revature.data.impl;

public enum PropertyType {
	
	COMPANY(0), 
	APP(1), 
	S3(2), 
	SERVER(3), 
	JENKINS(4), 
	SONARQUBE(5), 
	K(6), 
	V(7),
	FAPP(8),
	LINKTOKEN(9),
	S3BUCKET(10);
	
	private int value;
	
	PropertyType(int value){
		
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
