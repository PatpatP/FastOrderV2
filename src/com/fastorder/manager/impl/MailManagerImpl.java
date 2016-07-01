package com.fastorder.manager.impl;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import com.fastorder.manager.IMailManager;
import com.fastorder.utils.Mailing;

public class MailManagerImpl implements IMailManager{

	private Mailing mailing = new Mailing();

	public MailManagerImpl(){

	}

	@Override
	public void confirmSignUp(String mailDest)  throws EmailException{
		String object = "FASTORDER - Confirmation d'inscription";
		String message = "Votre inscription a été confirmé";
		
		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}

	@Override
	public void confirmCreateShop(String mailDest)  throws EmailException{
		String object = "FASTORDER - Confirmation création de votre magasin";
		String message = "Votre magasin a été créé";
		
		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
	
	@Override
	public void confirmUpdateShop(String mailDest)  throws EmailException{
		//TODO
	}
	
	@Override
	public void confirmDeleteShop(String mailDest)  throws EmailException{
		//TODO
	}

	@Override
	public void confirmCreateProduct(String mailDest)  throws EmailException{
		String object = "FASTORDER - Confirmation d'ajout d'un produit";
		String message = "Le produit a bien été ajouté";
		
		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
	
	@Override
	public void confirmUpdateProduct(String mailDest) throws EmailException{
		String object = "FASTORDER - Mise à jour du produit";
		String message = "Le produit a bien été mis à jour";
		
		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
	
	@Override
	public void confirmDeleteProduct(String mail) throws EmailException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmCreateOrder(String mailDest, double totalPrice) throws EmailException {
		String object = "FASTORDER - Confirmation de commande";
		String message = "Votre commande de "+totalPrice+" a été confirmé";

		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
	
	@Override
	public void sendReceivedOrder(String mailDest) throws EmailException {
		String object = "FASTORDER - Commande reçue";
		String message = "Vous avez reçu une nouvelle commande";

		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}

	@Override
	public void confirmFinishOrder(String mailDest) throws EmailException{
		String object = "FASTORDER - Rappel de votre commande";
		String message = "Votre commande est prête, vous pouvez venir le récupérer";

		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
}
