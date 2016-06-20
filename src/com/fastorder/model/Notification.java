package com.fastorder.model;

import java.util.Date;

import com.fastorder.enumeration.NotifCategoryEnum;
import com.fastorder.enumeration.NotifSendTypeEnum;

public class Notification {
	
	private int id;
	
	private String sender;
	private NotifSendTypeEnum sendType;
	private NotifCategoryEnum category;
	private Date triggerTime;
	private String subject;
	private String message;
	
	private User user;
	private Order order;
	
	public Notification(int id, String sender, NotifSendTypeEnum sendType, NotifCategoryEnum category,
			Date triggerTime, String subject, String message, User user, Order order) {
		super();
		this.id = id;
		this.sender = sender;
		this.sendType = sendType;
		this.category = category;
		this.triggerTime = triggerTime;
		this.subject = subject;
		this.message = message;
		this.user = user;
		this.order = order;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public NotifSendTypeEnum getSendType() {
		return sendType;
	}

	public void setSendType(NotifSendTypeEnum sendType) {
		this.sendType = sendType;
	}

	public NotifCategoryEnum getCategory() {
		return category;
	}

	public void setCategory(NotifCategoryEnum category) {
		this.category = category;
	}

	public Date getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
