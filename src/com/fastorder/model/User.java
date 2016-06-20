package com.fastorder.model;



import java.util.Date;
import java.util.Calendar;

import com.fastorder.enumeration.UserTypeEnum;

public class User{
	
	private int id;
	private String mail;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	private UserTypeEnum userType;
	private int addressId;
	private String password;
	private Date created;
	
	public User(int id, String mail, String phoneNumber, String firstName, String lastName, UserTypeEnum userType,
			int addressId, String password, Date create) {
		super();
		this.id = id;
		this.mail = mail;
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.addressId = addressId;
		this.password = password;
		this.created = create;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public Date getCreated(){
		return created;
	}
}
