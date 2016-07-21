<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<jsp:directive.page import="com.fastorder.model.Address" />
<html ng-app="fastOrder">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/app.js"></script>
</head>
<jsp:directive.include file="header.jsp" />
<body ng-controller="mainCtrl" ng-init='setShops(${listShop }); getBracket();'>
	<div id="globalDiv">
		<video muted="" loop="" data-src="http://v.tfstatic.com/homepage/subheader_video_0.mp4" poster="http://a.tfstatic.com/v-979/images/homepage/subheader_poster_0.jpg" 
		src="http://v.tfstatic.com/homepage/subheader_video_0.mp4" autoplay="autoplay"></video>
		<div class="col-lg-12 col-md-12 col-sm-12 divSearchHome">
	    	<input type="text" class="form-contol" ng-model="searchHome" placeholder=" Search for...">
	    	<div class="viewSearchHome" ng-if="searchHome !=''"><p ng-repeat="res in shopsList | filter:searchHome"><a href="order?idShop={{res.id}}">{{res.name}} ({{res.shopType}})</a></p></div>
	    </div>
	</div>
	<div class="blocMap">
		<div class="container contenu1">
		<jsp:directive.include file="navBar.jsp" />
		  <center><jsp:directive.include file="map.jsp" /></center>
		    <div class="row divMap">
		    	<div class="row titleHome"><br>Géolocalisation des restaurants !</div>
		    	<div id="map" class="col-lg-12 col-md-12 col-sm-12" style="height: 472px"></div>
		    </div>
		 </div>
	 </div>
	 <jsp:directive.include file="footer.jsp" />
