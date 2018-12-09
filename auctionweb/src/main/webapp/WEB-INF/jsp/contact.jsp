<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Contact me</title>
    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
      body {
        padding-top: 70px;
        /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
      }

      .othertop{margin-top:10px;}
    </style>
  </head>

  <body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#">Auction website</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/order">Order</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/sell">Sell</a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact<span class="sr-only">(current)</span></a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- <article id="cntmecol"> -->
      
        <div class="container">
          <h2>Contact me</h1>
          <hr>
          <div class="row">
              <div class="col-md-3">
                <div class="text-center">
                  <h5>Please provide following information</h5>
                  <img src="images/phone_text.jpg" class="img-reponsive img-circle" alt="avatar">
                </div>
              </div>
              
              <div class="col-md-9 personal-info">
                <h3 class="page-header"><b>Contact info</b></h3>

                <form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label class="col-lg-3 control-label">First name:</label>
                    <div class="col-lg-8">
                      <input class="form-control" type="text" value="Your first name..." required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-lg-3 control-label">Last name:</label>
                    <div class="col-lg-8">
                      <input class="form-control" type="text" value="Your last name..." required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-lg-3 control-label">Email:</label>
                    <div class="col-lg-8">
                      <input class="form-control" type="email" value="xxx@xxx.xxx" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Phone Number:</label>
                    <div class="col-md-8">
                      <input class="form-control" type="tel" placeholder="1234567890" pattern="^\d{10}$" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <fieldset>
                    <legend class="col-md-3">Gender: </legend>
                    <div class="col-md-8">
                      <input type="radio" name="gender" id="male" value="male" required> Male
                      <input type="radio" name="gender" id="female" value="female" required> Female
                    </div>
                  </fieldset>
                  </div>
                  <div class="form-group">
                    <div class="col-md-6 label">Please select this box if you wish to be added to our mailing list.</div>
                    <div class="col-md-6">
                      <input type="checkbox" name="chkMailingList" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label"></label>
                    <div class="col-md-8">
                      <input type="button" class="btn btn-primary" value="Submit">
                      <span></span>
                      <input type="reset" class="btn btn-danger" value="Reset">
                    </div>
                  </div>
                </form>
              </div>
            </div>
        </div>
      <!-- </article> -->
    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  </body>

</html>
