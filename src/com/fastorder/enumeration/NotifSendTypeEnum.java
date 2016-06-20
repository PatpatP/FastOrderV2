package com.fastorder.enumeration;

public enum NotifSendTypeEnum {

	MOBILE("Mobile Notif"),
	MAIL("Mail Notif");
	
	private String sendTypeName = "";
	
	NotifSendTypeEnum(String sendTypeName){
		this.sendTypeName = sendTypeName;
	}
	
	public String toString(){
		return sendTypeName;
	}
	
}
