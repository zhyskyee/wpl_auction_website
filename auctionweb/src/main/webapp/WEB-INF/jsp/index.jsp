<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Auction website</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom styles for this template -->
  <link href="css/full-slider.css" rel="stylesheet">
  <link rel="stylesheet" href="css/img.upload.css" type="text/css"></link>
  <link rel="stylesheet" href="css/sign.css"></link>

  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
  <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
  <script src="js/img_preview.js" type="text/JavaScript"></script>
  <script type="text/javascript" src="/js/login.js"></script>
</head>

  <body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#">BID! BID! BID!</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home
                <span class="sr-only">(current)</span>
              </a>
            </li>
              <li>
              <a class="nav-link" href="${pageContext.request.contextPath}/todayorder">
                Today's Items
              </a>
             </li>
           <%--  <c:if test="${sessionScope.user != null}"> --%>
            <li class="nav-item" id="profile" style="display:none;">
              <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
            </li>
            <li class="nav-item" id="order" style="display:none;">
              <a class="nav-link"  href="${pageContext.request.contextPath}/order">Order</a>
            </li>
            <li class="nav-item" id="sell" style="display:none;">
              <a class="nav-link" href="${pageContext.request.contextPath}/sell">Sell</a>
            </li>

           <%--  </c:if> --%>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact</a>
            </li>
          </ul>
          <ul class="navbar-nav ml-auto">
            <%-- <c:if test="${sessionScope.user == null}"> --%>
            <li class="dropdown" id="signinform">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Sign Up or Sign In</b> <span class="caret"></span></a>
          <ul id="login-dp" class="dropdown-menu .mega-dropdown-menu">
                <li>
                  <div class="row">
                    <div class="col-md-12">
                    Login via
                      <div class="social-buttons">
                        <a href="#" class="btn btn-fb"><i class="fa fa-facebook"></i> Facebook</a>
                        <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i> Twitter</a>
                      </div>
                    or&nbsp;&nbsp;<div class="info-msg" style="display: inline-block;"><i id="alertholder" class="fa fa-info-circle">${message}</i></div>
                     
                        <div class="form-group">
                           <label class="sr-only" for="username">User Name</label>
                           <input type="text" name="username" value="${username}" class="form-control" id="username" placeholder="User Name" required>
                        </div>
                        <div class="form-group">
                          <label class="sr-only" for="password">Password</label>
                          <input type="password" name="password" value="${password}" class="form-control" id="password" placeholder="Password" required>
                          <div class="help-block text-right"><a href="">Forget the password ?</a></div>
                        </div>
                        <div class="form-group">
                         <button id="submitLogin" type="submit" class="btn btn-primary btn-block">Sign in</button>
                        </div>
                        <div class="checkbox">
                         <label>
                           <input type="checkbox"> keep me logged-in
                         </label>
                        </div>
                    
                    </div>
                    <div class="col-md-6 bottom text-center">
                      <a href="${pageContext.request.contextPath}/register"><b>Sign up</b></a>
                    </div>
                    <div class="col-md-6 bottom text-center">
                    <a href="${pageContext.request.contextPath}/register" id="forgetpassword"><b>forget password</b></a>
                    </div>
                    

                  </div>
                </li>
              </ul>
            </li>
           <%--  </c:if> --%>
   <%--  <c:if test="${sessionScope.user != null}"> --%>
    <li class="nav-item" id="signout">
      <font color="white">Welcome!&nbsp;</font><font color="blue" id="showusername">${sessionScope.user.username}</font>
      <a class="nav-link" style="display: inline-block;" id="signoutbutton" href="${pageContext.request.contextPath}/logout">&nbsp;Sign Out</a>
    </li>
  <%-- </c:if> --%>
</ul>
</div>
</div>
</nav>

<header>
  <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner" role="listbox">
      <!-- Slide One - Set the background image for this slide in the line below -->
      <div class="carousel-item active" style="" href="#">
        <a href="${pageContext.request.contextPath}/profile">
          <img src="images/thumbnails.png" alt="home page picture" style="width: 100%; height: 100%">
          <div class="carousel-caption d-none d-md-block">
            <div class="bid_page">
             <div class="container">
              <div class="row">
                <div class="col-lg-12" style="text-align: center;">Bid Item For Now</div>
                <div class="col-lg-6">Name:<span id="itemname"></span></div>
                <div class="col-lg-6">Bid Price:<span id="bidprice"></span></div>
                <div class="col-lg-6">Description:<span id="itemdescription"></span></div>
                <br><br>
                <p id="bidmessage"></p><p id="mybidprice"></p>
                <div class="col-lg-12"><button type="button" class="btn btn-default" id="bidbutton">Bid!</button></div>
             

              </div>
             </div>
            </div>
            <p>This is a personal profile for the first slide.</p>
          </div></a>
        </div>
        <!-- Slide Two - Set the background image for this slide in the line below -->

        </div>
      </div>
    </header>
    <!-- Bootstrap core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</div>
</body>
</html>