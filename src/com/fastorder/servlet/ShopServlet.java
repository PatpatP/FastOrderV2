package com.fastorder.servlet;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.exceptions.FailInputException;
import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.manager.impl.MailManagerImpl;
import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Address;
import com.fastorder.model.Product;
import com.fastorder.model.Shop;
import com.fastorder.model.User;
import com.fastorder.utils.ConstUtils;
import com.fastorder.utils.Utils;
import com.fastorder.utils.UtilsBdd;
import com.fastorder.utils.ValidateInputField;
import com.mysql.jdbc.Connection;

@WebServlet(
		name = "shop-servlet",
		description = "Servlet handling shop function",
		urlPatterns={"/shops", "/createShop", "/updateShop", "/deleteShop",
				"/createProduct", "/updateProduct", "/deleteProduct", "/showShopOnMap", "/getImageShop", "/getImageProduct", "/manageShop"}
		)

@MultipartConfig(
		maxFileSize=1024*1024*10,      // 10MB
		maxRequestSize=1024*1024*50)   // 50MB
public class ShopServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3798116206381293087L;

	final static Logger logger = Logger.getLogger(ShopServlet.class);

	private UserManagerImpl userManager;
	private AddressManagerImpl addressManager;
	private ShopManagerImpl shopManager;
	private ValidateInputField validateInputField;
	private MailManagerImpl mailManager;

	private static final String SAVE_DIR = "img_shop";

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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String uri = request.getRequestURI();
		if(uri.contains("/createShop")){
			this.createShop(request, response);
		} else if(uri.contains("/manageShop")){
			this.manageShop(request, response);
		} else if(uri.contains("/deleteShop")){
			this.deleteShop(request, response);
		} else if(uri.contains("/createProduct")){
			this.createProduct(request, response);
		} else if(uri.contains("/updateProduct")){
			this.updateProduct(request, response);
		} else if(uri.contains("/deleteProduct")){
			this.deleteProduct(request, response);
		} else if(uri.contains("/shops")){
			this.shopsPage(request, response);
		}  else if(uri.contains("/showShopOnMap")){
			this.showShopOnMap(request, response);
		} else if(uri.contains("/getImageShop")){
			this.getShopImage(request, response);
		} else if(uri.contains("/getImageProduct")){
			this.getProductImage(request, response);
		} else if(uri.contains("/updateShop")){
			this.updateShop(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void createShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String mail = (String) request.getSession().getAttribute("mail");

		final String name = request.getParameter("name");
		final String description = request.getParameter("description");
		final String shopType = request.getParameter("shopType");

		final String street = request.getParameter("street");
		final String number = request.getParameter("number");
		final String zipCode = request.getParameter("zipCode");
		final String city = request.getParameter("city");
		final String country = request.getParameter("country");
		
		// Exemple de code à mettre dans : shopManager.createShop() 
      
		
		if(name!=null && description!=null && shopType!=null && street!=null && number!=null && zipCode!=null && city!=null && country!=null){

			//treatmentImage(request);

			boolean isInputValide = false;
			List<String> errors = new ArrayList<String>();

			try {
				validateInputField.validateName(name);
				isInputValide = true;
			} catch (FailInputException e) {
				errors.add(e.getMessage());
				isInputValide = false;
			}

			try {
				validateInputField.validateDescription(description);
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
				addressManager.createAddress(street, number, zipCode, city, country);
				int addressId = addressManager.getAddressId(street, number, zipCode, city, country);

				User user = userManager.getUser(mail);
				int userId = user.getId();
				
				String[] supportedContentTypes = {"image/jpeg", "image/png", "image/jpg"};

		        String savePath = ConstUtils.IMAGE_PATH;
		         
		        File fileSaveDir = new File(savePath);
		        if (!fileSaveDir.exists()) {
		            fileSaveDir.mkdir();
		        }
		        String fileName=null; 
		        for (Part part : request.getParts()) {
		            fileName = extractFileName(part);
		            String contentType = part.getContentType();
		            
		            if(fileName == null || fileName.isEmpty()) continue;
		            if (contentType == null || contentType.isEmpty()) continue;
		            if (!Arrays.asList(supportedContentTypes).contains(contentType)) continue;
		            
		            part.write(savePath + File.separator + fileName);
		            shopManager.createShop(name, description, shopType, userId, addressId, fileName);
		            System.out.println(savePath + File.separator + fileName);
		            
		        }

				 
				try {
					mailManager.confirmCreateShop(mail);
					logger.info("Mail pour la cr�ation d'un magasin envoy�");
				} catch (EmailException e) {
					logger.error("Une erreur est survenue lors de l'envoi du mail pour la cr�ation d'un magasin : " + e.getMessage());
				}
				response.sendRedirect("myshops");
			} else {
				List<String> shopInfo = new ArrayList<String>();
				shopInfo.add(ShopTypeEnum.CHINOIS.toString());
				shopInfo.add(ShopTypeEnum.JAPONAIS.toString());
				String shopTypeAsJson = Utils.convertJavaToJson(shopInfo);

				request.setAttribute("errors", errors);
				request.setAttribute("hasError", "hasError");
				request.setAttribute("action", "createShop");
				request.setAttribute("shopType", shopTypeAsJson);
				request.getRequestDispatcher("/WEB-INF/html/shopForm.jsp").forward(request, response);
			}
		} else {
			List<String> shopInfo = new ArrayList<String>();
			shopInfo.add(ShopTypeEnum.CHINOIS.toString());
			shopInfo.add(ShopTypeEnum.JAPONAIS.toString());

			String shopTypeAsJson = Utils.convertJavaToJson(shopInfo);
			request.setAttribute("action", "createShop");
			request.setAttribute("shopType", shopTypeAsJson);
			request.getRequestDispatcher("/WEB-INF/html/shopForm.jsp").forward(request, response);
		}
	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String idProduct = request.getParameter("idProduct");
		final String idShop = request.getParameter("idShop");
		shopManager.deleteProduct(Integer.parseInt(idProduct));
		response.sendRedirect("manageShop?idShop="+idShop);
	}

	private void shopsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Shop> shops = shopManager.getShops();
		String shopsToJson = Utils.convertJavaToJson(shops);

		request.setAttribute("action", "shops");

		System.out.println("Mes shops :"+shopsToJson);

		request.setAttribute("shopList", shopsToJson);
		request.getRequestDispatcher("/WEB-INF/html/listShops.jsp").forward(request, response);
	}

	private void manageShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		final String idShop = request.getParameter("idShop");

		if(idShop != null){
			List<Product> shopProduct = shopManager.getProducts(Integer.parseInt(idShop));
			String shopProductAsJson = Utils.convertJavaToJson(shopProduct);

			Shop shop = shopManager.getShop(Integer.parseInt(idShop));
			String shopAsJson = Utils.convertJavaToJson(shop);

			//TODO A modifie avec une m�thode Utils
			List<String> shopType = new ArrayList<String>();
			shopType.add(ShopTypeEnum.CHINOIS.toString());
			shopType.add(ShopTypeEnum.JAPONAIS.toString());

			String shopTypeAsJson = Utils.convertJavaToJson(shopType);
			System.out.println(shopTypeAsJson);

			List<String> productTypes = Utils.initProductType();

			request.setAttribute("action", "manageShop");
			request.setAttribute("idShop", idShop);
			request.setAttribute("actionAddProduct", "createProduct");
			request.setAttribute("actionUpdateProduct", "updateProduct");
			request.setAttribute("products", shopProductAsJson);
			request.setAttribute("shopType", shopTypeAsJson);
			request.setAttribute("shopInfo", shopAsJson);
			request.setAttribute("productType", productTypes);
			request.getRequestDispatcher("/WEB-INF/html/manageShop.jsp").forward(request, response);
		}
	}

	private void createProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String idShop = request.getParameter("idShop");
		final String mail = (String) request.getSession().getAttribute("mail");
		final String productType = request.getParameter("typeProduit");
		final String name = request.getParameter("name");
		final String description = request.getParameter("description");
		final String price = request.getParameter("price");

		if(productType!=null && name!=null && description!=null && price!=null){
			shopManager.createProduct(productType, name, description, Float.parseFloat(price), Integer.parseInt(idShop));

			try {
				mailManager.confirmCreateProduct(mail);
				logger.info("Mail pour la cr�ation d'un produit envoy�");
			} catch (EmailException e) {
				logger.error("Une erreur est survenue lors de l'envoi du mail pour la cr�ation d'un produit : " + e.getMessage());
			}

			request.setAttribute("action", "createProduct");
			response.sendRedirect("manageShop?idShop="+idShop);
		}
	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String idProduct = request.getParameter("idProduct");
		System.out.println("Produit a modif " + idProduct);
		final String idShop = request.getParameter("idShop");
		final String mail = (String) request.getSession().getAttribute("mail");
		final String productType = request.getParameter("typeProduit");
		final String name = request.getParameter("name");
		final String description = request.getParameter("description");
		final String price = request.getParameter("price");

