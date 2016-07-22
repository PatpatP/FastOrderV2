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
		<title>Modification Shop</title>
		<script type="text/javascript" src="js/angular.min.js"></script>
		<script type="text/javascript" src="js/app.js"></script>
</head>
<body ng-controller="myspaceCtrl" ng-init='manageShopProducts(${products }); getShopType(${shopType })'>
	<div id="globalDiv2">
	<div class="container contenu">
	<jsp:directive.include file="navBar.jsp" />
	<br/><br/><br/><br/><br/><br />
	
	<p class="noAccountDiv"> Modification d'un shop </p>
	<hr><br>
	<p class="noaccountText">ci-dessous se trouve les anciennes informations concernant votre commerce<br><br>
	<div class="row">
		<div class="blocInfoInscription">
			<form method="get" action="${action}">
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
							type="text" class="form-control" name="Description"
							placeholder="Description">
					</div>
					<div class="form-group">
						<label>Image</label> <input
							type="file" class="form-control" name="image">
					</div>
				</div>
				<div class="col-lg-5 col-md-5 col-lg-offset-1 col-md-offset-1">
					<div class="textInscription"></div>
					<br><br><br><br>
					<div class="form-group">
						<label for="exampleInputEmail1">Number</label> <input type="text"
							class="form-control" name="number" placeholder="Number">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Street</label> <input type="text"
							class="form-control" name="street" placeholder="Street">
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
					<div class="form-group">
						<label for="exampleInputEmail1">Country</label> <input type="text"
							class="form-control" name="country" placeholder="Country">
					</div>
				</div>
				<div class="col-lg-11 col-md-11 col-sm-11">
					<br>
					<br>
					<button type="submit"
						class="btn btn-success col-lg-3 col-md-3">Sauvegarder</button>
				</div>
			</form>
		</div>
	</div><br><br>
	
	<hr><br><br>
	<div class="row">
		<p class="stitleDiv col-lg-4 col-md-4 col-sm-5"> Mes produits  &nbsp;<button ng-click="showOrder($event)" style="border: 0 !important; font-size:0.8em; background: none !important"><span class="glyphicon glyphicon-sort"></span></button></p><br>
		<button class="btn btn-success  col-lg-2 col-md-2 col-lg-offset-5 col-md-offset-5" style="margin-top: -2% !important; margin-bottom: 6% !important;" data-toggle="modal" data-target="#ajoutProduit">+ Ajouter Produit</button>
		
		<!-- Popup création produit -->
			<div class="modal fade" id="ajoutProduit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content" style="margin-top: 12%">
			      <div class="modal-header bg-primary">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">Formulaire d'ajout d'un produit</h4>
			      </div>
			      <div class="modal-body row">
			        <form method="post" action="${actionAddProduct}">
						<div class="col-lg-12 col-md-12 col-sm-12">
						<div class="textInscription">Informations sur le produit</div>
						<hr><br>
							<div class="form-group">
								<label for="exampleInputEmail1">Type de produit</label> <select
									class="form-control" name="typeProduit">
									<c:forEach var="typeProduct" items="${productType }">
										<option value="${typeProduct }">${typeProduct }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Name</label> <input type="text" class="form-control" name="name" placeholder="Name">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Description</label> 
								<input type="text" class="form-control" name="description" placeholder="Description">
							</div>
							<div class="form-group">
								<input type="hidden" class="form-control" name="idShop" value="${idShop }">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Price</label> 
								<input type="text" class="form-control" name="price" placeholder="price">
							</div>
						</div>
				 	
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        <button type="submit" class="btn btn-primary">Add product</button>
			      </div>
			      </form>
			    </div>
			  </div>
			</div>
		
		<!-- Fin de la popup -->
		
		<div class="col-lg-12 col-md-12 col-sm-12 tabBrackets">
			<br>
			<table class="table table-striped">
				<th class="bg-primary text-center"> Nom </th> <th class="bg-primary text-center"> Description </th> <th class="bg-primary"> Type de produit </th> <th class="bg-primary"> Prix </th> <th colspan="2" class="bg-primary text-center"> Action  </th> 
				<tr ng-repeat="product in listProducts">
					<td>{{product.name}}</td>
					<td>{{product.description}}</td>
					<td>{{product.productType}}</td>
					<td>{{product.price}} &#128;</td>
					<td><button class="btn btn-success" ng-click="setIdProductToDelete(product.id)" data-toggle="modal" data-target="#modifProduit"> Modifier </button></td>
					<td><button class="btn btn-danger" ng-click="setIdProductToDelete(product.id)" data-toggle="modal" data-target="#deleteProduct">Supprimer </button></td>
					
				</tr>
			</table>
		</div>
	</div>
	<br><br><br><br>
	
	<!-- Popup suppression produit -->
		<div class="modal fade" id="deleteProduct" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content" style="margin-top: 12%">
		      <div class="modal-header bg-primary">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">Formulaire d'ajout d'un produit</h4>
		      </div>
		      <div class="modal-body row">
		        <center>
		        Etes-vous sur de vouloir supprimer ce produit ?<br><br>
		        <a href="deleteProduct?idProduct={{idProductToDelete}}&idShop=${idShop }" class="btn btn-warning">Supprimer </a>
		       </center>
		      </div>
		  </div>
		</div>
		</div>
	<!-- Fin de la popup -->
	
	<!-- Popup Modification produit -->
		<div class="modal fade" id="modifProduit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content" style="margin-top: 12%">
			      <div class="modal-header bg-primary">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">Modification d'un produit</h4>
			      </div>
			      <div class="modal-body row">
			        <form method="post" action="${actionUpdateProduct}">
						<div class="col-lg-12 col-md-12 col-sm-12">
						<div class="textInscription">Remplissez uniquement les champs à modifier</div>
						<hr><br>
							<div class="form-group">
								<label for="exampleInputEmail1">Type de produit</label> <select
									class="form-control" name="typeProduit">
									<c:forEach var="typeProduct" items="${productType }">
										<option value="${typeProduct }">${typeProduct }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Nom</label> <input type="text" class="form-control" name="name" placeholder="Name">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Description</label> 
								<input type="text" class="form-control" name="description" placeholder="Description">
							</div>
							<div class="form-group">
								<input type="hidden" class="form-control" name="idShop" value="${idShop }">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Prix</label> 
								<input type="text" class="form-control" name="price" placeholder="price">
							</div>
							<div class="form-group">
								<input type="hidden" class="form-control" name="idProduct" value="{{idProductToDelete}}">
							</div>
							<div class="form-group">
								<input type="hidden" class="form-control" name="idShop" value="${idShop }">
							</div>
						</div>
				 	
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
			       	<button type="submit" class="btn btn-warning">Modifier </a>
			       </div>
			      </form>
			    </div>
			  </div>
			</div>
	<!-- Fin de la popup -->
					
	</div>
	</div>
	<jsp:directive.include file="footer.jsp" />