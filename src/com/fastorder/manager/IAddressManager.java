package com.fastorder.manager;

import com.fastorder.model.Address;

public interface IAddressManager {
	
	public boolean createAddress(String street, String number, String zipCode, String city, String country);
	public int getAddressId(String street, String number, String zipCode, String city, String country);
	public Address getAddresssById(int id);

}