//		if(productType!=null && name!=null && description!=null && price!=null){
			shopManager.updateProduct(Integer.parseInt(idProduct), productType, name, description, Float.parseFloat(price), Integer.parseInt(idShop));

			try {
				mailManager.confirmUpdateProduct(mail);
				logger.info("Mail pour la mise � jour d'un produit envoy�");
			} catch (EmailException e) {
				logger.error("Une erreur est survenue lors de l'envoi du mail pour la mise � jour d'un produit : " + e.getMessage());
			}

			request.setAttribute("action", "updateProduct");
			response.sendRedirect("manageShop?idShop="+idShop);
//		}
	}

	private void showShopOnMap(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idShop = request.getParameter("idShop");
		if(idShop!=null){
			Shop shop = shopManager.getShop(Integer.parseInt(idShop));
			int addresseId = shop.getAddressId();
			Address addressShop = addressManager.getAddresssById(addresseId);
			String scheme = "http";
			String authority = "maps.google.fr";
			String path = "/maps";
			String number = addressShop.getNumber();
			String street = addressShop.getStreet();
			String zipCode = addressShop.getZipCode();
			String city = addressShop.getCity();
			String query = "f=q&hl=fr&q="+number+" "+street+" ,"+zipCode+" "+city ;
			if(Desktop.isDesktopSupported()) {
				if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
					try {
						URI uri = new URI(scheme, authority, path, query, null);
						System.out.println(uri.toASCIIString());
						java.awt.Desktop.getDesktop().browse(uri);
					} catch (URISyntaxException ex) {
						logger.error("Erreur - URL d'affichage de la map erron�");
					} catch (IOException ex) {
						logger.error("Erreur - Chargement de la map �chou�");
					}
				} else {
				}
			} else {
			}
			response.sendRedirect("shops");
		}
	}


	private void deleteShop(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String idShop = request.getParameter("idShop");
		Shop shop = shopManager.getShop(Integer.parseInt(idShop));
		int addressId = shop.getAddressId();
		addressManager.deleteAddress(addressId);
		shopManager.deleteShop(Integer.parseInt(idShop));
		response.sendRedirect("myshops");
	}
	
	private void updateShop(HttpServletRequest request, HttpServletResponse response) {
		
	}

	private void getShopImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String idShop = request.getParameter("shopId");
		
		shopManager.getShop(Integer.parseInt(idShop));
		
		File file = new File(ConstUtils.IMAGE_PATH);
		FileInputStream fis = new FileInputStream(file);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum); 
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        }
 
        byte[] bytes = bos.toByteArray();
        fis.close();
		
		
		
		
	}
	
	private void getProductImage(HttpServletRequest request, HttpServletResponse response){
		//R�cup�rer l'id du produit
		
	}
	
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}
	
}
