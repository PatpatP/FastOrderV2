<%@page import="com.fastorder.enumeration.UserTypeEnum"%>
<jsp:directive.page contentType="text/html; charset=ISO-8859-1" />
<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div id="navbar" class="navbar-collapse collapse">
        <div class="logo col-lg-2 col-md-2 col-sm-3"><a href="home"><img class="imgLogo" src="images/logo_fastorder.png"></a></div>
        <ul class="nav navbar-nav navbar-right" id="listNav">
        <li><a href="home" class="active">Home</a></li>
         <li><a href="shops" class="shops">Shops</a></li>
      <%  if(request.getSession().getAttribute("userName")!=null && request.getSession().getAttribute("userType").equals(UserTypeEnum.CLIENT.toString())){
      %>
      	 <li><a href="myspace" style="color:green;">${userName }</a></li>
         <li><a href="signOut" id="disconnect">Logout</a></li>
         <li><a href="shops" id="bracketHead"><span class="glyphicon glyphicon-shopping-cart" style="color:green; font-size:1.8em; margin-top:25%;"></span>({{size}})</a></li>
        
         
      <%
      }else if(request.getSession().getAttribute("userName")!=null && request.getSession().getAttribute("userType").equals(UserTypeEnum.MERCHANT.toString())){
      %>  
        <li><a href="myshops" class="myshops">Mes Shops</a></li>
        <li><a href="myspace" style="color:green;">${userName }</a></li>
      	<li><a href="signOut" id="disconnect">Logout</a></li>
		<li><a href="shops" id="bracketHead"><span class="glyphicon glyphicon-shopping-cart" style="color:green; font-size:1.8em; margin-top:25%;"></span>({{size}})</a></li>
        
      
      <%  
      } else {
      %> 
     
         <li><a href="login" id="login">Sign In</a></li>
         <li><a href="createAccount" id="signUp">Sign Up</a></li>
         <li><a href="shops" id="bracketHead"><span class="glyphicon glyphicon-shopping-cart" style="color:green; font-size:1.8em; margin-top:25%;"></span>({{size}})</a></li>
         
      <%
      }
      %>
           </ul> 
        </div><!--/.nav-collapse -->
      </div>
    </nav>