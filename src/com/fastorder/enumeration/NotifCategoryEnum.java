package com.fastorder.enumeration;

public enum NotifCategoryEnum {

	SALES("Sales");
	
	private String notifCategoryName = "";
	
	NotifCategoryEnum(String notifCategoryName) {
		this.notifCategoryName = notifCategoryName;
	}
	
	@Override
	public String toString(){
		return notifCategoryName;
	}
}
