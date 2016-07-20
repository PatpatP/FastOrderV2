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
				<div class="col-lg-6 col-md-6 col-sm-6">
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
				
				<div class="col-lg-6 col-md-6 col-sm-6">
					<p class="stitleDiv col-lg-12 col-md-12 col-sm-12"> Gérer mon compte </p><br><br><br>
					<a href="#" class="btn btn-success col-lg-5  col-md-5 col-sm-10">Modifier Compte</a>
					<a href="#" class="btn btn-warning col-lg-6  col-md-6 col-sm-10 col-lg-offset-1  col-md-offset-1">Supprimer Compte</a>
				</div>
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