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
			
			<p class="noAccountDiv"> Page Erreur 404 </p> 
			<hr><br>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-8 col-lg-offset-3 col-md-offset-3 col-sm-offset-2"><br>
					<div class="imgErreur col-lg-10 col-md-10 col-sm-11">
						<img src="http://www.yoannuzan.com/file/2015/10/image-erreur-404.png" width="450" heigh="350">
					</div>
					<div class="noAccountDiv col-lg-10 col-md-10 col-sm-11">
						<center><br>La page que vous rechercher n'existe pas, veuillez séléctionner une autre page dans le menu ci-dessus </center>
					</div>
				</div>			
			</div>
		</div>
	</div>
	<jsp:directive.include file="footer.jsp" />
	