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
			
			<% if(request.getAttribute("message") != null){ %>
			<div class="alert alert-danger" role="alert">
				<ul>
						<li>${message }</li>
				</ul>
			</div>
			<% } %>
			
			<p class="noAccountDiv"> Espace personnel </p> 
			<hr><br>
			<div class="row blocCoordonneesGestion">
				<div class="col-lg-3 col-md-3 col-sm-3 blocCoords">
					<div class="col-lg-10 col-md-10 col-sm-10 ">
						<p> <img src="https://thumbs.dreamstime.com/z/personne-occasionnelle-de-portrait-masculin-d-avatar-d-ic-ne-de-profil-46846328.jpg" class="blocPhotoUser col-lg-12 col-md-12"> </p>
					</div>
				</div>
				
				<div class="col-lg-9 col-md-9 col-sm-9 infoUser"><br>
					<div class="">
						<label for="exampleInputEmail1"> Compte de type : {{infoUser.userType}} </label> 
					</div>
					<div class="">
						<label for="exampleInputEmail1"> {{infoUser.lastName}} </label> 
					</div>
					<div class="">
						<label for="exampleInputEmail1"> {{infoUser.firstName}} </label> 
					</div>
					<div class="">
						<label for="exampleInputEmail1"> {{infoUser.mail}} </label> 
					</div>
					<div class="">
						<label for="exampleInputEmail1"> {{infoUser.phoneNumber}} </label> 
					</div><br>
					
					<div class="col-lg-12 col-md-12 col-sm-12 blocGestionBtn">
						<a href="updateAccount" class="btn btn-success col-lg-3  col-md-3 col-sm-3">Modifier Compte</a>
						<a href="deleteAccount?idUser={{infoUser.id}}" class="btn btn-warning col-lg-3  col-md-3 col-sm-3 col-lg-offset-1  col-md-offset-1">Supprimer Compte</a>
					</div>
				</div>
			
				<!-- <p class="stitleDiv col-lg-8 col-md-8 col-sm-8"> Gérer mon compte </p><br><br><br>   -->
			
			</div>
			<hr><br>
			
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