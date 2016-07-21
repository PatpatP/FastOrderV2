<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<jsp:directive.page import="com.fastorder.model.Address" />
<html>
<jsp:directive.include file="header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inscription</title>
</head>
<body>
	<div id="globalDiv2">
	<div class="container contenu">
	<jsp:directive.include file="navBar.jsp" />
	<br/><br/><br/><br/><br/><br />
	
	<p class="noAccountDiv"> Mise à jour du compte </p>
	<p class="noaccountText">Afin de mettre à jour vos informations, veuillez remplir seulement les champs à modifier<br><br>
	
	<% if(request.getAttribute("hasError") != null){ %>
	<div class="alert alert-danger" role="alert">
		<ul>
			<c:forEach var="error" items="${errors }">
				<li>${error }</li>
			</c:forEach>
		</ul>
	</div>
	<% } else if(request.getAttribute("error") != null){ %>
	<div class="alert alert-danger" role="alert">
		<ul>
				<li>${error }</li>
		</ul>
	</div>
	<% } %>
	
	<div class="row">
		<div class="blocInfoInscription">
			<form method="get" action="${action}">
				<div class="col-lg-5 col-md-5">
				<div class="textInscription">Vos Coordonnées</div>
				<hr><br>
					<div class="form-group">
						<label for="exampleInputEmail1">Type de compte</label> <select
							class="form-control" id="userType" name="userType">
							<option id="client" value="${client}">${client}</option>
							<option id="marchant" value="${merchant}">${merchant}</option>
						</select>
					</div>
					<div class="form-group">
						 <input
							type="text" class="form-control" id="firstName" name="firstName"
							placeholder="First Name">
					</div>
					<div class="form-group">
						 <input
							type="text" class="form-control" id="lastName" name="lastName"
							placeholder="Last Name">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" name="mail" id="mail" placeholder="Mail">
					</div>
					<div class="form-group">
						<input
							type="password" class="form-control" id="password" name="password"
							placeholder="Password">
					</div>
					<div class="form-group">
						<input
							type="text" class="form-control" id="phoneNumber" name="phoneNumber"
							placeholder="Phone Number">
					</div>
				</div>
				<div class="col-lg-5 col-md-5 col-lg-offset-1 col-md-offset-1">
					<div class="textInscription">Votre adresse</div>
					<hr><br>
					<div class="form-group">
						<label for="exampleInputEmail1">(*) Remplissez tous les champs</label> <input type="text"
							class="form-control" id="street" name="street" placeholder="Street">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" id="numberStreet" name="number" placeholder="Number">
					</div>
					<div class="form-group">
						<input
							type="text" class="form-control" id="zipCode" name="zipCode"
							placeholder="Zip Code">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" id="city" name="city" placeholder="City">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" id="country" name="country" placeholder="Country">
					</div>
				</div>
				<div class="col-lg-11 col-md-11 col-sm-11">
					<br>
					<br>
					<button type="submit"
						class="btn btnIndex col-lg-3 col-md-3" id="btnInscription">Mettre à jour</button>
				</div>
			</form>
		</div>
	</div>
	</div>
	</div>
	<jsp:directive.include file="footer.jsp" />