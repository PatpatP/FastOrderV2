<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="fastOrder">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<script type="text/javascript" src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/app.js"></script>
</head>
<jsp:directive.include file="header.jsp" />
<body ng-controller="mainCtrl" ng-init='setShops(${listShop }); getBracket();'>
<div id="globalDiv2">
		<div class="container contenu">
			<jsp:directive.include file="navBar.jsp" />
			<br/><br/><br/><br/><br/>
			
			<p class="noAccountDiv"> Mon panier
			 </p> 
			<hr><br>
			<div class="row" id="blocPanierGeneral">
				<div class="col-lg-12 col-md-12 col-sm-12 "><br>
					<div class="imgPanier col-lg-3 col-md-3 col-sm-3">
					</div>
					<div class="noAccountDiv col-lg-9 col-md-9 col-sm-9">
						<br>
						<p style="margin-left: 5%;">
							<div class="containerBracketPage">
						 		<div ng-repeat="brck in bracketStorage"> 
							 		<p class="col-lg-8 col-md-8 col-sm-8">{{brck.name}}</p> 
							 		<div class="col-lg-4 col-md-4 col-sm-4">
							 			<button ng-click="removeProduct(brck.id)" class="btnBrkt">-</button> [ {{brck.quantity}} ] <button ng-click="addToBracket(brck.id, brck.name, brck.price)" class="btnBrkt">+</button>
							 		</div>
						 		</div>
						 		
						 		<div class="">
						 			<br><hr style="border: 1px solid #bbb">
								 	<p class="col-lg-5 col-md-5 col-sm-5">  </p> <p class="col-lg-5 col-md-5 col-sm-5" align="right">{{prixTotal}} &#128;</p>
								</div>
								<a class="btn btnIndex col-lg-4 col-md-4 col-sm-5"  ng-click="resetBracket(bracketId);" ng-if="bracket.length>0" style="padding-top:1.6%"> Valider le panier</a>
								<br>   
						 	</div> 
						</p>
					</div>
				</div>			
			</div>
		</div>
	</div>
	<jsp:directive.include file="footer.jsp" />