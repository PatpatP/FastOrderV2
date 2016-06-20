<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.fastorder.model.User" />
<html>
<head>
</head>
<jsp:directive.include file="header.jsp" />
<body onload="load()">
	<div id="globalDiv">
	<video muted="" loop="" data-src="http://v.tfstatic.com/homepage/subheader_video_0.mp4" poster="http://a.tfstatic.com/v-979/images/homepage/subheader_poster_0.jpg" 
	src="http://v.tfstatic.com/homepage/subheader_video_0.mp4" autoplay="autoplay"></video>
	<div class="container contenu1">
	<jsp:directive.include file="navBar.jsp" />
	  <center><script src="js/map.js" type="text/javascript"></script></center>
	    <div class="row divMap">
	    	<div class="row titleHome">Bon Appétit !</div>
			<br><br><br>
	    	<div id="map" class="col-lg-8 col-md-8 col-sm-8" style="height: 462px"></div>
		    <div class="col-lg-4 col-md-4 col-sm-4 divSearchHome">
		    	<input type="text" class="form-control" placeholder="Search for...">
		    </div>
	    </div>
	 </div>
	 </div>
	 <div id="descriptionDivHome">
		<div class="container contenu2">
			<div class="row blocHr"><br><hr><br></div>
			<div class="row">
				<div class="col-lg-4 col-md-4 blocJoinUs">
					<br>
					<p>Votre inscription est gratuite</p><br><br>
					<h2>Rejoignez-nous!</h2><br><br>
					<a href="createAccount" class="btn btn-default col-lg-7 col-md-7 col-sm-6 col-lg-offset-3 col-md-offset-3 col-sm-offset-3">Inscription</a>
				</div>
				<div class="col-lg-7 col-md-7 col-lg-offset-1 col-md-offset-1 blocFidelity">
					<p>
						<b style="color:#f3482d">Votre fidélité récompensée !</b> <br><br>
						Plus vous réservez, plus vous gagnez : <br>
						1 commande = 1 point (des points de réduction)
						
				</div>
				<br><br>
			</div>
		</div>
	 </div>
	 <jsp:directive.include file="footer.jsp" />
