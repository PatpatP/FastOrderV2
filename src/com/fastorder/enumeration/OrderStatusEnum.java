package com.fastorder.enumeration;

public enum OrderStatusEnum {
	
	INPROGRESS("In Progress"),
	READY("Ready");
	
	private String status = "";
	
	OrderStatusEnum(String status){
		this.status = status;
	}
	
	@Override
	public String toString(){
		return status;
	}

}
