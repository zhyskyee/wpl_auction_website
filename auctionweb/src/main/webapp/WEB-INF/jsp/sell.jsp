<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Post page</title>
  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
 
  <script src="https://cdn.jsdelivr.net/npm/gijgo@1.9.10/js/gijgo.min.js" type="text/javascript"></script>
  <link href="https://cdn.jsdelivr.net/npm/gijgo@1.9.10/css/gijgo.min.css" rel="stylesheet" type="text/css" />
    
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
        <a class="navbar-brand" href="#">Post Item</a>
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
            <li class="nav-item active">
              <a class="nav-link" href="${pageContext.request.contextPath}/sell">Sell<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  
    <div class="container">
      <h2>Your items</h1>
      <hr>
      <div class="row">
        <!-- left column -->
        <div class="col-md-3">
          <div class="text-center">
            <img src="images/home_page.jpg" class="img-reponsive img-circle" alt="avatar">
          </div>
        </div>

        <!-- edit form column -->
        <div class="col-md-9">
          <h3 class="page-header"><b>Post item</b></h3>

          <form class="form-horizontal" role="form">
            <div class="form-group">
              <label class="col-3 control-label">Item Title</label>
              <div class="col-8">
                <input class="form-control" type="text" placeholder="Your item name..." id="title">
              </div>
            </div>
            <div class="form-group">
              <label class="col-3 control-label">description</label>
              <div class="col-8">
                <input class="form-control" type="text"  id="description">
              </div>
            </div>
            <div class="form-group">
              <label class="col-3 control-label">address</label>
              <div class="col-8">
                <textarea class="form-control" rows="5" placeholder="address" id="address"></textarea>
              </div>
            </div>
            <div class="form-group">
              <label class="col-4 control-label">Minimum bidding price(USD)</label>
              <div class="col-8">
                <input class="form-control" type="number" min="0" id="minimumprice">
              </div>
            </div>
            <div class="form-group">
              <label class="col-4 control-label">Picture</label>
              <div class="col-8">
                <input id="picture" type="file" accept="image/png,image/gif" name="file" />
              </div>
            </div>
             <div class="form-group">
              <label class="col-3 control-label">Select an auction date</label>
              <div class="col-8">
                 <!-- <div class="input-group date"> -->
                  <div class="inline-block">
                      <input id="auctiondate" class="form-control" type="date" /><button id="search" >Search</button></div>
                  <!-- </div> -->
                      <div class="col-lg-12" id="selecttime" style="display: block;">
                        <select id="myselector">
                          <option id="0">8：00 - 8：20</option>
                          <option id="1">8：20 - 8：40</option>
                          <option id="2">8：40 - 9：00</option>
                          <option id="3">9：20 - 9：40</option>
                          <option id="4">9:40 - 10:00</option>
                          <option id="5">10:00 - 10:20</option>
                          <option id="6">10:20 - 10:40</option>
                          <option id="7">10:40 - 11:00</option>
                          <option id="8">11:00 - 11:20</option>
                          <option id="9">11:20 - 11:40</option>
                          <option id="10">11:40- 12:00</option>
                          <option id="11">12:00 - 12:20</option>
                          <option id="12">12:20 - 12:40</option>
                          <option id="13">12:40 - 13:00</option>
                          <option id="14">13:00 - 13:20</option>
                          <option id="15">13:20 - 13:40</option>
                          <option id="16">13:40 - 14:00</option>
                          <option id="17">14:00 - 14:20</option>
                          <option id="18">14:20 - 14:40</option>
                          <option id="19">14:40 - 15:00</option>
                          <option id="20">15:00 - 15:20</option>
                          <option id="21">15:20 - 15:40</option>
                          <option id="22">15:40 - 16:00</option>
                          <option id="23">16:00 - 16:20</option>
                        </select>


                      </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-3 control-label"></label>
              <div class="col-8">
                <input type="button" id="submitbutton" class="btn btn-primary" value="Submit">
                <span></span>
                <input type="reset" class="btn btn-danger" value="Clear">
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
    <hr>
    
  </body>

  </html>