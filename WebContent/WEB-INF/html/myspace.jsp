<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<jsp:directive.page import="com.fastorder.model.Address" />
<html ng-app="fastOrder">
<jsp:directive.include file="header.jsp" />
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>My Space</title>
	<script type="text/javascript" src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/app.js"></script>
</head>
<body ng-controller="myspaceCtrl" ng-init='setInfoUser(${user }); setShops(${userShop }); setUserOrders(${myOrders }); setShopOrders(${shopOrders })'>
	<div id="globalDiv2">
		<div class="container contenu">
			<jsp:directive.include file="navBar.jsp" />
			<br/><br/><br/><br/><br/>
			
			<p class="noAccountDiv"> Espace personnel </p> 
			<hr><br>
			<div class="row">
				<p class="stitleDiv"> Mes coordonnées </p>
				<br>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div class="">
						<label for="exampleInputEmail1">Type de compte</label> :  {{infoUser.userType}}
					</div>
					<div class="">
						<label for="exampleInputEmail1">Nom</label> : {{infoUser.lastName}}
					</div>
					<div class="">
						<label for="exampleInputEmail1">Prenom</label> : {{infoUser.firstName}}
					</div>
					<div class="">
						<label for="exampleInputEmail1">E-Mail</label> : {{infoUser.mail}}
					</div>
					<div class="">
						<label for="exampleInputEmail1">Téléphone</label> : {{infoUser.phoneNumber}}
					</div>
				</div>
			</div>
			<hr><br>
			
			<%  if(request.getSession().getAttribute("userName")!=null && request.getSession().getAttribute("userType").equals(UserTypeEnum.MERCHANT.toString())){ %>
      		
      		<div class="row">
				<p class="stitleDiv"> Commande(s) reçue(s)  &nbsp;<button ng-click="showOrder($event)" style="border: 0 !important; font-size:0.8em; background: none !important"><span class="glyphicon glyphicon-sort"></span></button></p><br>
				<div class="col-lg-12 col-md-12 col-sm-12 tabBrackets">
					<br>
					<table class="table table-striped">
						<th class="bg-primary"> Restaurant </th><th class="bg-primary"> Date de la commande </th> <th class="bg-primary"> Temps </th> <th class="bg-primary"> Statut de la commande </th> <th class="bg-primary"> Prix </th> <th class="bg-primary"> Traitement </th>
						<tr ng-repeat="res in shopOrders">
							<td>{{res[1]}}</td>
							<td>{{res[5]}}</td>
							<td>{{res[3]}}</td>
							<td>{{res[4]}}</td>
							<td>{{res[6]}} &#128;</td>
							<td><a href="updateOrder?idOrder={{res[2]}}" class="btn btn-default"> Traiter la Commande </a></td>
						</tr>
					</table>
				</div>
			</div>
      		<br><br><br><br>
			<hr><br>
      		<% } %>
			
			
			<%  if(request.getSession().getAttribute("userName")!=null && request.getSession().getAttribute("userType").equals(UserTypeEnum.MERCHANT.toString())){
      		%>
			<div class="row"> <p class="stitleDiv col-lg-5 col-md-5"> Mes Shops </p> <a href="createShop" class="btn btn-success  col-lg-2 col-md-2 col-lg-offset-4 col-md-offset-4" role="button">+ Ajouter Shop</a> </div>
			<br><br><br>
			<div class="row">
				<div class="col-lg-4 col-sm-6 col-md-4" ng-repeat="shop in listShop">
				    <div class="thumbnail">
				      <img src="http://www.restomontreal.ca/portal/sushi_mooshi/gallery/images/a_sushi_mooshi.jpg" alt="...">
				      <div class="caption">
				        <h3>{{shop.name}}</h3>
				        <p>{{shop.description}}</p>
				        <br>
				        <p><a href="updateShop?idShop={{shop.id}}" class="btn btn-primary" role="button">G&eacute;rer</a> <a href="deleteShop?idShop={{shop.id}}" class="btn btn-danger col-lg-offset-5 col-md-offset-5" role="button">Supprimer</a></p>
				      </div>
				    </div>
				  </div>
			</div>
			<br><br><br><br>
			<hr><br>
			<%}%>
			
			<div class="row">
				<p class="stitleDiv"> Mes dernières commandes  &nbsp;<button ng-click="showOrder($event)" style="border: 0 !important; font-size:0.8em; background: none !important"><span class="glyphicon glyphicon-sort"></span></button></p><br>
				<div class="col-lg-12 col-md-12 col-sm-12 tabBrackets">
					<br>
					<table class="table table-striped">
						<th class="bg-primary"> Restaurant </th><th class="bg-primary"> Date de la commande </th> <th class="bg-primary"> Temps </th> <th class="bg-primary"> Statut de la commande </th> <th class="bg-primary"> Prix </th>
						<tr ng-repeat="res in userOrders">
							<td>{{res[5]}}</td>
							<td>{{res[3]}}</td>
							<td>{{res[1]}}</td>
							<td>{{res[2]}}</td>
							<td>{{res[4]}} &#128;</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:directive.include file="footer.jsp" />