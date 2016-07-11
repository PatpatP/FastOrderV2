package com.fastorder.utils;

import com.fastorder.exceptions.FailInputException;

public class ValidateInputField {
	
	public ValidateInputField() {
	}

	public void validateFirstName(String firstName) throws FailInputException{
		if ( firstName != null && firstName.trim().length() < 3 ) {
			if(!firstName.matches("[a-zA-Z-]")){
				throw new FailInputException( "Le nom utilisateur doit contenir au moins 3 caractères" );
			}
		}
	}

	public void validateLastName(String lastName) throws FailInputException{
		if ( lastName != null && lastName.trim().length() < 3 ) {
			if(!lastName.matches("[a-zA-Z-]")){
				throw new FailInputException( "Le prénom utilisateur doit contenir au moins 3 caractères" );
			}
		}
	}

	public void validateMail(String mail) throws FailInputException{
		if ( mail != null && mail.trim().length() != 0 ) {
			if ( !mail.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
				throw new FailInputException( "Adresse mail invalide" );
			}
		}
	}

	public void validatePhoneNumber(String phoneNumber)throws FailInputException{
		if ( phoneNumber != null && phoneNumber.trim().length() == 10 ) {
			if ( !phoneNumber.matches( "^0[0-9]([-.\\s]?\\d{2}){4}" ) ) {
				throw new FailInputException( "Merci de saisir un numéro de téléphone valide" );
			}
		}
	}

	public void validateCountry(String country) throws FailInputException{
		if ( country != null) {
			if(!country.matches("[a-zA-Z-]*")){
				throw new FailInputException( "Le nom de pays est invalide");
			}
		}
	}

	public void validateZipCode(String zipCode) throws FailInputException{
		if ( zipCode != null) {
			if(!zipCode.matches("[0-9]{5}")){
				throw new FailInputException( "Le code postale ne doit comporter que des chiffres");
			}
		}
	}

	public void validateStreetName(String streetName) throws FailInputException{
		if ( streetName != null) {
			if(!streetName.matches("[A-Za-z\\s]*")){
				throw new FailInputException( "Le nom de rue est invalide");
			}
		}
	}

	public void validateStreetNumber(String streetNumber) throws FailInputException{
		if ( streetNumber != null) {
			if(!streetNumber.matches("[0-9]")){
				throw new FailInputException( "Le numéro de rue est invalide");
			}
		}
	}
	
	public void validateCity(String city) throws FailInputException{
		if ( city != null) {
			if(!city.matches("[a-zA-Z-]*")){
				throw new FailInputException( "La ville saisie est invalide");
			}
		}
	}
	
	public void validateName(String name) throws FailInputException{
		if ( name != null) {
			if(!name.matches("[0-9a-zA-Z-\\s]")){
				throw new FailInputException( "Le nom saisie est invalide");
			}
		}
	}
	
	public void validateDescription(String description) throws FailInputException{
		if ( description != null) {
			if(!description.matches("[0-9a-zA-Z-\\s]")){
				throw new FailInputException( "Le description saisie est invalide");
			}
		}
	}
	
}
