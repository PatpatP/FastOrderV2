package com.fastorder.enumeration;

public enum NotifCategoryEnum {

	SALES("Sales");
	
	private String notifCategoryName = "";
	
	NotifCategoryEnum(String notifCategoryName) {
		this.notifCategoryName = notifCategoryName;
	}
	
	public String toString(){
		return notifCategoryName;
	}
}
