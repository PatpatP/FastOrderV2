package com.fastorder.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.exceptions.FailInputException;
import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.manager.impl.MailManagerImpl;
import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Address;
import com.fastorder.model.Order;
import com.fastorder.model.Shop;
import com.fastorder.model.User;
import com.fastorder.utils.Utils;
import com.fastorder.utils.UtilsBdd;
import com.fastorder.utils.ValidateInputField;
import com.mysql.jdbc.Connection;

@WebServlet(
		name = "user-servlet",
		description = "Servlet handling user login",
		urlPatterns={"/login", "/createAccount", "/home", "/signOut", "/myspace", "/myshops", "/updateAccount", "/deleteAccount", "/error404"}
		)

public class UserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4993147831768532172L;

	final static Logger logger = Logger.getLogger(UserServlet.class);

	private UserManagerImpl userManager;
	private AddressManagerImpl addressManager;
	private ShopManagerImpl shopManager;
	private MailManagerImpl mailManager;
	private ValidateInputField validateInputField;

	@Override
	public void init() throws ServletException {
		super.init();
		Connection connection = UtilsBdd.connectBDD();
		userManager = new UserManagerImpl(connection);
		addressManager= new AddressManagerImpl(connection);
		shopManager = new ShopManagerImpl(connection);
		mailManager = new MailManagerImpl();
		validateInputField = new ValidateInputField();
	}

	/**
	 * String : mail de l'utilisateur
	 * List<Product> : la liste des produits dans son panier
	 */


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String uri = request.getRequestURI();
		if(uri.contains("/login")) {
			this.loginUser(request, response);
		} else if(uri.contains("/createAccount")) {
			this.createUser(request, response);
		} else if(uri.contains("/home")){
			this.homePage(request, response);
		} else if(uri.contains("/signOut")){
			this.signOut(request, response);
		} else if(uri.contains("/myspace")){
			this.myspace(request, response);
		} else if(uri.contains("/myshops")){
			this.myshops(request, response);
		} else if(uri.contains("updateAccount")){
			this.updateUserData(request, response);
		} else if(uri.contains("deleteAccount")){
			this.deleteUser(request, response);
		} else {
			this.page404(request, response);
		}
	}

	private void page404(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("action", "Error 404");
		request.getRequestDispatcher("/WEB-INF/html/404.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void createUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		final String mail = request.getParameter("mail");
		final String phoneNumber = request.getParameter("phoneNumber");
		final String firstName = request.getParameter("firstName");
		final String lastName = request.getParameter("lastName");
		final String userType = request.getParameter("userType");
		final String password = request.getParameter("password");

		// Paramï¿½tres de l'objet Address
		final String street = request.getParameter("street");
		final String number = request.getParameter("number");
		final String zipCode = request.getParameter("zipCode");
		final String city = request.getParameter("city");
		final String country = request.getParameter("country");

		if(street== null && number==null && zipCode==null && city==null && country==null && mail==null && phoneNumber==null && firstName==null && lastName==null && password==null){
			request.setAttribute("action", "createAccount");
			request.setAttribute("client", "Client");
			request.setAttribute("merchant", "Merchant");
			request.getRequestDispatcher("/WEB-INF/html/userForm.jsp").forward(request, response);

		} else if(street!="" && number!="" && zipCode!="" && city!="" && country!="" && mail!="" && phoneNumber!="" && firstName!="" && lastName!="" && password!=""){
			List<String> errors = new ArrayList<String>();
			boolean isInputValide = false;

			try {
				validateInputField.validateLastName(lastName);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validateFirstName(firstName);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validateMail(mail);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validatePhoneNumber(phoneNumber);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validateStreetName(street);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validateStreetNumber(number);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validateZipCode(zipCode);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validateCountry(country);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validateCity(city);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			if(isInputValide){
				boolean mailAlreadyExist = userManager.checkMailExist(mail);
				if(mailAlreadyExist){
					errors.add("L'adresse mail est déjà utilisée.");

					String errorsAsJson = Utils.convertJavaToJson(errors);
					System.out.println(errorsAsJson);
					request.setAttribute("errors", errors);
					request.setAttribute("action", "createAccount");
					request.setAttribute("client", "Client");
					request.setAttribute("merchant", "Merchant");
					request.getRequestDispatcher("/WEB-INF/html/userForm.jsp").forward(request, response);
					return;
				} else {
					int addressId = 0;

					addressManager.createAddress(street, number, zipCode, city, country);
					addressId = addressManager.getAddressId(street, number, zipCode, city, country);

					UserTypeEnum userTypeEnum = null;
					if(userType!=null){
						if(userType.equals("Client")){
							userTypeEnum = UserTypeEnum.CLIENT;
						} else if( userType.equals("Merchant")){
							userTypeEnum = UserTypeEnum.MERCHANT;
						}
					}
					try {
						mailManager.confirmSignUp(mail);
						logger.info("Mail pour la création d'un compte utilisateur envoyé");
					} catch (EmailException e) {
						logger.error("Une erreur est survenue lors de l'envoi du mail pour la création d'un compte utilisateur : " + e.getMessage());
					}
					userManager.createUser(mail, phoneNumber, firstName, lastName, userTypeEnum, addressId, password);
					request.setAttribute("msgcreated", "Your account was created, you can sign in");
					response.sendRedirect("login");
					return;
				}
			} else {
				String errorsAsJson = Utils.convertJavaToJson(errors);
				System.out.println(errorsAsJson);
				request.setAttribute("errors", errors);
				request.setAttribute("hasError", "hasError");
				request.setAttribute("action", "createAccount");
				request.setAttribute("client", "Client");
				request.setAttribute("merchant", "Merchant");
				request.getRequestDispatcher("/WEB-INF/html/userForm.jsp").forward(request, response);
				return;
			}
		} else {
			request.setAttribute("error", "Veuillez remplir les champs");
			request.setAttribute("action", "createAccount");
			request.setAttribute("client", "Client");
			request.setAttribute("merchant", "Merchant");
			request.getRequestDispatcher("/WEB-INF/html/userForm.jsp").forward(request, response);
		}
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		final String mail = request.getParameter("mail");
		final String password = request.getParameter("password");

		if(mail!=null && password!=null){
			Map<String, String> errors = new HashMap<String, String>();
			boolean isInputValide = false;

			try {
				validateInputField.validateMail(mail);
				isInputValide = true;
			} catch (Exception e) {
				errors.put("mail", e.getMessage());
				isInputValide = false;
			}

			if(isInputValide){
				boolean isConnected = userManager.checkLogin(mail, password);

				if(isConnected){
					//redirection vers la page home
					User user = userManager.getUser(mail);

					request.getSession().setAttribute("mail", mail);
					request.getSession().setAttribute("userType", user.getUserType().toString());
					request.getSession().setAttribute("userName", user.getFirstName()+" "+user.getLastName());
					response.sendRedirect("home");
					return;
				} else {
					errors.put("connectionFail", "L'adresse mail ou le mot de passe est invalide");
					request.setAttribute("failConnect", "failConnect");
					request.setAttribute("erreurs", errors);
					request.setAttribute("action", "login");
					request.getRequestDispatcher("/WEB-INF/html/signIn.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("erreurs", errors);
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/WEB-INF/html/signIn.jsp").forward(request, response);
				return;
			}
		}
		request.setAttribute("action", "login");
		request.getRequestDispatcher("/WEB-INF/html/signIn.jsp").forward(request, response);
		return;
	}

	private void homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("action", "home");

		List<Shop> shops = shopManager.getShops();
		String shopsToJson = Utils.convertJavaToJson(shops);


		List<Address> shopsAddress = new ArrayList<Address>();
		List<String> tabAddress = new ArrayList<String>();
		if(shops!=null){
			for(Shop shop : shops){
				int addressId = shop.getAddressId();
				Address address = addressManager.getAddresssById(addressId);
				shopsAddress.add(address);

				String adressString = "[\""+shop.getName()+"\" ,"+"\""+address.getNumber()+" "+address.getStreet()+", "+address.getZipCode()+" "+address.getCity()+"\" ,"+"\"/FastOrderV2/order?idShop="+shop.getId()+"\"]";
				tabAddress.add(adressString);
			}

		}
		request.setAttribute("listShopAddress", tabAddress);
		request.setAttribute("listShop", shopsToJson);

		request.getRequestDispatcher("/WEB-INF/html/home.jsp").forward(request, response);
	}




	private void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("userName");
		response.sendRedirect("home");
	}

	private void myspace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String mail = (String) request.getSession().getAttribute("mail");

		if(mail!=null){
			User user = userManager.getUser(mail);
			Address address = addressManager.getAddresssById(user.getAddressId());
			String userAsJson = Utils.convertJavaToJson(user);
			String addressAsJson = Utils.convertJavaToJson(address);

			//Commande effectuï¿½e par l'utilisateur
			List<Order> myOrders = userManager.getUserOrders(user.getId());

			List<List<String>> userOrders = new ArrayList<List<String>>();

			for(Order order :  myOrders){
				Shop shop = shopManager.getShop(order.getShopId());
				List<String> orderWithShop = new ArrayList<String>();
				orderWithShop.add(order.getId()+"");
				orderWithShop.add(order.getEstimatedTime().toString());
				orderWithShop.add(order.getStatus().toString());
				orderWithShop.add(order.getCreated().toString());
				orderWithShop.add(order.getPriceTotal()+"");
				orderWithShop.add(shop.getName());
				userOrders.add(orderWithShop);
			}

			String userOrdersAsJson = Utils.convertJavaToJson(userOrders);
			System.out.println(userOrdersAsJson);

			request.setAttribute("action", "myspace");
			request.getSession().setAttribute("userType", user.getUserType().toString());
			request.setAttribute("user", userAsJson);
			request.setAttribute("userAddress", addressAsJson);
			request.setAttribute("myOrders", userOrdersAsJson);

			request.getRequestDispatcher("/WEB-INF/html/myspace.jsp").forward(request, response);
		}
	}

	private void myshops(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String mail = (String) request.getSession().getAttribute("mail");

		if(mail!=null){
			User user = userManager.getUser(mail);

			List<Shop> userShops = userManager.getUserShops(mail);
			String userShopsString = Utils.convertJavaToJson(userShops);

			//Commande reï¿½u par le magasin
			List<List<String>> shopOrders = new ArrayList<List<String>>();
			List<Shop> usersShop = userManager.getUserShops(mail);
			for (Shop shop : usersShop) {
				List<Order> receivedOrders = shopManager.getReceivedOrder(shop.getId());
				for (Order order : receivedOrders) {
					List<String> orderWithShop = new ArrayList<String>();
					orderWithShop.add(shop.getId()+"");
					orderWithShop.add(shop.getName());
					orderWithShop.add(order.getId()+"");
					orderWithShop.add(order.getEstimatedTime().toString());
					orderWithShop.add(order.getStatus().toString());
					orderWithShop.add(order.getCreated().toString());
					orderWithShop.add(order.getPriceTotal()+"");
					shopOrders.add(orderWithShop);
				}
			}

			String shopOrdersAsJson = Utils.convertJavaToJson(shopOrders);
			System.out.println(shopOrdersAsJson);

			request.setAttribute("action", "myspace");
			request.setAttribute("userShop", userShopsString);
			if(user.getUserType().equals(UserTypeEnum.MERCHANT)){
				request.setAttribute("shopOrders", shopOrdersAsJson);
			}
			request.getRequestDispatcher("/WEB-INF/html/myShops.jsp").forward(request, response);
		}

	}

	private void updateUserData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String oldMail = (String) request.getSession().getAttribute("mail");
		User user = userManager.getUser(oldMail);
		Address userAdress = addressManager.getAddresssById(user.getAddressId());
		String message = null;

		final String mail = request.getParameter("mail");
		final String phoneNumber = request.getParameter("phoneNumber");
		final String firstName = request.getParameter("firstName");
		final String lastName = request.getParameter("lastName");
		final String userType = request.getParameter("userType");
		final String mdp = request.getParameter("password");

		// Paramï¿½tres de l'objet Address
		final String street = request.getParameter("street");
		final String number = request.getParameter("number");
		final String zipCode = request.getParameter("zipCode");
		final String city = request.getParameter("city");
		final String country = request.getParameter("country");

		String newMail = null;
		String newPhoneNumber = null;
		String newFirstName = null;
		String newLastName = null;
		String newMdp = null;

		String newStreet = null;
		String newNumber = null;
		String newZipCode = null;
		String newCity = null;
		String newCountry = null;

		if(street== null && number==null && zipCode==null && city==null && country==null && mail==null && phoneNumber==null && firstName==null && lastName==null && mdp==null){
			request.setAttribute("action", "updateAccount");
			request.setAttribute("client", "Client");
			request.setAttribute("merchant", "Merchant");
			request.getRequestDispatcher("/WEB-INF/html/updateUserForm.jsp").forward(request, response);

		} else {
			if(mail!=""){
				newMail = mail;
			} else {
				newMail = user.getMail();
			}

			if(phoneNumber!=""){
				newPhoneNumber = phoneNumber;
			} else {
				newPhoneNumber = user.getPhoneNumber();
			}

			if(firstName!=""){
				newFirstName = firstName;
			} else {
				newFirstName = user.getFirstName();
			}

			if(lastName!=""){
				newLastName = lastName;
			} else {
				newLastName = user.getLastName();
			}

			if(mdp!=""){
				newMdp = BCrypt.hashpw(mdp, BCrypt.gensalt());
			} else {
				newMdp = user.getPassword();
			}

			if(street!=""){
				newStreet = street;
			} else {
				newStreet = userAdress.getStreet();
			}

			if(number!=""){
				newNumber = number;
			} else {
				newNumber = userAdress.getNumber();
			}

			if(zipCode!=""){
				newZipCode = zipCode;
			} else {
				newZipCode = userAdress.getZipCode();
			}

			if(city!=""){
				newCity = city;
			} else {
				newCity = userAdress.getCity();
			}

			if(country!=""){
				newCountry = country;
			} else {
				newCountry = userAdress.getCountry();
			}

			UserTypeEnum userTypeEnum = null;
			if(userType!=null){
				if(userType.equals("Client")){
					userTypeEnum = UserTypeEnum.CLIENT;
				} else if( userType.equals("Merchant")){
					userTypeEnum = UserTypeEnum.MERCHANT;
				}
			}

			boolean isAddressUpdated = addressManager.updateAddress(user.getAddressId(), newStreet, newNumber, newZipCode, newCity, newCountry);
			boolean isUserUpdated = userManager.updateUserData(user.getId(), newMail, newPhoneNumber, newFirstName, newLastName, userTypeEnum, user.getAddressId(), newMdp);

			if(isAddressUpdated && isUserUpdated){
				message = "Vos informations ont été mise à jour";
			} else {
				message = "Echec lors de la mise à jour de vos informations";
			}
			request.setAttribute("message", message);
			response.sendRedirect("myspace");
		}

	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String mail = (String) request.getSession().getAttribute("mail");

		boolean isDeleted = userManager.deleteUser(mail);

		if(isDeleted){
			request.getSession().removeAttribute("userName");
			response.sendRedirect("home");
		} else {
			response.sendRedirect("myspace");
		}
	}
}
