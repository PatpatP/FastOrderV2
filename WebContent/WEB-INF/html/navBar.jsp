<%@page import="com.fastorder.enumeration.UserTypeEnum"%>
<jsp:directive.page contentType="text/html; charset=ISO-8859-1" />
<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div id="navbar" class="navbar-collapse collapse">
        <div class="logo col-lg-2 col-md-2 col-sm-3"><a href="home">Fast Order</a></div>
        <ul class="nav navbar-nav navbar-right">
        <li><a href="home" class="active">Home</a></li>
         <li><a href="shops">Shops</a></li>
      <%  if(request.getSession().getAttribute("userName")!=null && request.getSession().getAttribute("userType").equals(UserTypeEnum.CLIENT.toString())){
      %>
         <li><a href="signOut">Logout</a></li>
         <li><a href="myspace" style="style:color:#6AE62D;">${userName }</a></li>
      <%
      }else if(request.getSession().getAttribute("userName")!=null && request.getSession().getAttribute("userType").equals(UserTypeEnum.MERCHANT.toString())){
      %>  
      	<li><a href="signOut">Logout</a></li>
<!--       	<li><span class="glyphicon glyphicon-user" style="color:green; font-size:1.8em; margin-top:25%;"></span></li> -->
        <li><a href="myspace" style="color:green;">${userName }</a></li>
      
      <%  
      } else {
      %> 
         
         <li><a href="login">Sign In</a></li>
         <li><a href="createAccount">Sign Up</a></li>
      <%
      }
      %>
           </ul> 
        </div><!--/.nav-collapse -->
      </div>
    </nav>