package com.fastorder.utils;

public class ValidateInputField {
	
	public ValidateInputField() {
	}

	public void validateFirstName(String firstName) throws Exception{
		if ( firstName != null && firstName.trim().length() < 3 ) {
			if(!firstName.matches("[a-zA-Z-]")){
				throw new Exception( "Le nom utilisateur doit contenir au moins 3 caractères" );
			}
		}
	}

	public void validateLastName(String lastName) throws Exception{
		if ( lastName != null && lastName.trim().length() < 3 ) {
			if(!lastName.matches("[a-zA-Z-]")){
				throw new Exception( "Le prénom utilisateur doit contenir au moins 3 caractères" );
			}
		}
	}

	public void validateMail(String mail) throws Exception{
		if ( mail != null && mail.trim().length() != 0 ) {
			if ( !mail.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
				throw new Exception( "Adresse mail invalide" );
			}
		}
	}

	public void validatePhoneNumber(String phoneNumber)throws Exception{
		if ( phoneNumber != null && phoneNumber.trim().length() == 10 ) {
			if ( !phoneNumber.matches( "^0[0-9]([-.\\s]?\\d{2}){4}" ) ) {
				throw new Exception( "Merci de saisir un numéro de téléphone valide" );
			}
		}
	}

	public void validateCountry(String country) throws Exception{
		if ( country != null) {
			if(!country.matches("[a-zA-Z-]*")){
				throw new Exception( "Le nom de pays est invalide");
			}
		}
	}

	public void validateZipCode(String zipCode) throws Exception{
		if ( zipCode != null) {
			if(!zipCode.matches("[0-9]{5}")){
				throw new Exception( "Le code postale ne doit comporter que des chiffres");
			}
		}
	}

	public void validateStreetName(String streetName) throws Exception{
		if ( streetName != null) {
			if(!streetName.matches("[A-Za-z\\s]*")){
				throw new Exception( "Le nom de rue est invalide");
			}
		}
	}

	public void validateStreetNumber(String streetNumber) throws Exception{
		if ( streetNumber != null) {
			if(!streetNumber.matches("[0-9]")){
				throw new Exception( "Le numéro de rue est invalide");
			}
		}
	}
	
	public void validateCity(String city) throws Exception{
		if ( city != null) {
			if(!city.matches("[a-zA-Z-]*")){
				throw new Exception( "La ville saisie est invalide");
			}
		}
	}
	
	public void validateName(String name) throws Exception{
		if ( name != null) {
			if(!name.matches("[0-9a-zA-Z-\\s]")){
				throw new Exception( "Le nom saisie est invalide");
			}
		}
	}
	
	public void validateDescription(String description) throws Exception{
		if ( description != null) {
			if(!description.matches("[0-9a-zA-Z-\\s]")){
				throw new Exception( "Le description saisie est invalide");
			}
		}
	}
	
}
