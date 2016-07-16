<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<jsp:directive.page import="com.fastorder.model.Address" />
<html>
<jsp:directive.include file="header.jsp" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connexion</title>
</head>
<body style="background: #eee">
	<div id="globalDiv2">
	<div class="container contenu">
	<jsp:directive.include file="navBar.jsp" />
	<br/><br/><br/><br/><br/><br/>
	<%	if(request.getAttribute("msgcreated") != null){
			%>
			<div class="alert alert-success" role="alert">
				<strong>Votre compte a été créé. Vous pouvez vous connecter</strong>
			</div>
			<%
			}
		%>
	<p class="noAccountDiv"> Connectez-vous à votre compte </p>
	<hr>
	<div class="row">
			<div class="col-lg-2 col-md-2 col-lg-offset-1 col-md-offset-1"><br><img src="images/logo_fastorder.png" height="200px"></div>
		<div class="blocPshow col-lg-9 col-md-9">
			<h2>Vous avez un restaurant ou magasin ?</h2>
			<p>
				Rejoignez FastOrder pour augmenter votre clientèle et attirer de nouveaux clients <br>
				Découvrez les services et avantages pour les comptes de type restaurant
			</p>
		</div>
	</div>
	<%	if(request.getAttribute("failConnect") != null){ %>
	<div class="alert alert-danger text-center" role="alert">${erreurs['connectionFail']}</div>
	<%
		}
	%>
	<div class="col-lg-6 col-md-6 col-sm-10">
	   <div class="panel panel-default">
		 <div class="panel-heading">Login</div>
		 <div class="panel-body">
			<form method="get" action="${action}">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div class="form-group">
						<label for="exampleInputEmail1">Mail</label> 
						<input type="text" class="form-control" name="mail" placeholder="Mail">
						<span class="erreur">${erreurs['mail']}</span>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Password</label> 
						<input type="password" class="form-control" name="password" placeholder="Password">
					</div>
					
				</div>
				<div class="col-lg-11 col-md-11">
					<br>
					<br>
					<button type="submit" class="btn btnIndex">Connexion</button>
				</div>
			</form>
		 </div>
	   </div>
	</div>
	<div class="col-lg-5 col-md-5 col-sm-10 col-lg-offset-1 col-md-offset-1">
		<p class="noAccountDiv"> Pas encore de compte ? </p>
			<br>
		<p class="noaccountText">
			Ne vous inquiétez pas, il est encore temps d'en créer un !<br><br>
			Vous pouvez choisir le type d'utilisateur lors de votre <br>
			création de compte (marchand ou utilisateur simple)
		</p>
		<br>
		<br>
		<a href="createAccount" class="btn btnIndex col-lg-8 col-md-8">Créer un compte</a>
	</div>
	</div>
	</div>
	<jsp:directive.include file="footer.jsp" />