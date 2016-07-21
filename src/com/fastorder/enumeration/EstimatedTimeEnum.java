package com.fastorder.enumeration;

public enum EstimatedTimeEnum {
	
	FIFTY("15 min"),
	THIRTY("30 min"),
	FORTYFIVE("45 min");
	
	private String time = "";
	
	EstimatedTimeEnum(String time){
		this.time = time;
	}
	
	@Override
	public String toString(){
		return time;
	}

}
