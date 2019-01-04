<%@page import="utd.wpl.pojo.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>User profile</title>
  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="/js/profile.js"></script>
  <!-- Custom CSS -->
  <style>
  body {
    padding-top: 70px;
    /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
  }

  .othertop{margin-top:10px;}
</style>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
      <![endif]-->

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
            <li class="nav-item ">
              <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="#">Profile<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/order">Order</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/sell">Sell</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

     <div class="container">
      <h2>User Profile</h1>
      <hr>
      <div class="row">
        <!-- left column -->
        <div class="col-md-3">
          <div class="text-center">
            <img src="" id="pic" class="avatar img-circle" alt="avatar" style="width:100px;height:100px;">
            <h6>Upload a different photo...</h6>

            <input type="file" class="form-control">
          </div>
        </div>

        <!-- edit form column -->
        <% User curUser = (User)session.getAttribute("user"); %>
        <div class="col-md-9 personal-info">
          <h3 class="page-header"><b>Personal info</b></h3>
          <form class="form-horizontal" method="post" role="form" accept-charset="UTF-8" action="${pageContext.request.contextPath}/profile/do">
            <div class="form-group">
              <label class="col-md-3 control-label">Username:</label>
              <div class="col-md-8">
                <input class="form-control" id="username" type="text" value="" required>
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-3 control-label">Email:</label>
              <div class="col-lg-8">
                <input class="form-control" type="email" id="email" value="" required>
              </div>
            </div>
            <div class="form-group">
              <label class="col-lg-3 control-label">Phone:</label>
              <div class="col-lg-8">
                <input class="form-control" type="tel" id="phone" value="" required>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label"></label>
              <div class="col-md-8">
                <input type="button" class="btn btn-primary" id="change" value="Save Changes">
                <span></span>
                <input type="reset" class="btn btn-default" value="Cancel">
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
    <hr>

  </body>

  </html>
