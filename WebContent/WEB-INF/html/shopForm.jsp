<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<jsp:directive.page import="com.fastorder.model.Address" />
<html ng-app="fastOrder">
<jsp:directive.include file="header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inscription</title>
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript" src="js/app.js"></script>
</head>
<body ng-controller="myspaceCtrl" ng-init='getShopType(${shopType })'>

	<div id="globalDiv2">
	<div class="container contenu">
	<jsp:directive.include file="navBar.jsp" />
	<br/><br/><br/><br/><br/><br />
	
	<p class="noAccountDiv"> Création de votre restaurant </p>
	<p class="noaccountText">Pour créer votre restaurant, veuillez remplir toutes les informations ci-dessous<br><br>
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
			<form method="post" action="${action} enctype="multipart/form-data">
				<div class="col-lg-5 col-md-5">
				<div class="textInscription">Votre Restaurant</div>
				<hr><br>
					<div class="form-group">
						<label for="exampleInputEmail1">Type de restaurant</label> <select
							class="form-control" name="shopType">
							<option ng-repeat="(key, value) in shopType" value="{{value}}">{{value}}</option> 
						</select>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Name</label> <input
							type="text" class="form-control" name="name"
							placeholder="Name">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Description</label> <input
							type="text" class="form-control" name="description"
							placeholder="Description">
					</div>
					<div class="form-group">
				       <label>Image</label> <input
							type="file" class="form-control" name="image">
					</div>
				</div>
				<div class="col-lg-5 col-md-5 col-lg-offset-1 col-md-offset-1">
					<div class="textInscription">Votre adresse</div>
					<hr><br>
					<div class="form-group">
						<label for="exampleInputEmail1">Street</label> <input type="text"
							class="form-control" name="street" placeholder="Street">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Number</label> <input type="text"
							class="form-control" name="number" placeholder="Number">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Zip Code</label> <input
							type="text" class="form-control" name="zipCode"
							placeholder="Zip Code">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">City</label> <input type="text"
							class="form-control" name="city" placeholder="City">
					</div>
					
				</div>
				<div class="col-lg-11 col-md-11 col-sm-11">
					<br>
					<br>
					<button type="submit"
						class="btn btn-danger col-lg-3 col-md-3">Ajouter magasin</button>
				</div>
			</form>
		</div>
	</div>
	</div>
	</div>
	<jsp:directive.include file="footer.jsp" />