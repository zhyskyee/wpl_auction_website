$(document).ready(function() {

	$("#resetbutton").click(
			var username = $("#username").val();
			var password = $("#password").val();

			$.ajax({
				url : "http://localhost:8080/user/forgetpassword",
				type : 'post',
				dataType : 'text',
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify({
					'username' : username,
					'password' : password
				}),
				success : function(data) {
					console.log(data);
					var datajson = JSON.parse(data);
					if (datajson.answer == "Success") {
					alert("successful");
                    window.location.href="http://localhost:8080"; 
					}
				},
				error : function(err) {
					alert("Error loading JS File"
							+ err);
				}
			});
	
	//监听回车键
	$(document).keyup(function(event){
		  if(event.keyCode ==13){
		   $("#submitRegister").trigger("click");
		  }
	});
})