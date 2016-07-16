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
	
	<p class="noAccountDiv"> Création de compte </p>
	<p class="noaccountText">Pour vous inscrire, remplissez toutes les informations ci-dessous<br><br>
	
	<% if(request.getAttribute("hasError") != null){ %>
	<div class="alert alert-danger" role="alert">
		<ul>
			<c:forEach var="error" items="${errors }">
				<li>${error }</li>
			</c:forEach>
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
							class="form-control" name="userType">
							<option value="${client}">${client}</option>
							<option value="${merchant}">${merchant}</option>
						</select>
					</div>
					<div class="form-group">
						 <input
							type="text" class="form-control" name="firstName"
							placeholder="First Name">
					</div>
					<div class="form-group">
						 <input
							type="text" class="form-control" name="lastName"
							placeholder="Last Name">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" name="mail" placeholder="Mail">
					</div>
					<div class="form-group">
						<input
							type="password" class="form-control" name="password"
							placeholder="Password">
					</div>
					<div class="form-group">
						<input
							type="text" class="form-control" name="phoneNumber"
							placeholder="Phone Number">
					</div>
				</div>
				<div class="col-lg-5 col-md-5 col-lg-offset-1 col-md-offset-1">
					<div class="textInscription">Votre adresse</div>
					<hr><br>
					<div class="form-group">
						<label for="exampleInputEmail1">(*) Remplissez tous les champs</label> <input type="text"
							class="form-control" name="street" placeholder="Street">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" name="number" placeholder="Number">
					</div>
					<div class="form-group">
						<input
							type="text" class="form-control" name="zipCode"
							placeholder="Zip Code">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" name="city" placeholder="City">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" name="country" placeholder="Country">
					</div>
				</div>
				<div class="col-lg-11 col-md-11 col-sm-11">
					<br>
					<br>
					<button type="submit"
						class="btn btnIndex col-lg-3 col-md-3">S'inscrire</button>
				</div>
			</form>
		</div>
	</div>
	</div>
	</div>
	<jsp:directive.include file="footer.jsp" />