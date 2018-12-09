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
  <script type="text/javascript" src="/js/index.js"></script>
</head>

  <body>
    <!-- Navigation -->
    <div class="container">
      <div class="row">
        <form id="forgetpasswordForm">
         <div class="col-lg-12" style=""><h1>Forget password</h1></div>
           <div class="">
              <div class="col-lg-12">
                  <label class="sr-only" for="username">User Name</label>
                   <input type="text" name="username" id="username" value="${username}" class="form-control" placeholder="User Name" required>
              </div>
              <div class="col-lg-12">
                  <label class="sr-only" for="resetpassword">Password</label>
                   <input type="password" name="resetpassword" id="resetpassword" value="${password}" class="form-control" placeholder="Password" required>
              </div>
         </div>
         <button class="btn btn-danger" id="resetbutton">Reset!</button>
      </form>
    </div>
</body>
</html>