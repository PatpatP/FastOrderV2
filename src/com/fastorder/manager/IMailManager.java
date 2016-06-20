package com.fastorder.manager;

import org.apache.commons.mail.EmailException;

public interface IMailManager {
	
	public void confirmSignUp(String mailDest) throws EmailException;
	
	public void confirmCreateShop(String mailDest) throws EmailException;
	public void confirmUpdateShop(String mailDest) throws EmailException;
	public void confirmDeleteShop(String mailDest) throws EmailException;
	
	public void confirmCreateProduct(String mailDest) throws EmailException;
	public void confirmUpdateProduct(String mailDest) throws EmailException;
	public void confirmDeleteProduct(String mailDest) throws EmailException;
	
	public void confirmCreateOrder(String mailDest, float totalPrice)  throws EmailException;
	public void sendReceivedOrder(String mailDest) throws EmailException;
	public void confirmFinishOrder(String mailDest) throws EmailException;
}
