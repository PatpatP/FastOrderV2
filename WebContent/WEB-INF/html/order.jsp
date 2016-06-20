<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<jsp:directive.page import="com.fastorder.model.Address" />
<jsp:directive.page import="com.fastorder.utils.Utils" />
<html ng-app="fastOrder">
	<jsp:directive.include file="header.jsp" />
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>List Shops</title>
		<script type="text/javascript" src="js/angular.min.js"></script>
		<script type="text/javascript" src="js/app.js"></script>
	</head>
	
<body ng-controller="mainCtrl" ng-init='setProducts(${shopsProducts })' style="background: #eee">
	<div id="globalDiv2">
		<div class="container contenu">
			<jsp:directive.include file="navBar.jsp" />
			<br/><br/><br/><br/><br/><br/>
	
			<p class="noAccountDiv"> Liste des plats </p>
			<hr><br>	
			<div class="row">
			
				<!-- Bloc Products -->
				
			   <div class="col-lg-7 col-md-7 col-sm-7">
					<div class="blocShop" ng-repeat="res in products">
					  <div class="">
					  	<p class="noaccountText">
							Nom : {{res.name}} <br>
							Description : {{res.description}} <br>
							Price : <span style="font-weight:bold; color: green">{{res.price}} &#128;</span><br>
						</p><br>
						 <%  if(request.getSession().getAttribute("userName")!=null){
     					 %>
						<button class="btn btn-success" ng-click='addToBracket(res.id, res.name, res.price)'> Ajouter au panier</button>
						<% } else { %>
						<a href="createAccount" class="btn btn-success" role="button"'> Ajouter au panier</a>
						<% } %>
					  </div>
					</div>
				</div>
				
				<!--  Bloc Bracket -->
				
				<div class="col-lg-4 col-md-4 col-ms-4 col-lg-offset-1 col-md-offset-1 col-sm-offset-1  blocBracket">
					<div class="titleBracket">
						<p><span class="glyphicon glyphicon-shopping-cart col-lg-4 col-md-4 col-sm-4 iconShop"> </span> <span class="titleBrckt"> Mon Panier </span></p>
					</div>
				 	<div class="containerBracket">
				 		<div ng-repeat="brck in bracket"> <p class="col-lg-8 col-md-8 col-sm-8">{{brck.name}}</p> <div class="col-lg-4 col-md-4 col-sm-4"><button ng-click="removeProduct(brck.id)" class="btnBrkt">-</button> [ {{brck.quantity}} ] <button ng-click="addToBracket(brck.id, brck.name, brck.price)" class="btnBrkt">+</button></div></div>   
				 	</div>
				 	<div class="footBracket">
				 		<p class="col-lg-5 col-md-5 col-sm-5"> Prix total : </p> <p class="col-lg-5 col-md-5 col-sm-5" align="right">{{prixTotal}} &#128;</p>
				 	</div>
				 	<a class="btn btn-primary col-lg-12 col-md-12 col-sm-12" href="validateBracket?bracket={{bracketId}}" ng-if="bracket.length>0"> Valider panier</a>
				 	<br><br>
				</div>
		 </dsiv>
		</div>
	</div>
	<jsp:directive.include file="footer.jsp" />
