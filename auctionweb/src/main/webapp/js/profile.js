$(document).ready(function() {
	var tourl ="http://localhost:8080/user/profile"+"?username=" +localStorage.getItem("username");
	 $.ajax({
		url : tourl,
		type : 'get',
		dataType : 'text',
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			console.log(data);
			var itemjson = JSON.parse(data);
			$("#username").val(itemjson.username);
			$("#email").val(itemjson.email);
			$("#phone").val(itemjson.phone);
           
		},
		error : function(err) {
			alert("Error loading JS File"
					+ err);
		} 
		
	});
	
	var tourl =  "http://localhost:8080/user/profile"+"?username="+ localStorage.getItem("username");
	console.log(tourl);
	var src ="/images/"+ localStorage.getItem("username")+".jpg";
	$("#pic").attr("src", src);
	
	$("#username").val(localStorage.getItem("username"));
	$("#change").click(
			function(e) {
			e.preventDefault();
			var username = $("#username").val();
			var email = $("#email").val();
			var phone = $("#phone").val();
	
	$.ajax({
		url : "http://localhost:8080/user/profile",
		type : 'post',
		dataType : 'text',
		data:JSON.stringify({
			'username' : username,
			'email' : email,
			'phone':phone
		}),
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			console.log(data);
			var itemjson = JSON.parse(data);
	       if (itemjson.answer == "success") {
                   alert("success");
	       }
           
		},
		error : function(err) {
			alert("Error loading JS File"
					+ err);
		} 
		
	});
			})
//    	
//});

	
})