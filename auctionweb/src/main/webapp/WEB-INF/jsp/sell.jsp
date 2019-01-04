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
  <script type="text/javascript" src="/js/post.js"></script>
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
      <h2>Post items</h1>
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
          
          
<form id="postForm" method="post" class="form-horizontal" role="form" accept-charset="UTF-8" action="${pageContext.request.contextPath}/item/new" enctype="multipart/form-data">
					
			
					<div class="container border border-dark rounded" style="padding: 15px;">
						<div class="form-group form-inline justify-content-center">
							<div class="row">
								<div class="small-12 medium-2 large-2 columns">

									<div class="p-image">
										<i class="fa fa-camera upload-button"></i>
										<input class="file-upload" type="file" accept="image/.jpg" name="pic" id="pic" />
									</div>
								</div>
							</div>
						</div>

		              <div class="form-group">
		              	<div class="row">
		              		<label for="title" class="col-lg-4 col-form-label">Title:</label>
		              		<div class="col-lg-8">
		              			<input type="text" class="form-control" id="title" name="title" value="">
		              		</div>
		              	</div>
		              </div>

		              <div class="form-group">
		              	<div class="row">
		              		<label for="description" class="col-lg-4 col-form-label">Description:</label>
		              		<div class="col-lg-8">
		              			<input type="text" class="form-control" id="description" name="description">
		              		</div>
		              	</div>
		              </div>

		              <div class="form-group">
		              	<div class="row">
		              		<label for="address" class="col-lg-4 col-form-label">Address:</label>
		              		<div class="col-lg-8">
		              			<input type="text" class="form-control" id="address" name="address">
		              		</div>
		              	</div>
		              </div>



		              <div class="form-group">
		              	<div class="row">
		              		<label for="min_price" class="col-lg-4 col-form-label">Minimum Bidding Price(USD):</label>
		              		<div class="col-lg-8">
		              			<input type="number" class="form-control" id="min_price" name="min_price">
		              		</div>
		              	</div>
		              </div>
		              		              
		              <div class="form-group">
		              	<div class="row">
		              		<label for="min_price" class="col-lg-4 col-form-label">Indextime:</label>
		              		<div class="col-lg-8">
		              			<input type="number" class="form-control" id="indextime" name="indextime">
		              		</div>
		              	</div>
		              </div>
		              
		              	<div class="form-group">
		              	<div class="row">
		              		<label for="min_price" class="col-lg-4 col-form-label">Auction_date:</label>
		              		<div class="col-lg-8">
		              			<input type="text" class="form-control" id="auctiondate" name="auctiondate">
		              		</div>
		              	</div>
		              </div>
		              
		             
		              <button id="submitbutton" type="submit" class="btn btn-primary">Submit</button>
		          </div>
		      </form>
		      
		      <div class="col-8">
                  <div class="inline-block">
                      <input type="date" class="form-control" id="auction_date1" name="auction_date1">
                      <button id="search" class="btn btn-info" style="float:right">Search</button></div>
                      <br><br> 
                      <div class="col-lg-12" id="selecttime" style="display: block;">
                        <select id="myselector">
                          <option id="0">8:00 - 8:20</option>
                          <option id="1">8:20 - 8:40</option>
                          <option id="2">8:40 - 9:00</option>
                          <option id="3">9:20 - 9:40</option>
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
      </div>
    </div>
    <hr>
    
  </body>

  </html>