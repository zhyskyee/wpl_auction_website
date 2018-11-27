<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<head>
<title>Register successfully</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="/images/favicon.ico" />
<link href="/css/success.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script> -->
<script type="text/javascript">
	$(function() {
		function jump(count) {
			window.setTimeout(function() {
				count--;
				if (count > 0) {
					$('#second').html(count);
					jump(count);
				} else {
					location.href = "${pageContext.request.contextPath}/";
				}
			}, 1000);
		}
		jump(5);
	})
</script>
</head>
<body>
	<center>
		<br/><br/>
		<div id="div-title">Register Success</div>
		<div id="div-main">
			<font size="5">Congraturation！<b>${username}</b><br> Sign up successfully! <font
				color="red" id="second">5</font> seconds jump to <a href="${pageContext.request.contextPath}/"> Home page </a>！
			</font>
			</div>
		</div>
	</center>
</body>
</html>