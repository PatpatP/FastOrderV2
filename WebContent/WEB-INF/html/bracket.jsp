<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="fastOrder">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/app.js"></script>
	<title>Insert title here</title>
</head>
<body ng-controller="mainCtrl" ng-init='setShops(${listShop }); getBracket();'>
<div id="globalDiv2">
		<div class="container contenu">
			<jsp:directive.include file="navBar.jsp" />
			<br/><br/><br/><br/><br/>
			
			<p class="noAccountDiv"> Espace personnel </p> 
			<hr><br>
			<div class="row blocPanierEspace">
				<div class="col-lg-3 col-md-3 col-sm-3 blocMiniPanier">
					<div class="col-lg-10 col-md-10 col-sm-10 ">
						<p> <img src="https://thumbs.dreamstime.com/z/personne-occasionnelle-de-portrait-masculin-d-avatar-d-ic-ne-de-profil-46846328.jpg" class="blocPhotoUser col-lg-12 col-md-12"> </p>
					</div>
				</div>
				
				<div class="col-lg-9 col-md-9 col-sm-9 infoPanier"><br>
					<div class="">
						<label for="exampleInputEmail1"> Test  </label> 
					</div>
					<br>
					
					<div class="col-lg-12 col-md-12 col-sm-12 blocGestionBtn">
						<a href="validateBracket" class="btn btn-success col-lg-3  col-md-3 col-sm-3">Valider Panier</a>
					</div>
				</div>			
			</div>
			
		</div>
	</div>
	<jsp:directive.include file="footer.jsp" />