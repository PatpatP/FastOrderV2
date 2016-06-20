<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<jsp:directive.page import="com.fastorder.model.Address" />
<html>
<jsp:directive.include file="header.jsp" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body class="container">
	<jsp:directive.include file="navBar.jsp" />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />

	<div class="panel panel-primary">
		<div class="panel-heading">Création de compte</div>
		<div class="panel-body">
			<form method="get" action="${action}">
				<div class="col-lg-5 col-md-5">
					<div class="form-group">
						<label for="exampleInputEmail1">Type de compte</label> <select
							class="form-control" name="userType">
							<option value="${client}">${client}</option>
							<option value="${merchant}">${merchant}</option>
						</select>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">First Name</label> <input
							type="text" class="form-control" name="firstName"
							placeholder="First Name">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Last Name</label> <input
							type="text" class="form-control" name="lastName"
							placeholder="Last Name">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Mail</label> <input type="text"
							class="form-control" name="mail" placeholder="Mail">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Password</label> <input
							type="password" class="form-control" name="password"
							placeholder="Password">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Phone Number</label> <input
							type="text" class="form-control" name="phoneNumber"
							placeholder="Phone Number">
					</div>
				</div>
				<div class="col-lg-5 col-md-5 col-lg-offset-1 col-md-offset-1">
					<div class="form-group">
						<label for="exampleInputEmail1">Street</label> <input type="text"
							class="form-control" name="street" placeholder="Street">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Number</label> <input type="text"
							class="form-control" name="number" placeholder="Number">
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
				<div class="col-lg-11">
					<br>
					<br>
					<button type="submit"
						class="btn btn-primary col-lg-2 col-lg-offset-5">Envoyer</button>
				</div>
			</form>
		</div>
	</div>



</body>
</html>