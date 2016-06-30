package com.fastorder.manager;

import java.util.List;

import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.model.Address;
import com.fastorder.model.Order;
import com.fastorder.model.Shop;
import com.fastorder.model.User;

public interface IUserManager {
	
	public List<User> getUsers();
	public User getUser(String mail);
	public User getUser(int id);
	public List<Shop> getUserShops(String mail);
	public List<Order> getUserOrders(int idUser);
	
	public boolean createUser(String mail, String phoneNumber, String firstName, String lastName, UserTypeEnum userType,
			int addressId, String password);
	public boolean updateUserData(int id, String mail, String phoneNumber, String firstName, String lastName, UserTypeEnum userType,
			int addressId, String password);
	public boolean deleteUser(String mail);
	
	public boolean checkLogin(String mail, String password);
	public boolean forgotPassword(String mail);
	public boolean setNewPassword(String password);
	
	public boolean checkMailExist(String mail);
	

}
