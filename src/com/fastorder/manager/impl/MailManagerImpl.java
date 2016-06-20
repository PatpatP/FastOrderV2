package com.fastorder.manager.impl;

import org.apache.commons.mail.EmailAttachment;
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
		String object = "ALLORESTO.FR - Confirmation d'inscription";
		String message = "Votre inscription a �t� confirm�";
		
		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}

	@Override
	public void confirmCreateShop(String mailDest)  throws EmailException{
		String object = "FASTORDER - Confirmation cr�ation de votre magasin";
		String message = "Votre magasin a �t� cr��";
		
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
		String object = "ALLORESTO.FR - Confirmation d'ajout d'un produit";
		String message = "Le produit a bien �t� ajout�";
		
		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
	
	@Override
	public void confirmUpdateProduct(String mailDest) throws EmailException{
		String object = "ALLORESTO.FR - Mise � jour du produit";
		String message = "Le produit a bien �t� mis � jour";
		
		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
	
	@Override
	public void confirmDeleteProduct(String mail) throws EmailException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmCreateOrder(String mailDest, float totalPrice) throws EmailException {
		String object = "ALLORESTO.FR - Confirmation de commande";
		String message = "Votre commande de "+totalPrice+" a �t� confirm�";

		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
	
	@Override
	public void sendReceivedOrder(String mailDest) throws EmailException {
		String object = "ALLORESTO.FR - Commande re�ue";
		String message = "Vous avez re�u une nouvelle commande";

		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}

	@Override
	public void confirmFinishOrder(String mailDest) throws EmailException{
		String object = "ALLORESTO.FR - Rappel de votre commande";
		String message = "Votre commande est pr�te, vous pouvez venir le r�cup�rer";

		MultiPartEmail email = null;
		email = mailing.createMail(mailDest, object, message);
		email.send();
	}
}
