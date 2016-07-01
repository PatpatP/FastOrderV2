package com.fastorder.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.manager.impl.MailManagerImpl;
import com.fastorder.manager.impl.OrderManagerImpl;
import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Order;
import com.fastorder.model.Product;
import com.fastorder.model.User;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

@WebServlet(
		name = "order-servlet",
		description = "Servlet handling order function",
		urlPatterns={"/order", "/validateBracket", "/updateOrder"}
		) 
public class OrderServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8774038084589808656L;

	final static Logger logger = Logger.getLogger(OrderServlet.class);
	
	private UserManagerImpl userManager;
	private ShopManagerImpl shopManager;
	private OrderManagerImpl orderManager;
	private MailManagerImpl mailManager;
	private HashMap<String, List<Product>> brackets;

	@Override
	public void init() throws ServletException {
		super.init();
		Statement statement = Utils.connectBDD();
		userManager = new UserManagerImpl(statement);
		shopManager = new ShopManagerImpl(statement);
		orderManager = new OrderManagerImpl(statement);
		mailManager = new MailManagerImpl();
		brackets = new HashMap<>();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String uri = request.getRequestURI();
		if(uri.contains("/order")){
			this.orderPage(request, response);
		} else if(uri.contains("/validateBracket")){
			this.validateBracket(request, response);
		}  else if(uri.contains("/updateOrder")){
			this.updateOrderStatus(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void orderPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		final String mail = (String) request.getSession().getAttribute("mail");
		final String shopId = request.getParameter("idShop");
		List<Product> products = shopManager.getProducts(Integer.parseInt(shopId));
		String productsToJson = Utils.convertJavaToJson(products);

		//Créer un panier pour l'utilisateur
		brackets.put(mail, new ArrayList<Product>());

		List<Product> userBracket = brackets.get(mail);
		userBracket.add(shopManager.getProduct(0));


		request.setAttribute("action", "order");
		request.getSession().setAttribute("userBracket", userBracket);
		//		System.out.println(productsToJson);
		request.setAttribute("shopsProducts", productsToJson);
		request.getRequestDispatcher("/WEB-INF/html/order.jsp").forward(request, response);
	}
	

	private void validateBracket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bracket = request.getParameter("bracket");
		final String mail = (String) request.getSession().getAttribute("mail");

		if(mail!=null){
			User user = userManager.getUser(mail);

			int idUser = user.getId();

			String[] bracketFormattedSplitted = Utils.convertStringToTabString(bracket);

			List<Product> brackProducts = orderManager.getProducts(bracketFormattedSplitted);

			if(brackProducts.size()>0){
				int shopId = brackProducts.get(0).getShopId();

				User shopOwner = shopManager.getOwner(shopId);
				String shopOwnerMail = shopOwner.getMail();

				double totalPrice = 0;
				for(Product product : brackProducts){
					totalPrice = totalPrice+product.getPrice();
				}
				try {
					mailManager.confirmCreateOrder(mail, totalPrice);
					mailManager.sendReceivedOrder(shopOwnerMail);
					logger.info("Mail pour la création de la commande envoyé");
					logger.info("Mail pour la réception de la commande envoyé");
				} catch (EmailException e) {
					logger.error("Une erreur est survenue lors de l'envoi du mail pour la création et la réception par le magasin de la commande : " + e.getMessage());
				}
				orderManager.create(brackProducts, EstimatedTimeEnum.FIFTY ,OrderStatusEnum.INPROGRESS, totalPrice, idUser, shopId);
				request.setAttribute("action", "validateBracket");
			}
			response.sendRedirect("myspace");
		}
	}

	
	private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idOrder = request.getParameter("idOrder");

		if(idOrder!=null){
			try {
				
				Order order = orderManager.getOrder(Integer.parseInt(idOrder));
				int userId = order.getUserId();
				User user = userManager.getUser(userId);
				mailManager.confirmFinishOrder(user.getMail());
				logger.info("Mail pour la mise à jour de la commande envoyé");
			} catch (EmailException e) {
				logger.error("Une erreur est survenue lors de l'envoi du mail pour la mise à jour de la commande : " + e.getMessage());
			}

			orderManager.updateOrderToInProgress(Integer.parseInt(idOrder));
			response.sendRedirect("myspace");
		}
	}

}
