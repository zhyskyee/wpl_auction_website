<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<head>
	<title>Register</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- Bootstrap core CSS -->
	<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<link href="/css/register.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/register.js"></script>
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
	<script src="js/img_preview.js" type="text/JavaScript"></script>
	<link rel="stylesheet" href="css/img.upload.css" type="text/css"></link>
</head>
<body>
	<br><br>
	<div class="container-fluid">
		<div class="row justify-content-center">
			<div class="mx-auto col-md-5">
				<form id="registerForm" method="post" class="form-horizontal" role="form" accept-charset="UTF-8" action="${pageContext.request.contextPath}/user/register" enctype="multipart/form-data">
					
					<h3>Sign Up</h3>
					<h6>Please fill in this form to create an account.</h6>
					<hr>
					
					<h3 class="page-header"><b>Personal info</b></h3>
					<div id="td-meg-div">${message}</div>
					<div class="container border border-dark rounded" style="padding: 15px;">
						<div class="form-group form-inline justify-content-center">
							<div class="row">
								<div class="small-12 medium-2 large-2 columns">
									<div class="circle">
										<!-- User Profile Image -->
										<img class="profile-pic" src="images/thumbnails.png">
									</div>
									<div class="p-image">
										<i class="fa fa-camera upload-button"></i>
										<input class="file-upload" type="file" accept="image/*" name="pic" id="pic" />
									</div>
								</div>
							</div>
						</div>

		               <!--  <div class="form-group">
		                  <div class="row">
		                      <label for="first_name" class="col-lg-3 col-form-label">First Name:</label>
		                      <div class="col-lg-9">
		                          <input type="text" class="form-control" id="first_name" name="first_name">
		                      </div>
		                  </div>
		                </div>

		                <div class="form-group">
		                  <div class="row">
		                      <label for="last_name" class="col-lg-3 col-form-label">Last name:</label>
		                      <div class="col-lg-9">
		                          <input type="text" class="form-control" id="last_name" name="last_name">
		                      </div>
		                  </div>
		              </div> -->

		              <div class="form-group">
		              	<div class="row">
		              		<label for="username" class="col-lg-4 col-form-label">Username:</label>
		              		<div class="col-lg-8">
		              			<input type="text" class="form-control" id="username" name="username" value="${username}">
		              		</div>
		              	</div>
		              </div>

		              <div class="form-group">
		              	<div class="row">
		              		<label for="password" class="col-lg-4 col-form-label">Password:</label>
		              		<div class="col-lg-8">
		              			<input type="password" class="form-control" id="password" name="password">
		              		</div>
		              	</div>
		              </div>

		              <div class="form-group">
		              	<div class="row">
		              		<label for="confirmPass" class="col-lg-4 col-form-label">Confirm password:</label>
		              		<div class="col-lg-8">
		              			<input type="password" class="form-control" id="confirmPass" name="confirmPass">
		              		</div>
		              	</div>
		              </div>

		              <div class="form-group">
		              	<div class="row">
		              		<label for="email" class="col-lg-4 col-form-label">Email:</label>
		              		<div class="col-lg-8">
		              			<input type="email" class="form-control" id="email" name="email" value="${email}">
		              		</div>
		              	</div>
		              </div>

		              <div class="form-group">
		              	<div class="row">
		              		<label for="phone" class="col-lg-4 col-form-label">Phone:</label>
		              		<div class="col-lg-8">
		              			<input type="tel" class="form-control" id="phone" name="phone">
		              		</div>
		              	</div>
		              </div>
		              
		              <button type="reset" class="btn btn-secondary">Reset</button>
		              <button id="submitRegister" type="submit" class="btn btn-primary">Register</button>
		          </div>
		      </form>
		  </div>
		</div>
	</div>
</body>
</html>