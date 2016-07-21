<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<jsp:directive.page import="com.fastorder.model.Address" />
<html ng-app="fastOrder">
	<jsp:directive.include file="header.jsp" />
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>List Shops</title>
		<script type="text/javascript" src="js/angular.min.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/app.js"></script>
	</head>
	
<body ng-controller="mainCtrl" ng-init='setShops(${shopList }); getBracket();' style="background: #eee" >
	<div id="globalDiv2">
		<div class="container contenu">
			<jsp:directive.include file="navBar.jsp" />
			<br/><br/><br/><br/><br/><br/>
			<p class="noAccountDiv"> Liste des magasins </p>
			<hr><br>	
			<div class="row blocSearchShop">
	    	  <p class="labelBlue col-lg-2 col-md-3 col-sm-6 col-lg-offset-1 col-md-offset-1 col-sm-offset-1"> Faire un filtre </p>
	    	  <div class="col-lg-7 col-md-7 col-sm-5">
	    		<input type="text" class="form-control" placeholder="Filter by..." ng-model="filtre">
	    	  </div> 		
	    	</div><br><br><br>
		    		
			<div class="row blocShop" ng-repeat="res in shopsList | filter:filtre">
			  <div class="col-lg-2 col-md-3 col-sm-4">
			  <img src="images/sushis.jpg" width="170" height="140">
 		  </div>
			   <div class="col-lg-10 col-md-9 col-sm-8">
			  	<p class="noaccountText">
					<strong> Nom : </strong> {{res.name}} <br>
					<strong>Description :</strong> {{res.description}} <br>
					<strong>Type de restauration :</strong> {{res.shopType}} <br><br>
					Avis : (124) <br>
				</p><br>
				<a href="showShopOnMap?idShop={{res.id}}" class="btn btn-success">Voir le restaurant</a>
				<a href="order?idShop={{res.id}}" id="btnShop{{$index}}" class="btn btn-warning">Commander</a>
			  </div><br>
			</div>
		</div>
	</div>
	<jsp:directive.include file="footer.jsp" />